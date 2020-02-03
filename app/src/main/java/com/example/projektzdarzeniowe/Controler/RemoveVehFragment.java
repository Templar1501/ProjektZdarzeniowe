package com.example.projektzdarzeniowe.Controler;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektzdarzeniowe.R;
import com.google.android.material.textfield.TextInputEditText;
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
public class RemoveVehFragment extends Fragment {
    private Button delete;
    private EditText idVeh;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    public RemoveVehFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remove_veh, container, false);

        delete = view.findViewById(R.id.deleteVehicle);
        idVeh = view.findViewById(R.id.deleteVehET);

        mDatabase = FirebaseDatabase.getInstance();

        mRef = mDatabase.getReference("Vehicles");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = idVeh.getText().toString();
                Query query = mRef.orderByChild("name");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            if(ds.child("uid").getValue().toString().equals(id)){
                                mRef.child(ds.child("name").getValue().toString()).setValue(null);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
                Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
