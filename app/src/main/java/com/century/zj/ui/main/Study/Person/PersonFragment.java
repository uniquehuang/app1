package com.century.zj.ui.main.Study.Person;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.century.zj.R;
import com.century.zj.base.BaseFragment;
import com.century.zj.databinding.FragmentPersonBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.PersonEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class PersonFragment extends BaseFragment {
    private FragmentPersonBinding binding;
    private PersonBaseAdapter adapter;
    private List<PersonEntity> personEntityArrayList = new ArrayList<>();

    public PersonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonBinding.inflate(inflater);
        initDta();
        return binding.getRoot();
    }

    private void initDta() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        adapter = new PersonBaseAdapter(R.layout.item_person_layout, personEntityArrayList);
        adapter.setDiffCallback(new PersonBaseAdapter.DiffPersonCallback());
        binding.recycleView.setAdapter(adapter);
        //设置点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                PersonEntity personEntity = personEntityArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(personEntity.getId()));
                bundle.putString("title", personEntity.getTitle());
                navigateToWithBundle(PersonActivity.class, bundle);
            }
        });
        //设置入场方式
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);

        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showToast("刷新了");
                page = 1;
                getPersonList(true);
            }
        });
        binding.smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getPersonList(false);
            }
        });
        getPersonList(true);
    }

    //分页加载
    private int page = 1;

    private void getPersonList(final boolean isRefresh) {
        Observable<BaseEntity<List<PersonEntity>>> observable = RetrofitFactory.getInstence().API().getPersonListByPage(page);
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<PersonEntity>>>(getActivity()) {
                    @Override
                    public void onNext(BaseEntity<List<PersonEntity>> listBaseEntity) {
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }

                        List<PersonEntity> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            if (isRefresh) {
                                personEntityArrayList = list;
                            } else {
                                personEntityArrayList.addAll(list);
                            }
                            adapter.setDiffNewData(personEntityArrayList);
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