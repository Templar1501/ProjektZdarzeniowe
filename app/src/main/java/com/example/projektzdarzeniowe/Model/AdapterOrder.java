package com.example.projektzdarzeniowe.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projektzdarzeniowe.R;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.MyHolder>{

    Context mContext;
    List<Order> orderList;

    public AdapterOrder(Context context, List<Order> orderList) {
        this.mContext = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_orders,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String date = orderList.get(position).getDate();
        String id = orderList.get(position).getId();
        String user = orderList.get(position).getUser();
        String vehicle = orderList.get(position).getVehicle();

        holder.dateTv.setText("DATE: "+date);
        holder.idTv.setText("ID: "+id);
        holder.userTv.setText("DRIVER: "+user);
        holder.vehicleTv.setText("VEHICLE: "+vehicle);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView dateTv,idTv,userTv,vehicleTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.dateTv);
            idTv = itemView.findViewById(R.id.idTv);
            userTv = itemView.findViewById(R.id.userTv);
            vehicleTv = itemView.findViewById(R.id.vehicleTv);
        }
    }
}
