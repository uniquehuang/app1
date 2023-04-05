package com.century.zj.ui.main.Study.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.century.zj.databinding.ItemEventLayoutBinding;
import com.century.zj.entity.EventEntity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> implements View.OnClickListener {
    private final Context mContext;
    private ArrayList<EventEntity> eventEntityArrayList;
    private EventAdapter.OnItemClickListener mOnItemClickListener;

    public EventAdapter(Context mContext, ArrayList<EventEntity> eventEntityArrayList) {
        this.mContext = mContext;
        this.eventEntityArrayList=eventEntityArrayList;
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventLayoutBinding binding =ItemEventLayoutBinding
                .inflate(LayoutInflater.from(mContext),parent,false);
        binding.getRoot().setOnClickListener(this);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        holder.binding.getRoot().setTag(position);
        EventEntity events = eventEntityArrayList.get(position);
        Glide.with(mContext)
                .load(events.getImgurl())
                .into(holder.binding.itemIvCardViewBak);
        holder.binding.itemTitle.setText(events.getTitle());

    }

    @Override
    public int getItemCount() {
        return eventEntityArrayList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        ItemEventLayoutBinding binding;

        public EventViewHolder(@NonNull ItemEventLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            int pos = (int) v.getTag();
            EventEntity eventEntity = eventEntityArrayList.get(pos);
            mOnItemClickListener.onItemClick(pos, eventEntity);
        }
    }


    //接口类实现方法按钮回传方法
    public interface OnItemClickListener {
        void onItemClick(int position, EventEntity item);

        /*void onItemClick(int mPosition);*/

    }


    //实现按钮方法回传,把fragment里面的onItemClickListener的onItemClick和onItemClick方法传回给Videoadapter
    public void setOnItemClickListener(EventAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


}
