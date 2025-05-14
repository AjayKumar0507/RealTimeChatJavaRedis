package com.kash.RealTimeChat.repository;


import com.kash.RealTimeChat.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
}