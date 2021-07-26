package com.neotao.rabbitmq.samples.ch03;

import com.neotao.rabbitmq.samples.ch03.config.RabbitMqConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testDirect() throws InterruptedException {
        String msg = "default msg send to direct exchange message";
        int i = 100;
        while (i > 0) {
            //routing key 对应 队列的名称
            rabbitTemplate.send(RabbitMqConfig.CH03_QUEUE_NAME, new Message(("[MSG]" + i + msg).getBytes(), new MessageProperties()));
            //复杂消息建议使用convertAndSend方法，通过MessageProperties配置消息参数来发送
            //rabbitTemplate.convertAndSend()
            i--;
            TimeUnit.MILLISECONDS.sleep(new Double(Math.random() * 1000).intValue());
        }
    }
}