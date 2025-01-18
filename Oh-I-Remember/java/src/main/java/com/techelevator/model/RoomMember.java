package com.techelevator.model;

import com.techelevator.model.user.User;

import java.time.LocalDateTime;

public class RoomMember {
    private int roomMemberId;
    private ChatRoom chatRoom;
    private User user;
    private LocalDateTime joinedAt;

    public RoomMember() {
    }

    public RoomMember(int roomMemberId, User user, ChatRoom chatRoom, LocalDateTime joinedAt) {
        this.roomMemberId = roomMemberId;
        this.user = user;
        this.chatRoom = chatRoom;
        this.joinedAt = joinedAt;
    }

    public int getRoomMemberId() {
        return roomMemberId;
    }

    public void setRoomMemberId(int roomMemberId) {
        this.roomMemberId = roomMemberId;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
