package com.techelevator.dao;

import com.techelevator.model.messages.CreateMessageDto;
import com.techelevator.model.messages.Message;

public interface MessageDao {
    //CREATE
    Message createMessage(CreateMessageDto createMessageDto, int userId);

    //READ
    public Message getMessageById(int messageId);

    //UPDATE


    //DELETE

}
