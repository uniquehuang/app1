package com.century.zj.ui.mine.collection;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.entity.CollectAttraction;
import com.century.zj.entity.NoteEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

class CollectionBaseAdapter extends BaseQuickAdapter<CollectAttraction, BaseViewHolder> {

    public CollectionBaseAdapter(int layoutResId, @Nullable List<CollectAttraction> data) {
        super(layoutResId, data);
        
    }


    @Override
    protected void convert(BaseViewHolder helper, CollectAttraction collectionEntity) {
        helper.setText(R.id.item_at_title,collectionEntity.getTitle());
        helper.setText(R.id.item_at_text,collectionEntity.getText().replace("\\u","\u3000"));
        Glide.with(getContext())
                .load(collectionEntity.getImgurl())
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(8)
                        ))
                .into((ImageView) helper.getView(R.id.item_at_img));
    }


    public static class DiffEventCallback extends DiffUtil.ItemCallback<CollectAttraction>{

        @Override
        public boolean areItemsTheSame(@NonNull CollectAttraction oldItem, @NonNull CollectAttraction newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CollectAttraction oldItem, @NonNull CollectAttraction newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getText().equals(newItem.getText())
                    && oldItem.getUserphone().equals(newItem.getUserphone());
        }
    }
}
