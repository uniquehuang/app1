package com.century.zj.ui.mine.note;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.entity.EventEntity;
import com.century.zj.entity.NoteEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

class NoteBaseAdapter extends BaseQuickAdapter<NoteEntity, BaseViewHolder> {

    public NoteBaseAdapter(int layoutResId, @Nullable List<NoteEntity> data) {
        super(layoutResId, data);
        
    }


    @Override
    protected void convert(BaseViewHolder helper, NoteEntity noteEntity) {
        helper.setText(R.id.item_title,noteEntity.getTitle());
        helper.setText(R.id.item_note,noteEntity.getNote());

    }


    public static class DiffEventCallback extends DiffUtil.ItemCallback<NoteEntity>{

        @Override
        public boolean areItemsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getNote().equals(newItem.getNote())
                    && oldItem.getUserphone().equals(newItem.getUserphone());
        }
    }
}
