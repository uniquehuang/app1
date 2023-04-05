package com.century.zj.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAtViewPager2 extends RecyclerView {
    public RecyclerViewAtViewPager2(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewAtViewPager2(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewAtViewPager2(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);  //设置不拦截
        return super.dispatchTouchEvent(ev);
    }
}
