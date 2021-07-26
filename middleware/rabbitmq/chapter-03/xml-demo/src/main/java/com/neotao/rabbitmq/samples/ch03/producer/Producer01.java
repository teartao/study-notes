package com.neotao.rabbitmq.samples.ch03.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 生产者
 *
 * @author neotao
 */
@Service
public class Producer01 {
    private Logger logger = LoggerFactory.getLogger(Producer01.class);

    /**
     * amqpTemplate direct 交换机
     */
    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;


    /**
     * 一个direct交换机换机的发送例子
     *
     * @param message 待发送到消息
     */
    public void sendMessage(Object message) {
        logger.info("发送消息:" + message);

        // Exchange 为 direct 模式，直接指定routingKey
        amqpTemplate.convertAndSend("SAMPLE_CH03_XML_MSG_01_KEY", "[Direct交换机：SAMPLE_CH03_XML_MSG_01_KEY] " + message);

        // amqpTemplate.convertAndSend("SAMPLE_CH03_XML_MSG_02_KEY", "[Direct : SAMPLE_CH03_XML_MSG_02_KEY] " + message);

    }
}

