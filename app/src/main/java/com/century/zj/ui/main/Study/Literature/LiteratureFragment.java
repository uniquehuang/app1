package com.century.zj.ui.main.Study.Literature;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.century.zj.R;
import com.century.zj.base.BaseFragment;
import com.century.zj.databinding.FragmentLiteratureBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.EventEntity;
import com.century.zj.entity.LiteratureEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class LiteratureFragment extends BaseFragment {
    private FragmentLiteratureBinding binding;
    private LiteratureBaseAdapter adapter;
    private List<LiteratureEntity> literatureEntityArrayList = new ArrayList<>();

    public LiteratureFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLiteratureBinding.inflate(inflater);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        adapter = new LiteratureBaseAdapter(R.layout.item_literature_layout, literatureEntityArrayList);
        adapter.setDiffCallback(new LiteratureBaseAdapter.DiffLiteratureCallback());
        binding.recycleView.setAdapter(adapter);
        //设置点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                LiteratureEntity literatureEntity = literatureEntityArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(literatureEntity.getId()));
                bundle.putString("title", literatureEntity.getTitle());
                navigateToWithBundle(LiteratureActivity.class, bundle);
            }
        });
        //设置入场方式
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showToast("刷新了");
                page = 1;
                getLiteratureList(true);
            }
        });
        binding.smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getLiteratureList(false);
            }
        });
        getLiteratureList(true);
    }

    //分页加载
    private int page = 1;

    private void getLiteratureList(final boolean isRefresh) {
        Observable<BaseEntity<List<LiteratureEntity>>> observable = RetrofitFactory.getInstence().API().getLiteratureListByPage(page);
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<LiteratureEntity>>>(getActivity()) {
                    @Override
                    public void onNext(BaseEntity<List<LiteratureEntity>> listBaseEntity) {
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }
                        List<LiteratureEntity> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            if (isRefresh) {
                                literatureEntityArrayList = list;
                            } else {
                                literatureEntityArrayList.addAll(list);
                            }
                            adapter.setDiffNewData(literatureEntityArrayList);
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