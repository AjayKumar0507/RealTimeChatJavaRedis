package com.kash.RealTimeChat.repository;


import com.kash.RealTimeChat.domain.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, Long> {

    Optional<ChatRoom> findById(Long id);
}
