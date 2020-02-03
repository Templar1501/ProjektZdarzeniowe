package com.example.projektzdarzeniowe.Controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projektzdarzeniowe.Model.User;
import com.example.projektzdarzeniowe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mRegisterBtn;
    private EditText mEmailEt, mPasswordEt, mNameEt,mPhoneEt;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailEt = findViewById(R.id.emailEt);
        mPasswordEt = findViewById(R.id.passwordEt);
        mPhoneEt = findViewById(R.id.phoneEt);
        mNameEt = findViewById(R.id.nameEt);
        mRegisterBtn = findViewById(R.id.register);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Registering...");

        mAuth = FirebaseAuth.getInstance();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                }else if(password.length()<10){
                    mPasswordEt.setError("Password too short");
                    mPasswordEt.setFocusable(true);
                }else{
                    createAccount(email,password);
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void createAccount(String email, String password){

        mProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mProgressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();
                            String name = mNameEt.getText().toString();
                            String phone = mPhoneEt.getText().toString();

                            User driver = new User (email,name,uid,phone,"");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users");

                            myRef.child(uid).setValue(driver);

                            Toast.makeText(RegisterActivity.this,
                                    "Registered..\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,
                                    DashboardActivity.class));
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }) ;
    }
}
