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

public class TvActivity extends AppCompatActivity {

    int valueVolume = 0;
    int valueChannel = 0;

    Button btnIncreaseVolume, btnDecreaseVolume, btnIncreaseChannel, btnDecreaseChannel;
    TextView tvValueVolume, tvValueChannel;
    LabeledSwitch aSwitch;
    String username, deviceKey;
    DatabaseReference TvRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        initialize();

    }

    private void initialize() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        deviceKey = intent.getStringExtra("deviceKey");
        TvRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
        Toast.makeText(this, "This is "+ TvRef, Toast.LENGTH_SHORT).show();
        aSwitch=findViewById(R.id.switchTv);
        aSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == false){
                    TvRef.setValue("OFF");
                }else {
                    TvRef.setValue("ON");
                }
            }
        });
        btnDecreaseVolume = findViewById(R.id.btnDecreaseVolume);
        btnDecreaseVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueVolume > 0)
                    valueVolume = valueVolume - 1;
                displayVolume(valueVolume);
            }
        });
        btnIncreaseVolume = findViewById(R.id.btnIncreaseVolume);
        btnIncreaseVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueVolume < 100)
                    valueVolume = valueVolume + 1;
                displayVolume(valueVolume);
            }
        });
        tvValueVolume = findViewById(R.id.tvValueVolume);
        valueVolume = Integer.valueOf(tvValueVolume.getText().toString());







        btnDecreaseChannel = findViewById(R.id.btnDecreaseChannel);
        btnDecreaseChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueChannel > 1)
                    valueChannel = valueChannel - 1;
                displayChannel(valueChannel);
            }
        });
        btnIncreaseChannel = findViewById(R.id.btnIncreaseChannel);
        btnIncreaseChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueChannel < 50)
                    valueChannel = valueChannel + 1;
                displayChannel(valueChannel);
            }
        });
        tvValueChannel = findViewById(R.id.tvValueChannel);
        valueChannel = Integer.valueOf(tvValueChannel.getText().toString());


    }

    private void displayVolume(int number) {
        tvValueVolume.setText( String.valueOf(number));
    }
    private void displayChannel(int number) {
        tvValueChannel.setText( String.valueOf(number));
    }
}