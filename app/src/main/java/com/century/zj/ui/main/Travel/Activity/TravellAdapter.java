package com.century.zj.ui.main.Travel.Activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.entity.AttractionEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

class TravellAdapter extends BaseQuickAdapter<AttractionEntity, BaseViewHolder> {

    public TravellAdapter(int layoutResId, @Nullable List<AttractionEntity> data) {
        super(layoutResId, data);
        
    }


    @Override
    protected void convert(BaseViewHolder helper, AttractionEntity item) {
        helper.setText(R.id.item_at_title,item.getTitle());
        helper.setText(R.id.item_at_text,item.getText());
        /*helper.setText(R.id.item_category,item.getCategory());
        helper.setText(R.id.item_author,item.getPlace());*/
        Glide.with(getContext())
                .load(item.getImgurl())
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(8)
                        ))
                .into((ImageView) helper.getView(R.id.item_at_img));
    }

    public static class DiffAttrCallback extends DiffUtil.ItemCallback<AttractionEntity>{

        @Override
        public boolean areItemsTheSame(@NonNull AttractionEntity oldItem, @NonNull AttractionEntity newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AttractionEntity oldItem, @NonNull AttractionEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
                    /*&& oldItem.getCategory().equals(newItem.getCategory())
                    && oldItem.getImgurl().equals(newItem.getImgurl());*/
        }
    }
}
