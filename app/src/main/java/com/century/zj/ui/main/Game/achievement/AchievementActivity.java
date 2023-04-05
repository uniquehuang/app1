package com.century.zj.ui.main.Game.achievement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.century.zj.R;
import com.century.zj.databinding.ActivityAchievementBinding;

public class AchievementActivity extends AppCompatActivity {
    private HeaderWaveHelper mHeaderWaveHelper;
    ActivityAchievementBinding activityAchievementBinding;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAchievementBinding = ActivityAchievementBinding.inflate(getLayoutInflater());
        setContentView(activityAchievementBinding.getRoot());
        HeaderWaveView waveView =activityAchievementBinding.headerWaveView;
        ImageView imageView = activityAchievementBinding.imageView;
        final ScrollView mScrollView = activityAchievementBinding.sv;

        mHeaderWaveHelper = new HeaderWaveHelper(waveView, Color.parseColor("#80FC7A8C"),Color.parseColor("#40FB3D53"),imageView);
        SharedPreferences sharedPreferences=getSharedPreferences("User_Tab",MODE_PRIVATE);
        if (sharedPreferences.getInt("readnumber",0)>0){
            activityAchievementBinding.read.setImageResource(R.drawable.read_one_already);
            activityAchievementBinding.beginRead.setImageResource(R.drawable.begin_read_already);
            activityAchievementBinding.numberGame.setText(String.valueOf(2));
        }else {
            activityAchievementBinding.read.setImageResource(R.drawable.read_one);
            activityAchievementBinding.beginRead.setImageResource(R.drawable.begin_read);
            activityAchievementBinding.numberGame.setText(String.valueOf(0));
        }

        //SDK API23以下请自行继承ScrollView实现该方法。
        mScrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {

            if (mScrollView.getScrollY() > 85) {
                mHeaderWaveHelper.cancel();
            } else {
                mHeaderWaveHelper.start();
            }

        });

        activityAchievementBinding.backGame.setOnClickListener(v -> finish());
    }
    @Override
    protected void onPause() {
        super.onPause();
        mHeaderWaveHelper.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHeaderWaveHelper.start();
    }
}