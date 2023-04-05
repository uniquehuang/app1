package com.century.zj.ui.main.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.databinding.FragmentGameBinding;
import com.century.zj.ui.main.Game.achievement.AchievementActivity;
import com.century.zj.ui.main.Game.answer.AnswerGame;
import com.century.zj.ui.main.Game.draw.DrawActivity;
import com.century.zj.ui.main.Game.music.SongActivity;
import com.century.zj.ui.main.Game.puzzle.PuzzleGame;
import com.century.zj.ui.main.Game.ranking.RankActivity;
import com.century.zj.ui.main.Game.song.MusicActivity;
import com.century.zj.ui.main.Study.Event.EventFragment;
import com.century.zj.util.MyBanner;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;


public class GameFragment extends Fragment {
    FragmentGameBinding fragmentGameBinding;
    private MyBanner bannerView;
    public GameFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentGameBinding= FragmentGameBinding.inflate(getLayoutInflater());
        return fragmentGameBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bannerView = fragmentGameBinding.gameView;
        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.drawable.game_practice);
        bannerList.add(R.drawable.puzzle2);
        bannerList.add(R.drawable.red_read);
        bannerList.add(R.drawable.red_draw);
        bannerList.add(R.drawable.game_song);
        //广告的点击事件
        bannerView.setBannerPageClickListener((view1, i) -> {
            switch (i){
                case 0:
                    startActivity(new Intent(getContext(), AnswerGame.class));
                    break;
                case 1:
                    startActivity(new Intent(getContext(), PuzzleGame.class));
                    break;
                case 2:
                    startActivity(new Intent(getContext(), SongActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(getContext(), DrawActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(getContext(), MusicActivity.class));
                    break;
            }
        });
        bannerView.setPages(bannerList, EventFragment.BannerViewHolder::new);
//        fragmentGameBinding.puzzle.setOnClickListener(v -> startActivity(new Intent(getContext(), PuzzleGame.class)));
        fragmentGameBinding.achievement.setOnClickListener(v -> startActivity(new Intent(getContext(),AchievementActivity.class)));
        fragmentGameBinding.gameRank.setOnClickListener(v -> startActivity(new Intent(getContext(), RankActivity.class)));
//        fragmentGameBinding.practive.setOnClickListener(v -> startActivity(new Intent(getContext(), AnswerGame.class)));
    }
    //广告栏的数据绑定
    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.cel_banner, null);
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