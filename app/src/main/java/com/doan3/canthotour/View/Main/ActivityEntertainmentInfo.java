package com.doan3.canthotour.View.Main;

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
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityEntertainmentInfo extends AppCompatActivity {
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    TextView txtTenDD, txtDiaChi, txtSDT, txtLoaiHinh, txtGia, txtGioiThieu, txtGio;
    String masp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdichvu);

        btnLuuDiaDiem = findViewById(R.id.btnLuuDiaDiemDv);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCanDv);
        btnChiaSe = findViewById(R.id.btnChiaSeDv);
        txtTenDD = findViewById(R.id.textViewTenDv);
        txtDiaChi = findViewById(R.id.textViewDiaChiDv);
        txtSDT = findViewById(R.id.textViewSdtDv);
        txtLoaiHinh = findViewById(R.id.textViewLoaiHinhDv);
        txtGia = findViewById(R.id.textViewGiaDv);
        txtGioiThieu = findViewById(R.id.textViewGioiThieuDv);
        txtGio = findViewById(R.id.textViewGioDv);

        masp = getIntent().getStringExtra("masp");

        String idService, idPlace;
        String urlService = null, urlPlace = null;
        ArrayList<String> urlEntertainment = new ArrayList<>();

        // lấy id dịch vụ trong ăn uống, lấy id địa điểm trong dịch vụ
        try {
            urlEntertainment.add(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS + "/" + masp);

            idService = new ActivityEatInfo.GetIdService().execute(urlEntertainment, Config.JSON_ENTERTAINMENT).get();
            idPlace = new ActivityEatInfo.GetIdPlace().execute(Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService).get();
            urlService = Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService;
            urlPlace = Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + idPlace;

            new ActivityEatInfo.GetIdService().execute(urlEntertainment, Config.JSON_ENTERTAINMENT);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        new ActivityEatInfo.LoadPlace(this).execute(urlEntertainment, Config.JSON_ENTERTAINMENT);
        new ActivityEatInfo.LoadServiceInfo(this).execute(urlService);
        new ActivityEatInfo.LoadPlaceInfo(this).execute(urlPlace);

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
                        startActivity(new Intent(ActivityEntertainmentInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEntertainmentInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEntertainmentInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEntertainmentInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

}
