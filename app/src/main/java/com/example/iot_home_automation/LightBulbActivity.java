package com.example.iot_home_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Device;

public class LightBulbActivity extends AppCompatActivity {

    String username, deviceKey;
    DatabaseReference bulbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_bulb);
        initialize();
    }

    private void initialize() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        deviceKey = intent.getStringExtra("deviceKey");
        bulbRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
        Toast.makeText(this, "This is "+ bulbRef, Toast.LENGTH_SHORT).show();

//        Button btnOn = findViewById(R.id.btnOn);
//        Button btnOff = findViewById(R.id.btnOff);

//        btnOn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bulbRef.setValue("ON");
//                Toast.makeText(LightBulbActivity.this,"Device is ON", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        btnOff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bulbRef.setValue("OFF");
//                Toast.makeText(LightBulbActivity.this,"Device is OFF", Toast.LENGTH_LONG).show();
//            }
//        });

    }

}