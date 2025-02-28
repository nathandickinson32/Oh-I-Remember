package com.techelevator.dao;

import com.techelevator.model.CreateFriendRequestDto;
import com.techelevator.model.FriendRequest;
import com.techelevator.model.FriendRequestResponseDto;
import com.techelevator.model.user.User;

import java.util.List;

public interface FriendDao {

    //CREATE
    public FriendRequest createFriendRequest(CreateFriendRequestDto createFriendRequestDto, int userId);

    //READ
    public FriendRequest getFriendRequestById(int requestId);
    public List<User> getFriends(int userId);
    public boolean checkPendingRequest(int senderId, String receiverUserName);
    public List<FriendRequest> getFriendRequests(int userId);
    public List<User> getUsersById(List<Integer> userIds);

    //UPDATE
    public FriendRequest friendRequestResponse(FriendRequestResponseDto friendRequestResponseDto);

    //DELETE
    public void deleteFriendRequest(int requestId);
    public void deleteFriendship(int userId1, int userId2);

}
