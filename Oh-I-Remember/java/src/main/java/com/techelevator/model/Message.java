package com.techelevator.model;

import com.techelevator.model.user.User;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int roomId;
    private int senderId;
    private String message;
    private LocalDateTime createdAt;
    private boolean isArchived;

    public Message() {
    }

    public Message(int messageId, int roomId, int senderId, String message, LocalDateTime createdAt, boolean isArchived) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.senderId = senderId;
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
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
