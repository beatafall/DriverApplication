package com.example.terkepes;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.terkepes.Class.BusLineDriver;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.MyviewHolder> {

    Context context;
    List<BusLineDriver> list;

    public BusAdapter( List<BusLineDriver> list, Context context) {
        this.list = list;
        this.context=context;
    }

    public void setBusAdapter(List<BusLineDriver> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.line.setText(list.get(position).getVonalId());
        holder.bus.setText(list.get(position).getBuszId());
        holder.date.setText(list.get(position).getDatum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView line, bus, date;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.lineItem);
            bus = itemView.findViewById(R.id.busItem);
            date =  itemView.findViewById(R.id.dateItem);
        }
    }
}


