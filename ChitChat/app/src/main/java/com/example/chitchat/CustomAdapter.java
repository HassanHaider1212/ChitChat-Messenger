package com.example.chitchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Message> localDataSet;
    String sender_phone;
    public static class sender_ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView7;

        public sender_ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView1= view.findViewById(R.id.sender_textview);
            textView2= view.findViewById(R.id.sender_date);
            textView3= view.findViewById(R.id.sender_body);
            textView7= view.findViewById(R.id.sender_status);
        }
    }
    public static class receiver_ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView4;
        public TextView textView5;
        public TextView textView6;
        public TextView textView8;
        public receiver_ViewHolder(View view) {
            super(view);

            textView4= view.findViewById(R.id.receiver_textview);
            textView5= view.findViewById(R.id.receiver_date);
            textView6= view.findViewById(R.id.receiver_body);
            textView8= view.findViewById(R.id.receiver_status);
        }
    }

    public CustomAdapter(ArrayList<Message> dataSet,String sender_phone) {
        this.localDataSet=dataSet;
        this.sender_phone=sender_phone;
    }

    @Override
    public int getItemViewType(int position) {
        if(Objects.equals(localDataSet.get(position).getStatus(), "0"))
            return 0;//sender
        else
            return 1;//receiver
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if (viewType == 0) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.chat_layout, viewGroup, false);
            return new CustomAdapter.sender_ViewHolder(view);
        }
        else if (viewType == 1){
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.reply_layout, viewGroup, false);
            return new CustomAdapter.receiver_ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position)
    {
        if(Objects.equals(localDataSet.get(position).getReceiver_number(), sender_phone))
        {
            switch (getItemViewType(position)) {
                case 0: {
                    CustomAdapter.sender_ViewHolder vh = (com.example.chitchat.CustomAdapter.sender_ViewHolder) viewHolder;
                    vh.textView1.setText(localDataSet.get(position).getReceiver_name());
                    vh.textView2.setText(localDataSet.get(position).getTimestamp());
                    vh.textView3.setText(localDataSet.get(position).getBody());
                    vh.textView7.setText("Received");
                    break;
                }
                case 1: {
                    CustomAdapter.receiver_ViewHolder vh = (CustomAdapter.receiver_ViewHolder) viewHolder;
                    vh.textView4.setText(localDataSet.get(position).getReceiver_name());
                    vh.textView5.setText(localDataSet.get(position).getTimestamp());
                    vh.textView6.setText(localDataSet.get(position).getBody());
                    vh.textView8.setText("Sent");
                    break;
                }
                default:
                    break;
            }
        }
        else
        {
            switch (getItemViewType(position)) {
                case 0: {
                    CustomAdapter.sender_ViewHolder vh = (com.example.chitchat.CustomAdapter.sender_ViewHolder) viewHolder;
                    vh.textView1.setText(localDataSet.get(position).getReceiver_name());
                    vh.textView2.setText(localDataSet.get(position).getTimestamp());
                    vh.textView3.setText(localDataSet.get(position).getBody());
                    vh.textView7.setText("Sent");
                    break;
                }
                case 1: {
                    CustomAdapter.receiver_ViewHolder vh = (CustomAdapter.receiver_ViewHolder) viewHolder;
                    vh.textView4.setText(localDataSet.get(position).getReceiver_name());
                    vh.textView5.setText(localDataSet.get(position).getTimestamp());
                    vh.textView6.setText(localDataSet.get(position).getBody());
                    vh.textView8.setText("Received");
                    break;
                }
                default:
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
