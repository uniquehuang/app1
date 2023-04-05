package com.century.zj.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zhouwei.mzbanner.MZBannerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyBanner extends MZBannerView {

    public MyBanner(@NonNull Context context) {
        super(context);
    }

    public MyBanner(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBanner(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyBanner(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int startX, startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);  //设置不拦截

        return super.dispatchTouchEvent(ev);
    }

}