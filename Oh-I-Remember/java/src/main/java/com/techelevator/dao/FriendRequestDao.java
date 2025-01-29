package com.techelevator.dao;

import com.techelevator.model.CreateFriendRequestDto;
import com.techelevator.model.FriendRequest;
import com.techelevator.model.FriendRequestResponseDto;
import com.techelevator.model.user.User;

import java.util.List;

public interface FriendRequestDao {

    //CREATE
    public FriendRequest createFriendRequest(CreateFriendRequestDto createFriendRequestDto, int userId);

    //READ
    public FriendRequest getFriendRequestById(int requestId);
    public List<User> getFriends(int userId);

    //UPDATE
    public FriendRequest friendRequestResponse(FriendRequestResponseDto friendRequestResponseDto);
}
