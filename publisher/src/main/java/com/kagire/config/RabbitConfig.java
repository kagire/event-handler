package com.kagire.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJavaTypeMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Value("${rabbit.exchange}")
    private String exchange;
    @Value("${rabbit.queue}")
    private String eventQueue;
    @Value("${rabbit.routingKey}")
    private String routingKey;

    @Bean
    CustomExchange directExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(exchange, "x-delayed-message", true, false, args);
    }

    @Bean(name = "eventQueue")
    Queue eventQueue() {
        return new Queue(eventQueue, true);
    }

    @Bean
    Binding eventBinding(@Qualifier("eventQueue") Queue queue, CustomExchange exchange) {
        return BindingBuilder
            .bind(queue)
            .to(exchange)
            .with(routingKey)
            .noargs();
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        final var jsonRabbitTemplate = new RabbitTemplate(connectionFactory);
        var converter = new JacksonJsonMessageConverter();
        converter.setTypePrecedence(JacksonJavaTypeMapper.TypePrecedence.INFERRED);
        jsonRabbitTemplate.setMessageConverter(converter);
        return jsonRabbitTemplate;
    }
}
