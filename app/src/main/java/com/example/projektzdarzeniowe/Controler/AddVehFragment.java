package com.example.projektzdarzeniowe.Controler;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projektzdarzeniowe.Model.Vehicle;
import com.example.projektzdarzeniowe.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddVehFragment extends Fragment {
    private Button addBtn;
    private EditText nameEt,idEt;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    public AddVehFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_veh, container, false);

        addBtn = view.findViewById(R.id.addVehicle);
        nameEt = view.findViewById(R.id.vehicleEto);
        idEt = view.findViewById(R.id.idVehEto);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Vehicles");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicle();
                Toast.makeText(getActivity(),"Vehicle added",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void addVehicle(){
        String name = nameEt.getText().toString();
        String id = idEt.getText().toString();

        Vehicle vehicle = new Vehicle(name,"yes",id);

        mRef.child(name).setValue(vehicle);
    }

}
