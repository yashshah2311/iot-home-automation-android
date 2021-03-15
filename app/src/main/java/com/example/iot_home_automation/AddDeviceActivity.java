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
    RadioButton rbOn, rbOff;

    long id = 0;
    String actualValue = "", defaultValue = "OFF";

    DatabaseReference devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        //to fetch device list in the spinner dropdown
        devices = FirebaseDatabase.getInstance().getReference("devices");
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        mySpinner.setAdapter(adapter);

        fetchData();

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        devices = FirebaseDatabase.getInstance().getReference().child("users").child("shruti").child("devicesList");

        devices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    id=(snapshot.getChildrenCount());
                Toast.makeText(AddDeviceActivity.this, "Device is added" + id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void fetchData()
    {
        //to fetch devices in spinner dropdown
        listener = devices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren())
                    list.add(mydata.getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void insertData()
    {
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String spinnerText = spinner.getSelectedItem().toString();

        //to insert data in firebase users table
        devices = FirebaseDatabase.getInstance().getReference().child("users").child("shruti").child("devicesList");

        devices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                String spinnerText = spinner.getSelectedItem().toString();

                    Device device = new Device(spinnerText, defaultValue, actualValue);
                    devices.child(String.valueOf(id)).setValue(device);
                    Toast.makeText(AddDeviceActivity.this, "Device is added", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddDeviceActivity.this,HomeActivity.class);
                    startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        rgButton = (RadioGroup)findViewById(R.id.rgButton);
        rgButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = rgButton.getCheckedRadioButtonId();
                switch(id) {
                    case R.id.rbOn:
                        actualValue = "ON";
                        break;
                    case R.id.rbOff:
                        actualValue = "OFF";
                        break;
                }
            }
        });
    }
}