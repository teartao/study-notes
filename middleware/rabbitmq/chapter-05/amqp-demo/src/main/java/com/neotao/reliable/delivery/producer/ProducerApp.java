package com.neotao.reliable.delivery.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;

/**
 * 消息投递的可靠性保证
 *
 * @author neotao
 * @date 2020/11/13 上午11:24
 */
@ComponentScan(basePackages = "com.neotao.reliable.delivery.producer")
public class ProducerApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProducerApp.class);

        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitAdmin.declareExchange(new DirectExchange("RELIABLE_SEND_EXCHANGE", true, false, new HashMap<>()));

        MessageProperties messageProperties = new MessageProperties();
        // 消息持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("持久化消息".getBytes(), messageProperties);

        rabbitTemplate.send("RELIABLE_SEND_EXCHANGE", "reliable.msg", message, new CorrelationData("2020112701"));
        rabbitTemplate.send("RELIABLE_SEND_EXCHANGE", "reliable.msg.wrong", message, new CorrelationData("2020112702"));
    }
}
