package com.century.zj.ui.main.Travel.Activity;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.century.zj.R;
import com.century.zj.api.Api;
import com.century.zj.api.ApiConfig;
import com.century.zj.api.TtitCallback;
import com.century.zj.databinding.ActivityTravelBinding;
import com.century.zj.entity.AttractionEntity;
import com.century.zj.entity.PlaceEntity;
import com.century.zj.entity.PlaceListResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


public class TravelActivity extends AppCompatActivity {

    ActivityTravelBinding travelBinding;
    private TextView tr_text;
    private TextView tr_title;
    private ImageView tr_img;
    private MapView mapView;
    private AMap aMap;
    private ImageButton tr_Button;
    private PopupMenu popupMenu;
    private TextView tr_textView;
    private LatLng[] latLngs;
    private static MarkerOptions[] markerOptions;
    private Marker[] markers;
    private List<LatLng> latLngList = new ArrayList<LatLng>();
    private Polyline polyline;
    private  List<PlaceEntity> datas = new ArrayList<>();
    private TravellAdapter adapter;
    private List<AttractionEntity> attractionEntityList
            = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setDiffNewData(attractionEntityList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travelBinding = ActivityTravelBinding.inflate(getLayoutInflater());
        setContentView(travelBinding.getRoot());
        //获取控件实体类
        mapView =travelBinding.map;
        mapView.onCreate(savedInstanceState);
        tr_Button=travelBinding.travelButton;
        tr_textView=travelBinding.travelTop;
        tr_text=travelBinding.trText;
        tr_title=travelBinding.trTitle;
        tr_img=travelBinding.trImg;
        init();
        travelBinding.travelLogo.setOnClickListener(v -> finish());
        aMap.setOnMarkerClickListener(mMarkerListener);//添加标记点击事件
        //添加Popupmenu弹出事件
        popupMenu =new PopupMenu(this,tr_Button);
        getMenuInflater().inflate(R.menu.menu_travel,popupMenu.getMenu());
        popupMenu.setForceShowIcon(true);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId())
            {
                case R.id.firstTravel:
                    tr_textView.setText("红色精神之旅");
                    Toast.makeText(this,
                            "您选择了【" + item.getTitle() + "】",
                            Toast.LENGTH_SHORT).show();
                    aMap.clear(); //清除地图上的坐标
                    getPlaceList(0);
                    tr_img.setBackgroundResource(R.drawable.br1);
                    tr_title.setText(R.string.spirit_travel);
                    tr_text.setText(R.string.tr1);
                    AttractionClick();
                    break;
                case R.id.secondTravel:
                    tr_textView.setText("红色信仰之旅");
                    Toast.makeText(this,
                            "您单击了【" + item.getTitle() + "】菜单项",
                            Toast.LENGTH_SHORT).show();
                    aMap.clear();
                    getPlaceList(1);
                    tr_img.setBackgroundResource(R.drawable.br2);
                    tr_title.setText(R.string.belief_travel);
                    tr_text.setText(R.string.tr2);
                    AttractionClick();
                    break;
                case R.id.thirdTravel:
                    tr_textView.setText("烽火岁月之旅");
                    Toast.makeText(this,
                            "您单击了【" + item.getTitle() + "】菜单项",
                            Toast.LENGTH_SHORT).show();
                    aMap.clear();
                    getPlaceList(2);
                    tr_img.setBackgroundResource(R.drawable.br3);
                    tr_title.setText(R.string.Beacon_years);
                    tr_text.setText(R.string.tr3);
                    AttractionClick();
                    break;
                case R.id.fourthTravel:
                    tr_textView.setText("山海垦荒之旅");
                    Toast.makeText(this,
                            "您单击了【" + item.getTitle() + "】菜单项",
                            Toast.LENGTH_SHORT).show();
                    aMap.clear();
                    getPlaceList(3);
                    tr_img.setBackgroundResource(R.drawable.br4);
                    tr_title.setText(R.string.sea_reclamation);
                    tr_text.setText(R.string.tr4);
                    AttractionClick();
                    break;
            }
            return true;
        });
        initData();
    }

    public void onPopupButtonClick(View button)
    {
        popupMenu.show();
    }

    private void init(){
        if(aMap==null){
            aMap=mapView.getMap();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void initData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        travelBinding.listAttraction.setLayoutManager(linearLayoutManager);
        adapter = new TravellAdapter(R.layout.item_attraction_layout, attractionEntityList);
        adapter.setDiffCallback(new TravellAdapter.DiffAttrCallback());
        travelBinding.listAttraction.setAdapter(adapter);
        getPlaceList(0);  //默认选中第一条路
        AttractionClick();
    }

    private void getPlaceList(int i){
        HashMap<String, Object> params = new HashMap<>();
        String[] road=new String[]{
                ApiConfig.FIRSTPLACE_LIST_ALL,ApiConfig.SECONDPLACE_LIST_ALL,ApiConfig.THIRDPLACE_LIST_ALL,ApiConfig.FOURTHPLACE_LIST_ALL
        };
        Api.config(road[i], params).getRequest(this, new TtitCallback() {

            @Override
            public void onSuccess(String res) {
                PlaceListResponse response = new Gson().fromJson(res, PlaceListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<PlaceEntity> list = response.getList();
                    datas =list;
                    markerOptions=new MarkerOptions[datas.size()];
                    latLngs=new LatLng[datas.size()];
                    markers=new Marker[datas.size()];
                    latLngList.clear(); //默认清除上此遗留的坐标点
                    attractionEntityList.clear();
                    for (int i=0;i<datas.size();i++) {
                        AttractionEntity attractionEntity= new AttractionEntity();
                        PlaceEntity placeEntity = list.get(i);
                        attractionEntity.setId(placeEntity.getId());
                        attractionEntity.setTitle(placeEntity.getTitle());
                        attractionEntity.setImgurl(placeEntity.getImgurl());
                        if(placeEntity.getText()!=null)
                        attractionEntity.setText(placeEntity.getText().replace("\\u","\u3000"));
                        attractionEntityList.add(attractionEntity);
                        latLngs[i]=new LatLng(placeEntity.getLatitude(), placeEntity.getLongitude());
                        latLngList.add(latLngs[i]);
                        markers[i] = aMap.addMarker(new MarkerOptions().position(latLngs[i]).title(placeEntity.getTitle()).snippet(placeEntity.getInfo())
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(getResources(), R.drawable.tr_flag))));
                        markers[i].setFlat(true);
                    }
                    mHandler.sendEmptyMessage(0);
                    CameraUpdate cu= CameraUpdateFactory.changeLatLng(latLngs[0]);
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(8));
                    aMap.moveCamera(cu);
                    polyline =aMap.addPolyline(new PolylineOptions().
                            addAll(latLngList).width(10).color(Color.argb(255, 255, 48, 48)));
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }
    AMap.OnMarkerClickListener mMarkerListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {

            Intent intent=new Intent(TravelActivity.this,AttractionActivity.class);
            //通过intent传参，可以传实体类
            intent.putExtra("title",marker.getTitle());
            startActivity(intent);
            return true; // 返回:true 表示点击marker 后marker 不会移动到地图中心；返回false 表示点击marker 后marker 会自动移动到地图中心
        }
    };

    public void AttractionClick(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AttractionEntity attractionEntity = attractionEntityList.get(position);
                CameraUpdate cu= CameraUpdateFactory.changeLatLng(latLngList.get(position));
                markers[position].showInfoWindow();
                aMap.moveCamera(cu);
                Intent intent=new Intent(TravelActivity.this,AttractionActivity.class);
                intent.putExtra("title",attractionEntity.getTitle());
                startActivity(intent);
            }
        });
    }
}
