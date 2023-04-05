package com.century.zj.ui.main.Forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.century.zj.R;

import java.util.List;
import java.util.Random;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private Context mContext;
    private List<Friend> mFriendList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image1;
        ImageView image2;
        ImageView image3;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            image1 = view.findViewById(R.id.friend_image);
            image2 = view.findViewById(R.id.friend_imag);
            image3 = view.findViewById(R.id.friend_ima);
        }
    }
    public FriendAdapter(List<Friend> friendList) {
        mFriendList = friendList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_forum_card,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        getRandomLength();
        holder.image2.setVisibility(View.GONE);
        holder.image3.setVisibility(View.GONE);
        if (getRandomLength() == 1) {
            holder.image1.setVisibility(View.VISIBLE);
        } else if (getRandomLength() == 2) {
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
        } else if (getRandomLength() == 3) {
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            holder.image3.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
    private int getRandomLength() {
        Random random = new Random();
        int length = random.nextInt(3) + 1;
        return length;
    }
}
