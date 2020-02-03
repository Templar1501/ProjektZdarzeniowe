package com.example.projektzdarzeniowe.Controler;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.projektzdarzeniowe.Model.AdapterVehicle;
import com.example.projektzdarzeniowe.Model.Vehicle;
import com.example.projektzdarzeniowe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AdapterVehicle mAdapterVehicle;
    private List<Vehicle> mVehicle;
    private Button takeBtn,returnBtn, addOrRemove;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private String admin;

    public VehicleFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);
        mRecyclerView = view.findViewById(R.id.vehicleView);
        takeBtn = view.findViewById(R.id.takeBtn);
        returnBtn = view.findViewById(R.id.returnBtn);
        addOrRemove = view.findViewById(R.id.addOrRemVehBtn);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference("Users");

        Query query = mRef.orderByChild("uid").equalTo(mUser.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    admin = ""+ds.child("admin").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mVehicle = new ArrayList<>();
        getAllVehicles();

        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TakeVehicleActivity.class));
            }
        });
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ReturnVehicleActivity.class));
            }
        });
        addOrRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admin.equals("yes")){
                    startActivity(new Intent(getActivity(),AddOrRemoveVehicleActivity.class));

                }else{
                    Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    private void getAllVehicles() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vehicles");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mVehicle.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Vehicle vehicle = ds.getValue(Vehicle.class);
                    mVehicle.add(vehicle);
                }
                mAdapterVehicle = new AdapterVehicle(getActivity(),mVehicle);
                mRecyclerView.setAdapter(mAdapterVehicle);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
