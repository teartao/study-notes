package com.neotao.rabbitmq.samples.ch02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author neotao
 */
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