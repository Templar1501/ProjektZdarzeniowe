package com.example.projektzdarzeniowe.Controler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projektzdarzeniowe.Model.AdapterVehicle;
import com.example.projektzdarzeniowe.Model.Order;
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
import java.util.HashMap;
import java.util.List;

public class TakeVehicleActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AdapterVehicle mAdapterVehicle;
    private List<Vehicle> mVehicle;
    private List<Order> mOrders;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef,mRef2;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_take_vehicle);
        super.onCreate(savedInstanceState);
        mRecyclerView = findViewById(R.id.takeVehView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Vehicles");
        mRef2 = mDatabase.getReference("Orders");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mOrders = new ArrayList<>();
        mVehicle = new ArrayList<>();

        Query query1 = mRef2.orderByChild("user").equalTo(mUser.getUid());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds1:dataSnapshot.getChildren()){
                    Order order = ds1.getValue(Order.class);
                    mOrders.add(order);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showAvailableVehicles();
    }
    private void showAvailableVehicles() {
        Query query = mRef.orderByChild("available").equalTo("yes");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Vehicle vehicle = ds.getValue(Vehicle.class);
                    mVehicle.add(vehicle);
                }
                mAdapterVehicle = new AdapterVehicle(TakeVehicleActivity.this,mVehicle);
                mAdapterVehicle.setOnVehicleListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        takeVehicle(mVehicle,mOrders);
                        mRecyclerView.setAdapter(mAdapterVehicle);
                        startActivity(new Intent(TakeVehicleActivity.this,
                                DashboardActivity.class));
                    }
                });
                mRecyclerView.setAdapter(mAdapterVehicle);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private HashMap<String,Object> takeVehicle(List<Vehicle> v, List<Order> o){
        HashMap<String,Object> result = new HashMap<>();
        result.clear();

        for(Vehicle vs:v){
            for(Order os:o){
                if(os.getVehicle().equals(vs.getUid()) && os.getDate()
                        .equals(java.time.LocalDate.now().toString())){
                    result.put("available","no");
                    mRef.child(vs.getName()).updateChildren(result);
                    Toast.makeText(TakeVehicleActivity.this,
                            "Vehicle taken",Toast.LENGTH_SHORT).show();
                    return result;
                }
            }
        }
        Toast.makeText(TakeVehicleActivity.this,
                "Vehicle not taken",Toast.LENGTH_SHORT).show();
        return result;
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
