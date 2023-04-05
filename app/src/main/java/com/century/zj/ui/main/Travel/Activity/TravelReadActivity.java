package com.century.zj.ui.main.Travel.Activity;

import android.os.Bundle;

import com.century.zj.databinding.ActivityTravelreadBinding;

import androidx.appcompat.app.AppCompatActivity;

public class TravelReadActivity extends AppCompatActivity {

    ActivityTravelreadBinding travelreadBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travelreadBinding = ActivityTravelreadBinding.inflate(getLayoutInflater());
        setContentView(travelreadBinding.getRoot());
    }

}
