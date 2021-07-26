package com.neotao.amqp.admin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.neotao.amqp")
public class AdminTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdminTest.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);

        // 声明一个交换机
        rabbitAdmin.declareExchange(new DirectExchange("ADMIN_EXCHANGE", false, false));

        // 声明一个队列
        rabbitAdmin.declareQueue(new Queue("ADMIN_QUEUE", false, false, false));

        // 声明一个绑定
        rabbitAdmin.declareBinding(new Binding("ADMIN_QUEUE", Binding.DestinationType.QUEUE,
                "ADMIN_EXCHANGE", "admin", null));

    }
}
