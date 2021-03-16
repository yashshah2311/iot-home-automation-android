package com.example.iot_home_automation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.iot_home_automation.AddDeviceActivity;
import com.example.iot_home_automation.HomeActivity;
import com.example.iot_home_automation.LightBulbActivity;
import com.example.iot_home_automation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Device;

public class HomeFragment extends Fragment  {

    //private HomeViewModel homeViewModel;
    LinearLayout lin1, lin2;
    ImageView btnAdd;
    String username;
    DatabaseReference usersTable, userDevice;

    List<Device> devices;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        initialize(root);
        return root;

    }

    private void initialize(View v) {
        //Intent intent = getIntent();
        //username = intent.getStringExtra("user");


        lin1 = v.findViewById(R.id.lin1);
        lin2 = v.findViewById(R.id.lin2);

        usersTable = FirebaseDatabase.getInstance().getReference("users");

        devices = new ArrayList<Device>();


    }





}