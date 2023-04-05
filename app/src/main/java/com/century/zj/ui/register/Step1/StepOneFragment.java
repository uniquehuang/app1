package com.century.zj.ui.register.Step1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.century.zj.R;
import com.century.zj.databinding.FragmentStepOneBinding;
import com.century.zj.viewmodel.MyViewModel;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class StepOneFragment extends Fragment {
    FragmentStepOneBinding fragmentStepOneBinding;
    MyViewModel myViewModel;
    private TimeCount time;
    public StepOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentStepOneBinding = FragmentStepOneBinding.inflate(getLayoutInflater());
        return fragmentStepOneBinding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventHandler handler;
        myViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(
                requireActivity().getApplication(),
                requireActivity(),
                getArguments())).get(MyViewModel.class);
        //当内容没有输入时，button为不可用状态。
        fragmentStepOneBinding.button2.setEnabled(false);
        fragmentStepOneBinding.button2.setBackground(requireContext().getDrawable(R.drawable.enable_false));
        //获取焦点，以便弹出键盘
        fragmentStepOneBinding.Number.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String number = fragmentStepOneBinding.Number.getText().toString().trim();
                String yzm = fragmentStepOneBinding.verificationCode.getText().toString().trim();
                fragmentStepOneBinding.button2.setBackground(!number.isEmpty() && !yzm.isEmpty() ? requireContext().getDrawable(R.drawable.button_style) : requireContext().getDrawable(R.drawable.enable_false));
                fragmentStepOneBinding.button2.setEnabled(!number.isEmpty() && !yzm.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        fragmentStepOneBinding.Number.addTextChangedListener(textWatcher);
        fragmentStepOneBinding.verificationCode.addTextChangedListener(textWatcher);

        time = new TimeCount(60000, 1000);

        //SMSSDK获取验证码
        fragmentStepOneBinding.getYzm.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "获取验证码", Toast.LENGTH_SHORT).show();
            String phone = fragmentStepOneBinding.Number.getText().toString();
            SMSSDK.getVerificationCode("86", phone);
            time.start();
        });
        MobSDK.init(getActivity(), "33c067abdf106", "9f6d9ce49f3e14b498587328460e2054");
        handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "验证成功", Toast.LENGTH_SHORT).show();
                            myViewModel.insertPhone(fragmentStepOneBinding.Number.getText().toString());
                            Navigation.findNavController(requireView()).navigate(R.id.action_stepOneFragment_to_stepTwoFragment);
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "验证码已发送", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(Objects.requireNonNull(throwable.getMessage()));
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "提交错误信息", Toast.LENGTH_SHORT).show());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        SMSSDK.registerEventHandler(handler);
        MobSDK.submitPolicyGrantResult(true, null);
        fragmentStepOneBinding.button2.setOnClickListener(v -> {
            String number = fragmentStepOneBinding.verificationCode.getText().toString();
            String phone = fragmentStepOneBinding.Number.getText().toString();
            SMSSDK.submitVerificationCode("86", phone, number);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            myViewModel.save();
        });
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            fragmentStepOneBinding.getYzm.setBackgroundColor(Color.parseColor("#B6B6D8"));
            fragmentStepOneBinding.getYzm.setClickable(false);
            fragmentStepOneBinding.getYzm.setText(millisUntilFinished / 1000 +"后获取");
        }

        @Override
        public void onFinish() {
            fragmentStepOneBinding.getYzm.setText("重新获取");
            fragmentStepOneBinding.getYzm.setClickable(true);
//            fragmentStepOneBinding.getYzm.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }

}