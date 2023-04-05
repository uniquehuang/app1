package com.century.zj.ui.mine.collection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.century.zj.R;
import com.century.zj.base.BaseActivity;
import com.century.zj.databinding.ActivityCollectionBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.CollectAttraction;
import com.century.zj.entity.NoteEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.ui.main.Travel.Activity.AttractionActivity;
import com.century.zj.ui.main.Travel.Activity.TravelActivity;
import com.century.zj.util.ShareSheetDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectionActivity extends BaseActivity {
    ActivityCollectionBinding binding;
    CollectionBaseAdapter adapter;
    private List<CollectAttraction> collectEntityArrayList = new ArrayList<>();

    @Override
    protected View initLayout() {
        return binding.getRoot();
    }

    @Override
    protected void initView() {
    binding= ActivityCollectionBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        adapter = new CollectionBaseAdapter(R.layout.item_collecttion_layout, collectEntityArrayList);
        adapter.setDiffCallback(new CollectionBaseAdapter.DiffEventCallback());
        binding.recycleView.setAdapter(adapter);

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
        //设置入场方式
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                CollectAttraction collectAttraction = collectEntityArrayList.get(position);
                Intent intent=new Intent(CollectionActivity.this, AttractionActivity.class);
                //通过intent传参，可以传实体类
                intent.putExtra("title",collectAttraction.getTitle());
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                CollectAttraction collectAttraction = collectEntityArrayList.get(position);
                new ShareSheetDialog().show(getSupportFragmentManager(), "MyBottomSheetDialog");
                return false;
            }
        });
        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showToast("刷新了");
                getCollectionList(true);
            }
        });
        binding.smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getCollectionList(false);
            }
        });
        getCollectionList(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        binding.toolbar.setTitle("收藏");
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
    private void getCollectionList(final boolean isRefresh) {
        Observable<BaseEntity<List<CollectAttraction>>> observable = RetrofitFactory.getInstence().API().collectGet(findByKey("userphone"));
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<CollectAttraction>>>(this) {
                    @Override
                    public void onNext(BaseEntity<List<CollectAttraction>> listBaseEntity) {
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }
                        List<CollectAttraction> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            if (isRefresh) {
                                collectEntityArrayList = list;
                            }
                            adapter.setDiffNewData(collectEntityArrayList);
                            adapter.notifyDataSetChanged();
                        } else {
                            if (isRefresh) {
                                showToast("暂时无数据");
                            } else {
                                showToast("没有更多数据");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }
                    }
                }
        );
    }
}
