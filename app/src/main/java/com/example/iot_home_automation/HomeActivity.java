package com.example.iot_home_automation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Device;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener, ChildEventListener {

    LinearLayout lin1, lin2;
    ImageView btnAdd;
    String username;
    DatabaseReference  usersTable, userDevice;

    List<Device> devices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    private void initialize() {

        Intent intent = getIntent();
        username = intent.getStringExtra("user");


        lin1 = findViewById(R.id.lin1);
        lin2 = findViewById(R.id.lin2);

        usersTable = FirebaseDatabase.getInstance().getReference("users");

        devices = new ArrayList<Device>();

        findDevices();
        addPlusButton();




    }



    @Override
    public void onClick(View v) {

        int drawableId = (Integer)v.getTag();
        switch(drawableId){
            case R.drawable.lightbulb:
                Toast.makeText(this, "The light bulb was called successfully", Toast.LENGTH_LONG).show();
                break;
            case R.drawable.tv:
                Toast.makeText(this, "The TV was called successfully", Toast.LENGTH_LONG).show();
                break;
            case R.drawable.ac:
                Toast.makeText(this, "The AC was called successfully", Toast.LENGTH_LONG).show();
                break;
            case R.drawable.fridge:
                Toast.makeText(this, "The fridge was called successfully", Toast.LENGTH_LONG).show();
                break;
            case R.drawable.soil_moisture:
                Toast.makeText(this, "The soil moisture was called successfully", Toast.LENGTH_LONG).show();
                break;
            case R.drawable.add:
                Toast.makeText(this, "The add button was called successfully", Toast.LENGTH_LONG).show();

                break;
        }

    }

    private void findDevices() {
        //usersTable.addChildEventListener(this);
        userDevice = usersTable.child(username).child("devicesList");
        userDevice.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        Device device = snapshot.getValue(Device.class);
        devices.add(device);
        addButton(device);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            String test = snapshot.child("actualValue").getValue().toString();
            Toast.makeText(this, test, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Couldn't find", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    private void addButton(Device myDevice) {
        ImageView im = new ImageView(HomeActivity.this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(160, 160);
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(160,160);
        params.setMargins(10,0,10,0);
        im.setScaleType(ImageView.ScaleType.FIT_XY);
        im.setLayoutParams(params);
        im.setPadding(15, 15, 15, 15);

        String deviceName = myDevice.getDeviceName();
        switch(deviceName){
            case "Bulb":
                im.setImageResource(R.drawable.lightbulb);
                im.setTag(R.drawable.lightbulb);
                break;
            case "TV":
                im.setImageResource(R.drawable.tv);
                im.setTag(R.drawable.tv);
                break;
            case "Fridge":
                im.setImageResource(R.drawable.fridge);
                im.setTag(R.drawable.fridge);
                break;
            case "AC":
                im.setImageResource(R.drawable.ac);
                im.setTag(R.drawable.ac);
                break;
            case "Soil Moisture":
                im.setImageResource(R.drawable.soil_moisture);
                im.setTag(R.drawable.soil_moisture);
                break;
        }

        if(myDevice.getActualValue().equals("ON")){
            im.setBackgroundResource(R.drawable.image_selector_on);
        }
        else if(myDevice.getActualValue().equals("OFF")){
            im.setBackgroundResource(R.drawable.image_selector_off);
        }

        im.setOnClickListener(this);
        if(devices.size() <= 4){
            if(devices.size() == 4){
                btnAdd.setVisibility(View.GONE);
            }
            if(lin1.getChildCount() == 2){
                lin2.addView(im);
            }
            else{
                lin1.addView(im);
            }
        }


    }
    private void addPlusButton(){
        ImageView im = new ImageView(HomeActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(160,160);
        params.setMargins(5,0,5,0);
        im.setScaleType(ImageView.ScaleType.FIT_XY);
        im.setLayoutParams(params);
        im.setPadding(15, 15, 15, 15);
        im.setImageResource(R.drawable.add);
        im.setOnClickListener(this);
        im.setTag(R.drawable.add);
        btnAdd = im;
        lin2.addView(im);
    }
}