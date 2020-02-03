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

public class AdapterVehicle extends RecyclerView.Adapter<AdapterVehicle.MyHolder>{

    Context mContext;
    List<Vehicle> vehicleList;
    View.OnClickListener mOnClickListener;


    public AdapterVehicle(Context context, List<Vehicle> vehicleList) {
        this.mContext = context;
        this.vehicleList = vehicleList;
    }

    public void setOnVehicleListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_vehicle,parent,false);
        view.setOnClickListener(mOnClickListener);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String name = vehicleList.get(position).getName();
        String uid = vehicleList.get(position).getUid();
        String available = vehicleList.get(position).getAvailable();




        holder.nameTvv.setText("NAME: "+name);
        holder.uidTvv.setText("ID: "+uid);
        holder.availableTvv.setText("AVAILABLE "+available);
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTvv,availableTvv,uidTvv;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            nameTvv = itemView.findViewById(R.id.nameTvv);
            uidTvv = itemView.findViewById(R.id.uidTvv);
            availableTvv = itemView.findViewById(R.id.availableTvv);
        }


    }
}
