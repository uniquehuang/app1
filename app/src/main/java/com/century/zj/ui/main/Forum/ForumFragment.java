package com.century.zj.ui.main.Forum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.databinding.FragmentForumBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.Talk_Form;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.ui.main.Forum.write.WriteActivity;
import com.century.zj.util.UserConfigForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ForumFragment extends Fragment {
    FragmentForumBinding fragmentForumBinding;
    private ForumAdapter forumAdapter;
    //    private FriendAdapter adapter;
    List<Talk_Form> mFriendList=new ArrayList<>();
    public ForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentForumBinding= FragmentForumBinding.inflate(getLayoutInflater());
        return fragmentForumBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences shp=requireActivity().getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
        if (shp.getString("userimg",null)!=null){
            Glide.with(requireContext()).load(shp.getString("userimg",null)).into(fragmentForumBinding.image1Head);
        }
        fragmentForumBinding.forumName.setText(shp.getString(UserConfigForm.UN,""));
        fragmentForumBinding.writeTextWord.setOnClickListener(v -> {
            if (shp.getInt("USER",0)==1){
                startActivity(new Intent(requireContext(), WriteActivity.class));
            }else {
                new AlertDialog.Builder(getContext()).setMessage("未登录无法进操作").setPositiveButton("确认",(dialog,which)->{}).show();
            }
        });
        fragmentForumBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            ImageView imageView = fragmentForumBinding.image1Head;
            if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) { // 折叠状态
//                collapsingToolbar.setTitle("朋友圈");
                imageView.setVisibility(View.GONE);
            } else { // 非折叠状态
//                collapsingToolbar.setTitle("");
                imageView.setVisibility(View.VISIBLE);
            }
        });
        //设置item布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentForumBinding.recyclerView.setLayoutManager(linearLayoutManager);
        //baseAdapter
        forumAdapter = new ForumAdapter(R.layout.fragment_forum_card, mFriendList);
        //设置不同数据发生改变
        forumAdapter.setDiffCallback(new ForumAdapter.DiffEventCallback());
        fragmentForumBinding.recyclerView.setAdapter(forumAdapter);
        getEventListByPage();
    }

    private void getEventListByPage() {
        Observable<BaseEntity<List<Talk_Form>>> observable = RetrofitFactory.getInstence().API().upForum();
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
                        mFriendList.clear();
                        mFriendList = listBaseEntity.getData();
                        forumAdapter.setDiffNewData(mFriendList);
                        forumAdapter.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        Timer timer=new Timer();//实例化Timer类
        timer.schedule(new TimerTask(){
            public void run(){
                getEventListByPage();}},500);//五百毫秒
    }


}