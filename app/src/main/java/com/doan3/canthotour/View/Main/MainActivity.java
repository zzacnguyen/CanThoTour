package com.doan3.canthotour.View.Main;

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

import com.doan3.canthotour.Adapter.EatAdapter;
import com.doan3.canthotour.Adapter.EntertainmentAdapter;
import com.doan3.canthotour.Adapter.HotelAdapter;
import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.PlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Eat;
import com.doan3.canthotour.Model.Entertainment;
import com.doan3.canthotour.Model.Hotel;
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
    private void menuBotNavBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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
    private void initView_Place(){
        new place().execute(Config.URL_HOST+Config.URL_GET_ALL_PLACES);
    }

    //Get view eat
    private void initView_Eat(){
        new eat().execute(Config.URL_HOST+Config.URL_GET_ALL_EATS);
    }

    //Get view hotel
    private void initView_Hotel(){
        new hotel().execute(Config.URL_HOST + Config.URL_GET_ALL_HOTELS);
    }

    private void initView_Entertainment(){
        new entertainment().execute(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
    }

    //Custom view place
    private class place extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arr = JsonHelper.parseJsonNoId(new JSONObject(s), Config.JSON_LOAD);
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_PLACE);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDanh);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Place> listPlace = new ArrayList<>();

                // json địa danh có 8 phần tử, phần tử 1 là tên địa danh nên i % 8 == 1 để lấy tên địa danh
                // giới hạn load 5 phần tử nên 8 * 5 = 40
                // nếu không giới hạn thì thay 40 = arrayList.size()
                int size = (arrayList.size() > 40)? 40 : arrayList.size();
                for (int i = 0; i < size; i++){
                    if (i % 8 == 1)
                        listPlace.add(new Place(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                PlaceAdapter placeAdapter = new PlaceAdapter(listPlace, getApplicationContext());
                recyclerView.setAdapter(placeAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Custom view eat
    private class eat extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arr = JsonHelper.parseJsonNoId(new JSONObject(s), Config.JSON_LOAD);
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_EAT);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_AnUong);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Eat> listEat = new ArrayList<>();

                // json ăn uống có 4 phần tử, phần tử 1 là tên địa danh nên i % 4 == 1 để lấy tên địa danh
                // giới hạn load 5 phần tử nên 4 * 5 = 20
                // nếu không giới hạn thì thay 20 = arrayList.size()
                int size = (arrayList.size() > 20)? 20 : arrayList.size();
                for (int i = 0; i < size; i++){
                    if (i % 4 == 1)
                        listEat.add(new Eat(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                EatAdapter eatAdapter = new EatAdapter(listEat, getApplicationContext());
                recyclerView.setAdapter(eatAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Custom view hotel
    private class hotel extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arr = JsonHelper.parseJsonNoId(new JSONObject(s), Config.JSON_LOAD);
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_HOTEL);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_KhachSan);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Hotel> listHotel = new ArrayList<>();

                // json khách sạn có 5 phần tử, phần tử 1 là tên địa danh nên i % 5 == 1 để lấy tên địa danh
                // giới hạn load 5 phần tử nên 5 * 5 = 25
                // nếu không giới hạn thì thay 25 = arrayList.size()
                int size = (arrayList.size() > 25)? 25 : arrayList.size();
                for (int i = 0; i < size; i++){
                    if (i % 5 == 1)
                        listHotel.add(new Hotel(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                HotelAdapter hotelAdapter = new HotelAdapter(listHotel, getApplicationContext());
                recyclerView.setAdapter(hotelAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Custom view entertain
    private class entertainment extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arr = JsonHelper.parseJsonNoId(new JSONObject(s), Config.JSON_LOAD);
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_ENTERTAINMENT);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_VuiChoi);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Entertainment> listEntertainment = new ArrayList<>();

                // json khách sạn có 5 phần tử, phần tử 1 là tên địa danh nên i % 5 == 1 để lấy tên địa danh
                // giới hạn load 5 phần tử nên 5 * 5 = 25
                // nếu không giới hạn thì thay 25 = arrayList.size()
                int size = (arrayList.size() > 25)? 25 : arrayList.size();
                for (int i = 0; i < size; i++){
                    if (i % 5 == 1)
                        listEntertainment.add(new Entertainment(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                EntertainmentAdapter entertainmentAdapter = new EntertainmentAdapter(listEntertainment, getApplicationContext());
                recyclerView.setAdapter(entertainmentAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //endregion


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

    //endregion
}
