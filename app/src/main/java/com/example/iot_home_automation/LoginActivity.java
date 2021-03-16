package com.example.iot_home_automation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iot_home_automation.ui.home.HomeFragment;
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
        btnLogin.setOnClickListener(this);
        usersDatabase= FirebaseDatabase.getInstance().getReference("users");
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
            if (TextUtils.isEmpty(email)){
                Toast.makeText(this,"Username cannot be empty",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Password cannot be empty",Toast.LENGTH_LONG).show();
                return;
            }

            if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)){
                usersChild=FirebaseDatabase.getInstance().getReference().child("users").child(email);
                if(usersChild!= null){
                    usersChild.addValueEventListener(this);
                }else{
                    Toast.makeText(this,"User with Username :" +  email + " Does not exists.",Toast.LENGTH_LONG).show();
                }
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
            String username = snapshot.child("userName").getValue().toString();

            if(TextUtils.equals(pass, password)){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(LoginActivity.this, NewMainActivity.class);
                intent.putExtra("user", username);
                startActivity(intent);
            }else{
            Toast.makeText(this,"Please Login with Valid Username/Password",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}