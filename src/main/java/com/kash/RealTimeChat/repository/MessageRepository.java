package com.kash.RealTimeChat.repository;

import com.kash.RealTimeChat.model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<Messages, String> {
    List<Messages> findByRecipientIdOrGroupId(String recipientId, String groupId);


    @Query("{ $or: [ { 'senderId': ?0, 'recipientId': ?1 }, { 'senderId': ?1, 'recipientId': ?0 } ] }")
    List<Messages> findPrivateMessagesBetweenUsers(String senderId, String recipientId);


    List<Messages> findByGroupId(String groupId);

}