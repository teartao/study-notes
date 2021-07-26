# 基本概念模型

前一节简单认识了mq，了解了它的基本功能和特性，也简单聊了下它的弊端，接下来我们通过一些简单的分析来思考并推断出mq的基本模型。

## 思考分析

根据上一节的介绍，首先从mq的基本功能特性说起。

我们需要做一个阻塞的消息队列中间件，这个中间件需要提供 `IP+端口`来对外提供服务(有点操作系统概念或工作经验的话，你会发现大多数计算机程序都是通过IP+端口提供服务，实现监听/通信等功能)。

### 1. 创建服务端连接

   所以，我们首先需要通过socket 开启一个端口比如5672(后面会发现官方使用的就是这个)。客户端可以通过这个`IP+端口`连接到我们自己的mq。这里服务端可以通过Java的BlockingQueue来实现消息的阻塞接收。

### 2. 创建生产者客户端

   接下来我们的socket服务端程序会监听客户端发来的消息，这个消息可以是文本，可以是二进制(进行了一定的编码/解码)，最熟悉的当然还是http传输json，我们这里暂不考虑http的实现方式，假设就是一段json字符串。这样的话服务端就可以接收某客户端A发来的消息了。

### 3. 创建消费者客户端

   这时，服务端、消息的生产者就都有了。我们再创建一个客户端，连接上自己的mq，并取走前面这个“生产者”发来的消息。这样就实现了 消息从`生产者->服务端->消费者`这个过程。

简单画个草图

<img src="./imgs/01_MyMQModel.png" alt="01_MyMQModel" style="zoom: 80%;" />

### 4. 指定生产消费规则

(后面会发现它叫路由规则)

这时候生产者和消费者都是单一的，如果我们有很多程序都要产生消息怎么办？并且会有很多对应的消费者去消费指定生产者发送来的消息。那么我们就需要定一个生产和消费的规则。

假设这个规则是一段业务描述，比如：

- 订单创建接口生产(发送给mq服务端)的mq消息它的id叫做"创建订单消息"
- 商品新增接口生产的消息id叫做"新增商品消息"
- 用户创建接口生产的消息id叫做"添加用户消息"

然后对应的订单/商品/会员消费者分别去取对应的消息。

消费者为什么能知道拿哪个id？会不会拿错？生产者id瞎写怎么办？这就需要生产者和消费者定一个id的值了，常见的做法是，写个枚举或一个通用类里面写入字符串常量，供大家作为id约定共同使用。

为了方便理解，下面用一些简单的伪代码表达一下大意。

```java
public class MessageKeys{
    public static final String ORDER_CREATE = "创建订单消息";
    public static final String GOODS_CREATE = "新增商品消息";
    public static final String USER_CREATE = "添加用户消息";
}

/*
 *生产者伪代码
 */
public class OrderService{
    public void sendMessage(){
        String createOrderMsg = "{}";// 创建订单的json参数
        orderProducerClient.sendMsg(MessageKeys.ORDER_CREATE , createOrderMsg);
    }
}

public class GoodsService{
    public void sendMessage(){
        String createGoodMsg = "{}";// 新增商品的json参数
        goodsProducerClient.sendMsg(MessageKeys.GOODS_CREATE , createGoodMsg);
    }
}

public class UserService{
    public void sendMessage(){
        String createUserMsg = "{}";// 添加用户的json参数
        userProducerClient.sendMsg(MessageKeys.USER_CREATE , createUserMsg);
    }
}

/**
 * 接下来就是消费者获取mq消息做业务处理了
 */
public class OrderHandleService{
    public void handleOrderMessage(){
        String createOrderMsg = orderConsumerClient.getMsg(MessageKeys.ORDER_CREATE);
        //消费者拿到mq消息做订单创建的业务处理
        OrderParam orderParam = parseJsonStringToObject(createOrderMsg);
        orderService.createOrder(orderParam);        
    }
}
// 后面的伪代码类似，我就不重复写了... 懂大概意思就行 
```

这样，就实现了不同消息的生产和消费者之间通信的路由规则。

