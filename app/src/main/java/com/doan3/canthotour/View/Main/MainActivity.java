package com.doan3.canthotour.View.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.PlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.Content.ActivityEat;
import com.doan3.canthotour.View.Main.Content.ActivityEntertainment;
import com.doan3.canthotour.View.Main.Content.ActivityHotel;
import com.doan3.canthotour.View.Main.Content.ActivityPlace;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnDiaDanh, btnAnUong, btnKhachSan, btnVuiChoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        btnDiaDanh = findViewById(R.id.btnTatCaDiaDanh);
        btnAnUong = findViewById(R.id.btnTatCaQuanAn);
        btnKhachSan = findViewById(R.id.btnTatCaKhachSan);
        btnVuiChoi = findViewById(R.id.btnTatCaVuiChoi);

        setSupportActionBar(toolbar);

        btnDiaDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityPlace.class));
            }
        });

        btnAnUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityEat.class));
            }
        });

        btnKhachSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityHotel.class));
            }
        });

        btnVuiChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityEntertainment.class));
            }
        });

        initView_Place();
        initView_Eat();
        initView_Hotel();
        initView_Entertainment();

        menuBotNavBar();

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

    //region Nội dung chính (content-view)

    //Get view place
    private void initView_Place() {
        // đổi đường dẫn từ String sang ArrayList để truyền vào AsyncTask
        ArrayList<String> url = new ArrayList<>();
        url.add(Config.URL_HOST + Config.URL_GET_ALL_PLACES);
        url.add("40");
        url.add("8");
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDanh);

        new Load(this, recyclerView).execute(url, Config.JSON_PLACE);
    }

    //Get view eat
    private void initView_Eat() {
        // đổi đường dẫn từ String sang ArrayList để truyền vào AsyncTask
        ArrayList<String> url = new ArrayList<>();
        url.add(Config.URL_HOST + Config.URL_GET_ALL_EATS);
        url.add("20");
        url.add("4");
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_AnUong);

        new Load(this, recyclerView).execute(url, Config.JSON_EAT);
    }

    //Get view hotel
    private void initView_Hotel() {
        // đổi đường dẫn từ String sang ArrayList để truyền vào AsyncTask
        ArrayList<String> url = new ArrayList<>();
        url.add(Config.URL_HOST + Config.URL_GET_ALL_HOTELS);
        url.add("25");
        url.add("5");
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_KhachSan);

        new Load(this, recyclerView).execute(url, Config.JSON_HOTEL);
    }

    private void initView_Entertainment() {
        // đổi đường dẫn từ String sang ArrayList để truyền vào AsyncTask
        ArrayList<String> url = new ArrayList<>();
        url.add(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
        url.add("25");
        url.add("5");
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_VuiChoi);

        Load LoadInfo = new Load(this, recyclerView);
        LoadInfo.execute(url, Config.JSON_ENTERTAINMENT);
    }

    //region Topbar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Custom view eat
    private class Load extends AsyncTask<ArrayList<String>, ArrayList<Place>, Void> {
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public Load(Activity act, RecyclerView rv) {
            activity = act;
            recyclerView = rv;
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected Void doInBackground(ArrayList<String>... strings) {
            /*
            strings[0] truyền vào 3 giá trị
            1 là url để get dữ liệu
            2 là số dữ liệu giới hạn load lên trang chủ
            3 là số phần tử của json
            */

            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0].get(0))), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), strings[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Place> list = new ArrayList<>();
            // limit là số giới hạn load lên trang chủ
            // num là số phần tử của json
            int limit = Integer.parseInt(strings[0].get(1));
            int num = Integer.parseInt(strings[0].get(2));

            int size = (arrayList.size() > limit) ? limit : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i++) {
                if (i % num == 1) {
                    list.add(new Place(R.drawable.benninhkieu1, arrayList.get(i)));
                    publishProgress(list);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Place>[] values) {
            super.onProgressUpdate(values);
            PlaceAdapter placeAdapter = new PlaceAdapter(values[0], getApplicationContext());
            recyclerView.setAdapter(placeAdapter);
        }
    }
}
