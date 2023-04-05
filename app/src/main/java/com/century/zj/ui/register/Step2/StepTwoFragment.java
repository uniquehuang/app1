package com.century.zj.ui.register.Step2;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import okhttp3.OkHttpClient;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;

import com.century.zj.R;

import com.century.zj.api.Api;
import com.century.zj.api.ApiConfig;
import com.century.zj.api.TtitCallback;
import com.century.zj.databinding.FragmentStepTwoBinding;
import com.century.zj.util.UserConfigForm;
import com.century.zj.ui.login.LoginActivity;
import com.century.zj.viewmodel.MyViewModel;


import java.util.HashMap;

public class StepTwoFragment extends Fragment {
    FragmentStepTwoBinding fragmentStepTwoBinding;
    MyViewModel myViewModel;
    OkHttpClient okHttpClient;
    int gender;

    public StepTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentStepTwoBinding = FragmentStepTwoBinding.inflate(getLayoutInflater());
        return fragmentStepTwoBinding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(
                requireActivity().getApplication(),
                requireActivity(),
                getArguments())).get(MyViewModel.class);
        //当内容没有输入时，button为不可用状态。
        fragmentStepTwoBinding.button3.setEnabled(false);
        fragmentStepTwoBinding.button3.setBackground(requireContext().getDrawable(R.drawable.enable_false));
        //获取焦点，以便弹出键盘
        fragmentStepTwoBinding.passwordOne.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwordOne = fragmentStepTwoBinding.passwordOne.getText().toString().trim();
                String passwordTwo = fragmentStepTwoBinding.passwordTwo.getText().toString().trim();
                boolean b = !passwordOne.isEmpty() && !passwordTwo.isEmpty() && passwordOne.equals(passwordTwo);
                fragmentStepTwoBinding.button3.setBackground(b ? requireContext().getDrawable(R.drawable.button_style) : requireContext().getDrawable(R.drawable.enable_false));
                fragmentStepTwoBinding.button3.setEnabled(b);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        fragmentStepTwoBinding.passwordOne.addTextChangedListener(textWatcher);
        fragmentStepTwoBinding.passwordTwo.addTextChangedListener(textWatcher);
        fragmentStepTwoBinding.radioGender.setOnCheckedChangeListener((group, checkedId) -> {
            if (fragmentStepTwoBinding.radioBoy.getId()==checkedId){
                gender=1;
            }else {
                gender=0;
            }
        });
        fragmentStepTwoBinding.button3.setOnClickListener(v -> {
            myViewModel.insertName(fragmentStepTwoBinding.TextName.getText().toString());
            myViewModel.insertPassword(fragmentStepTwoBinding.passwordOne.getText().toString());
            Log.d("账号密码和名字", "onViewCreated: " + myViewModel.getPhone().getValue() + "||" + myViewModel.getPassword().getValue() + "||" + myViewModel.getUsername().getValue());
            register(myViewModel.getPhone().getValue(), myViewModel.getPassword().getValue(), myViewModel.getUsername().getValue(),gender);
            myViewModel.save();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    private void register(String account, String pwd, String name,int user_gender) {
        okHttpClient = new OkHttpClient.Builder().build();
        HashMap<String, Object> m = new HashMap<>();
        m.put(UserConfigForm.PS, pwd);
        m.put(UserConfigForm.UN, name);
        m.put(UserConfigForm.UP, account);
        m.put(UserConfigForm.UGE,user_gender);
        Api.config(ApiConfig.REGISTER, m).postRequest(requireActivity(), new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                requireActivity().runOnUiThread(() -> Log.i("结果", "run: " + res));
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}