package com.kash.RealTimeChat.repository;


import com.kash.RealTimeChat.domain.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoomId(Long id);
}
