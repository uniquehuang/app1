package com.century.zj.ui.splash;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.databinding.ActivitySplashBinding;
import com.century.zj.ui.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private static final long LOADING_TIME = 5000;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToMain();
            }
        });
        /*Glide.with(this).asGif().load(R.drawable.app_splash_bg_4).into(binding.splashLoadingBak);
        startAnim();
        keepLoading();*/
        Glide.with(this)
                .asGif()
                .load("http://39.108.56.102:8083/resource/img/5.gif")   // 请求图片的路径,可以是网络图片
                .into(binding.splashLoadingBak);        // 显示到ImageView 上面,icon为绑定了ImageView控件的对象
        startAnim();
        keepLoading();
    }

    private void keepLoading() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        skipToMain();
                    }
                });
            }
        }, LOADING_TIME);
    }

    private void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.splash_loading_anim);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.splashLoadingItem.startAnimation(animation);
    }


    private void skipToMain() {
        timer.cancel();
        binding.splashLoadingItem.clearAnimation();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
        finish();
    }


}
