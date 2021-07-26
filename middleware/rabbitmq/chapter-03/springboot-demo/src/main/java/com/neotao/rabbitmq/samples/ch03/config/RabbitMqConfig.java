package com.neotao.rabbitmq.samples.ch03.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author neotao
 */
@Configuration
public class RabbitMqConfig {

    public static final String CH03_DIRECT_EXCHANGE = "CH03_DIRECT_EXCHANGE";
    public static final String CH03_QUEUE_NAME = "CH03_QUEUE_NAME";
    public static final String CH03_ROUTING_KEY = "CH03_ROUTING_KEY";


    /**
     * 声明新的直连交换机
     *
     * @return
     */
    @Bean
    public Exchange directExchange() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.directExchange(CH03_DIRECT_EXCHANGE).durable(false).build();
    }

    /**
     * 声明新的队列
     *
     * @return
     */
    @Bean
    public Queue directQueue() {
        return new Queue(CH03_QUEUE_NAME);
    }

    /**
     * 将消息队列和交换机绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding defaultExchangeQueueBind(@Qualifier(value = "directQueue") Queue queue,
                                            @Qualifier(value = "directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CH03_ROUTING_KEY).noargs();
    }

}