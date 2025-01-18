package com.techelevator.model;

import com.techelevator.model.user.User;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private ChatRoom chatRoom;
    private User sender;
    private String message;
    private LocalDateTime createdAt;
    private boolean isArchived;

    public Message() {
    }

    public Message(int messageId, ChatRoom chatRoom, User sender, String message, LocalDateTime createdAt, boolean isArchived) {
        this.messageId = messageId;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
        this.isArchived = isArchived;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
