package com.example.chitchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    TextView textView;
    String sender_phone;
    private static ItemClickListener listener;
    static ArrayList<Conversation> localDataSet;
    Conversation con;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.title);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) v.getTag();
                    //listener.onClick(pos);
                }
            });
        }
        public TextView getTextView() {
            return textView;
        }
    }

    public MainAdapter(ArrayList<Conversation> dataSet,String sender_phone1 ,ItemClickListener ls) {
        localDataSet=dataSet;
        listener=ls;
        sender_phone=sender_phone1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mainlayout, viewGroup, false);
        textView=view.findViewById(R.id.title);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position)
    {
        if(Objects.equals(localDataSet.get(position).getSender_number(), sender_phone))
        {
            viewHolder.textView.setText(localDataSet.get(position).getReceiver_username());
        }
        else
        {
            viewHolder.textView.setText(localDataSet.get(position).getSender_username());
        }




        viewHolder.itemView.setOnClickListener(view -> {
            listener.onClick(position);
        });
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public interface ItemClickListener{
        void onClick(int details);
    }
}