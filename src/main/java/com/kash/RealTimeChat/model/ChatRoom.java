package com.kash.RealTimeChat.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "chatrooms")
public class ChatRoom {
    @Id
    private String id;
    private String name;
    private List<String> memberIds;
}