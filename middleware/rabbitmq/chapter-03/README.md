# rabbitMQ在项目中配置

## rabbitMQ在springboot中的配置

这部分配置其实很简单，springboot已经在start中封装了很多基础配置和api，我们主要配置如下方面即可：

1. pom.xml引入rabbitMQ

2. application.yml/application.properties中添加rabbitMQ连接信息

3. 配置常用的交换机，队列，并将队列绑定至交换机

4. 发送消息至指定交换机

   

### 1. 添加maven依赖

```xml
<!-- springboot的starter中帮我们管理好了jar包的依赖，只需要添加一个starter即可 -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>

<!-- 项目需要以web服务形式运行，启动常开的消费者监听消息队列，因此加入web的starter -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- 为了测试，我还添加了spring test 和junit 根据自己项目需要，可以不添加 -->
 <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-logging</artifactId>
</dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
  <exclusions>
    <exclusion>
      <groupId>org.junit.vintage</groupId>
      <artifactId>junit-vintage-engine</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.springframework.amqp</groupId>
  <artifactId>spring-rabbit-test</artifactId>
  <scope>test</scope>
</dependency>

<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.13</version>
  <scope>test</scope>
</dependency>

```
### 2. 添加rabbitMQ连接配置

```yaml
server:
  port: 3003
spring:
  application:
    name: rabbitmq-producer
  rabbitmq:
    host: 192.168.1.128
    port: 5672
    username: guest
    password: guest
    virtual-host: /
```



### 3.  基础bean配置

```java
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author neotao
 */
@Configuration
public class RabbitMqConfig {

    public static final String CH03_DIRECT_EXCHANGE = "CH03_DIRECT_EXCHANGE";
    public static final String CH03_QUEUE_NAME = "CH03_QUEUE_NAME";
    public static final String CH03_ROUTING_KEY = "CH03_ROUTING_KEY";


    /**
     * 声明新的直连交换机
     *
     * @return
     */
    @Bean
    public Exchange directExchange() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.directExchange(CH03_DIRECT_EXCHANGE).durable(true).build();
    }

    /**
     * 声明新的队列
     *
     * @return
     */
    @Bean
    public Queue directQueue() {
        return new Queue(CH03_QUEUE_NAME);
    }

    /**
     * 将消息队列和交换机绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding defaultExchangeQueueBind(@Qualifier(value = "directQueue") Queue queue,
                                            @Qualifier(value = "directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CH03_ROUTING_KEY).noargs();
    }

}
```



消费者线程（监听消息队列），通过spring提供的`@RabbitListener`可以让spring容器自动监听消息队列，当队列中有消息时，自动调用我们的`handleDirectMsg()`方法

```java
@Component
public class Consumer {
    @RabbitListener(queues = {RabbitMqConfig.CH03_QUEUE_NAME})
    public void handleDirectMsg(String msg, Message message, Channel channel) {
        System.out.println("收到消息" + msg);
    }
}
```



生产者线程，由于生产者可以是系统中接口/服务，也可以是其它项目中某个接口，因此我这里以单元测试运行，用来模拟生产者不断发消息的过程。每次随机停顿0-1秒，连续发100条，以便于在rabbitMQ的web管理界面看到生产和消费折线图。

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testDirect() throws InterruptedException {
        String msg = "default msg send to direct exchange message";
        int i = 100;
        while (i > 0) {
            //routing key 对应 队列的名称
            rabbitTemplate.send(RabbitMqConfig.CH03_QUEUE_NAME, new Message(("[MSG]" + i + msg).getBytes(), new MessageProperties()));
            //复杂消息建议使用convertAndSend方法，通过MessageProperties配置消息参数来发送
            //rabbitTemplate.convertAndSend()
            i--;
            TimeUnit.MILLISECONDS.sleep(new Double(Math.random() * 1000).intValue());
        }
    }
}
```



### 4. 生产者和消费者测试

先启动springboot项目，项目中包含一个`@Component`注解自动注入的消费者bean。此时还没有消息产生。

然后启动单元测试，单元测试是一个生产者，这个生产者会发送多条消息到mq服务器。这时，由于项目中消费者监听了服务器队列，于是便可以不断收到消息了，通过mq的管理界面也可以看到消息的生产和消费折线图。



## RabbitMQ在xml中配置

还是从简单的direct交换机来说



1. 导入pom依赖
2. 创建生产者
3. 创建消费者
4. 配置xml中生产者、消费者、直连交换机、队列，并将队列和交换机绑定
5. 启动测试

### 1. 添加maven依赖

```xml

    <properties>
        <!-- spring版本号 -->
        <spring.version>5.1.0.RELEASE</spring.version>
        <!-- log4j日志文件管理包版本 -->
        <slf4j.version>1.6.6</slf4j.version>
        <log4j.version>1.2.12</log4j.version>
        <!-- junit版本号 -->
        <junit.version>4.10</junit.version>
    </properties>

    <dependencies>
        <!-- spring 肯定是必须的 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!--rabbitmq依赖 -->
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit</artifactId>
            <version>1.3.5.RELEASE</version>
        </dependency>
        <!-- 日志文件管理包 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>

        <!--单元测试依赖 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```



### 2. 创建生产者

```java
/**
 * direct交换机发送消息
 */
