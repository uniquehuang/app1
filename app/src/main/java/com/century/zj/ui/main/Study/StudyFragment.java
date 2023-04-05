package com.century.zj.ui.main.Study;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.century.zj.databinding.FragmentStudyBinding;
import com.century.zj.ui.main.Study.Event.EventFragment;
import com.century.zj.ui.main.Study.Literature.LiteratureFragment;
import com.century.zj.ui.main.Study.Person.PersonFragment;
import com.century.zj.ui.main.Study.Video.VideoFragment;
import com.century.zj.ui.main.Study.Work.WorkFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;


public class StudyFragment extends Fragment {
    private FragmentStudyBinding binding;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>(
            Arrays.asList("红色事件","红色影像","重要文献","红色人物","文艺作品"));
//    private PagAdapter pagAdapter;
    private PagAdapter2 pagAdapter2;
    private VideoFragment videoFragment;
    private EventFragment eventFragment;
    private LiteratureFragment literatureFragment;
    private PersonFragment personFragment;
    private WorkFragment workFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudyBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoFragment= new VideoFragment();
                eventFragment= new EventFragment();
                literatureFragment= new LiteratureFragment();
                personFragment= new PersonFragment();
                workFragment= new WorkFragment();
                mFragments.add(eventFragment);
                mFragments.add(videoFragment);
                mFragments.add(literatureFragment);
                mFragments.add(personFragment);
                mFragments.add(workFragment);
                //把tab和viewpager绑定到一起
                //binding.tab.setupWithViewPager(binding.viewpager2);

                pagAdapter2 = new PagAdapter2(getChildFragmentManager(),getLifecycle(),mFragments);
                binding.viewpager2.setAdapter(pagAdapter2);
                //实现动态接口，绑定viewpager2和tab
                new TabLayoutMediator(binding.tab,binding.viewpager2, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(titles.get(position));
                    }
                }).attach();
            }


        });



    }
}