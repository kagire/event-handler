package com.kagire.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class EventMessage {

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    private double delay; //<-- will be snoozed in mq for this value
    private LocalDateTime createdAt;
    private LocalDateTime expireAt; //<-- will expire and will NOT be sent to mq after this threshold
    private LocalDateTime sendAt; //<-- will be sent to mq after this threshold
    private String body;

    public EventMessage(MessageType type, double delay, double expire, double send, String body) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.delay = delay;
        this.createdAt = LocalDateTime.now();
        this.expireAt = LocalDateTime.now().plusSeconds((long) expire);
        this.sendAt = LocalDateTime.now().plusSeconds((long) send);
        this.body = body;
    }

    public EventMessageDTO asDTO() {
        return new EventMessageDTO(type, body, expireAt);
    }

    @Getter
    @AllArgsConstructor
    public static class EventMessageDTO {
        private MessageType type;
        private String body;
        private LocalDateTime expireAt;
    }
}
