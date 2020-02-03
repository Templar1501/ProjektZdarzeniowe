package com.example.projektzdarzeniowe.Controler;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.projektzdarzeniowe.Model.Order;
import com.example.projektzdarzeniowe.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrderFragment extends Fragment {

    private Button addBtn;
    private TextInputEditText mDate,mId,mVehicle,mDriver;
    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;

    public AddOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order, container, false);

        addBtn = view.findViewById(R.id.addOrder);
        mDate = view.findViewById(R.id.dateEto);
        mId = view.findViewById(R.id.idEto);
        mVehicle = view.findViewById(R.id.vehicleEto);
        mDriver = view.findViewById(R.id.userEto);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Orders");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();

                Toast.makeText(getActivity(),"Order added",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(),OrderActivity.class));
            }
        });
        return view;
    }
    public void addOrder(){
        String date = mDate.getText().toString();
        String id = mId.getText().toString();
        String user = mDriver.getText().toString();
        String vehicle = mVehicle.getText().toString();

        Order order = new Order(date,id,user,vehicle);

        mReference.child(id).setValue(order);
    }

}
