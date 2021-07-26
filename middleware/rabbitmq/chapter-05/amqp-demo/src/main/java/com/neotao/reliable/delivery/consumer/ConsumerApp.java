package com.neotao.reliable.delivery.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 消息投递的可靠性保证
 *
 * @author neotao
 * @date 2020/11/13 上午11:24
 */
@ComponentScan(basePackages = "com.neotao.reliable.delivery.consumer")
public class ConsumerApp {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(ConsumerApp.class);
    }
}