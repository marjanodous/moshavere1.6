package com.example.hoquqi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    List<Item>itemList;
    Context mContext;
    public ItemAdapter(List<Item>itemList, Context mContext){
        this.itemList = itemList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(aView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item aItem = itemList.get(position);
        holder.aMessage.setText(aItem.getuMessage());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView aMessage;


        public MyViewHolder(View itemView) {
            super(itemView);
            aMessage = itemView.findViewById(R.id.aya_midanid);

        }

        @Override
        public void onClick(View view) {

        }
    }

}