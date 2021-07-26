package com.neotao.ttl;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author neotao
 * @date 2020/11/13 上午11:24
 */
@ComponentScan(basePackages = "com.neotao.ttl")
public class TtlSender {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TtlSender.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("4000"); // 消息的过期属性，单位ms
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message("这条消息4秒后过期".getBytes(), messageProperties);
        rabbitTemplate.send("TTL_EXCHANGE", "ttl.msg", message);

        // 随队列的过期属性过期，单位ms
        rabbitTemplate.convertAndSend("TTL_EXCHANGE", "ttl.msg", "这条消息");

    }
}
