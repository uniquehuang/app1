package com.century.zj.ui.main.Study.Literature;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.century.zj.R;
import com.century.zj.base.BaseActivity;
import com.century.zj.databinding.ActivityLiteratureBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.LiteratureEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.util.ShareSheetDialog;
import com.century.zj.util.textSelect.SelectableTextHelper;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import androidx.annotation.NonNull;
import rx.Observable;

public class LiteratureActivity extends BaseActivity {
    private ActivityLiteratureBinding binding;
    private String id;
    private String title;

    @Override
    protected View initLayout() {
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding = ActivityLiteratureBinding.inflate(getLayoutInflater());

    }

    @Override
    protected void initData() {
        //获取点击事件传进的url
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            title = bundle.getString("title");
        }
        binding.toolbar.setTitle(title);
        setSupportActionBar(binding.toolbar);
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
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("www");
                            }
                        }).show();
            }
        });
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
        getLiteratureDetailObject();
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
                showToast("语音功能");
                break;
            case R.id.share:
                new ShareSheetDialog().show(getSupportFragmentManager(), "MyBottomSheetDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getLiteratureDetailObject() {
        Observable<BaseEntity<LiteratureEntity>> observable = RetrofitFactory.getInstence().API().getLiteratureListById(Integer.parseInt(id));
        toSubscribe(observable, new ProgressDialogSubscriber<BaseEntity<LiteratureEntity>>(this) {
            @Override
            public void onNext(BaseEntity<LiteratureEntity> literatureEntityBaseEntity) {

                if (literatureEntityBaseEntity.isSuccess()){

                    binding.content.contentText1.setText(literatureEntityBaseEntity.getData().getArticle()
                            .replace("\\n","\n")
                            .replace("\\u","\u3000"));
                    binding.content.contentText2.setText(literatureEntityBaseEntity.getData().getAnotherarticle()
                            .replace("\\n","\n")
                            .replace("\\u","\u3000"));
                    Glide.with(getApplicationContext())
                            .load(literatureEntityBaseEntity.getData().getAnotherimgurl())
                            .into(binding.content.contentImg);
                    Glide.with(getApplicationContext())
                            .load(literatureEntityBaseEntity.getData().getImgurl())
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
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    };
}