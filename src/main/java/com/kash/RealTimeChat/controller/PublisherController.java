package com.kash.RealTimeChat.controller;

import com.kash.RealTimeChat.domain.model.ChatMessagePayload;
import com.kash.RealTimeChat.redis.RedisPublisher;
import com.kash.RealTimeChat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PublisherController {

    @Autowired
    private RedisPublisher redisPublisher;

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/join")
    @SendTo("/sub/{chatRoomId}")
    public ChatMessagePayload handleJoin(@Payload ChatMessagePayload message) {
        String topic = message.getChatRoomId().toString();
        redisPublisher.publish(ChannelTopic.of(topic), message);
        return message; // Relayed to STOMP clients
    }

    @MessageMapping("/chat/message")
    @SendTo("/sub/{chatRoomId}")
    public ChatMessagePayload sendMessage(@Payload ChatMessagePayload message) {
        String topic = message.getChatRoomId().toString();
        redisPublisher.publish(ChannelTopic.of(topic), message);
        return message; // Relayed to STOMP clients
    }
}