# RabbitMQ可靠性投递

本节主要讨论一些复杂情况下的MQ问题。通过一些异常情况和方案来确保消息正确投递。

## MQ和DB操作的一致性问题

确保MQ消息和数据库一致是消息可靠性的要求之一。下面列出一些常见的异常场景进行分析。

- 先投递消息再更新数据库
- 先更新数据库再投递消息

**先投递消息再更新数据库**：当消息投递成功而数据库连接失败时，操作mq回滚可能比较麻烦。因此建议**先操作数据库再发送mq**，利用数据库的事务来回滚数据。这样，先操作数据库暂不提交，等待mq发送成功再提交，可确保数据库和mq一致。一旦mq操作出现失败情况，而此时数据库尚未提交，直接根据mq失败的异常，通过数据库事务控制回滚即可。

**拓展：**该思路依然适用于一些其它类似场景，如：redis缓存和数据库操作的问题，先操作数据库，再操作缓存，在缓存失败时对数据库进行回滚。

其实可以把mq redis 等(需要和数据库保持一致的)中间件都看做缓存，甚至文件的读写等IO操作都可以视作缓存。将数据库放在前面操作是利用了数据库事务回滚方便的特性，可以在后续操作出现异常时回滚，从而保证一致性。(可以继续引申出分布式事务的问题，这里暂不讨论)。



## MQ的确认机制

在mq消息发送过程中，可能存在网络或其它异常情况导致消息发送失败。RabbitMQ提供了确认机制。即：生产者发送了消息给MQ服务端之后，服务端会给生产者一个应答，只要生产者收到了应答，便证明服务端收到了消息。

确认机制有两种：

- 事务模式
- 确认模式

**事务模式**

和数据库类似，在发送消息前开启事务，结束后提交事务。

只要提交成功便可以确保消息投递到了MQ服务端。

事务模式可以通过`channel.txSelect()`来开启,`channel.txCommit()`来提交。如果中途出现了错误，通过`channel.txRollback()`来回滚。springboot中可以通过`rabbitTemplate.setChannelTransacted(true)`来开启事务。

缺点：channel是阻塞的，未提交时无法发送下一条消息，会影响投递性能。（这一点和数据库也类似，没有提交之前，和事务相关的锁定操作都不允许执行），一般不建议使用该模式。

**确认模式：**

确认模式又分了三种：

- 普通确认模式：发一条确认一条；缺点：单条效率低。
- 批量确认：发了多条一起确认；缺点：不同场景需求不同，无法准确估量一次确认的数量。
- 异步确认：发送消息后，一个独立的确认线程(Listener)专门用来处理未确认的消息。[推荐]

异步原为`ConfirmListener` , RabbitTemplate对channel做了封装，具体用法如下：

```java
rabbitTemplate.setConfirmCallback(
  new RabbitTemplate.ConfirmCallback() {
    @Override
    public void confirm(CorrelationData correlationData, 
                        boolean ack, String cause) {
      if (!ack) {
        System.out.println("发送消息失败：" + cause);
        throw new RuntimeException("发送异常：" + cause);
      }
    }
  }
);
```



## 投递失败的消息处理

消息可能由于路由键设置错误，或队列配置错误等，无法投递成功。

常见处理方式有：重新投递；将消息路由到备份交换机。这里主要说下备份交换机的设置。

`channel.basicPublish`有两个参数`mandatory`和`immediate`

```java
//发送消息
channel.basicPublish(EXCHANGE NAME, "", true,
                     MessageProperties.PERSISTENT TEXT PLAIN,
                     "mandatory Msg".getBytes());

//投递失败后返回
channel.addReturnListener(new ReturnListener()(
    public void handleReturn ( int replyCode, String replyText,String exchange, 
                              String routingKey, AMQP.BasicProperties basicProperties ,
                              byte[] body) throws IOException{
      String message = new String(body);
      System.out.println("返回: " + message);
    }
});
```

Spring AMQP中配置方式如下

```java
rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((RabbitTemplate.ReturnCallback) (message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("回发的消息：");
            System.out.println("replyCode: " + replyCode);
            System.out.println("replyText: " + replyText);
            System.out.println("exchange: " + exchange);
            System.out.println("routingKey: " + routingKey);
        });
```

创建交换机时添加备份交换机。

```java
Map<String,Object> arguments = new HashMap<String,Object>();
arguments.put("alternate-exchange","ALTERNATE_EXCHANGE"); // 指定交换机的备份交换机
channel.exchangeDeclare("TEST_EXCHANGE","topic", false, false, false, arguments);
```



## 未被消费的消息处理

如果消息在队列中长期未被消费，而此时出现了宕机等异常关闭的情况，数据便会丢失。有经验的同学肯定能想到应该将数据持久化到磁盘。RabbitMQ自然提供了该功能。配置方法如下：

```java
// 设置消息参数为持久化
MessageProperties messageProperties = new MessageProperties();
messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
Message message = new Message("消息持久化".getBytes(), messageProperties);

//设置队列持久化
@Bean("persistentQueue")
public Queue persistentQueue() {
    // queueName, durable, exclusive, autoDelete, Properties
    // 直接设置durable参数为true即可
    return new Queue("PERSISTENT_QUEUE", true, false, false, new HashMap<>());
}

//设置交换机持久化
@Bean("persistentExchange")
public DirectExchange persistentExchange() {
    // exchangeName, durable, exclusive, autoDelete, Properties
    return new DirectExchange("PERSISTENT_EXCHANGE", true, false, new HashMap<>());
}
```



## 消费过程中出现异常

消息已经正常投递，且被正常接收用于消费。而在消费过程中出现了异常，该消息未被正确处理时。

这种情况下，通常是按需重新发送给消费者进行消费。rabbitMQ提供了“消费者确认”机制，同前文提到的服务端确认类似，此处由消费者发送确认消息给服务端。设置方法如下：

```properties
# springboot设置,参数值可选：
# NONE：自动ACK
# MANUAL：手动ACK
# AUTO：无异常，则发送ack。
spring.rabbitmq.listener.direct.acknowledge-mode=manual
spring.rabbitmq.listener.simple.acknowledge-mode=manual
```



## 消息补偿

假设上述ack配置后，由于业务处理较长或网络情况，服务端迟迟没有收到ack怎么办？

常见的是按业务需要设定/约定一个足够长的时间，当超过该时间进行一次重新投递。



## 幂等性

假设上述的重新投递只是慢，A消息在B重新投递后，依然正确发送了ack，则导致处理了两次同样的消息。因此可以对A B...重复发送的消息做校验/锁定/生成唯一ID等操作，避免重复处理相同消息。



## 集群

前面讲到了消息未被消费的情况，假设消息在持久化过程中出现了故障怎么办？常见做法是对RabbitMQ做集群处理。



## 总结

异常情况还有很多，这里只列出了一些常见的异常情况，并提供了一些相对简单成熟的处理思路。在具体生产环境中，根据业务的复杂程度，数据一致性、可靠性要求的不同，还有其它更多的复杂情况，就得根据实际需要做特定处理了，具体问题还得具体分析。

