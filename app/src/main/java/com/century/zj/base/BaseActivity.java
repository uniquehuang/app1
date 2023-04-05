package com.century.zj.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.dueeeke.videoplayer.player.VideoViewManager;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        setContentView(initLayout());
        initData();
    }

    protected abstract View initLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void navigateTo(Class cls) {
        Intent in = new Intent(mContext, cls);
        startActivity(in);
    }

    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(mContext, cls);
        in.setFlags(flags);
        startActivity(in);
    }

    protected void insertVal(String key, String val) {
        SharedPreferences sp = getSharedPreferences("User_Tab", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.apply();
    }

    protected String findByKey(String key) {
        SharedPreferences sp = getSharedPreferences("User_Tab", MODE_PRIVATE);
        return sp.getString(key, "");
    }

    //管理线程
    public <R> void  toSubscribe(rx.Observable<R> o, Subscriber<R> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }

}
