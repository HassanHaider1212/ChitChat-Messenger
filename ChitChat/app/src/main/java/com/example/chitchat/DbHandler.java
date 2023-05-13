package com.example.chitchat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase.db";
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS create_conversation");
        db.execSQL("DROP TABLE IF EXISTS create_message");
        String create_conversation = "Create TABLE Conversation (conversation_id INTEGER PRIMARY KEY AUTOINCREMENT, sender_username TEXT, receiver_username TEXT) ";
        String create_message = "Create TABLE Messages (conversation_id INTEGER NOT NULL REFERENCES Conversation(conversation_id), message_id INTEGER PRIMARY KEY AUTOINCREMENT,body TEXT,timestamp TEXT,status TEXT)";

        try {
            db.execSQL(create_conversation);
            db.execSQL(create_message);
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade schema when version number is increased
        if (oldVersion < 2 && newVersion >= 2) {
            // add a new column to message table
            db.execSQL("DROP TABLE IF EXISTS create_conversation");
            db.execSQL("DROP TABLE IF EXISTS create_message");
            onCreate(db);
        }
    }
}