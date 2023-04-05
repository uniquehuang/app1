package com.century.zj.ui.mine.cricle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.century.zj.R;
import com.century.zj.databinding.ActivityCricleBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.Talk_Form;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.ui.main.Forum.ForumAdapter;


import java.util.ArrayList;
import java.util.List;

public class CircleActivity extends AppCompatActivity {
    ActivityCricleBinding circleBinding;
    private ForumAdapter circleAdapter;
    //    private FriendAdapter adapter;
    List<Talk_Form> mFriendList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circleBinding=ActivityCricleBinding.inflate(getLayoutInflater());
        setContentView(circleBinding.getRoot());
        circleBinding.circleBack.setOnClickListener(v -> finish());
        //设置item布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        circleBinding.circleRecycle.setLayoutManager(linearLayoutManager);
        //baseAdapter
        circleAdapter = new ForumAdapter(R.layout.fragment_forum_card, mFriendList);
        //设置不同数据发生改变
        circleAdapter.setDiffCallback(new ForumAdapter.DiffEventCallback());
        circleBinding.circleRecycle.setAdapter(circleAdapter);
    }

    private void getEventListByPage() {
        SharedPreferences shp=this.getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
        Observable<BaseEntity<List<Talk_Form>>> observable = RetrofitFactory.getInstence().API().getMineForum(shp.getString("userphone",null));
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<Talk_Form>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<Talk_Form>> listBaseEntity) {
                        List<Talk_Form> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            mFriendList = list;
                            circleAdapter.setDiffNewData(mFriendList);
                            circleAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEventListByPage();
    }
}