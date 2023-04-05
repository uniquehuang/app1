package com.century.zj.ui.forget_password;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.century.zj.databinding.ActivityForgetBinding;

public class ForgetActivity extends AppCompatActivity {
    ActivityForgetBinding activityForgetBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgetBinding=ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(activityForgetBinding.getRoot());
    }
}