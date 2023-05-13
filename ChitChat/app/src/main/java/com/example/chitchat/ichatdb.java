package com.example.chitchat;

public interface ichatdb{
    public void addConversations(Conversation con);
    public void getUsername(String sender_phone, String sender_name, chatfirebasedb.ChatDbCallback callback);
    public void addMessages(Message msg);
    public void getMessages_body_timestamp(String pos,chatfirebasedb.ChatDbCallback2 callback);
}