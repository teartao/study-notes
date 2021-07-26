package com.neotao.dlx.delayplugin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neotao
 * @date 2020/11/13 上午11:24
 */
@Configuration
public class DelayPluginConfig {
    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri("amqp://guest:guest@127.0.0.1:5672");
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean("delayExchange")
    public TopicExchange exchange() {
        Map<String, Object> argss = new HashMap<String, Object>();
        argss.put("x-delayed-type", "direct");
        return new TopicExchange("DELAY_EXCHANGE", true, false, argss);
    }

    @Bean("delayQueue")
    public Queue deadLetterQueue() {
        return new Queue("DELAY_QUEUE", true, false, false, new HashMap<>());
    }

    @Bean
    public Binding bindingDead(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") TopicExchange exchange) {
        // 无条件路由
        return BindingBuilder.bind(queue).to(exchange).with("#");
    }

}
