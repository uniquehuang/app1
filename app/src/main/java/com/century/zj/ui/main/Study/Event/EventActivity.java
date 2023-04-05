package com.century.zj.ui.main.Study.Event;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.century.zj.R;
import com.century.zj.base.BaseActivity;

import com.century.zj.databinding.ActivityEventBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.EventEntity;
import com.century.zj.entity.ReadEntity;
import com.century.zj.entity.VoiceEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.util.ShareSheetDialog;
import com.century.zj.util.textSelect.SelectableTextHelper;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import rx.Observable;

import static java.text.MessageFormat.format;

public class EventActivity extends BaseActivity {
    private ActivityEventBinding binding;
    private String id;
    private String title;
    private MediaPlayer mediaPlayer;
    private BaseEntity<EventEntity> datas;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
//                    getVoice();
                    break;
            }
        }
    };


    @Override
    protected View initLayout() {
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding = ActivityEventBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        //获取点击事件传进的url
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            title = bundle.getString("title");

        }
        //获取toolbar
        binding.toolbar.setTitle(title);
        setSupportActionBar(binding.toolbar);
        binding.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this , R.color.white));
        binding.toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this , R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //实现回退功能
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.floating.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_book_24));
                Observable<BaseEntity<String>> observable3 = RetrofitFactory.getInstence().API()
                        .updateUserRead(new ReadEntity(Integer.parseInt(id),1,findByKey("userphone")));
                toSubscribe(observable3, new ProgressDialogSubscriber<BaseEntity<String>>(getApplicationContext()) {
                    @Override
                    public void onNext(BaseEntity<String> readEntityBaseEntity) {
                        if (readEntityBaseEntity.isSuccess()){
                            Log.i("TAG", "onNext: "+readEntityBaseEntity.getMsg());
                        }
                    }
                });
                Snackbar.make(v, "你已阅读此文章", Snackbar.LENGTH_LONG)
                        .setAction("取消阅读标记", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("取消成功");
                                binding.floating.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_bookmark_border_24));
                                Observable<BaseEntity<String>> observable4 = RetrofitFactory.getInstence().API()
                                        .updateUserRead(new ReadEntity(Integer.parseInt(id),0,findByKey("userphone")));
                                toSubscribe(observable4, new ProgressDialogSubscriber<BaseEntity<String>>(getApplicationContext()) {
                                    @Override
                                    public void onNext(BaseEntity<String> readEntityBaseEntity) {
                                        if (readEntityBaseEntity.isSuccess()){
                                            Log.i("TAG", "onNext: "+readEntityBaseEntity.getMsg());
                                        }
                                    }
                                });
                            }
                        }).show();
            }
        });
        //文字的选择
        SelectableTextHelper mSelectableTextHelper1 = new SelectableTextHelper.Builder(binding.content.contentText1)
                .setSelectedColor(getResources().getColor(R.color.selected_blue))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(getResources().getColor(R.color.cursor_handle_color))
                .build();
        SelectableTextHelper mSelectableTextHelper2 = new SelectableTextHelper.Builder(binding.content.contentText2)
                .setSelectedColor(getResources().getColor(R.color.selected_blue))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(getResources().getColor(R.color.cursor_handle_color))
                .build();

        //设置字体
        binding.content.contentText1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/STKAITI.TTF"));
        binding.content.contentText2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/STKAITI.TTF"));

        //语音
        mediaPlayer = new MediaPlayer();

        //获取数据
        getEventDetailObject();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voice:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }

                break;
            case R.id.share:
                new ShareSheetDialog().show(getSupportFragmentManager(), "MyBottomSheetDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getEventDetailObject() {
        Observable<BaseEntity<EventEntity>> observable1 = RetrofitFactory.getInstence().API().getEventListById(Integer.parseInt(id),findByKey("userphone"));
        toSubscribe(observable1, new ProgressDialogSubscriber<BaseEntity<EventEntity>>(this) {
            @Override
            public void onNext(BaseEntity<EventEntity> eventEntityBaseEntity) {

                if (eventEntityBaseEntity.isSuccess()) {
                    datas = eventEntityBaseEntity;
                    if (Integer.parseInt(eventEntityBaseEntity.getData().getReadflag())==1){
                        binding.floating.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_book_24));
                    }else {
                        binding.floating.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_bookmark_border_24));
                    }
                    binding.content.contentText1.setText(eventEntityBaseEntity.getData().getArticle()
                            .replace("\\n", "\n")
                            .replace("\\u", "\u3000"));
                    Log.i("TAG", "onNext: 1111111111111111111" + binding.content.contentText1.getText().toString());
                    binding.content.contentText2.setText(eventEntityBaseEntity.getData().getAnotherarticle()
                            .replace("\\n", "\n")
                            .replace("\\u", "\u3000"));
                    mHandler.sendEmptyMessage(0);
                    Glide.with(getApplicationContext())
                            .load(eventEntityBaseEntity.getData().getImgurl())
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new RoundedCorners(8)
                                    ))
                            .into(binding.content.contentImg);
                    Glide.with(getApplicationContext())
                            .load(eventEntityBaseEntity.getData().getAnotherimgurl())
                            .into(new CustomTarget<Drawable>() {
                                @Nullable
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    // Do something with the Drawable here
                                    //找不到实例
                                    binding.toolbarLayout.setBackground(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                    // 从任何视图中删除onResourceReady中提供的Drawable，并确保不保留对它的引用。
                                }
                            });
                    try {
                        mediaPlayer.setDataSource(eventEntityBaseEntity.getData().getAudiourl());
                        mediaPlayer.prepareAsync();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }


    void getVoice() {
        //语音合成:
        //接口：https://reptile.akeyn.com/voice/text2audio?content=合成的文字
        //例子：https://reptile.akeyn.com/voice/text2audio?content=我们在广州天河
        String text1 = binding.content.contentText1.getText().toString();
        String text2 = binding.content.contentText2.getText().toString();
        String t = format("{0}{1}", text1, text2);
        Observable<VoiceEntity> observable2 = RetrofitFactory.getInstence().API().getVoice(text1);
        toSubscribe(observable2, new ProgressDialogSubscriber<VoiceEntity>(getApplicationContext()) {
            @Override
            public void onNext(VoiceEntity voiceEntity) {
//                try {
//                    if (voiceEntity.getCode()==1){
//
//                        Log.i("TAG1", "onNext: "+voiceEntity.getTips());
//                        mediaPlayer.setDataSource(voiceEntity.getUrl());
//                        mediaPlayer.prepareAsync();
//                    }else {
//                        Log.i("TAG1", "onNext: ");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    ;

  /*  //获取单个object数据
    private void getEventDetailObject1() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id",id);
        Api.config(ApiConfig.EVENT_LIST_BY_ID, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getString("code").equals("200")){
                        JSONObject jsonObjectData= new JSONObject(jsonObject.getString("data"));
                        article= jsonObjectData.getString("article")
                                .replace("\\n","\n")
                                .replace("\\u","\u3000");
                        anotherarticle=jsonObjectData.getString("anotherarticle")
                                .replace("\\n","\n")
                                .replace("\\u","\u3000");
                        anotherimgurl =jsonObjectData.getString("anotherimgurl");
                        imgurl =jsonObjectData.getString("imgurl");
                        audiourl =jsonObjectData.getString("audiourl");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("TAG", "run: "+article);
                                mHandler.sendEmptyMessage(0);
                                binding.content.contentText1.setText(article);
                                binding.content.contentText2.setText(anotherarticle);
                                Glide.with(getApplicationContext())
                                        .load(imgurl)
                                        .apply(new RequestOptions()
                                        .transforms(new CenterCrop(), new RoundedCorners(8)
                                        ))
                                        .into(binding.content.contentImg);
                                Glide.with(getApplicationContext())
                                        .load(anotherimgurl)
                                        .into(new CustomTarget<Drawable>() {
                                            @Nullable
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                // Do something with the Drawable here
                                                //找不到实例
                                                binding.toolbarLayout.setBackground(resource);
                                            }
                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                                // 从任何视图中删除onResourceReady中提供的Drawable，并确保不保留对它的引用。
                                            }
                                        });
                            }
                        });
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("数据获取失败了");
            }
        });
    }
*/
}