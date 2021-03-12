package com.example.iot_home_automation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    EditText edEmail,edPassword;
    Button btnLogin;
    DatabaseReference usersDatabase,usersChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    void initialize(){
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edEmail=(EditText)findViewById(R.id.edEmail);
        edPassword=(EditText)findViewById(R.id.edPassword);

        //btn on click
        btnLogin.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogin:
                login();
                break;
        }
    }

    void login(){
        try {
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            if(email != null && password != null){
                usersDatabase.orderByChild("email").equalTo(email);
                if(usersChild!= null){
                    usersChild.addValueEventListener(this);
                }else{
                    Toast.makeText(this,"User with Email ID :" +  email + " Does not exists.",Toast.LENGTH_LONG).show();
                }
            }
            if (email == null){
                Toast.makeText(this,"Email ID cannot be empty",Toast.LENGTH_LONG).show();
            }
            if(password == null){
                Toast.makeText(this,"Password cannot be empty",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            String password = edPassword.getText().toString();
            String pass=snapshot.child("password").getValue().toString();
            if(password == pass){

                Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();

            }else{
            Toast.makeText(this,"The data do not exist",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}