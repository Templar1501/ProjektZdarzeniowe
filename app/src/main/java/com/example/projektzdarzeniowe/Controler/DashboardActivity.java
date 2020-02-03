package com.example.projektzdarzeniowe.Controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.projektzdarzeniowe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView navigationView = findViewById(R.id.navView);

        ProfileFragment fragment1 = new ProfileFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();

        navigationView.setOnNavigationItemSelectedListener(selectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.nav_vehicle:
                            VehicleFragment fragment1 = new VehicleFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content,fragment1,"");
                            ft1.commit();
                            return true;
                        case R.id.nav_profile:
                            ProfileFragment fragment2 = new ProfileFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content,fragment2,"");
                            ft2.commit();
                            return true;
                        case R.id.nav_logout:
                            mAuth.signOut();
                            checkUserStatus();
                            return true;
                    }
                    return false;
                }
            };
    private void checkUserStatus(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){ }
        else{
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        }
    }
    @Override
    protected void onStart(){
        checkUserStatus();
        super.onStart();
    }

}
