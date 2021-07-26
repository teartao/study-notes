package com.neotao.dlx.ttl;


import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author neotao
 * @date 2020/11/13 上午11:24
 */
@ComponentScan(basePackages = "com.neotao.dlx.ttl")
public class DlxSender {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DlxSender.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        // 随队列的过期属性过期，单位ms
        rabbitTemplate.convertAndSend("ORI_USE_EXCHANGE", "ori.use.msg", "测试死信消息");
    }
}
