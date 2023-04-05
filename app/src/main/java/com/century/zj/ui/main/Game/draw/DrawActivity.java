
package com.century.zj.ui.main.Game.draw;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.century.zj.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DrawActivity extends AppCompatActivity {


    private ImageView iv;
    private Bitmap baseBitmap;
    private Button btn_resume;
    private Button btn_save;
    private Canvas canvas;
    private Paint paint;
    private ImageView back;

    float radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions =permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(DrawActivity.this, permissions, 1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

//        setContentView(new CustomView1(this));

//        setContentView(new CustomView2(this));

        radio = 4;

        iv = (ImageView) findViewById(R.id.iv);
        // 初始化一个画笔，笔触宽度为5，颜色为红色
        paint = new Paint();
        paint.setStrokeWidth(radio);
        paint.setColor(Color.RED);
        iv = (ImageView) findViewById(R.id.iv);
        btn_resume = (Button) findViewById(R.id.btn_resume);
        btn_save = (Button) findViewById(R.id.btn_save);
        back=findViewById(R.id.return_back);

        btn_resume.setOnClickListener(click);
        btn_save.setOnClickListener(click);
        iv.setOnTouchListener(touch);
        back.setOnClickListener(v -> finish());

    }

    private View.OnTouchListener touch = new View.OnTouchListener() {
        // 定义手指开始触摸的坐标
        float startX;
        float startY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                // 用户按下动作
                case MotionEvent.ACTION_DOWN:
                    // 第一次绘图初始化内存图片，指定背景为白色
                    if (baseBitmap == null) {
                        baseBitmap = Bitmap.createBitmap(iv.getWidth(),
                                iv.getHeight(), Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(baseBitmap);
                        /*canvas.drawColor(Color.WHITE);*/
                    }
                    // 记录开始触摸的点的坐标
                    startX = event.getX();
                    startY = event.getY();
                    break;
                // 用户手指在屏幕上移动的动作
                case MotionEvent.ACTION_MOVE:
                    // 记录移动位置的点的坐标
                    float stopX = event.getX();
                    float stopY = event.getY();

                    Thread t =new Thread(new Runnable() {
                        @Override
                        public void run() {
                            radio+=0.1;

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();

                    paint.setStrokeWidth(radio);
                    //根据两点坐标，绘制连线
                    canvas.drawLine(startX, startY, stopX, stopY, paint);

                    // 更新开始点的位置
                    startX = event.getX();
                    startY = event.getY();
                    // 把图片展示到ImageView中
                    iv.setImageBitmap(baseBitmap);
                    break;
                case MotionEvent.ACTION_UP:
                    radio = 5;
                    break;
                default:
                    break;
            }
            return true;
        }
    };
    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:
                    /*saveBitmap();*/
                    showDialog();
                    Bitmap bitmap = viewToBitmap(iv);
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
                    if (bitmap != null) {
                        boolean bRet = saveBitmapToSd(bitmap, Environment.getExternalStorageDirectory() + File.separator + "drawView.jpg");
                        if (bRet) {
                            showToast("图片保存成功");
                        } else {
                            showToast("图片保存失败");
                        }
                    }
                    break;
                case R.id.btn_resume:
                    resumeCanvas();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * View 转换成 Bitmap
     * @param view
     * @return
     */
    private Bitmap viewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.party);
        builder.setTitle("红船历史背景");
        builder.setMessage(R.string.redboat);
        builder.setPositiveButton("我知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

    /**
     * 将bitmap保存到sd卡
     * @param bitmap
     */
    private boolean saveBitmapToSd(Bitmap bitmap,String savePath) {
        File file = new File(savePath);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void saveImage(String path) {
        File f = new File(path);
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(), f.getAbsolutePath(), f.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    /**
     * 保存图片到SD卡上
     */
    protected void saveBitmap() {

        try {
            // 保存图片到SD卡上
            String fileName = "/sdcard/"+System.currentTimeMillis() + ".png";
            File file = new File(fileName);
            FileOutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            showToast("保存图片成功");
//             // Android设备Gallery应用只会在启动的时候扫描系统文件夹
//             // 这里模拟一个媒体装载的广播，用于使保存的图片可以在Gallery中查看
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment
                    .getExternalStorageDirectory()));
            sendBroadcast(intent);
            stream.close();
        } catch (Exception e) {
            showToast("保存图片失败");
            e.printStackTrace();
        }
    }

    // 手动清除画板的绘图，重新创建一个画板
    protected void resumeCanvas() {
        if (baseBitmap != null) {
            baseBitmap = Bitmap.createBitmap(iv.getWidth(),
                    iv.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(baseBitmap);
            /*canvas.drawColor(Color.WHITE);*/
            iv.setImageBitmap(baseBitmap);
            showToast("清除画板成功，可以重新开始绘图");
        }
    }
    public void showToast(String msg) {
        Toast.makeText(DrawActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}

/**
 * 使用内部类 自定义一个简单的View
 * @author Administrator
 *
 */
class CustomView1 extends View {

    Paint paint;

    public CustomView1(Context context) {
        super(context);
        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.YELLOW);//颜色
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);//画笔大小
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //直接将View显示区域用某个颜色填充满
        //canvas.drawColor(Color.BLUE);

        //绘圆
        canvas.drawCircle(100, 100, 90, paint);
        //绘线
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        canvas.drawLine(300,300,400,500,paint);

        RectF rect = new RectF(100, 100, 300, 300);

        //绘制弧线区域
        paint.setColor(Color.RED);
        canvas.drawArc(rect, //弧线所使用的矩形区域大小
                0,  //开始角度
                120, //扫过的角度
                true, //是否使用中心
                paint);

        //矩形区域内切椭圆
        rect = new RectF(500,500,600,700);
        canvas.drawOval(rect, paint);

        //绘矩形
        paint.setColor(Color.BLUE);
        rect = new RectF(800,800,1000,1000);
        canvas.drawRect(rect,paint);

        //绘圆角矩形
        paint.setColor(Color.YELLOW);
        canvas.drawRoundRect(rect,
                50, //x轴的半径
                50, //y轴的半径
                paint);

        Path path = new Path(); //定义一条路径
        path.moveTo(100, 500); //移动到 坐标10,10
        path.lineTo(300, 600);
        path.lineTo(200,500);
        path.lineTo(100, 500);
        canvas.drawPath(path, paint);

    }
}
