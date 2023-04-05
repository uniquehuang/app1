package com.century.zj.ui.main.Travel;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.century.zj.R;
import com.century.zj.databinding.FragmentTravelBinding;
import com.century.zj.ui.main.Travel.Activity.TravelActivity;
import com.century.zj.util.NbButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class TravelFragment extends Fragment {

    private NbButton button;
    private RelativeLayout rlContent;
    private Handler handler;
    private Animator animator;
    FragmentTravelBinding travelBinding;

    public TravelFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       travelBinding= FragmentTravelBinding.inflate(inflater);
       return travelBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //绑定两个控件
       rlContent=travelBinding.rlContent;
       button=travelBinding.buttonTest;
        //将relative的背景设置为透明
        rlContent.getBackground().setAlpha(0);

        handler=new Handler();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.startAnim();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //跳转
                        gotoNew();
                    }
                },3000);

            }
        });
    }
    private void gotoNew() {
        button.gotoNew();

        final Intent intent=new Intent(getActivity(), TravelActivity.class);

        int xc=(button.getLeft()+button.getRight())/2;
        int yc=(button.getTop()+button.getBottom())/2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animator= ViewAnimationUtils.createCircularReveal(rlContent,xc,yc,0,1111);
        }
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    }
                },200);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        rlContent.getBackground().setAlpha(255);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Tag","-----------start---------------");
        rlContent.getBackground().setAlpha(0);
        button.Background();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Tag","-------------resume------");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Tag","-------------Pause------");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Tag","-------------Stop------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag","-------------DestroyView------");
    }


}