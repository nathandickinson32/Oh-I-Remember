package com.techelevator.model;

import com.techelevator.model.user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ChatRoom {
    private int roomId;
    private String roomName;
    private boolean isGroup;
    private int createdByUserId;
    private LocalDateTime createdAt;
    private List<User> members;
    public ChatRoom(){};

    public ChatRoom(int roomId, String roomName, boolean isGroup, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.isGroup = isGroup;
        this.createdAt = createdAt;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
