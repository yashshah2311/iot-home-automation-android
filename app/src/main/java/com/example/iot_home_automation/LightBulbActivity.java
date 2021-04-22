package com.example.iot_home_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LightBulbActivity extends AppCompatActivity {
    ImageView imageBulb;

    LabeledSwitch aSwitch;
    String username, deviceKey;
    DatabaseReference bulbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_bulb);
        initialize();
    }

    private void initialize() {
        try {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            imageBulb = findViewById(R.id.imaBulb);

            Intent intent = getIntent();
            username = intent.getStringExtra("user");
            deviceKey = intent.getStringExtra("deviceKey");
            bulbRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
            Toast.makeText(this, "This is "+ bulbRef, Toast.LENGTH_SHORT).show();

            aSwitch = findViewById(R.id.switchBulb);

            aSwitch.setOnToggledListener(new OnToggledListener() {
                @Override
                public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                    if(isOn == false){
                        bulbRef.setValue("OFF");
                        aSwitch.setOn(false);
                    }else {
                        bulbRef.setValue("ON");
                        aSwitch.setOn(true);
                    }
                }
            });

        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }


    }
}