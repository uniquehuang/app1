package com.century.zj.ui.mine.note;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.century.zj.R;
import com.century.zj.base.BaseActivity;
import com.century.zj.databinding.ActivityNoteActityBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.EventEntity;
import com.century.zj.entity.NoteEntity;
import com.century.zj.entity.WorkEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.ui.main.Study.Work.WorkBaseAdapter;
import com.century.zj.util.ShareSheetDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;

public class NoteActity extends BaseActivity {
    private ActivityNoteActityBinding binding;
    private NoteBaseAdapter adapter;
    private List<NoteEntity> noteEntityArrayList = new ArrayList<>();
    @Override
    protected View initLayout() {
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding = ActivityNoteActityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        adapter = new NoteBaseAdapter(R.layout.item_note_layout, noteEntityArrayList);
        adapter.setDiffCallback(new NoteBaseAdapter.DiffEventCallback());
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
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                NoteEntity noteEntity = noteEntityArrayList.get(position);
                new ShareSheetDialog().show(getSupportFragmentManager(), "MyBottomSheetDialog");
                return false;
            }
        });
        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showToast("刷新了");
                getNoteList(true);
            }
        });
        binding.smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getNoteList(false);
            }
        });
        getNoteList(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        binding.toolbar.setTitle("笔记");
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

    private void getNoteList(final boolean isRefresh) {
        Observable<BaseEntity<List<NoteEntity>>> observable = RetrofitFactory.getInstence().API().getNote(findByKey("userphone"));
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<NoteEntity>>>(this) {
                    @Override
                    public void onNext(BaseEntity<List<NoteEntity>> listBaseEntity) {
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }
                        List<NoteEntity> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            if (isRefresh) {
                                noteEntityArrayList = list;
                            }
                            adapter.setDiffNewData(noteEntityArrayList);
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