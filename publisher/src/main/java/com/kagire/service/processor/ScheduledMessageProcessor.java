package com.kagire.service.processor;

import com.kagire.service.broker.RabbitSender;
import com.kagire.service.db.EventMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.kagire.model.MessageType.EXPIRING;

@Component
@RequiredArgsConstructor
public class ScheduledMessageProcessor {

    private final EventMessageRepository eventMessageRepository;
    private final RabbitSender rabbitSender;

    @Scheduled(fixedRate = 3_000)
    @Transactional
    public void processMessages() {
        eventMessageRepository.findAll().forEach(eventMessage -> {
            LocalDateTime now = LocalDateTime.now();

            if (EXPIRING.equals(eventMessage.getType()) && now.isAfter(eventMessage.getExpireAt())) {
                System.out.println("message EXPIRED: " + eventMessage.getBody());
                eventMessageRepository.delete(eventMessage);
                return;
            }

            if (now.isAfter(eventMessage.getSendAt())) {
                System.out.println("message SENT: " + eventMessage.getBody());
                rabbitSender.sendEventMessage(eventMessage);
                eventMessageRepository.delete(eventMessage);
            }
        });
    }
}
