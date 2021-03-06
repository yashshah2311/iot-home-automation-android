package com.example.iot_home_automation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener, ChildEventListener, AdapterView.OnItemClickListener {
    ListView lvMenu;
    ArrayList<String> listOfMenu;
    ArrayAdapter<String> menuAdapter;
    LinearLayout lin1, lin2;
    ImageView btnAdd;
    String username;
    Button btnConnect;
    DatabaseReference  usersTable, userDevice;
    DrawerLayout drawerLayout;
    String bulbKey, tvKey, fridgeKey, acKey, soilKey, deviceKey, deviceActualValue, bulbValue;
    ArrayList<Device> devices;
    String tvValue, fridgeValue,soilValue,acValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    private void initialize() {
        lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setOnItemClickListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        btnConnect = findViewById(R.id.btnAddConnection);
        btnConnect.setOnClickListener(this);
        listOfMenu = new ArrayList<String>();
        listOfMenu.add("Home");
        listOfMenu.add("Logout");
        menuAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfMenu);
        lvMenu.setAdapter(menuAdapter);
        Intent intent = getIntent();
        username = intent.getStringExtra("user");


        lin1 = findViewById(R.id.lin1);
        lin2 = findViewById(R.id.lin2);

        usersTable = FirebaseDatabase.getInstance().getReference("users");

        devices = new ArrayList<Device>();




    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAddConnection){
            ViewGroup layout = (ViewGroup) btnConnect.getParent();
            if(layout != null)
                layout.removeView(btnConnect);
            findDevices();
            addPlusButton();
        }
        else{
            int drawableId = (Integer)v.getTag();
            Intent intent;
            switch(drawableId){
                case R.drawable.lightbulb:
                    deviceKey = bulbKey;
                    deviceActualValue = bulbValue;
                    Toast.makeText(this, "The Bulb was called successfully", Toast.LENGTH_LONG).show();
                    intent=new Intent(HomeActivity.this,LightBulbActivity.class);
                    intent.putExtra("user", username);
                    intent.putExtra("deviceKey", deviceKey);
                    intent.putExtra("deviceActualValue", deviceActualValue);

                    startActivity(intent);
                    break;
                case R.drawable.tv:
                    deviceKey = tvKey;
                    deviceActualValue = tvValue;
                    Toast.makeText(this, "The TV was called successfully", Toast.LENGTH_LONG).show();
                    intent=new Intent(HomeActivity.this,TvActivity.class);
                    intent.putExtra("user", username);
                    intent.putExtra("deviceKey", deviceKey);
                    intent.putExtra("deviceActualValue", deviceActualValue);
                    startActivity(intent);
                    break;
                case R.drawable.ac:
                    deviceKey = acKey;
                    deviceActualValue = acValue;
                    Toast.makeText(this, "The AC was called successfully", Toast.LENGTH_LONG).show();
                    intent=new Intent(HomeActivity.this,ACActivity.class);
                    intent.putExtra("user", username);
                    intent.putExtra("deviceKey", deviceKey);
                    intent.putExtra("deviceActualValue", deviceActualValue);
                    startActivity(intent);
                    break;
                case R.drawable.fridge:
                    deviceActualValue = fridgeValue;
                    deviceKey = fridgeKey;
                    Toast.makeText(this, "The fridge was called successfully", Toast.LENGTH_LONG).show();
                    intent=new Intent(HomeActivity.this,FridgeActivity.class);
                    intent.putExtra("user", username);
                    intent.putExtra("deviceKey", deviceKey);
                    intent.putExtra("deviceActualValue", deviceActualValue);
                    startActivity(intent);
                    break;
                case R.drawable.soil_moisture:
                    deviceKey = soilKey;
                    deviceActualValue = soilValue;
                    Toast.makeText(this, "The soil moisture was called successfully", Toast.LENGTH_LONG).show();
                    intent=new Intent(HomeActivity.this,SoilMoistureActivity.class);
                    intent.putExtra("user", username);
                    intent.putExtra("deviceKey", deviceKey);
                    intent.putExtra("deviceActualValue", deviceActualValue);
                    startActivity(intent);
                    break;
                case R.drawable.add:
                    Intent intentAdd=new Intent(HomeActivity.this,AddDeviceActivity.class);
                    intentAdd.putExtra("user", username);
                    startActivity(intentAdd);
                    break;
            }
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
                bulbValue = myDevice.getActualValue();
                bulbKey = String.valueOf(devices.size() - 1);
                im.setImageResource(R.drawable.lightbulb);
                im.setTag(R.drawable.lightbulb);
                break;
            case "TV":
                tvValue = myDevice.getActualValue();
                tvKey = String.valueOf(devices.size() - 1);
                im.setImageResource(R.drawable.tv);
                im.setTag(R.drawable.tv);
                break;
            case "Fridge":
                fridgeValue = myDevice.getActualValue();
                fridgeKey = String.valueOf(devices.size() - 1);
                im.setImageResource(R.drawable.fridge);
                im.setTag(R.drawable.fridge);
                break;
            case "AC":
                acValue= myDevice.getActualValue();
                acKey = String.valueOf(devices.size() - 1);
                im.setImageResource(R.drawable.ac);
                im.setTag(R.drawable.ac);
                break;
            case "Soil Moisture":
                soilValue = myDevice.getActualValue();
                soilKey = String.valueOf(devices.size() - 1);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        drawerLayout.closeDrawer(lvMenu);
        String menu = listOfMenu.get(i);
        switch(menu){
            case "Home":
                Intent intent=new Intent(HomeActivity.this,HomeActivity.class);
                intent.putExtra("user", username);
                startActivity(intent);
                break;
            case "Logout":
                Intent intent1=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
        }
    }
}