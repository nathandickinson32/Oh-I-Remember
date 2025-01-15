package com.techelevator.model.messages;

public class CreateMessageDto {
    private int sentById;
    private int receivedById;
    private String message;

    public int getSentById() {
        return sentById;
    }

    public void setSentById(int sentById) {
        this.sentById = sentById;
    }

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
