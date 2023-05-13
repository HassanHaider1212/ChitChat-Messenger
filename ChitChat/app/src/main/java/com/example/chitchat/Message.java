package com.example.chitchat;

public class Message {
    private String body;
    private String timestamp;
    private String status;
    private String receiver_number;
    private String receiver_name;
    public Message(String receiver_number,String receiver_name,String body, String timestamp,String status) {
        this.receiver_number=receiver_number;
        this.receiver_name=receiver_name;
        this.body = body;
        this.timestamp = timestamp;
        this.status = status;
    }
    public Message() {
        // empty constructor needed for Firebase
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiver_number() {
        return receiver_number;
    }

    public void setReceiver_number(String receiver_number) {
        this.receiver_number = receiver_number;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }
}