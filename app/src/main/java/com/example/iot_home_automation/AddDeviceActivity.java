package com.example.iot_home_automation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Device;
import model.User;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener{

    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Button btnAdd;
    RadioGroup rgButton;
    RadioButton rb;
    String username;
    long id = 0;
    String actualValue = "", defaultValue = "OFF";

    DatabaseReference devices, users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        //to fetch device list in the spinner dropdown
        devices = FirebaseDatabase.getInstance().getReference("devices");
        users = FirebaseDatabase.getInstance().getReference("users").child(username);
        fetchData();
        rgButton=(RadioGroup)findViewById(R.id.rgButton);
        rgButton.setOnClickListener(this);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


    }


    public void fetchData()
    {
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        mySpinner.setAdapter(adapter);
        //to fetch devices in spinner dropdown
        listener = devices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren()) {
                    list.add(mydata.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void insertData()
    {

        //to insert data in firebase users table
        users = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("devicesList");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                String spinnerText = spinner.getSelectedItem().toString();
                getNewID();
                int btnId = rgButton.getCheckedRadioButtonId();
                if (btnId != -1) {
                    rb = findViewById(btnId);
                    actualValue = rb.getText().toString();
                    if(actualValue.equalsIgnoreCase("On")){
                        actualValue = "ON";
                    }else {
                        actualValue = "OFF";
                    }
                    Toast.makeText(AddDeviceActivity.this, "Device Actual value " + actualValue, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddDeviceActivity.this, "Device Actual value not selected" + actualValue, Toast.LENGTH_SHORT).show();
                }
                Device device = new Device(spinnerText, defaultValue, actualValue);
                users.child(String.valueOf(id + 1)).setValue(device);
                Toast.makeText(AddDeviceActivity.this, "Device added successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddDeviceActivity.this,HomeActivity.class);
                intent.putExtra("user", username);
                startActivity(intent);
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getNewID(){
        users = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("devicesList");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    id=(snapshot.getChildrenCount());
                    Toast.makeText(AddDeviceActivity.this, "Device is added" + id, Toast.LENGTH_SHORT).show();
                }else {
                    id = -1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnAdd:
                insertData();
                break;
        }
    }

    void radioButton(){
        rgButton = (RadioGroup)findViewById(R.id.rgButton);
        rgButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }
}