package com.doan3.canthotour.View.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import java.util.ArrayList;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityEatInfo extends AppCompatActivity {
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    int ma;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdichvu);


        btnChiaSe = findViewById(R.id.btnChiaSeDv);

        ma = getIntent().getIntExtra("masp", 1);

        load(this, Config.URL_HOST + Config.URL_GET_ALL_EATS + "/" + ma, Config.JSON_EAT);

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
                        startActivity(new Intent(ActivityEatInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEatInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEatInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEatInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    public void load(Activity activity, String url, ArrayList<String> formatJson) {
        TextView txtTenDv = activity.findViewById(R.id.textViewTenDv);
        TextView txtGioiThieu = activity.findViewById(R.id.textViewGioiThieuDv);
        TextView txtGia = activity.findViewById(R.id.textViewGiaDv);
        TextView txtGio = activity.findViewById(R.id.textViewGioDv);
        TextView txtLoaiHinh = activity.findViewById(R.id.textViewLoaiHinhDv);
        TextView txtDiaChi = activity.findViewById(R.id.textViewDiaChiDv);
        TextView txtSDT = activity.findViewById(R.id.textViewSdtDv);
        TextView txtWebsite = activity.findViewById(R.id.textViewWebsite);

        ServiceInfo serviceInfo = new ModelService().getEatInfo(url, formatJson);
        txtTenDv.setText(serviceInfo.getTen());
        txtGioiThieu.setText(serviceInfo.getGioiThieuDV());
        txtGia.setText(serviceInfo.getGiaThapNhat() + " -> " + serviceInfo.getGiaCaoNhat());
        txtGio.setText(serviceInfo.getGioMoCua() + " -> " + serviceInfo.getGioDongCua());
        txtLoaiHinh.setText(serviceInfo.getGioiThieuDV());
        txtDiaChi.setText(serviceInfo.getDiaChi());
        txtSDT.setText(serviceInfo.getSdt());
        txtWebsite.setText(serviceInfo.getWebsite());
    }
}
