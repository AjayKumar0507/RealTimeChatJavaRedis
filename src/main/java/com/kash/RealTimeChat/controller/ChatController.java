package com.kash.RealTimeChat.controller;

import com.kash.RealTimeChat.model.Messages;
import com.kash.RealTimeChat.service.ChatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://cwave.netlify.com")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/private")
    public void sendPrivateMessage(@Payload Messages message) {
        System.out.println("Received private message: " + message);
        if (message.getRecipientId() == null || message.getRecipientId().isEmpty()) {
            System.out.println("Invalid recipientId: " + message.getRecipientId());
            return;
        }
        try {
            chatService.saveMessage(message);
            System.out.println("Sending to recipient: " + message.getRecipientId());
            messagingTemplate.convertAndSendToUser(
                    message.getRecipientId(), "/queue/private", message);
            System.out.println("Sending to sender: " + message.getSenderId());
            messagingTemplate.convertAndSendToUser(
                    message.getSenderId(), "/queue/private", message);
            message.setDelivered(true);
            chatService.updateMessageStatus(message);
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }

    @MessageMapping("/group")
    public void sendGroupMessage(@Payload Messages message) {
        System.out.println("Received group message for group " + message.getGroupId() + ": " + message);
        if (message.getGroupId() == null || message.getGroupId().isEmpty()) {
            System.out.println("Invalid groupId: " + message.getGroupId());
            return;
        }
        try {
            chatService.saveMessage(message);
            System.out.println("Sending group message to /topic/group/" + message.getGroupId());
            messagingTemplate.convertAndSend("/topic/group/" + message.getGroupId(), message);
        } catch (Exception e) {
            System.out.println("Failed to send group message: " + e.getMessage());
        }
    }

    @MessageMapping("/chat/typing")
    public void typingIndicator(@Payload TypingIndicator indicator) {
        System.out.println("Typing indicator from " + indicator.getSenderId() + " to " + (indicator.isGroup() ? "group " + indicator.getGroupId() : indicator.getRecipientId()));
        if (indicator.isGroup()) {
            messagingTemplate.convertAndSend("/topic/group/" + indicator.getGroupId() + "/typing", indicator);
        } else {
            messagingTemplate.convertAndSendToUser(
                    indicator.getRecipientId(), "/queue/typing", indicator);
        }
    }

    @MessageMapping("/chat/read")
    public void markAsRead(@Payload String messageId) {
        System.out.println("Marking message as read: " + messageId);
        chatService.markMessageAsRead(messageId);
    }

    @GetMapping("/private")
    public List<Messages> getPrivateMessages(
            @RequestParam String senderId,
            @RequestParam String recipientId) {
                List<Messages> messagesList = chatService.getPrivateMessages(senderId, recipientId);
                System.out.println(messagesList);
        return chatService.getPrivateMessages(senderId, recipientId);
    }

    
    @GetMapping("/group/{groupId}")
    public List<Messages> getGroupMessages(@PathVariable String groupId) {
        return chatService.getGroupMessages(groupId);
    }

}

class TypingIndicator {
    private String senderId;
    private String recipientId;
    private String groupId;
    private boolean typing;
    private boolean isGroup;

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public boolean isTyping() { return typing; }
    public void setTyping(boolean typing) { this.typing = typing; }
    public boolean isGroup() { return isGroup; }
    public void setGroup(boolean group) { isGroup = group; }
}