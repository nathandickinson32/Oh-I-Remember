package com.techelevator.model.messages;

import java.util.Date;

public class Message {
    private int messageId;
    private int sentById;
    private int receivedById;
    private String message;
    private Date date;
    private boolean isArchived;
    private String archivedNotes;

    public int getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getArchivedNotes() {
        return archivedNotes;
    }

    public void setArchivedNotes(String archivedNotes) {
        this.archivedNotes = archivedNotes;
    }
}
