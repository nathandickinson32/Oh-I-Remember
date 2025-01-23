package com.techelevator.dao;

import com.techelevator.model.CreateFriendRequestDto;
import com.techelevator.model.FriendRequest;

public interface FriendRequestDao {

    //CREATE
    public FriendRequest createFriendRequest(CreateFriendRequestDto createFriendRequestDto, int userId);

    //READ
    public FriendRequest getFriendRequestById(int requestId);
}
