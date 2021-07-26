package com.neotao.ttl;

import com.neotao.util.ResourceUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
 * 消息存活时间TTL
 *
 * @author neotao
 * @date 2020/11/13 上午11:24
 */
@Configuration
public class TtlConfig {
    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
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

    @Bean("ttlQueue")
    public Queue queue() {
        Map<String, Object> map = new HashMap<String, Object>();
        // 队列中的消息未被消费11秒后过期
        map.put("x-message-ttl", 11000);
        // 队列30秒没有使用以后会被删除
        // map.put("x-expire", 30000);
        return new Queue("TTL_QUEUE", true, false, false, map);
    }

    @Bean("expireQueue")
    public Queue expireQueue() {
        Map<String, Object> map = new HashMap<String, Object>();
        // 队列30秒没有使用以后会被删除
        map.put("x-expire", 30000);
        return new Queue("EXPIRE_QUEUE", true, false, false, map);
    }

    @Bean("ttlExchange")
    public DirectExchange exchange() {
        return new DirectExchange("TTL_EXCHANGE", true, false, new HashMap<>());
    }

    @Bean
    public Binding binding(@Qualifier("ttlQueue") Queue queue, @Qualifier("ttlExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("ttl.msg");
    }

}