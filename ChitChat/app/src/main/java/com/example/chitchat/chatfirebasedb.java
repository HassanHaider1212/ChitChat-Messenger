package com.example.chitchat;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class chatfirebasedb implements ichatdb{

    private DatabaseReference mDatabase;
    private Context context;
    public chatfirebasedb(Context ctx){
        context = ctx;
    }

    public interface ChatDbCallback {
        void onUsernameReceived(ArrayList<Conversation> usernames);
    }

    public interface ChatDbCallback2 {
        void onUsernameReceived2(ArrayList<Message> msg);
    }

    @Override
    public void addConversations(Conversation con) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Conversation");
        mDatabase.keepSynced(true);

        String iD = mDatabase.push().getKey();
        Conversation conversation = new Conversation(con.getReceiver_number(),con.getSender_username(), con.getReceiver_username(),con.getSender_number());
        mDatabase.child(iD).setValue(conversation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(context,"Record  Saved in Conversation Table", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Record Not Saved in Conversation Table", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void getUsername(String sender_phone,String sender_name, ChatDbCallback callback) {
        ArrayList<Conversation> name_arr = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Conversation");
        mDatabase.keepSynced(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name_arr.clear();
                if (dataSnapshot.exists()) {
                    int size = (int) dataSnapshot.getChildrenCount();
                    //Toast.makeText(context.getApplicationContext(), "Contacts Size : " + size, Toast.LENGTH_SHORT).show();

                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        Conversation con = i.getValue(Conversation.class); //fetches a particular node from the database
                        assert con != null;
                        if(Objects.equals(con.getSender_number(), sender_phone)|| Objects.equals(con.getReceiver_number(), sender_phone))
                            name_arr.add(con); //add the node to the arraylist
                    }
                }
                callback.onUsernameReceived(name_arr);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(context,"FireBase!! Failed to Read Conversation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addMessages(Message msg) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Messages");
        mDatabase.keepSynced(true);

        String iD = mDatabase.push().getKey();

        String body = msg.getBody();
        String timestamp = msg.getTimestamp();
        String status = msg.getStatus();
        String receiver_number = msg.getReceiver_number();
        String receiver_name=msg.getReceiver_name();

        Message message = new Message(receiver_number,receiver_name, body, timestamp, status);

        mDatabase.child(iD).setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(context,"Record  Saved in Messages Table", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Record Not Saved in Messages Table", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void getMessages_body_timestamp(String phone,ChatDbCallback2 callback2) {
        ArrayList<Message> msg = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Messages");
        mDatabase.keepSynced(true);

        Query query = mDatabase.orderByChild("receiver_number").equalTo(phone);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msg.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Message message = i.getValue(Message.class);
                        msg.add(message);
                    }
                }
                callback2.onUsernameReceived2(msg);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Firebase!! Failed to Read Messages", Toast.LENGTH_SHORT).show();
            }
        });
    }

}