package com.doan3.canthotour.View.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityServiceInfo extends AppCompatActivity {
    Button btnChiaSe;
    int ma;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdichvu);

        btnChiaSe = findViewById(R.id.btnChiaSeDv);

        ma = getIntent().getIntExtra("masp", 1);

        load(this, Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + ma);

        menuBotNavBar();
    }

    private void menuBotNavBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityServiceInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityServiceInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityServiceInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityServiceInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    public void load(Activity activity, String url) {
        TextView txtTenDv = activity.findViewById(R.id.textViewTenDv);
        TextView txtGioiThieu = activity.findViewById(R.id.textViewGioiThieuDv);
        TextView txtGiaThap = activity.findViewById(R.id.textViewGiaThap);
        TextView txtGiaCao = activity.findViewById(R.id.textViewGiaCao);
        TextView txtGioMo = activity.findViewById(R.id.textViewGioMc);
        TextView txtGioDong = activity.findViewById(R.id.textViewGioDong);
        TextView txtDiaChi = activity.findViewById(R.id.textViewDiaChiDv);
        TextView txtSDT = activity.findViewById(R.id.textViewSdtDv);
        TextView txtWebsite = activity.findViewById(R.id.textViewWebsite);
        ImageView imgChiTiet1Thumb = activity.findViewById(R.id.imgChiTiet1);
        ImageView imgChiTiet2Thumb = activity.findViewById(R.id.imgChiTiet2);
        ImageView imgBanner = activity.findViewById(R.id.imgBanner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        TextView fbEvent = findViewById(R.id.fb_sukien);

        ServiceInfo serviceInfo = new ModelService().getServiceInfo(url);

        if (serviceInfo.getTenAU() != null) {
            txtTenDv.setText(serviceInfo.getTenAU());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbAnUong));
            toolbarTitle.setText("Chi tiết quán ăn");
        } else if (serviceInfo.getTenKS() != null) {
            txtTenDv.setText(serviceInfo.getTenKS());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbKhachSan));
            toolbarTitle.setText("Chi tiết khách sạn");
        } else if (serviceInfo.getTenTQ() != null) {
            txtTenDv.setText(serviceInfo.getTenTQ());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbThamQuan));
            toolbarTitle.setText("Danh sách điểm tham quan");
        } else if (serviceInfo.getTenPT() != null) {
            txtTenDv.setText(serviceInfo.getTenPT());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbPhuongTien));
            toolbarTitle.setText("Chi tiết phương tiện");
        } else {
            txtTenDv.setText(serviceInfo.getTenVC());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbVuiChoi));
            toolbarTitle.setText("Chi tiết điểm vui chơi");
        }
        if (serviceInfo.getLhsk().equals("null")) {
            fbEvent.setVisibility(TextView.INVISIBLE);
        } else {
            fbEvent.setText(serviceInfo.getLhsk());
            fbEvent.setVisibility(TextView.VISIBLE);
        }
        txtGioiThieu.setText(serviceInfo.getGioiThieuDV());
        txtGiaThap.setText(serviceInfo.getGiaThapNhat());
        txtGiaCao.setText(serviceInfo.getGiaCaoNhat());
        txtGioMo.setText(serviceInfo.getGioMoCua());
        txtGioDong.setText(serviceInfo.getGioDongCua());
        txtDiaChi.setText(serviceInfo.getDiaChi());
        txtSDT.setText(serviceInfo.getSdt());
        txtWebsite.setText(serviceInfo.getWebsite());

        String urlChiTiet1 = null, urlChiTiet2 = null, urlBanner = null;
        try {
            urlChiTiet1 = new ModelService.Load().execute(Config.URL_HOST + "lay-mot-hinh-thumb-1/" + serviceInfo.getId())
                    .get().split("\\+")[0].substring(1);
            urlChiTiet2 = new ModelService.Load().execute(Config.URL_HOST + "lay-mot-hinh-thumb-2/" + serviceInfo.getId())
                    .get().split("\\+")[0].substring(1);
            urlBanner = new ModelService.Load().execute(Config.URL_HOST + "lay-mot-hinh-banner/" + serviceInfo.getId())
                    .get().split("\\+")[0].substring(1);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Picasso.with(this).load(Config.URL_HOST + urlChiTiet1).into(imgChiTiet1Thumb);
        Picasso.with(this).load(Config.URL_HOST + urlChiTiet2).into(imgChiTiet2Thumb);
        Picasso.with(this).load(Config.URL_HOST + urlBanner).into(imgBanner);

    }
}
