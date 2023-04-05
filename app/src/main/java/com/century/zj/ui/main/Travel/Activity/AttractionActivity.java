package com.century.zj.ui.main.Travel.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.century.zj.R;
import com.century.zj.base.BaseActivity;

import com.century.zj.databinding.ActivityAttractionBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.CollectAttraction;
import com.century.zj.entity.Comment;
import com.century.zj.entity.NoteEntity;
import com.century.zj.entity.Scenery;
import com.century.zj.entity.Weather;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.util.textSelect.SelectableTextHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AttractionActivity extends BaseActivity {

    ActivityAttractionBinding attractionBinding;
    private String title;
    private Toolbar toolbar;
    public static String CityId;
    public static String ARURL;
    public String IMGURL;
    private CommentBaseAdapter adapter;
    private List<Comment> commentArrayList
            = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    adapter.setDiffNewData(commentArrayList);
                    adapter.setNewData(commentArrayList);
                    break;
            }
        }
    };

    @Override
    protected View initLayout() {
        return attractionBinding.getRoot();
    }

    @Override
    protected void initView() {
        attractionBinding = ActivityAttractionBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        Intent intent =getIntent();
        title=intent.getStringExtra("title");
        getSceneryObject();
        toolbar=attractionBinding.toolbar;
        toolbar.setTitle(title);
        //设置折叠后的字体颜色
        attractionBinding.atToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this,R.color.smssdk_common_white));
        setSupportActionBar(attractionBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //返回
        toolbar.setNavigationIcon(R.drawable.returnback);
        attractionBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        attractionBinding.content.atAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ARURL!=null){
                    Intent intent =new Intent(AttractionActivity.this,ARActivity.class);
                    intent.putExtra("arurl",ARURL);
                    startActivity(intent);
                }
                else {
                    showToast("该景区AR功能暂未开放");
                }
            }
        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        attractionBinding.content.recycleView.setLayoutManager(linearLayoutManager);
        //baseAdapter
        adapter = new CommentBaseAdapter(R.layout.item_comment, commentArrayList);
        //设置不同数据发生改变
        adapter.setDiffCallback(new CommentBaseAdapter.DiffEventCallback());
        attractionBinding.content.recycleView.setAdapter(adapter);
        getCommentList();
        attractionBinding.content.atPostcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputServer = new EditText(mContext);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("请输入评论")
                        .setIcon(R.drawable.comment)
                        .setView(inputServer)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
               });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = inputServer.getText().toString();
                        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                        SharedPreferences shp=mContext.getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
                        if (shp.getString("userphone",null) != null){
                            Observable<BaseEntity<String>> observable = RetrofitFactory.getInstence().API().postComment(
                                    new Comment(title,text,shp.getString("username",null),shp.getString("userimg",null)));
                            observable.subscribeOn(Schedulers.io())
                                    .unsubscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressDialogSubscriber<BaseEntity<String>>(mContext) {
                                        @Override
                                        public void onNext(BaseEntity<String> noteEntityBaseEntity) {
                                            if (noteEntityBaseEntity.isSuccess()){
                                                Log.i("TAG", "onNext: "+noteEntityBaseEntity.getMsg());
                                                commentArrayList.clear();
                                                getCommentList();
                                                mHandler.sendEmptyMessage(0);
                                            }
                                        }
                                        @Override
                                        public void onError(Throwable e) {
                                            super.onError(e);
                                        }
                                    });

                        }else {
                            Toast.makeText(mContext, "用户未登录", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();

            }
        });

    }


    private void getSceneryObject(){
        Observable<BaseEntity<Scenery>> observable = RetrofitFactory.getInstence().API().getScenery(title);
        toSubscribe(observable, new ProgressDialogSubscriber<BaseEntity<Scenery>>(this) {
            @Override
            public void onNext(BaseEntity<Scenery> sceneryBaseEntity) {
                if (sceneryBaseEntity.isSuccess()) {
                    Scenery scenery = sceneryBaseEntity.getData();
                    ARURL=scenery.getArurl();
                    CityId=scenery.getCityid();
                    getWeatherDetailObject();
                    //过气方法
                    Glide.with(AttractionActivity.this)
                            .asBitmap()
                            .load(scenery.getSortimgurl())
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    Drawable drawable = new BitmapDrawable(resource);

                                    attractionBinding.floating.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                                    attractionBinding.floating.setImageDrawable(drawable);
                                }

                            });
                    Glide.with(getApplicationContext())
                            .load(scenery.getImgurl())
                            .into(new CustomTarget<Drawable>() {
                                @Nullable
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    // Do something with the Drawable here
                                    //找不到实例
                                    attractionBinding.atToolbarLayout.setBackground(resource);
                                }
                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                    // 从任何视图中删除onResourceReady中提供的Drawable，并确保不保留对它的引用。
                                }
                            });
                    attractionBinding.content.atInfo.setText(scenery.getSceneryinfo()
                            .replace("\\n","\n")
                            .replace("\\u","\u3000"));
                    Glide.with(getApplicationContext())
                            .load(scenery.getSimgurl())
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new RoundedCorners(8)
                                    )).into(attractionBinding.content.atImg);
                    IMGURL=scenery.getImgurl();
                    attractionBinding.content.atAboutInfo.setText(scenery.getAboutinfo()
                            .replace("\\n","\n")
                            .replace("\\u","\u3000"));
                    Glide.with(getApplicationContext())
                            .load(scenery.getAboutimgurl())
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new RoundedCorners(8)
                                    )).into(attractionBinding.content.atAboutImg);
                    attractionBinding.content.atMyname.setText(scenery.getPersonname());
                    Glide.with(getApplicationContext())
                            .load(scenery.getPersonimg())
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new RoundedCorners(8)
                                    )).into(attractionBinding.content.atMyimg);
                    attractionBinding.content.atUserComment.setText(scenery.getComment()
                            .replace("\\n","\n")
                            .replace("\\u","\u3000"));
                    if (scenery.getAcomment()!=null){
                        attractionBinding.content.atMynames.setText(scenery.getApersonname());
                        Glide.with(getApplicationContext())
                                .load(scenery.getApersonimg())
                                .apply(new RequestOptions()
                                        .transforms(new CenterCrop(), new RoundedCorners(8)
                                        )).into(attractionBinding.content.atMyimgs);
                        attractionBinding.content.atUserComments.setText(scenery.getAcomment()
                                .replace("\\n","\n")
                                .replace("\\u","\u3000"));
                    }


                }
            }
        });
    }


    private void getWeatherDetailObject() {
        Observable<Weather> observable = RetrofitFactory.getInstence().API().getWeather(CityId);
        toSubscribe(observable, new ProgressDialogSubscriber<Weather>(this) {
            @Override
            public void onNext(Weather weather) {
                attractionBinding.content.atCity.setText(weather.getCity());
                attractionBinding.content.atWeather.setText(weather.getWea());
                switch (weather.getWea()){
                    case "晴":attractionBinding.content.atWeatherimg.setBackgroundResource(R.drawable.sun);
                    break;
                    case "雾":attractionBinding.content.atWeatherimg.setBackgroundResource(R.drawable.wu);
                    break;
                    case "小雨":attractionBinding.content.atWeatherimg.setBackgroundResource(R.drawable.rain);
                    break;
                    default:attractionBinding.content.atWeatherimg.setBackgroundResource(R.drawable.cloudy);
                    break;
                }
                attractionBinding.content.atTemperature.setText(weather.getTem()+"℃");
                attractionBinding.content.atWind.setText(weather.getWin()+weather.getWin_speed());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attraction, menu);
        Log.i("2","菜单已经被创建--------");
        System.out.println("菜单被创建");
        return super.onCreateOptionsMenu(menu);
    }
    int flag=0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.collect:
                if (flag==0) {
                    item.setIcon(R.drawable.collected);
                    flag = 1;
                    showToast("收藏成功");

                }
                   else if (flag==1){
                    item.setIcon(R.drawable.collect);
                    flag=0;
                    showToast("取消成功");
                }
                SharedPreferences shp = mContext.getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
                if (shp.getString("userphone", null) != null) {
                    Observable<BaseEntity<CollectAttraction>> observable = RetrofitFactory.getInstence().API().collectAttraction(
                            new CollectAttraction(shp.getString("userphone", null),title,flag,IMGURL)
                    );
                    observable.subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ProgressDialogSubscriber<BaseEntity<CollectAttraction>>(mContext) {

                                @Override
                                public void onNext(BaseEntity<CollectAttraction> collectAttractionBaseEntity) {
                                    if (collectAttractionBaseEntity.isSuccess())
                                        Log.i("TAG", "onNext: " + collectAttractionBaseEntity.getMsg());

                                }

                                @Override
                                public void onError(Throwable e) {
                                    //super.onError(e);
                                }

                            });

                } else {
                    Toast.makeText(mContext, "用户未登录", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getCommentList() {
        Observable<BaseEntity<List<Comment>>> observable = RetrofitFactory.getInstence().API().getComment(title);
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<Comment>>>(this) {
                    @Override
                    public void onNext(BaseEntity<List<Comment>> listBaseEntity) {
                        List<Comment> list = listBaseEntity.getData();
                        commentArrayList=list;
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                                commentArrayList = list;
                        }
                        adapter.setDiffNewData(commentArrayList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }
        );
    }

}