如果是多生产者，多消费者也是没有问题的，因为消息是阻塞的，只有一个线程可以拿到消息，那么谁拿到消息就谁来消费，都是可以的。既可以是不同生产者生产相同消息，也可以是不同生产者生产不同参数的消息。消费者也是一样，可以多个一起消费提升系统处理消息的性能。

我们再基于上面的描述画个草图

<img src="./imgs/02_MQ_Msg_ProCon.png" alt="02_MQ_Msg_ProCon" style="zoom:80%;" />





### 5. 优化客户端连接

了解计算机原理的都知道IO相对于CPU 内存的计算是很慢的，而且网络IO大多数时候比磁盘更慢。因此客户端每次向服务器发送/获取消息的时候如果都需要建立服务端连接，再释放，或者都保持长时间连接的话，是非常消耗服务端和客户端资源的，因此我们希望有一个角色可以帮助我们管理连接信息，又能够方便快截地发送消息。于是，在上述模型的基础上还有个channel(信道/通道)的概念。下面我们看两张图把这些概念统一梳理一下。

## rabbitMQ基本概念

### RabbitMQ 基本模型结构图

至此，我们其实已经推断出了RabbitMQ一些基本功能及对象模型，但都是我们自己理解猜测，下面正式看下RabbitMQ的模型架构图

<img src="./imgs/03_RabbitMQ_MODEL.png" alt="03_RabbitMQ_MODEL" style="zoom:80%;" />

RabbitMQ整体上是一个生产者与消费者模型，主要负责接收、存储和转发消息。可以把RabbitMQ想象成邮局。邮箱暂存信件，，邮局负责组织邮递员将信件送至收件人<sup>[[1]](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)</sup>。

生产者：生产并发送消息的程序

消息队列：类似邮箱，在邮局内部用来存储消息

消费者：接收并处理消息的程序。

消费者和生产者不是必须在同一台机器上，当rabbitMQ用在分布式环境时，它们可以在同一台主机上，也可以在不同的主机上。一个程序既可以是生产者，也可以是消费者。

从代码层面来说，某一行代码调用RabbitMQ的api发送消息，它的下一行便可以获取发送出的消息进行处理，只是大多数情况下我们并不会这么使用。



### RabbitMQ编程模型结构图

上面这个基本模型结构图是一个比较常见的架构图，但它只介绍了RabbitMQ的核心概念模型，另外还有一幅常见的模型结构图增加了channel的概念，channel用户维护消费者和生产者的连接。

![04_RabbitMQ_Model](./imgs/04_RabbitMQ_Model.png)

这个图我用英文标注是为了方便大家在接下来的代码中对应到相应的概念模型，也为了方便大家跟上面的中文模型做对比，毕竟很多文档中翻译各不一样，通过英文来说会更加准确点。比如上面这个Channel，有的翻译为信道，也有翻译为通道，对应这张图就可以明确知道RabbitMQ的模型在代码中对应什么概念了。

上面这个图我还额外增加了Exchange的类型：direct fanout headers topic 这些也都是RabbitMQ中默认的交换机类型。也可以在RabbitMQ安装后通过web界面看到。

<img src="./imgs/05_RabbitMQ-web-exchange.png" alt="05_RabbitMQ-web-exchange" style="zoom:80%;" />

