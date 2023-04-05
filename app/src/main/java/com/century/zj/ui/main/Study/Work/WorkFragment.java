package com.century.zj.ui.main.Study.Work;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.century.zj.R;
import com.century.zj.base.BaseFragment;
import com.century.zj.databinding.FragmentWorkBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.WorkEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class WorkFragment extends BaseFragment {
    private FragmentWorkBinding binding;
    private WorkBaseAdapter adapter;
    private List<WorkEntity> workEntityArrayList = new ArrayList<>();

    public WorkFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkBinding.inflate(inflater);
        initDta();
        return binding.getRoot();
    }

    private void initDta() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        adapter = new WorkBaseAdapter(R.layout.item_work_layout, workEntityArrayList);
        adapter.setDiffCallback(new WorkBaseAdapter.DiffWorkCallback());
        binding.recycleView.setAdapter(adapter);
        //设置点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                WorkEntity workEntity = workEntityArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(workEntity.getId()));
                bundle.putString("title", workEntity.getTitle());
                navigateToWithBundle(WorkActivity.class, bundle);            }
        });
        //设置入场方式
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showToast("刷新了");
                page= 1;
                getWorkList(true);
            }
        });
        binding.smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getWorkList(false);
            }
        });
        getWorkList(true);
    }

    //分页加载
    private int  page= 1;
    private void getWorkList(final boolean isRefresh) {
        Observable<BaseEntity<List<WorkEntity>>> observable = RetrofitFactory.getInstence().API().getWorkListByPage(page);
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<WorkEntity>>>(getActivity()) {
                    @Override
                    public void onNext(BaseEntity<List<WorkEntity>> listBaseEntity) {
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }
                            List<WorkEntity> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            if (isRefresh) {
                                workEntityArrayList = list;
                            } else {
                                workEntityArrayList.addAll(list);
                            }
                            adapter.setDiffNewData(workEntityArrayList);
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