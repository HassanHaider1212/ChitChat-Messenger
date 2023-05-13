package com.example.chitchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    RecyclerView recyclerView;
    //public static ArrayList<Message> global_msg = new ArrayList<>();
    CustomAdapter ad;
    ichatdb dao;
    String receiver_phone_number;
    String receiver_user_name;
    String sender_phone_number;
    String sender_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dao = new chatdb(this);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editTextTextPersonName);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dao=new chatfirebasedb(this);

        Intent intent = getIntent();
        receiver_phone_number=intent.getStringExtra("receiver_phone_number");
        receiver_user_name=intent.getStringExtra("receiver_user_name");
        sender_phone_number=intent.getStringExtra("sender_phone number");
        sender_user_name=intent.getStringExtra("sender name");

        getMsgData();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void messenger(View view) {
        String data = editText.getText().toString();
        //String str = "This is an auto-generated Text.";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a");
        String formattedDate = formatDate.format(new Date());
        if (!data.isEmpty())
        {
            Message temp=new Message();
            temp.setReceiver_number(receiver_phone_number);
            temp.setTimestamp(formattedDate);
            temp.setBody(data);
            if(Objects.equals(sender_phone_number, receiver_phone_number)) {
                temp.setReceiver_name(receiver_user_name);
                temp.setStatus("1");
            }
            else
            {
                temp.setReceiver_name(sender_user_name);
                temp.setStatus("0");
            }
            dao.addMessages(temp);
            getMsgData();
            editText.getText().clear();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String text = editText.getText().toString();
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(receiver_user_name, text);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String text = preferences.getString(receiver_user_name, "");
        editText.setText(text);
    }

    public void getMsgData(){
        dao.getMessages_body_timestamp(receiver_phone_number,new chatfirebasedb.ChatDbCallback2() {
            @Override
            public void onUsernameReceived2(ArrayList<Message> msg) {
                //if(!msg.isEmpty()){
                ad = new CustomAdapter(msg,sender_phone_number);
                recyclerView.setAdapter(ad);
                //recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                ad.notifyDataSetChanged();
                recyclerView.scrollToPosition(msg.size()-1);
                //}
            }
        });
    }

}