Channel：也就是我们上面[思考分析第5节 优化客户端连接](# 5. 优化客户端连接)部分提到的概念。可以理解成类似线程池一样的东西，线程池是为了维护线程，减少创建关闭线程的开销而独立存在。这里的channel也可以理解成为了减少开启关闭服务端连接会话成本而维护的一个连接对象，里面的每个channel就类似一个线程。

VirtualHost ( 虚拟主机 / 虚拟机 )  : RabbitMQ内部还有VirtualHost的概念，这个概念既可以理解为一台新的RabbitMQ服务器，它具备独立的Exchane Queue，也可以理解为命名空间的概念，通过虚拟主机将一台RabbitMQ服务器划分为多个虚拟主机，以便于不同业务，不通应用开发过程中隔离。

比如：公司只有一台服务器安装了RabbtMQ，平台组的项目中订单系统，商品系统使用同一个MQ大家理所当然，因为它们之间有交互，需要通过MQ交换消息实现通信，但是行政部门的OA项目也需要使用MQ，他们的MQ跟我们的电商平台没有任何关联，这时我们就可以创建多个VirtualHost对MQ进行逻辑上的隔离，让不同系统使用不同的虚拟主机进行消息通信。



## 终于等到你：HelloWorld

前面讲了那么多，终于到了RabbitMQ的HelloWorld。直接看demo吧。

生产者：

```java
public class Producer {
    //定义队列名称
    private static final String DEFAULT_QUEUE = "SAMPLE_CH02";

    public static void main(String[] args) throws IOException, TimeoutException {
        //通过连接工厂创建新的连接和mq建立连接
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            //ip地址，我本地装在虚拟机里面，你们装在本机直接使用127.0.0.1即可
            factory.setHost("192.168.1.128");
            //RabbitMQ默认通信端口，页面登录的15672是rabbitmq默认的web管理端口
            factory.setPort(5672);
            //默认的用户名密码都是guest,如果是docker安装，默认密码在安装时候指定，就不一定是这个了
            factory.setUsername("guest");
            factory.setPassword("guest");
            //设置虚拟机，一个mq服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
            //rabbitmq默认虚拟机名称为"/"，虚拟机相当于一个独立的mq服务
            factory.setVirtualHost("/");
            //创建RabbitMQ服务的TCP连接
            connection = factory.newConnection();
            //创建Exchange的会话通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();
            channel.queueDeclare(DEFAULT_QUEUE, true, false, false, null);
            String message = "HelloWorld";
            channel.basicPublish("", DEFAULT_QUEUE, null, message.getBytes());
            System.out.println("生产者发送消息[" + message + "]到RabbitMQ");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            //先关闭通道，因为通道是在connection里面的，所以关闭需要先关闭通道再关闭连接，跟开启顺序正好相反
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
```

消费者

```java
public class Consumer {
    private static final String DEFAULT_QUEUE = "SAMPLE_CH02";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ所在服务器的ip和端口
        factory.setHost("192.168.1.128");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(DEFAULT_QUEUE, true, false, false, null);
        //定义消费方法
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //当接收到消息后，此方法将被调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                //设置交换机 路由key 消息id,这里我们获得了，但是并没有使用，会在后面详细介绍
                String exchange = envelope.getExchange();
                String routingKey = envelope.getRoutingKey();
                //消息id，mq在通道中标识消息的id，可用于确认消息已接收，就是告诉mq哪个消息我接受了，和编程回复相呼应
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("收到消息：" + msg);
            }
        };
        channel.basicConsume(DEFAULT_QUEUE, true, consumer);
        //消费者不需要关闭连接，因为消费者要监听消息队列，保持监听
    }
}
```

然后依次运行Producer便可以看到生产者先生产了消息，再运行Consumer便可以看到消费者接收到了生产者发送的消息了。



### 总结

至此，我们介绍了RabbitMQ的主要模型概念，并且实现了RabbitMQ生产者和消费者的通信。总结一下基本步骤：

**生产者基本步骤：**

1. `ConnectionFactory` 创建连接(IP+Port、用户名/密码、设置VirtualHost )
2. 创建连接`Connection`
3. 获得`Channel`
4. 声明/指定消息队列
5. 发送消息
6. 关闭连接

**消费者基本步骤：**

1. `ConnectionFactory` 创建连接(IP+Port )
2. 获得`channel`
3. 定义消费方法(回复响应)

这里注意：用户名密码和VirtualHost在消费者端不是必需设置的，默认情况下不用设置。



## 最后

至此我们就完成了基础的RabbitMQ生产者和消费者通信实现消息的发&收了。后面我会继续讲RabbitMQ和springBoot、Spring Xml方式的集成，还有一些常见用法(封装常用API)等。



参考文献：

[1] RabbitMQ官网：https://www.rabbitmq.com/
[2] RabbitMQ实战指南 朱忠华




















