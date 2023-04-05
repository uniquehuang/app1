package com.century.zj.ui.main.Game.song;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.century.zj.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_pause, iv;
    private ObjectAnimator mAnimator;
    private MediaPlayer mPlayer;
    private TextView tv;
    int flag=1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        initAnimator();//进入页面加载动画
        initPlayer();//进入页面加载音乐
        tv.setOnClickListener(v -> finish());
    }

    private void initPlayer() {
        mPlayer = MediaPlayer.create(this, R.raw.liuyingzhige);
        mPlayer.setLooping(true);//设置为循环播放
        mPlayer.start();//开始播放音乐
        mPlayer.pause();//暂停播放音乐
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initAnimator() {
        mAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0.0f, 360.0f);
        mAnimator.setDuration(3000);//设定转一圈的时间
        mAnimator.setRepeatCount(Animation.INFINITE);//设定无限循环
        mAnimator.setRepeatMode(ObjectAnimator.RESTART);// 循环模式
        mAnimator.setInterpolator(new LinearInterpolator());// 匀速
        mAnimator.start();//动画开始
        mAnimator.pause();//动画暂停
    }

    private void initView() {
        iv_pause = findViewById(R.id.iv_pause);
        iv = findViewById(R.id.iv);
        iv_pause.setOnClickListener(this);
        tv= findViewById(R.id.music_back);
    }

    private boolean isPause = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.iv_pause) {
            if (!isPause) {
                iv_pause.setBackgroundResource(R.drawable.iv_pause);
                mAnimator.resume();//动画继续
                mPlayer.start();//继续开始音乐播放
                isPause = true;
                if(flag==1){
                    showDialog();
                    flag++;
                }


            } else {
                iv_pause.setBackgroundResource(R.drawable.iv_start);
                mAnimator.pause();//动画暂停
                mPlayer.pause();//暂停音乐播放
                isPause = false;
            }
        }
    }

    @Override
    protected void onDestroy() {//销毁
        super.onDestroy();
        mPlayer.stop();//停止播放音乐
    }
    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.comment);
        builder.setTitle("刘英之歌");
        builder.setMessage(R.string.liuying);
        builder.setPositiveButton("我知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

}