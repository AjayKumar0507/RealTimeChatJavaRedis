package com.kash.RealTimeChat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kash.RealTimeChat.domain.model.ChatMessagePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber implements MessageListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Deserialize Redis message
            String messageBody = new String(message.getBody());
            ChatMessagePayload payload = objectMapper.readValue(messageBody, ChatMessagePayload.class);
            // Relay to STOMP clients
            messagingTemplate.convertAndSend("/sub/" + payload.getChatRoomId(), payload);
        } catch (Exception e) {
            System.err.println("Error processing Redis message: " + e.getMessage());
        }
    }
}      