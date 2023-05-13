package com.example.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    EditText sender_name;
    EditText sender_phone_no;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    public void _login(View v){
        sender_name=findViewById(R.id.sender_name);
        sender_phone_no=findViewById(R.id.editTextPhoneNumber);
        login=findViewById(R.id.buttonLogin);

        String sendername=sender_name.getText().toString().trim();
        String senderphone=sender_phone_no.getText().toString().trim();
        if(!sendername.isEmpty() && !senderphone.isEmpty()) {

            Intent intent = new Intent(this, MainActivity2.class);

            intent.putExtra("login_sender_name", sendername);
            intent.putExtra("login_sender_phone_no",senderphone);

            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Field Cannot be Empty!", Toast.LENGTH_SHORT).show();
        }
    }
}