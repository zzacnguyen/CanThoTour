package com.doan3.canthotour.View.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.doan3.canthotour.Adapter.ServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.Content.ActivityService;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityAddPlace;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Search.ActivitySearch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnDiaDanh, btnAnUong, btnKhachSan, btnVuiChoi, btnPhuongTien;
    FloatingActionButton fab, fabThemDiaDiem;
    boolean doanhnghiep = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        fabThemDiaDiem = findViewById(R.id.fab_themdiadiem);
        btnDiaDanh = findViewById(R.id.btnTatCaDiaDanh);
        btnAnUong = findViewById(R.id.btnTatCaQuanAn);
        btnKhachSan = findViewById(R.id.btnTatCaKhachSan);
        btnVuiChoi = findViewById(R.id.btnTatCaVuiChoi);
        btnPhuongTien = findViewById(R.id.btnTatCaPhuongTien);

        setSupportActionBar(toolbar);

        // region click button
        btnDiaDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_PLACES);
                startActivity(iEventInfo);
            }
        });

        btnAnUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_EATS);
                startActivity(iEventInfo);
            }
        });

        btnKhachSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_HOTELS);
                startActivity(iEventInfo);
            }
        });

        btnVuiChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
                startActivity(iEventInfo);
            }
        });

        btnPhuongTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_VEHICLES);
                startActivity(iEventInfo);
            }
        });
        // endregion

        // region load service
        // load place
        RecyclerView recyclerViewDD = findViewById(R.id.RecyclerView_DiaDanh);
        loadService(recyclerViewDD, Config.URL_HOST + Config.URL_GET_ALL_PLACES, Config.JSON_PLACE);

        // load eat
        RecyclerView recyclerViewAU = findViewById(R.id.RecyclerView_AnUong);
        loadService(recyclerViewAU, Config.URL_HOST + Config.URL_GET_ALL_EATS, Config.JSON_EAT);

        // load entertainment
        RecyclerView recyclerViewVC = findViewById(R.id.RecyclerView_VuiChoi);
        loadService(recyclerViewVC, Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS, Config.JSON_ENTERTAINMENT);

        // load hotel
        RecyclerView recyclerViewKS = findViewById(R.id.RecyclerView_KhachSan);
        loadService(recyclerViewKS, Config.URL_HOST + Config.URL_GET_ALL_HOTELS, Config.JSON_HOTEL);

        // load vehicle
        RecyclerView recyclerViewPT = findViewById(R.id.RecyclerView_PhuongTien);
        loadService(recyclerViewPT, Config.URL_HOST + Config.URL_GET_ALL_VEHICLES, Config.JSON_VEHICLE);
        // endregion

        display_doanhnghiep();

        menuBotNavBar();

    }

    //region Topbar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        startActivityForResult(new Intent(MainActivity.this, ActivitySearch.class), 2);

        return super.onOptionsItemSelected(item);
    }
    // endregion

    void fabOnClick() { //Floating bar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityAddPlace.class));
            }
        });
    }

    void display_doanhnghiep() {
        if (doanhnghiep == false) {
            fabOnClick();
        }
    }

    //Bottom navigation bar
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
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(MainActivity.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(MainActivity.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(MainActivity.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    //Custom view service
    private void loadService(RecyclerView recyclerView, String url, ArrayList<String> formatJson) {
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Service> services = new ModelService().getServiceList(url, formatJson);

        ServiceAdapter serviceAdapter =
                new ServiceAdapter(services, getApplicationContext());
        recyclerView.setAdapter(serviceAdapter);
        serviceAdapter.notifyDataSetChanged();
    }
}

