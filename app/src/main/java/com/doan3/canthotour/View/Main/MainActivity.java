package com.doan3.canthotour.View.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Adapter.ServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.ModelEvent;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.Model.SessionManager;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.Content.FragmentService;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityAddPlace;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Search.ActivityAdvancedSearch;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import q.rorbin.badgeview.QBadgeView;

import static com.doan3.canthotour.View.Personal.ActivityPersonal.userType;

public class MainActivity extends AppCompatActivity {

    public static int badgeNumber;
    public static Handler UIHandler;
    @SuppressLint("StaticFieldLeak")
    public static Fragment fragment = null;
    @SuppressLint("StaticFieldLeak")
    static BottomNavigationView bottomNavigationView;

    static {
        try {

            badgeNumber = Integer.parseInt(new httpGet().execute(Config.URL_HOST + Config.URL_GET_EVENT_NUMBER).get())
                    - ModelEvent.getJsonFileEvent().length();

            UIHandler = new Handler(Looper.getMainLooper()); //Khai báo UIHandler để tạo gọi được phương thức runOnUI

//region notifyRepeat
            Thread notifyRepeat = new Thread() {
                @Override
                public void run() {
                    while (!isInterrupted()) {
                        try {
                            Thread.sleep(120000); //1000 ms = 1 sec
                            runOnUI(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        badgeNumber = Integer.parseInt(new httpGet()
                                                .execute(Config.URL_HOST + Config.URL_GET_EVENT_NUMBER).get())
                                                - ModelEvent.getJsonFileEvent().length();
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            notifyRepeat.start();

            //endregion
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    Toolbar toolbar;
    Button btnPlace, btnEat, btnHoTel, btnEntertain, btnVehicle;
    ImageView btnSearch;
    FloatingActionButton fabAddPlace;
    FragmentManager fragmentManager = getFragmentManager();
    SessionManager sessionManager;

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    //Bottom navigation bar
    public static void menuBotNavBar(final Activity activity, int i) {
        bottomNavigationView = activity.findViewById(R.id.bottomNavView_Bar); //Bottom navigation view
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(i);
        menuItem.setChecked(true);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0); //Hiển thị ở trang chủ
        View v = bottomNavigationMenuView.getChildAt(2); //Hiển thị dấu chấm đỏ khi có thông báo

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

        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSearch = new Intent(MainActivity.this, ActivityAdvancedSearch.class);
                startActivity(iSearch);
            }
        });
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        if (!isStoragePermissionGranted()) {
            load();
        } else {
            load();
        }
    }

    void load() {
        toolbar = findViewById(R.id.toolbar);
        fabAddPlace = findViewById(R.id.fab);
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

    void fabOnClick() { //Floating bar
        fabAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActivityAddPlace.class));
            }
        });
    }

    void display_enterprise() {
        // nếu người dùng không phải người dùng cá nhân thì cho thêm
        if (userType != null && !userType.equals("1")) {
            fabAddPlace.setVisibility(View.VISIBLE);
            fabOnClick();
        } else
            fabAddPlace.setVisibility(View.GONE);
    }

    //Custom view service
    private void loadService(RecyclerView recyclerView, String url, ArrayList<String> formatJson) {
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Service> services = new ModelService().getServiceInMain(url, formatJson);

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

//            case R.id.btnSearch:
//                fragment = new FragmentSearch();
//                break;
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

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}

