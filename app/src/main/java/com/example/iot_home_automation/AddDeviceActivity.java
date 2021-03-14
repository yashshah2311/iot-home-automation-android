package com.example.iot_home_automation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AddDeviceActivity extends AppCompatActivity {

    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Button btnAdd;

    DatabaseReference devices;
    DatabaseReference deviceLists;

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
        //to insert data in firebase users table
        deviceLists = FirebaseDatabase.getInstance().getReference().child("users").child("yash_9999").child("devicesList").child("2");
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String spinnerText = spinner.getSelectedItem().toString();
        deviceLists.push().setValue(spinnerText)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Device Added", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}