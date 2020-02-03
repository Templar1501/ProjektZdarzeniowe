package com.example.projektzdarzeniowe.Controler;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projektzdarzeniowe.Model.AdapterOrder;
import com.example.projektzdarzeniowe.Model.Order;
import com.example.projektzdarzeniowe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowOrdersFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AdapterOrder mAdapterOrder;
    private List<Order> mOrders;

    public ShowOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_orders, container, false);
        mRecyclerView = view.findViewById(R.id.ordersView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mOrders = new ArrayList<>();
        getAllOrders();

        return view;
    }
    private void getAllOrders() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mOrders.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    mOrders.add(order);

                }
                mAdapterOrder = new AdapterOrder(getActivity(),mOrders);
                mRecyclerView.setAdapter(mAdapterOrder);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
