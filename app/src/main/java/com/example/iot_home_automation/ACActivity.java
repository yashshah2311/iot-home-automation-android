package com.example.iot_home_automation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ACActivity extends AppCompatActivity  {
    int value = 0;
    Button btnIncrease, btnDecrease;
    TextView tvValue;
    LabeledSwitch aSwitch;
    String username, deviceKey;
    DatabaseReference acRef;
    String deviceActualValue;
    LinearLayout llTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c);
        initialize();

    }

    private void initialize() {
        llTemperature = findViewById(R.id.llTemperature);
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        deviceKey = intent.getStringExtra("deviceKey");
        deviceActualValue = intent.getStringExtra("deviceActualValue");
        acRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
        //Toast.makeText(this, "This is "+ acRef, Toast.LENGTH_SHORT).show();

        aSwitch=findViewById(R.id.switchAC);
        if(deviceActualValue.equals("ON")){
            aSwitch.setOn(true);
            llTemperature.setVisibility(View.VISIBLE);
        }else{
            aSwitch.setOn(false);
            llTemperature.setVisibility(View.GONE);
        }

        aSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == false){
                    acRef.setValue("OFF");
                }else {
                    acRef.setValue("ON");
                }
            }
        });
        btnDecrease = findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value > 14)
                    value = value - 1;
                display(value);
            }
        });
        btnIncrease = findViewById(R.id.btnIncrease);
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value < 30)
                    value = value + 1;
                display(value);
            }
        });
        tvValue = findViewById(R.id.tvValue);
        value = Integer.valueOf(tvValue.getText().toString());

    }


    private void display(int number) {
        tvValue.setText( String.valueOf(number));
    }


}