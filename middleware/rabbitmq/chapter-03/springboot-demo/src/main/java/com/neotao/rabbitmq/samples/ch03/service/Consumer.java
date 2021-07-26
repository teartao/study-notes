package com.neotao.rabbitmq.samples.ch03.service;

import com.neotao.rabbitmq.samples.ch03.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ消费者线程，监听mq服务器
 *
 * @author neotao
 */
@Component
public class Consumer {

    @RabbitListener(queues = {RabbitMqConfig.CH03_QUEUE_NAME})
    public void handleDirectMsg(String msg, Message message, Channel channel) {
        System.out.println("收到消息" + msg);
    }
}
