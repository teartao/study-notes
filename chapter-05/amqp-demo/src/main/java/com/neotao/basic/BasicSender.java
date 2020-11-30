package com.neotao.basic;


import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.neotao.basic")
public class BasicSender {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicSender.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitTemplate.convertAndSend("", "BASIC_FIRST_QUEUE", "-------- a direct msg");

        rabbitTemplate.convertAndSend("BASIC_TOPIC_EXCHANGE", "basic.msg1", "-------- basic.topic.msg1");
        rabbitTemplate.convertAndSend("BASIC_TOPIC_EXCHANGE", "basic.msg2", "-------- basic.topic.msg2");

        rabbitTemplate.convertAndSend("BASIC_FANOUT_EXCHANGE", "", "-------- a fanout msg");


    }
}
