package com.example.projektzdarzeniowe.Controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class ReturnVehicleActivity extends AppCompatActivity {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_vehicle);
        mRecyclerView = findViewById(R.id.returnVehView);
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
        Query query = mRef.orderByChild("available").equalTo("no");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Vehicle vehicle = ds.getValue(Vehicle.class);
                    mVehicle.add(vehicle);
                }
                mAdapterVehicle = new AdapterVehicle(ReturnVehicleActivity.this,mVehicle);
                mAdapterVehicle.setOnVehicleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        returnVehicle(mVehicle,mOrders);
                        mRecyclerView.setAdapter(mAdapterVehicle);
                        Toast.makeText(ReturnVehicleActivity.this,
                                "Vehicle returned",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ReturnVehicleActivity.this,
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
    private HashMap<String,Object> returnVehicle(List<Vehicle> v, List<Order> o){
        HashMap<String,Object> result = new HashMap<>();

        for(Vehicle vs:v){
            for(Order os:o){
                if(os.getVehicle().equals(vs.getUid())){
                    result.put("available","yes");
                    mRef.child(vs.getName()).updateChildren(result);
                    return result;
                }
            }
        }
        return result;
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
