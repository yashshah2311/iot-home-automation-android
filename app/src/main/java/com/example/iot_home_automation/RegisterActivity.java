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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    EditText edFirstName, edLastName, edUserName, edPassword;
    Button btnRegister;
    DatabaseReference usersDatabase,usersChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    void initialize(){
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edUserName=(EditText)findViewById(R.id.edEmail);
        edPassword=(EditText)findViewById(R.id.edPassword);
        edFirstName=(EditText)findViewById(R.id.edFirstName);
        edLastName=(EditText)findViewById(R.id.edLastName);
        //btn on click
        btnRegister.setOnClickListener(this);
        usersDatabase= FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRegister:
                register();
                break;
        }
    }

    void register(){
        try {
            String username = edUserName.getText().toString();
            String password = edPassword.getText().toString();
            String lastname = edLastName.getText().toString();
            String firstname = edFirstName.getText().toString();
            if (TextUtils.isEmpty(username)){
                Toast.makeText(this,"Username cannot be empty",Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(firstname)){
                Toast.makeText(this,"Firstname cannot be empty",Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(lastname)){
                Toast.makeText(this,"Lastname cannot be empty",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Password cannot be empty",Toast.LENGTH_LONG).show();
                return;
            }

            if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(firstname)){
                usersChild=FirebaseDatabase.getInstance().getReference().child("users").child(username);
                if(usersChild != null){
                    usersChild.addValueEventListener(this);
                }else{
                    Toast.makeText(this,"User with username :" +  username + " already exists.Please select another username",Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String username = edUserName.getText().toString();
        String password = edPassword.getText().toString();
        String lastname = edLastName.getText().toString();
        String firstname = edFirstName.getText().toString();
        if (snapshot.exists()) {
            Toast.makeText(this,"User with username :" +  username + " already exists.Please select another username",Toast.LENGTH_LONG).show();
        } else {
            User user = new User(username, firstname, lastname, password);
            usersDatabase.child(username).setValue(user);
            Toast.makeText(this, "The User is registered to the system", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}