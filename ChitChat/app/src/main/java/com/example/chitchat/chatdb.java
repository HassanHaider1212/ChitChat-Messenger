//package com.example.chitchat;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import java.util.ArrayList;
//
//public class chatdb implements ichatdb{
//    ArrayList<String> name_arr = new ArrayList<>();
//    ArrayList<Integer> p = new ArrayList<>();
//    ArrayList<Message> msg1 = new ArrayList<>();
//    private Context context;
//    public chatdb(Context ctx){
//        context = ctx;
//    }
//    @Override
//    public void addConversations(Conversation con){
//        DbHandler dbHelper = new DbHandler(context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("sender_username", con.getSender_username());
//        values.put("receiver_username", con.getReceiver_username());
//        Conversation.c = db.insert("Conversation", null, values);
//        //Log.d("primary keys:", Long.toString(Conversation.c));
//        dbHelper.close();
//    }
//    @Override
//    public int getUserid(String pos){
//        DbHandler dbHelper = new DbHandler(context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String query = "SELECT conversation_id FROM Conversation WHERE receiver_username LIKE '%' || ? || '%' ";
//        Cursor cursor = db.rawQuery(query, new String[] { pos });
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                p.add(cursor.getInt(0));
//            } while (cursor.moveToNext());
//        }
//        dbHelper.close();
//        return p.get(0);
//    }
//    @Override
//    public ArrayList<String> getUsername(){
//
//        DbHandler dbHelper = new DbHandler(context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String query = "SELECT receiver_username FROM Conversation";
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                name_arr.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//        dbHelper.close();
//        return name_arr;
//    }
//    @Override
//    public void addMessages(Message msg){
//        DbHandler dbHelper = new DbHandler(context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        ContentValues values = new ContentValues();
//        //Log.d("ADD Message:", Long.toString(Conversation.c));
//        values.put("conversation_id",Conversation.c);
//        values.put("body", msg.getBody());
//        values.put("timestamp", msg.getTimestamp());
//        values.put("status", msg.getStatus());
//        Message.m = db.insert("Messages", null, values);
//        //Log.d("msgtag", Long.toString(Message.m));
//        dbHelper.close();
//    }
//    @Override
//    public ArrayList<Message> getMessages_body_timestamp(int pos){
//
//        DbHandler dbHelper = new DbHandler(context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String query = "SELECT body, timestamp, status  FROM Messages WHERE conversation_id = ?";
//        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(pos) });
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                Message temp=new Message("0","0","0");
//                temp.setBody(cursor.getString(0));
//                temp.setTimestamp(cursor.getString(1));
//                temp.setStatus(cursor.getString(2));
//                msg1.add(temp);
//            } while (cursor.moveToNext());
//        }
//        dbHelper.close();
//        return msg1;
//    }
//}