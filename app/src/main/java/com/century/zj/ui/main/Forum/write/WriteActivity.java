package com.century.zj.ui.main.Forum.write;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.century.zj.databinding.ActivityWriteBinding;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.util.SaveTool;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WriteActivity extends AppCompatActivity {

    private String picPath = null;
    String TAG = "MainActivity";
    int anInt=1;
    List<File> files=new ArrayList<>();
    ActivityWriteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.uploadImage.setOnClickListener(v ->{
            String s= String.valueOf(binding.word.getText()).trim();
            uploadFile(files,s);
            finish();
        });
        binding.writeBack.setOnClickListener(v -> finish());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Finger touch screen event
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // get current focus,Generally it is EditText
            View view = getCurrentFocus();
            if (isShouldHideSoftKeyBoard(view, ev)) {
                hideSoftKeyBoard(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideSoftKeyBoard(View view, MotionEvent event) {
        if ((view instanceof EditText)) {
            int[] l = { 0, 0 };
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top +view.getHeight(), right = left
                    + view.getWidth();
            // If click the EditText event ,ignore it
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // if the focus is EditText,ignore it;
        return false;
    }

    private void hideSoftKeyBoard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //当选择的图片不为空的话，在获取到图片的途径
            Uri uri = data.getData();
            Log.e(TAG, "uri = " + uri);
            try {
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, pojo, null, null, null);
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int col_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(col_index);
                    //这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
                    //这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        anInt++;
                        picPath = path;
                        files.add(new File(picPath));
                        Log.v(TAG, picPath);
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        if (anInt==2){
                            binding.imageView2.setVisibility(View.VISIBLE);
                            binding.imageView1.setImageBitmap(bitmap);
                        }else if (anInt==3){
                            binding.imageView3.setVisibility(View.VISIBLE);
                            binding.imageView2.setImageBitmap(bitmap);
                        }else if (anInt==4){
                            binding.imageView4.setVisibility(View.VISIBLE);
                            binding.imageView3.setImageBitmap(bitmap);
                        }
                    } else {
                        alert();
                    }
                } else {
                    alert();
                }
            } catch (Exception ignored) {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定", (dialog1, which) -> picPath = null).create();
        dialog.show();
    }

    public void uploadFile(List<File> file,String text) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", "imageView");//ParamKey.TOKEN 自定义参数key常量类，即参数名
        for (int i=0;i<file.size();i++){
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file.get(i));
            builder.addFormDataPart("file"+(i+1), file.get(i).getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        SharedPreferences sharedPreferences=this.getSharedPreferences("User_Tab",Context.MODE_PRIVATE);
        List<MultipartBody.Part> parts = builder.build().parts();
        Observable<Object> baseEntity=RetrofitFactory.getInstence().API().downForum(sharedPreferences.getString("userphone",null),null,text,parts);
        baseEntity.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.v("TAG6", (String) o);
                    }
                });
    }
    public void OnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
}