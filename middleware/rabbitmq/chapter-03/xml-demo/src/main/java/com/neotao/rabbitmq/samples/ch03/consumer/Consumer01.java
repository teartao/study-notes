package com.neotao.rabbitmq.samples.ch03.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;


/**
 * @author neotao
 */
public class Consumer01 implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(Consumer01.class);

    public void onMessage(Message message) {
        logger.info("消费者01 接收消息 : " + new String(message.getBody()));
    }
}