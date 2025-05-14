package com.kash.RealTimeChat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "messages")
public class Messages {
    @Id
    private String id;
    private String senderId;
    private String recipientId;
    private String groupId;
    private String content;
    private boolean delivered;
    private boolean read;
}