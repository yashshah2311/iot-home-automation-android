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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xw.repo.BubbleSeekBar;

import java.util.Random;

public class SoilMoistureActivity extends AppCompatActivity {

    TextView tvValue;
    BubbleSeekBar bubbleSeekBar;
    LabeledSwitch aSwitch;
    String username, deviceKey;
    DatabaseReference soilRef;
    String deviceActualValue;
    LinearLayout llbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_moisture);

        initialize();
    }

    private void initialize() {

        try {
            llbar = findViewById(R.id.llbar);
            Random random = new Random();
            int moisture = random.nextInt(10-1)+1;
            FirebaseDatabase db = FirebaseDatabase.getInstance();

            Intent intent = getIntent();
            username = intent.getStringExtra("user");
            deviceKey = intent.getStringExtra("deviceKey");
            deviceActualValue = intent.getStringExtra("deviceActualValue");
            soilRef = db.getReference("users").child(username).child("devicesList").child(deviceKey).child("actualValue");
            //Toast.makeText(this, "This is "+ soilRef, Toast.LENGTH_SHORT).show();

            aSwitch = findViewById(R.id.switchSoil);
            if(deviceActualValue.equals("ON")){
                aSwitch.setOn(true);
                llbar.setVisibility(View.VISIBLE);
            }else{
                aSwitch.setOn(false);
                llbar.setVisibility(View.GONE);
            }

            aSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == false){
                    soilRef.setValue("OFF");
                }else {
                    soilRef.setValue("ON");
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
            bubbleSeekBar.setProgress(moisture);

        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }

    }

}