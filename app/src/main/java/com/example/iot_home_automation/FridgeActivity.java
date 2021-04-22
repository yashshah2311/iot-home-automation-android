package com.example.iot_home_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FridgeActivity extends AppCompatActivity {
    int valueFridge = 0;
    int valueFreezer = 0;

    Button btnIncreaseFridge, btnDecreaseFridge, btnIncreaseFreezer, btnDecreaseFreezer;
    TextView tvValueFridge, tvValueFreezer;
    LabeledSwitch aSwitch;
    String username, deviceKey;
    DatabaseReference fridgeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        initialize();

    }

    private void initialize() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        deviceKey = intent.getStringExtra("deviceKey");
        fridgeRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
        Toast.makeText(this, "This is "+ fridgeRef, Toast.LENGTH_SHORT).show();
        aSwitch=findViewById(R.id.switchFridge);
        aSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == false){
                    fridgeRef.setValue("OFF");
                }else {
                    fridgeRef.setValue("ON");
                }
            }
        });
        btnDecreaseFridge = findViewById(R.id.btnDecreaseFridge);
        btnDecreaseFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueFridge > 1)
                    valueFridge = valueFridge - 1;
                displayFridge(valueFridge);
            }
        });
        btnIncreaseFridge = findViewById(R.id.btnIncreaseFridge);
        btnIncreaseFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueFridge < 9)
                    valueFridge = valueFridge + 1;
                displayFridge(valueFridge);
            }
        });
        tvValueFridge = findViewById(R.id.tvValueFridge);
        valueFridge = Integer.valueOf(tvValueFridge.getText().toString());







        btnDecreaseFreezer = findViewById(R.id.btnDecreaseFreezer);
        btnDecreaseFreezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueFreezer > -7)
                    valueFreezer = valueFreezer - 1;
                displayFreezer(valueFreezer);
            }
        });
        btnIncreaseFridge = findViewById(R.id.btnIncreaseFreezer);
        btnIncreaseFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueFreezer < -1)
                    valueFreezer = valueFreezer + 1;
                displayFreezer(valueFreezer);
            }
        });
        tvValueFreezer = findViewById(R.id.tvValueFreezer);
        valueFreezer = Integer.valueOf(tvValueFreezer.getText().toString());


    }

    private void displayFridge(int number) {
        tvValueFridge.setText( String.valueOf(number));
    }
    private void displayFreezer(int number) {
        tvValueFreezer.setText( String.valueOf(number));
    }

}