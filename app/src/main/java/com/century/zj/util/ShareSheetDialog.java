package com.century.zj.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.century.zj.R;
import com.century.zj.databinding.DialogShareSheetdialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShareSheetDialog extends BottomSheetDialogFragment {
    private DialogShareSheetdialogBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.ShareSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =DialogShareSheetdialogBinding.inflate(getLayoutInflater());
        initView();
        return binding.getRoot();

    }

    private void initView() {
        binding.dialogTitle.setText("分享");
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
