package com.kash.RealTimeChat.service;

import com.kash.RealTimeChat.model.Messages;
import com.kash.RealTimeChat.repository.MessageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    public void saveMessage(Messages message) {
        System.out.println("Saving message: " + message);
        messageRepository.save(message);
    }

    public void updateMessageStatus(Messages message) {
        System.out.println("Updating message status: " + message);
        messageRepository.save(message);
    }

    public void markMessageAsRead(String messageId) {
        messageRepository.findById(messageId).ifPresent(message -> {
            message.setRead(true);
            messageRepository.save(message);
            System.out.println("Message marked as read: " + messageId);
        });
    }

    public List<Messages> getPrivateMessages(String senderId, String recipientId) {
        return messageRepository.findPrivateMessagesBetweenUsers(senderId, recipientId);
    }

    public List<Messages> getGroupMessages(String groupId) {
        return messageRepository.findByGroupId(groupId);
    }


}