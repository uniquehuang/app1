package com.century.zj.ui.main.Game.achievement;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class MoveImageView extends ImageView {

    //按下那一刻的坐标和 控件上下左右距离
    private float lastX;
    private float lastY;
    private int left;
    private int top;
    private int right;
    private int bottom;


    //如果是拖动事件就不用响应点击事件
    boolean isMove = false;
    boolean isAnimatoring = false;

    //屏幕宽高
    private int screenWidthPx;
    private int screenHeightPx;


    public MoveImageView(Context context) {
        this(context, null);
    }

    public MoveImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidthPx = getScreenWidthPx(getContext());
        screenHeightPx = getScreenHeightPx(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //动画执行过程中，不响应一切操作，
                // 这里相当于不让其走后面的MotionEvent.ACTION_MOVE:与MotionEvent.ACTION_UP:
                if (isAnimatoring) {
                    return false;
                }
                lastX = event.getRawX();
                lastY = event.getRawY();
                left = getLeft();
                top = getTop();
                right = getRight();
                bottom = getBottom();
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                float x = event.getRawX();
                float y = event.getRawY();


                int l = (int) (left + (x - lastX));
                int t = (int) (top + (y - lastY));
                int r = (int) (right + (x - lastX));
                int b = (int) (bottom + (y - lastY));
                layout(l, t, r, b);
                break;
            case MotionEvent.ACTION_UP:
                if (isMove) {
                    //如果顶部拖出屏幕外面，回正
                    if (getTop() < 0) {
                        layout(getLeft(), 0, getRight(), getHeight());
                    }

                    //getBottom() 获取到的是 控件底部到父容器顶部的距离，所以需要减去状态栏的高度
                    int bottomHeight = screenHeightPx - getStatusBarHeight(getContext());
                    //如果底部拖出屏幕外面，回正
                    if (getBottom() > bottomHeight) {
                        layout(getLeft(), bottomHeight-getHeight(), getRight(), bottomHeight);
                    }
                    isMove = false;
                    startAnimation();
                    return true;
                }
                return super.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private void startAnimation() {

        isAnimatoring = true;

        //右边距
        int marinRight = 20;
        int endValue = screenWidthPx - marinRight;
        ValueAnimator animator = ValueAnimator.ofInt(getRight(), endValue);

        animator.setDuration(Math.abs(endValue - getRight()) > 1000 ? 1000 : Math.abs(endValue - getRight()));

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                layout(curValue - getWidth(), getTop(), curValue, getHeight() + getTop());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimatoring = false;
                animator.removeAllUpdateListeners();
                animator.removeAllListeners();
            }
        });
        animator.start();
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 24;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;
    }

    public static int getScreenWidthPx(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (windowManager != null) {
//   windowManager.getDefaultDisplay().getMetrics(dm);
            windowManager.getDefaultDisplay().getRealMetrics(dm);
            return dm.widthPixels;
        }
        return 0;

    }

    public static int getScreenHeightPx(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (windowManager != null) {
//   windowManager.getDefaultDisplay().getMetrics(dm);
            windowManager.getDefaultDisplay().getRealMetrics(dm);
            return dm.heightPixels;
        }
        return 0;

    }

}
