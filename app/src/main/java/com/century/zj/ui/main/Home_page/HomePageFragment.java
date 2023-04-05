package com.century.zj.ui.main.Home_page;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.century.zj.R;

import com.century.zj.api.Api;
import com.century.zj.api.TtitCallback;
import com.century.zj.databinding.FragmentHomePageBinding;
import com.century.zj.entity.RedThings_Form;
import com.century.zj.entity.ResponsePeople;
import com.century.zj.entity.RedPeople_Form;
import com.century.zj.entity.ResponseThings;
import com.century.zj.ui.main.Home_page.search.SearchActivity;
import com.century.zj.ui.main.Study.Event.EventActivity;
import com.century.zj.ui.main.Study.Person.PersonActivity;
import com.century.zj.util.UserConfigForm;
import com.century.zj.util.UserOperation;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class HomePageFragment extends Fragment {
    FragmentHomePageBinding homePageBinding;
    private int index = 0;
    private final int AUTOPLAY = 2;
    Random random = new Random();
    int id_one = 1;
    int id_two = random.nextInt(14) + 2;
    int id_three = random.nextInt(14) + 2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homePageBinding = FragmentHomePageBinding.inflate(getLayoutInflater());
        return homePageBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homePageBinding.btnSearch.setOnClickListener(v -> startActivity(new Intent(getContext(), SearchActivity.class)));
        while (id_two == id_three) {
            id_three = random.nextInt(14) + 2;
        }
        int[] idNumber = {id_one, id_two, id_three};
        //图片资源数组
        int[] imageResIDs = new int[]{R.drawable.fm1, R.drawable.fm2, R.drawable.fm3, R.drawable.fm4};
        ImageAdapter adapter = new ImageAdapter(imageResIDs, requireContext());
        homePageBinding.myGallery.setAdapter(adapter);
        //homePageBinding.myGallery.setSpacing(20); //图片之间的间距
        homePageBinding.myGallery.setSelection((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % imageResIDs.length);

        homePageBinding.myGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        SharedPreferences shp = requireActivity().getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
        Log.v("999999999999999999999", String.valueOf(shp.getAll()));
        SharedPreferences.Editor editor = shp.edit();
        homePageBinding.punchTheClock.setOnClickListener(v -> {
            if (shp.getInt("USER",0)==1){
                editor.putInt(UserConfigForm.USD, shp.getInt(UserConfigForm.USD, 0) + 1);
                editor.apply();
                new AlertDialog.Builder(requireContext()).setTitle("打卡完成").setMessage("美好的一天从打卡开始").setPositiveButton("确认", (dialog, which) -> {
                    HashMap<String, Object> hp = new HashMap<>();
                    Log.v("!!!!!!!!!!!!!!!", String.valueOf(shp.getInt(UserConfigForm.USD, 0)));
                    hp.put(UserConfigForm.USD, shp.getInt(UserConfigForm.USD, 0));
                    UserOperation
                            .UserConfig(shp.getInt(UserConfigForm.ID, 0), hp)
                            .UserInformation(requireContext(), "/user/updateUserStudyDaysCountById");
                }).show();
            }else {
                new AlertDialog.Builder(requireContext()).setTitle("打卡失败").setMessage("请先登录账号").setPositiveButton("确认", (dialog, which) -> {}).show();
            }
        });
        Gson gson = new Gson();
        for (int i = 0; i < 3; i++) {
            int j = i;
            HashMap<String, Object> m = new HashMap<>();
            m.put("id", idNumber[i]);
            m.put("userphone","19857317365");
            //红色人物
            Api.config("/person/getPersonById", m).getRequest(requireContext(), new TtitCallback() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onSuccess(String res) {
                    ResponsePeople responsePeople = gson.fromJson(res, ResponsePeople.class);
                    RedPeople_Form redPeople_form = responsePeople.getData();
                    if (responsePeople.getCode() != null) {
                        if (responsePeople.getCode().equals("200")) {
                            if (j == 0) {
                                requireActivity().runOnUiThread(() -> {
                                    homePageBinding.redPeopleHeadview.setBackground(requireContext().getDrawable(R.drawable.jiangbaili));
                                    homePageBinding.redPeopleHeadtext.setText(redPeople_form.getTitle());
                                    //设置点击事件
                                    homePageBinding.redPeopleHeadview.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", String.valueOf(redPeople_form.getId()));
                                            bundle.putString("title", redPeople_form.getTitle());
                                            navigateToWithBundle(PersonActivity.class, bundle);
                                        }
                                    });

                                });
                            } else if (j == 1) {
                                try {
                                    requireActivity().runOnUiThread(() -> {
                                        homePageBinding.redPeopleText1.setText(redPeople_form.getText());
                                        Glide.with(requireActivity()).load(redPeople_form.getImgurl()).into((ImageView) homePageBinding.redPeopleView1);
                                        //设置点击事件
                                        homePageBinding.redPeopleText1.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redPeople_form.getId()));
                                                bundle.putString("title", redPeople_form.getTitle());
                                                navigateToWithBundle(PersonActivity.class, bundle);
                                            }
                                        });
                                        //设置点击事件
                                        homePageBinding.redPeopleView1.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redPeople_form.getId()));
                                                bundle.putString("title", redPeople_form.getTitle());
                                                navigateToWithBundle(PersonActivity.class, bundle);
                                            }
                                        });

                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    requireActivity().runOnUiThread(() -> {
                                        homePageBinding.redPeopleText2.setText(redPeople_form.getText());
                                        Glide.with(requireActivity()).load(redPeople_form.getImgurl()).into((ImageView) homePageBinding.redPeopleView2);
                                        //设置点击事件
                                        homePageBinding.redPeopleView2.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redPeople_form.getId()));
                                                bundle.putString("title", redPeople_form.getTitle());
                                                navigateToWithBundle(PersonActivity.class, bundle);
                                            }
                                        });
                                        //设置点击事件
                                        homePageBinding.redPeopleText2.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redPeople_form.getId()));
                                                bundle.putString("title", redPeople_form.getTitle());
                                                navigateToWithBundle(PersonActivity.class, bundle);
                                            }
                                        });
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d("TAG", "run: " + res);
                        }
                    } else {
                        Log.d("TAG", "!!!!!!!!!!!!!!!: " + res);
                    }
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
            //红色事迹
            Api.config("/event/getEventById", m).getRequest(requireContext(), new TtitCallback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(String res) {
                    ResponseThings responseThings = gson.fromJson(res, ResponseThings.class);
                    RedThings_Form redThings_form = responseThings.getData();
                    if (responseThings.getCode() != null) {
                        if (responseThings.getCode().equals("200")) {
                            if (j == 0) {
                                try {
                                    requireActivity().runOnUiThread(() -> {
                                        homePageBinding.redAddressText1.setText(redThings_form.getTitle() + "\n" + redThings_form.getPlace());
                                        Glide.with(requireActivity()).load(redThings_form.getImgurl()).into(homePageBinding.redAddressView1);
                                        //设置点击事件
                                        homePageBinding.redAddressText1.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redThings_form.getId()));
                                                bundle.putString("title", redThings_form.getTitle());
                                                bundle.putString("userphone","1235");
                                                navigateToWithBundle(EventActivity.class, bundle);
                                            }
                                        });
                                        //设置点击事件
                                        homePageBinding.redAddressView1.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redThings_form.getId()));
                                                bundle.putString("title", redThings_form.getTitle());
                                                bundle.putString("userphone","1235");
                                                navigateToWithBundle(EventActivity.class, bundle);
                                            }
                                        });

                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (j == 1) {
                                try {
                                    requireActivity().runOnUiThread(() -> {
                                        homePageBinding.redAddressText2.setText(redThings_form.getTitle() + "\n" + redThings_form.getPlace());
                                        Glide.with(requireActivity()).load(redThings_form.getImgurl()).into(homePageBinding.redAddressView2);
                                        //设置点击事件
                                        homePageBinding.redAddressText2.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redThings_form.getId()));
                                                bundle.putString("title", redThings_form.getTitle());
                                                bundle.putString("userphone","1235");
                                                navigateToWithBundle(EventActivity.class, bundle);
                                            }
                                        });
                                        //设置点击事件
                                        homePageBinding.redAddressView2.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redThings_form.getId()));
                                                bundle.putString("title", redThings_form.getTitle());
                                                bundle.putString("userphone","1235");
                                                navigateToWithBundle(EventActivity.class, bundle);
                                            }
                                        });
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    requireActivity().runOnUiThread(() -> {
                                        homePageBinding.redAddressText3.setText(redThings_form.getTitle() + "\n" + redThings_form.getPlace());
                                        Glide.with(requireActivity()).load(redThings_form.getImgurl()).into( homePageBinding.redAddressView3);
                                        //设置点击事件
                                        homePageBinding.redAddressText3.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redThings_form.getId()));
                                                bundle.putString("title", redThings_form.getTitle());
                                                bundle.putString("userphone","1235");
                                                navigateToWithBundle(EventActivity.class, bundle);
                                            }
                                        });


                                        //设置点击事件
                                        homePageBinding.redAddressView3.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", String.valueOf(redThings_form.getId()));
                                                bundle.putString("title", redThings_form.getTitle());
                                                bundle.putString("userphone","1235");
                                                navigateToWithBundle(EventActivity.class, bundle);
                                            }
                                        });
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d("TAG", "run: " + res);
                        }
                    } else {
                        Log.d("TAG", "!!!!!!!!!!!!!!!: " + res);
                    }
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }

        // 设置点击事件监听
        homePageBinding.myGallery.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(requireContext(), "当前位置position:" + position + "的图片被选中了", Toast.LENGTH_SHORT).show());

        Timer timer = new Timer();
        timer.schedule(task, 3000, 3000);
    }

    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = AUTOPLAY;
            index = homePageBinding.myGallery.getSelectedItemPosition();
            index++;
            handler.sendMessage(message);
        }
    };
    //在消息队列中实现对控件的更改
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == AUTOPLAY) {
                homePageBinding.myGallery.setSelection(index);
            }
        }
    };
    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(getActivity(), cls);
        in.putExtras(bundle);
        startActivity(in);
    }

}