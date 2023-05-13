package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

import android.Manifest;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity implements MainAdapter.ItemClickListener{
    RecyclerView recyclerView;
    ArrayList<Conversation> answer = new ArrayList<>();
    ArrayList<Conversation> check = new ArrayList<>();
    ArrayList<String> check2 = new ArrayList<>();
    EditText text;
    EditText phone;
    String receiver_user_name;
    String receiver_phone_number;
    String sender_phone_number;
    String sender_user_name;
    MainAdapter ad;
    ichatdb dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //dao = new chatdb(this);

        Intent intent = getIntent();
        sender_user_name=intent.getStringExtra("login_sender_name");
        sender_phone_number=intent.getStringExtra("login_sender_phone_no");

        recyclerView=findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dao = new chatfirebasedb(this);

        getData();
    }
    public void clickHandler(View v){
        text= findViewById(R.id.username);
        phone= findViewById(R.id.editTextPhone);
        receiver_user_name=text.getText().toString().toLowerCase().trim();
        receiver_phone_number=phone.getText().toString().trim();
        Boolean flag=true;
        if(!receiver_phone_number.isEmpty() && !receiver_user_name.isEmpty())
        {
            for(int i=0;i<answer.size();i++)
            {
                if(receiver_phone_number.equals(answer.get(i).getReceiver_number()) || receiver_phone_number.equals(answer.get(i).getSender_number()))//answers must contain phonenumbers
                {
                    flag=false;
                    break;
                }
            }
            if(flag)
            {
                if(receiver_phone_number.length()==11)
                {
                    Intent intent = new Intent(this, MainActivity.class);
                    dao.addConversations(new Conversation(receiver_phone_number,sender_user_name,receiver_user_name,sender_phone_number));
                    text.getText().clear();
                    phone.getText().clear();

                    intent.putExtra("sender name",sender_user_name);
                    intent.putExtra("sender_phone number",sender_phone_number);
                    intent.putExtra("receiver_phone_number",receiver_phone_number);
                    intent.putExtra("receiver_user_name",receiver_user_name);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Phone Number should contain 11 digits.", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Enter Unique Contact Phone-Number!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Fields should not be empty.", Toast.LENGTH_SHORT).show();
        }
        getData();
    }
    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("receiver_phone_number",answer.get(pos).getReceiver_number());
        intent.putExtra("receiver_user_name",answer.get(pos).getReceiver_username());

        intent.putExtra("sender name",sender_user_name);
        intent.putExtra("sender_phone number",sender_phone_number);

        startActivity(intent);
    }

    public void fetch_contacts(View v){
        //first of all I have given permission in menifest file.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            //now request the permission
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        else{
            //for lower than marsmallow version
            //to get the phonebook
            getcontact();
        }
    }

    private void getcontact() {
        // to pass all phonebook to cursor
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,null);
        //boolean flag=true;
        getData();
        // to fetch all the contact from cursor
        while(cursor.moveToNext())
        {
            boolean flag=true;
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String mobile=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).toString().trim().replace(" ","").replace("-","").replaceFirst("\\+92","0");
            //now add the data in array list
            //if(Objects.equals(mobile, sender_phone_number)|| Objects.equals(receiver_phone_number, sender_phone_number)) {
            for(int i=0;i<check.size();i++)
            {
                if(check.get(i).getReceiver_number().equals(mobile) || check.get(i).getSender_number().equals(mobile))//answers must contain phonenumbers
                {
                    flag = false;
                    break;
                }
            }
            if(flag)
            {
                if(mobile.length()==11 && !check2.contains(mobile))
                {
                    check2.add(mobile);
                    dao.addConversations(new Conversation(mobile, sender_user_name, name, sender_phone_number));
                }
            }
            else{
                Toast.makeText(this, "Contact already exist!", Toast.LENGTH_SHORT).show();
            }
        }
        // for the contacts UI updatioon.
        getData();
    }

    //to get the output of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                //now permission is granted call function again.
                getcontact();
            }
        }
    }

    public void getData() {
        dao.getUsername(sender_phone_number, receiver_user_name, new chatfirebasedb.ChatDbCallback() {
            @Override
            public void onUsernameReceived(ArrayList<Conversation> usernames) {
                // Do something with the usernames arraylist
                answer = usernames;
                check = usernames;
                //if (!usernames.isEmpty()) {
                ad = new MainAdapter(usernames, sender_phone_number, MainActivity2.this);
                recyclerView.setAdapter(ad);
                //recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity2.this, LinearLayoutManager.VERTICAL));
                ad.notifyDataSetChanged();
                recyclerView.scrollToPosition(usernames.size() - 1);
                //}
            }
        });
    }
}