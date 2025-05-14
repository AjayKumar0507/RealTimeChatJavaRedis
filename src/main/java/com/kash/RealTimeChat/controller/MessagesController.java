package com.kash.RealTimeChat.controller;

import com.kash.RealTimeChat.model.Messages;
import com.kash.RealTimeChat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/messages")
@CrossOrigin(origins = "https://cwave.netlify.com")
public class MessagesController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/history")
    public List<Messages> getChatHistory(@RequestParam String userId) {
        System.out.println("Fetching chat history for userId: " + userId);
        List<Messages> messages = messageRepository.findByRecipientIdOrGroupId(userId, userId);
        System.out.println("Returning chat history: " + messages);
        return messages;
    }
}