package com.doan3.canthotour.View.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnPlace, btnEat, btnHoTel, btnEntertain, btnVehicle;
    FloatingActionButton fab, fabAddPlace;
    boolean enterprise = false;
    public static int test = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        fabAddPlace = findViewById(R.id.fab_addplace);
        btnPlace = findViewById(R.id.btnAllPlace);
        btnEat = findViewById(R.id.btnAllEat);
        btnHoTel = findViewById(R.id.btnAllHotel);
        btnEntertain = findViewById(R.id.btnAllEntertain);
        btnVehicle = findViewById(R.id.btnAllVehicle);

        setSupportActionBar(toolbar);

        // region click button
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_PLACES);
                startActivity(iEventInfo);
            }
        });

        btnEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_EATS);
                startActivity(iEventInfo);
            }
        });

        btnHoTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_HOTELS);
                startActivity(iEventInfo);
            }
        });

        btnEntertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEventInfo = new Intent(MainActivity.this, ActivityService.class);
                iEventInfo.putExtra("url", Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
                startActivity(iEventInfo);
            }
        });

        btnVehicle.setOnClickListener(new View.OnClickListener() {
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
        RecyclerView recyclerViewDD = findViewById(R.id.RecyclerView_Place);
        loadService(recyclerViewDD, Config.URL_HOST + Config.URL_GET_ALL_PLACES, Config.GET_KEY_JSON_PLACE);

        // load eat
        RecyclerView recyclerViewAU = findViewById(R.id.RecyclerView_Eat);
        loadService(recyclerViewAU, Config.URL_HOST + Config.URL_GET_ALL_EATS, Config.GET_KEY_JSON_EAT);

        // load entertainment
        RecyclerView recyclerViewVC = findViewById(R.id.RecyclerView_Entertain);
        loadService(recyclerViewVC, Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS, Config.GET_KEY_JSON_ENTERTAINMENT);

        // load hotel
        RecyclerView recyclerViewKS = findViewById(R.id.RecyclerView_Hotel);
        loadService(recyclerViewKS, Config.URL_HOST + Config.URL_GET_ALL_HOTELS, Config.GET_KEY_JSON_HOTEL);

        // load vehicle
        RecyclerView recyclerViewPT = findViewById(R.id.RecyclerView_Vehicle);
        loadService(recyclerViewPT, Config.URL_HOST + Config.URL_GET_ALL_VEHICLES, Config.GET_KEY_JSON_VEHICLE);
        // endregion

        display_enterprise();

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
                startActivityForResult(new Intent(MainActivity.this, ActivityAddPlace.class), 1);
            }
        });
    }

    void display_enterprise() {
        if (enterprise == false) {
            fabOnClick();
        }
    }

    //Bottom navigation bar
    private void menuBotNavBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar); //Bottom navigation view
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0); //Hiển thị ở trang chủ
        View v = bottomNavigationMenuView.getChildAt(2); //Hiển thị dấu chấm đỏ khi có thông báo
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        new QBadgeView(this).bindTarget(v)
                        .setBadgeNumber(1)  //Set số hoạt động
                        .setBadgeGravity(Gravity.START | Gravity.TOP)
                        .setGravityOffset(26,0,true);

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

