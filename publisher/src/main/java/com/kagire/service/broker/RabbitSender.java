package com.kagire.service.broker;

import com.kagire.model.EventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitSender {

    @Value("${rabbit.exchange}")
    private String exchange;
    @Value("${rabbit.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendEventMessage(EventMessage eventMessage) {
        rabbitTemplate.convertAndSend(
            exchange, routingKey,
            eventMessage.asDTO(),
            message -> {
                message.getMessageProperties().setHeader("x-delay", eventMessage.getDelay() * 1000);
                message.getMessageProperties().setHeader("Content-Type", "json");
                return message;
            });
    }
}