@Service
public class Producer01 {
    private Logger logger = LoggerFactory.getLogger(Producer01.class);

    /**
     * amqpTemplate direct 交换机
     */
    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    /**
     * 一个direct交换机换机的发送例子
     *
     * @param message
     */
    public void sendMessage(Object message) {
        logger.info("发送消息:" + message);

        // Exchange 为 direct 模式，直接指定routingKey
        amqpTemplate.convertAndSend("SAMPLE_CH03_XML_MSG_01_KEY", 
 					"[Direct交换机：SAMPLE_CH03_XML_MSG_01_KEY] " + message);
    }
}
```



### 3. 创建消费者
```java
public class Consumer01 implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(Consumer01.class);

    public void onMessage(Message message) {
        logger.info("消费者01 接收消息 : " + new String(message.getBody()));
    }
}
```



### 4. 配置生产者、消费者、队列、交换机、并绑定

```xml
<!--配置connection-factory，指定连接rabbit server参数 -->
<rabbit:connection-factory id="connectionFactory" virtual-host="/" 
username="guest" password="guest" host="127.0.0.1" port="5672"/>

<!--通过指定下面的admin信息，自动创建exchange和queue -->
<rabbit:admin id="connectAdmin" connection-factory="connectionFactory"/>

<!--  开始定义队列了。这里的队列主要用于发消息，后面的消费者也可以监听这个队列，
实现指定队列消息的收、发 -->
<!--定义queue -->
<rabbit:queue name="XML_DEMO_QUEUE01" declared-by="connectAdmin"/>

<!--定义direct交换机,01 02 都用这个交换机 -->
<rabbit:direct-exchange name="DIRECT_EXCHANGE_01_02" declared-by="connectAdmin">
    <rabbit:bindings>
        <rabbit:binding queue="XML_DEMO_QUEUE01" key="SAMPLE_CH03_XML_MSG_01_KEY">
        </rabbit:binding>
    </rabbit:bindings>
</rabbit:direct-exchange>

<!--定义rabbitTemplate用于数据的接收和发送 -->
<rabbit:template id="amqpTemplate" connection-factory="connectionFactory" exchange="DIRECT_EXCHANGE_01_02"/>

<!--第一个消费者,并监听上面的直连交换机声明的队列 -->
<bean id="consumer01" class="com.neotao.rabbitmq.samples.ch03.consumer.Consumer01"/>

<!-- 定义监听器，监视rabbitMQ服务端队列queue，当有消息到达时会通知监听在对应的队列上的监听对象 -->
<rabbit:listener-container connection-factory="connectionFactory">
    <rabbit:listener queues="XML_DEMO_QUEUE01" ref="consumer01"/>
</rabbit:listener-container>
```



### 5. 创建启动程序，加载/运行消费者、生产者

```java
/**
 * 容器启动程序，类似于项目中的tomcat
 * xml demo生产者测试
 *
 * @author neotao
 */
public class ApplicationCh03XmlTest {

    private static ApplicationContext context = null;

    public static void sendMessage() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Producer01 producer01 = (Producer01) context.getBean("producer01");
        int k = 20;
        while (k > 0) {
            producer01.sendMessage("01生产者。第" + k + "次发送的消息");
            k--;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        sendMessage();
    }
}
```



## 结束语

至此 springboot 、xml 项目配置rabbitMQ 便完成了。

将两种方式结合起来看，虽然形式不一样，但是做的事情还是一样的：

1. 配置连接信息
2. 创建交换机
3. 创建队列
4. 绑定队列和交换机
5. 创建生产者，(指定路由键/队列名)发送消息到指定队列
6. 创建消费者，监听队列，收到队列中发来的(特定路由键的)消息



目前的三个demo都还是很简单的直连交换机，都是1-1 对应的消息，还没有正式进入rabbitMQ功能介绍。下一节讲一下RabbitMQ不同交换机类型的消息发送与接收，并通过它们的特点分析一些常见场景。