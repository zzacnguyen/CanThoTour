package com.doan3.canthotour.View.Main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Adapter.ServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.Content.FragmentService;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityAddPlace;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Search.ActivitySearch;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    public static int badgeNumber;
    public static Fragment fragment = null;
    Toolbar toolbar;
    Button btnPlace, btnEat, btnHoTel, btnEntertain, btnVehicle;
    FloatingActionButton fab, fabAddPlace;
    boolean enterprise = false;
    FragmentManager fragmentManager = getFragmentManager();

    //Bottom navigation bar
    public static void menuBotNavBar(final Activity activity, int i) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavView_Bar); //Bottom navigation view
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(i);
        menuItem.setChecked(true);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0); //Hiển thị ở trang chủ
        View v = bottomNavigationMenuView.getChildAt(2); //Hiển thị dấu chấm đỏ khi có thông báo
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        try {
            String getBadgeNumber = new httpGet().execute(Config.URL_HOST + Config.URL_GET_EVENT_NUMBER).get();
            badgeNumber = Integer.parseInt(getBadgeNumber);
            Log.d("badge number", getBadgeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        new QBadgeView(activity).bindTarget(v)
                .setBadgeNumber(badgeNumber)  //Set số thông báo hiển thị
                .setBadgeGravity(Gravity.START | Gravity.TOP)
                .setGravityOffset(26, 0, true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        activity.startActivity(new Intent(activity, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        Intent iNotify = new Intent(activity, ActivityNotify.class);
                        badgeNumber = 0; //Ẩn thông báo đi khi đã ấn vào
                        activity.startActivity(iNotify);
                        break;
                    case R.id.ic_canhan:
                        activity.startActivity(new Intent(activity, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

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

        menuBotNavBar(this, 0);

    }

    //region Topbar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu, menu);
        return true;
    }
    // endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        startActivityForResult(new Intent(MainActivity.this, ActivitySearch.class), 2);

        return super.onOptionsItemSelected(item);
    }

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

    public void addFragment(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new FragmentService();
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.btnAllPlace:
                bundle.putString("url", Config.URL_HOST + Config.URL_GET_ALL_PLACES);
                fragment.setArguments(bundle);
                break;

            case R.id.btnAllEat:
                bundle.putString("url", Config.URL_HOST + Config.URL_GET_ALL_EATS);
                fragment.setArguments(bundle);
                break;

            case R.id.btnAllHotel:
                bundle.putString("url", Config.URL_HOST + Config.URL_GET_ALL_HOTELS);
                fragment.setArguments(bundle);
                break;

            case R.id.btnAllEntertain:
                bundle.putString("url", Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
                fragment.setArguments(bundle);
                break;

            case R.id.btnAllVehicle:
                bundle.putString("url", Config.URL_HOST + Config.URL_GET_ALL_VEHICLES);
                fragment.setArguments(bundle);
                break;
        }

        fragmentTransaction.add(R.id.mainFragContent, fragment, "frag");
        fragmentTransaction.addToBackStack("aaa");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) { //đếm số lượng trong ngăn xếp còn bao nhiêu số lượng fragment
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}

