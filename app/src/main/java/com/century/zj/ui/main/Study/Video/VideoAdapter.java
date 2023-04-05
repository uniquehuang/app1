package com.century.zj.ui.main.Study.Video;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.databinding.ItemVideoLayoutBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.UpdateCountEntity;
import com.century.zj.entity.VideoEntity;
import com.century.zj.http.ProgressDialogSubscriber;
import com.century.zj.http.RetrofitFactory;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context mContext;
    private List<VideoEntity> datas;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemClickListener mOnItemClickListener;


    public void setDatas(List<VideoEntity> datas) {
        this.datas = datas;
    }


    public VideoAdapter(Context mContext, List<VideoEntity> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    public VideoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoLayoutBinding binding = ItemVideoLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false);

        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        //定位
        /*holder.itemView.setTag(position);*/
        //寻找数据
        VideoEntity videoEntity = datas.get(position);
        //设置数据
        holder.binding.title.setText(videoEntity.getTitle());
        holder.binding.author.setText(videoEntity.getName());
        holder.binding.sort.setText(videoEntity.getSort());
//        holder.binding.tvDz.setText(String.valueOf(videoEntity.getDzcount()));
//        holder.binding.tvComment.setText(String.valueOf(videoEntity.getCommentcount()));
//        holder.binding.tvCollect.setText(String.valueOf(videoEntity.getCollectcount()));
        if (datas.get(position).getId()==videoEntity.getId()){
            int likenum = videoEntity.getDzcount();
            int commentnum = videoEntity.getCommentcount();
            int collectnum = videoEntity.getCollectcount();
            int flagLike = videoEntity.getDzflag();
            int flagCollect = videoEntity.getScflag();
            if (flagLike == 1) {
                holder.binding.tvDz.setTextColor(Color.parseColor("#E21918"));
                holder.binding.imgLike.setImageResource(R.mipmap.dianzan_select);
            }else {
                holder.binding.tvDz.setTextColor(Color.parseColor("#161616"));
                holder.binding.imgLike.setImageResource(R.mipmap.dianzan);
            }
            if (flagCollect == 1) {
                holder.binding.tvCollect.setTextColor(Color.parseColor("#E21918"));
                holder.binding.imgCollect.setImageResource(R.mipmap.collect_select);
            }else {
                holder.binding.tvCollect.setTextColor(Color.parseColor("#161616"));
                holder.binding.imgCollect.setImageResource(R.mipmap.item_video_collect);
            }
            holder.binding.tvDz.setText(String.valueOf(likenum));
            holder.binding.tvComment.setText(String.valueOf(commentnum));
            holder.binding.tvCollect.setText(String.valueOf(collectnum));
            holder.flagCollect = flagCollect;
            holder.flagLike = flagLike;
        }

        Glide.with(mContext)
                .load(videoEntity.getUserimg())
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(180)
                        ))
                .into(holder.binding.imgHeader);
        Glide.with(mContext)
                .load(videoEntity.getImgurl())
                .into(holder.mThumb);
        holder.mPosition = position;


    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mThumb;
        ItemVideoLayoutBinding binding;
        private int flagCollect;
        private int flagLike;
        public int mPosition;

        public VideoViewHolder(@NonNull ItemVideoLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            mThumb = binding.getRoot().findViewById(R.id.thumb);

            //绑定mPlayerContainer的监听，先进行监听事件触发
            if (mOnItemChildClickListener != null) {
                binding.playerContainer.setOnClickListener(this);
            }
            //绑定整个itemView的监听，后进行监听事件触发
            if (mOnItemClickListener != null) {
                binding.getRoot().setOnClickListener(this);
            }
            binding.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeNum = Integer.parseInt(binding.tvDz.getText().toString());
                    if (flagLike == 1) { //已点赞
                        if (likeNum > 0) {
                            binding.tvDz.setText(String.valueOf(--likeNum));
                            binding.tvDz.setTextColor(Color.parseColor("#161616"));
                            binding.imgLike.setImageResource(R.mipmap.dianzan);
                            if (flagLike == 1)
                                flagLike = 0;
                            else
                                flagLike = 1;
                            updateCount(datas.get(mPosition).getId()
                                    ,Integer.parseInt(binding.tvCollect.getText().toString())
                                    ,Integer.parseInt(binding.tvComment.getText().toString())
                                    ,likeNum
                                    ,flagLike
                                    ,flagCollect);

                        }
                    } else {//未点赞
                        binding.tvDz.setText(String.valueOf(++likeNum));
                        binding.tvDz.setTextColor(Color.parseColor("#E21918"));
                        binding.imgLike.setImageResource(R.mipmap.dianzan_select);
                        if (flagLike == 1)
                            flagLike = 0;
                        else
                            flagLike = 1;
                        updateCount(datas.get(mPosition).getId()
                                ,Integer.parseInt(binding.tvCollect.getText().toString())
                                ,Integer.parseInt(binding.tvComment.getText().toString())
                                ,likeNum
                                ,flagLike
                                ,flagCollect);

                    }

                }
            });
            binding.imgCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int collectNum = Integer.parseInt(binding.tvCollect.getText().toString());
                    if (flagCollect == 1) { //已收藏
                        if (collectNum > 0) {
                            binding.tvCollect.setText(String.valueOf(--collectNum));
                            binding.tvCollect.setTextColor(Color.parseColor("#161616"));
                            binding.imgCollect.setImageResource(R.mipmap.item_video_collect);
                            if (flagCollect == 1)
                                flagCollect = 0;
                            else
                                flagCollect = 1;
                            updateCount(datas.get(mPosition).getId()
                                    ,collectNum
                                    ,Integer.parseInt(binding.tvComment.getText().toString())
                                    ,Integer.parseInt(binding.tvDz.getText().toString())
                                    ,flagLike
                                    ,flagCollect);

                        }
                    } else {//未收藏
                        binding.tvCollect.setText(String.valueOf(++collectNum));
                        binding.tvCollect.setTextColor(Color.parseColor("#E21918"));
                        binding.imgCollect.setImageResource(R.mipmap.collect_select);
                        if (flagCollect == 1)
                            flagCollect = 0;
                        else
                            flagCollect = 1;
                        updateCount(datas.get(mPosition).getId()
                                ,collectNum
                                ,Integer.parseInt(binding.tvComment.getText().toString())
                                ,Integer.parseInt(binding.tvDz.getText().toString())
                                ,flagLike
                                ,flagCollect);

                    }
                }
            });


            //通过tag将ViewHolder和itemView绑定
            binding.getRoot().setTag(this);
        }

        @Override
        public void onClick(View v) {
            /*if (mOnItemClickListener != null) {
                int pos = (int) v.getTag();
                VideoEntity deviceEntity = datas.get(pos);
                mOnItemClickListener.onItemClick(pos, deviceEntity);
            }*/
            if (v.getId() == R.id.player_container) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }

        }
    }
    private void updateCount(int id, int collectcount,int commentcount,int dzcount, int dzflag,int scflag){
        Observable<BaseEntity<String>> observable = RetrofitFactory.getInstence().API()
                .updateCount(new UpdateCountEntity(id,collectcount,commentcount,dzcount,dzflag,scflag));
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressDialogSubscriber<BaseEntity<String>>(mContext) {
                    @Override
                    public void onNext(BaseEntity<String> stringBaseEntity) {
                        if (stringBaseEntity.isSuccess()){
                            Log.i("TAG", "onNext: "+stringBaseEntity.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });


    }
    //接口类实现方法按钮回传方法
    public interface OnItemClickListener {
        /*void onItemClick(int position, VideoEntity item);*/

        void onItemClick(int mPosition);

    }

    //接口类实现方法子按钮回传方法
    public interface OnItemChildClickListener {
        void onItemChildClick(int position);
    }


    //实现按钮方法回传,把fragment里面的onItemClickListener的onItemClick和onItemClick方法传回给Videoadapter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //实现按钮方法回传，把fragment里面的onItemChildClickListener的onItemChildClick（）方法传回给Videoadapter
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }


}


