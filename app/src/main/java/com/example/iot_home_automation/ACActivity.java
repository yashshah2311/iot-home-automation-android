package com.example.iot_home_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.angads25.toggle.widget.LabeledSwitch;

public class ACActivity extends AppCompatActivity  {
    int value = 0;
    Button btnIncrease, btnDecrease;
    TextView tvValue;
    LabeledSwitch aSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c);
        initialize();

    }

    private void initialize() {
        aSwitch=findViewById(R.id.switchAC);
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