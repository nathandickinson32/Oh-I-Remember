package com.techelevator.model.messages;

public class CreateMessageDto {
    private int receivedById;
    private String message;



    public int getReceivedById() {
        return receivedById;
    }

    public void setReceivedById(int receivedById) {
        this.receivedById = receivedById;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
