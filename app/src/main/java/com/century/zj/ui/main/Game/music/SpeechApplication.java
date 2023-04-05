package com.century.zj.ui.main.Game.music;


import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

public class SpeechApplication extends Application {

    @Override
    public void onCreate() {

        //   5ef048e1  为在开放平台注册的APPID  注意没有空格，直接替换即可
        SpeechUtility.createUtility(SpeechApplication.this, "appid=f5309970");

        super.onCreate();
    }
}


