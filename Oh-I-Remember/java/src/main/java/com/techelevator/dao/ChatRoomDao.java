package com.techelevator.dao;

import com.techelevator.model.ChatRoom;
import com.techelevator.model.CreateChatRoomDTO;
import com.techelevator.model.user.User;

import java.util.List;

public interface ChatRoomDao {
    //CREATE
    public ChatRoom createChatRoom(CreateChatRoomDTO createChatRoomDTO, int userId);

    //READ
    public ChatRoom getChatRoomById(int id);

    public List<Integer> getMembersByRoomId(int roomId);


    //UPDATE
    void addMemberToChatRoom( int roomId, int userId);


}
