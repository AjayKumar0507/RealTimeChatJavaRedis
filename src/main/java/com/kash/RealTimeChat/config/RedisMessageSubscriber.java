package com.kash.RealTimeChat.config;


import com.kash.RealTimeChat.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // Handle Redis messages if needed
        // For now, WebSocket handles most real-time communication
    }
}