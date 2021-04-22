package com.example.iot_home_automation;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xw.repo.BubbleSeekBar;

public class SoilMoistureActivity extends AppCompatActivity {

    TextView tvValue;
    BubbleSeekBar bubbleSeekBar;
    LabeledSwitch aSwitch;
    String username, deviceKey;
    DatabaseReference soilRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_moisture);

        initialize();
    }

    private void initialize() {

        try {
            FirebaseDatabase db = FirebaseDatabase.getInstance();

            Intent intent = getIntent();
            username = intent.getStringExtra("user");
            deviceKey = intent.getStringExtra("deviceKey");
            soilRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
            Toast.makeText(this, "This is "+ soilRef, Toast.LENGTH_SHORT).show();

            aSwitch = findViewById(R.id.switchSoil);

            aSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == false){
                    soilRef.setValue("OFF");
                    aSwitch.setOn(false);
                }else {
                    soilRef.setValue("ON");
                    aSwitch.setOn(true);
                }
            }
            });

            tvValue = findViewById(R.id.tvType);
            bubbleSeekBar = findViewById(R.id.seekBar);
            bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
                @SuppressLint("ResourceAsColor")
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                    if (progress < 4) {
                        tvValue.setText("Dry");

                        bubbleSeekBar.setSecondTrackColor(getResources().getColor(R.color.redd));
                    } else if (progress > 7) {
                        tvValue.setText("Wet");
                        bubbleSeekBar.setSecondTrackColor(getResources().getColor(R.color.blue));
                    } else {
                        tvValue.setText("Moist");
                        bubbleSeekBar.setSecondTrackColor(getResources().getColor(R.color.green));
                    }
                }

                @Override
                public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                }

                @Override
                public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                }
            });

        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }

    }

}