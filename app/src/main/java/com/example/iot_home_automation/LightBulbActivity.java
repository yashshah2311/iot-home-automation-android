package com.example.iot_home_automation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LightBulbActivity extends AppCompatActivity {
    ImageView imageBulb;

    LabeledSwitch aSwitch;
    String username, deviceKey, deviceActualValue;
    DatabaseReference bulbRef;
    LinearLayout llBulb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_bulb);
        initialize();
    }

    private void initialize() {
        try {
            llBulb = findViewById(R.id.llBulb);
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            imageBulb = findViewById(R.id.imaBulb);

            Intent intent = getIntent();
            username = intent.getStringExtra("user");
            deviceKey = intent.getStringExtra("deviceKey");
            deviceActualValue = intent.getStringExtra("deviceActualValue");
            bulbRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
            //Toast.makeText(this, "This is "+ bulbRef, Toast.LENGTH_SHORT).show();

            aSwitch = findViewById(R.id.switchBulb);
            if(deviceActualValue.equals("ON")){
                aSwitch.setOn(true);
                llBulb.setVisibility(View.VISIBLE);
            }else{
                aSwitch.setOn(false);
                llBulb.setVisibility(View.GONE);
            }

            aSwitch.setOnToggledListener(new OnToggledListener() {
                @Override
                public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                    if(isOn == false){
                        bulbRef.setValue("OFF");

                    }else {
                        bulbRef.setValue("ON");
                    }
                }
            });


        }catch (Exception e){

            Log.d("Error",e.getMessage());
        }


    }
}