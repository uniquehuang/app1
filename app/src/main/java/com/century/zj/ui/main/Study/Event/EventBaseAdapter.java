package com.century.zj.ui.main.Study.Event;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.entity.EventEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

class EventBaseAdapter extends BaseQuickAdapter<EventEntity, BaseViewHolder> {

    public EventBaseAdapter(int layoutResId, @Nullable List<EventEntity> data) {
        super(layoutResId, data);
        
    }


    @Override
    protected void convert(BaseViewHolder helper, EventEntity item) {
        helper.setText(R.id.item_title,item.getTitle());
        helper.setText(R.id.item_category,item.getCategory());
        helper.setText(R.id.item_author,item.getPlace());
        Glide.with(getContext())
                .load(item.getImgurl())
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(8)
                        ))
                .into((ImageView) helper.getView(R.id.item_iv_cardViewBak));
    }

    public static class DiffEventCallback extends DiffUtil.ItemCallback<EventEntity>{

        @Override
        public boolean areItemsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getCategory().equals(newItem.getCategory())
                    && oldItem.getImgurl().equals(newItem.getImgurl());
        }
    }
}
