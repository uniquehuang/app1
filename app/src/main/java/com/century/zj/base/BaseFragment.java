package com.century.zj.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.dueeeke.videoplayer.player.VideoViewManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseFragment extends Fragment {
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initData();
//    }

    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(getActivity(), cls);
        in.putExtras(bundle);
        startActivity(in);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    //管理线程
    public <R> void  toSubscribe(rx.Observable<R> o, Subscriber<R> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    //视频播放器
    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }


}
