package com.techelevator.dao;

import com.techelevator.model.CreateMessageDto;
import com.techelevator.model.Message;

public interface MessageDao {
    //CREATE
    public Message createMessage(CreateMessageDto createMessageDto, int userId);

    //READ
    public  Message getMessageById(int messageId);
}
