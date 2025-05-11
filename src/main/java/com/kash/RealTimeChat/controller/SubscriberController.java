package com.kash.RealTimeChat.controller;

import com.kash.RealTimeChat.domain.model.ChatRoom;
import com.kash.RealTimeChat.redis.RedisSubscriber;
import com.kash.RealTimeChat.repository.ChatMessageRepository;
import com.kash.RealTimeChat.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/chat")
public class SubscriberController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private RedisSubscriber redisSubscriber;

    @Autowired
    private RedisMessageListenerContainer redisMessageListener;

    @PostMapping
    public ChatRoom createChatRoom(@RequestParam String name) {
        ChatRoom newChatRoom = ChatRoom.getBuilder()
                .withName(name)
                .build();
        ChannelTopic topic = ChannelTopic.of(newChatRoom.getId().toString());
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        return chatRoomRepository.save(newChatRoom);
    }

    @GetMapping
    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    @GetMapping(path = "/rooms/{chatRoomId}/messages")
    public ResponseEntity<?> getChatMessages(@PathVariable("chatRoomId") Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
        if (chatRoom != null) {
            return new ResponseEntity<>(chatRoom.getMessages(), HttpStatus.OK);
        }
        log.error("Error message - chat room not found with id: {}", chatRoomId);
        return new ResponseEntity<>("Error message - chat room not found", HttpStatus.NOT_FOUND);
    }
}