package com.century.zj.ui.main.Travel.Activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.entity.Comment;
import com.century.zj.entity.EventEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

class CommentBaseAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {

    public CommentBaseAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
        
    }


    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.at_comment,item.getCo());
        helper.setText(R.id.at_username,item.getUsername());
        helper.setText(R.id.post_time,item.getTime());
        Glide.with(getContext())
                .load(item.getUserimg())
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(8)
                        ))
                .into((ImageView) helper.getView(R.id.at_userimg));
    }

    public static class DiffEventCallback extends DiffUtil.ItemCallback<Comment>{

        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getCo().equals(newItem.getCo())
                    && oldItem.getUserimg().equals(newItem.getUserimg());
        }
    }
}
