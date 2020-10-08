package com.neotao.rabbitmq.samples.ch02;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author neotao
 */
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
            /**
             *
             * @param consumerTag:用来表示消费者，在监听队列时设置channel.basicConsume
             * @param envelope:信封，通过envelope
             * @param properties 消息属性
             * @param body 消息内容
             * @throws IOException
             */
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
        /*
         * 参数明细：
         * queue：队列名称
         * autoAck：自动回复，当消费者接收到消息后要告诉mq消息已接收，如果将此参数设置为true
         表示会自动回复mq，如果设置为false要通过编程实现回复
         * 如果不回复则消息一直存在于消息队列中，也就是说消费者一旦过来它会一直给你发
         * callback：消费方法，当消费者接收到消息要执行的方法，回调方法
         */
        //String queue, boolean autoAck, Consumer callback
        channel.basicConsume(DEFAULT_QUEUE, true, consumer);
        //消费者不需要关闭连接，因为消费者要监听消息队列，保持监听
    }
}