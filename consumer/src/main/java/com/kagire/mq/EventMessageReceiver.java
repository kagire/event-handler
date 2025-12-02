package com.kagire.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Component
public class EventMessageReceiver {

    private static final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "${rabbit.queue}")
    public void receive(Message<byte[]> message) {
        EventMessage event = mapper.readValue(message.getPayload(), EventMessage.class);
        boolean isExpired = "EXPIRING".equals(event.type) && LocalDateTime.now().isAfter(event.expireAt);
        System.out.println("Event: " + event.body + (isExpired ? ", but it expired" : ""));
    }

    record EventMessage (
        String body,
        String type,
        LocalDateTime expireAt
    ) {}
}
