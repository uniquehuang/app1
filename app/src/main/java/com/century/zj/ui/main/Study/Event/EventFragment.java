package com.century.zj.ui.main.Study.Event;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.base.BaseFragment;
import com.century.zj.databinding.FragmentEventBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.EventEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.util.MyBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;


public class EventFragment extends BaseFragment {
    private FragmentEventBinding binding;
    /*private EventAdapter adapter;*/
    private EventBaseAdapter adapter;

    private List<EventEntity> eventEntityArrayList
            = new ArrayList<>();
    private MyBanner bannerView;

    /*private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setDiffNewData(eventEntityArrayList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
*/
    public EventFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDta();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater);

        return binding.getRoot();
    }

    private void initDta() {


        //设置item布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        //baseAdapter
        adapter = new EventBaseAdapter(R.layout.item_event_layout, eventEntityArrayList);
        //设置不同数据发生改变
        adapter.setDiffCallback(new EventBaseAdapter.DiffEventCallback());

        binding.recycleView.setAdapter(adapter);


        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.list_header, null);
        adapter.addHeaderView(headerView);
        bannerView = headerView.findViewById(R.id.bannerView);
        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.drawable.im_banner1);
        bannerList.add(R.drawable.im_banner2);
        bannerList.add(R.drawable.im_banner3);
        bannerList.add(R.drawable.im_banner4);
        //广告的点击事件
        bannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                showToast("点击了第" + i + "照片");
            }
        });


        bannerView.setPages(bannerList, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                EventEntity eventEntity = eventEntityArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(eventEntity.getId()));
                bundle.putString("title", eventEntity.getTitle());
                navigateToWithBundle(EventActivity.class, bundle);

            }
        });

        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);

        //刷新事件
        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showToast("刷新了");
                page = 1;
                getEventListByPage(true);

            }
        });
        binding.smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getEventListByPage(false);
            }
        });
        getEventListByPage(true);
    }

    //分页加载
    private int page = 1;

    private void getEventListByPage(final boolean isRefresh) {
        Observable<BaseEntity<List<EventEntity>>> observable = RetrofitFactory.getInstence().API().getEventListByPage(page);
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<EventEntity>>>(getActivity()) {
                    @Override
                    public void onNext(BaseEntity<List<EventEntity>> listBaseEntity) {
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }
                        List<EventEntity> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            if (isRefresh) {
                                eventEntityArrayList = list;
                            } else {
                                eventEntityArrayList.addAll(list);
                            }
                            adapter.setDiffNewData(eventEntityArrayList);
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
/*

    private void getEventList1(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        Api.config(ApiConfig.EVENT_LIST_ALL, params).getRequest(getActivity(), new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    binding.smart.finishRefresh(true);
                } else {
                    binding.smart.finishLoadMore(true);
                }
                EventListResponse response = new Gson().fromJson(res, EventListResponse.class);

                if (response != null && response.getCode() == 200) {
                    List<EventEntity> list = response.getList();
                    if (list != null && list.size() > 0 && i < list.size()) {
                        if (isRefresh) {
                            for (int j = i; i < 4; i++, j++) {
                                eventEntityArrayList.add(list.get(j));
                            }
                        } else {
                            eventEntityArrayList.add(list.get(i++));
                            if (i < list.size()) {
                                eventEntityArrayList.add(list.get(i++));
                            }
                            if (i < list.size()) {
                                eventEntityArrayList.add(list.get(i++));
                            }
                            if (i < list.size()) {
                                eventEntityArrayList.add(list.get(i++));
                            }
                        }
                        //同步数据
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            showToastSync("暂时无数据");
                        } else {
                            showToastSync("没有更多数据");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (isRefresh) {
                    binding.smart.finishRefresh(true);
                } else {
                    binding.smart.finishLoadMore(true);
                }
            }
        });
    }
*/

    //广告栏的数据绑定
    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.cel_banner, null);
            mImageView = view.findViewById(R.id.banner_img);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            Glide.with(context).load(data).into(mImageView);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerView.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.start();//开始轮播
    }


}