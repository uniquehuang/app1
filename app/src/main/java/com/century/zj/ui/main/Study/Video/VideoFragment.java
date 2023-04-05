package com.century.zj.ui.main.Study.Video;

import android.content.pm.ActivityInfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Observable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.century.zj.R;
import com.century.zj.base.BaseFragment;
import com.century.zj.databinding.FragmentVideoBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.VideoEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.util.Tag;
import com.century.zj.util.Utils;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment implements VideoAdapter.OnItemClickListener, VideoAdapter.OnItemChildClickListener {
    private FragmentVideoBinding binding;


    private VideoAdapter adapter;

    //视频播放器
    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;

    private static LinearLayoutManager linearLayoutManager;

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;

    private List<VideoEntity> datas = new ArrayList<>();

    public VideoFragment() {
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(inflater);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        //设置播放器
        initVideoView();
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器LinearLayoutManager
        binding.recycleView.setLayoutManager(linearLayoutManager);
        //设置适配器,传入actity和数据
        adapter = new VideoAdapter(getActivity());
        binding.recycleView.setAdapter(adapter);
        //设置点击事件监听
        //item
        /*adapter.setOnItemClickListener(this);*/
        //child
        adapter.setOnItemChildClickListener(this);
        //播放器播放
        binding.recycleView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            //视频播放器移出屏幕之外，使用releaseVideoView方法释放VideoView
            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });
        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showToast("刷新了");
                page = 1;
                getVideoList(true);
            }
        });
        binding.smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getVideoList(false);
            }
        });
        getVideoList(true);

    }


    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG", "onPause: " + "停止");
        pause();
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: ");
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }

    /**
     * PrepareView被点击
     */
    @Override
    public void onItemChildClick(int position) {
        startPlay(position);
        showToast("这是第" + position + "个item");
    }


    /**
     * 开始播放
     *
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoEntity videoEntity = datas.get(position);
//        边播边存
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(videoBean.getUrl());
//        mVideoView.setUrl(proxyUrl);

        //设置数据属性url和title
        mVideoView.setUrl(videoEntity.getPlayurl());
        mTitleView.setTitle(videoEntity.getTitle());

        //设置布局管理器
        View itemView = linearLayoutManager.findViewByPosition(position);
        if (itemView == null) return;
        VideoAdapter.VideoViewHolder viewHolder = (VideoAdapter.VideoViewHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.binding.prepareView, true);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.binding.playerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager().add(mVideoView, Tag.LIST);
        mVideoView.start();
        mCurPos = position;

    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }


    @Override
    public void onItemClick(int mPosition) {
        showToast("大的");
    }


    //分页加载
    private int page = 1;

    private void getVideoList(final boolean isRefresh) {
        Observable<BaseEntity<List<VideoEntity>>> observable = RetrofitFactory.getInstence().API().getVideoListByPage(page);
        toSubscribe(observable,
                new ProgressDialogSubscriber<BaseEntity<List<VideoEntity>>>(getActivity()) {
                    @Override
                    public void onNext(BaseEntity<List<VideoEntity>> listBaseEntity) {
                        if (isRefresh) {
                            binding.smart.finishRefresh(true);
                        } else {
                            binding.smart.finishLoadMore(true);
                        }
                        List<VideoEntity> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            if (isRefresh) {
                                datas = list;
                            } else {
                                datas.addAll(list);
                            }
                            adapter.setDatas(datas);
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