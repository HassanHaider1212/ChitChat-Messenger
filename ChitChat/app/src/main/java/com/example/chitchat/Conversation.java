package com.example.chitchat;

public class Conversation {
    private String receiver_number;
    private String sender;
    private String receiver;
    private String sender_number;
    public static String temp;
    public Conversation(String receiver_number, String sender_username,String receiver_username,String sender_number) {
        this.receiver_number=receiver_number;
        this.sender=sender_username;
        this.receiver=receiver_username;
        this.sender_number=sender_number;
    }
    public Conversation() {
        //default constructor
    }
    public String getSender_username() {
        return sender;
    }

    public void setSender_username(String sender_username) {
        this.sender = sender_username;
    }
    public String getReceiver_username() {
        return receiver;
    }

    public void setReceiver_username(String receiver_username) {
        this.receiver = receiver_username;
    }
    public String getReceiver_number() {
        return receiver_number;
    }

    public void setReceiver_number(String receiver_number) {
        this.receiver_number = receiver_number;
    }

    public String getSender_number() {
        return sender_number;
    }

    public void setSender_number(String sender_number) {
        this.sender_number = sender_number;
    }
}