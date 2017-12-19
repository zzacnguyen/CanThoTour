package com.doan3.canthotour.View.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.doan3.canthotour.View.Personal.ActivityAddPlace;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Search.ActivitySearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnDiaDanh, btnAnUong, btnKhachSan, btnVuiChoi;
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

        startActivity(new Intent(MainActivity.this, ActivitySearch.class));

        return super.onOptionsItemSelected(item);
    }
    // endregion

    //region Init View
    //Get view place
    private void initView_Place() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDanh);

        LoadPlace threadLoadPlace = new LoadPlace(this, recyclerView);
        threadLoadPlace.execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES);
    }

    //Get view eat
    private void initView_Eat() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_AnUong);
        LoadEat threadLoadEat = new LoadEat(this, recyclerView);
        threadLoadEat.execute(Config.URL_HOST + Config.URL_GET_ALL_EATS);
    }

    //Get view hotel
    private void initView_Hotel() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_KhachSan);

        LoadHotel threadLoadHotel = new LoadHotel(this, recyclerView);
        threadLoadHotel.execute(Config.URL_HOST + Config.URL_GET_ALL_HOTELS);
    }

    //Get view entertainment
    private void initView_Entertainment() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_VuiChoi);

        LoadEntertainment threadLoadEntertainment = new LoadEntertainment(this, recyclerView);
        threadLoadEntertainment.execute(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
    }

    //endregion

    void fabOnClick() { //Floating bar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityAddPlace.class));
            }
        });
    }

    void display_doanhnghiep(){
        if(doanhnghiep == false){
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

    //Custom view place
    private class LoadPlace extends AsyncTask<String, ArrayList<Place>, Void> {
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public LoadPlace(Activity act, RecyclerView rv) {
            activity = act;
            recyclerView = rv;
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected Void doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJsonNoId(new JSONArray(arr.get(0)), Config.JSON_PLACE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Place> list = new ArrayList<>();

            int size = (arrayList.size() > 35) ? 35 : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i += 7) {
                list.add(new Place(R.drawable.benninhkieu1, arrayList.get(i)));
                publishProgress(list);
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

    private class LoadEat extends AsyncTask<String, ArrayList<Eat>, Void> {
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public LoadEat(Activity act, RecyclerView rv) {
            activity = act;
            recyclerView = rv;
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected Void doInBackground(String... strings) {
            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJsonNoId(new JSONArray(arr.get(0)), Config.JSON_EAT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Eat> list = new ArrayList<>();

            int size = (arrayList.size() > 15) ? 15 : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i += 3) {
                list.add(new Eat(R.drawable.benninhkieu1, arrayList.get(i)));
                publishProgress(list);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Eat>[] values) {
            super.onProgressUpdate(values);
            EatAdapter eatAdapter = new EatAdapter(values[0], getApplicationContext());
            recyclerView.setAdapter(eatAdapter);
        }
    }

    private class LoadHotel extends AsyncTask<String, ArrayList<Hotel>, Void> {
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public LoadHotel(Activity act, RecyclerView rv) {
            activity = act;
            recyclerView = rv;
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected Void doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJsonNoId(new JSONArray(arr.get(0)), Config.JSON_HOTEL);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Hotel> list = new ArrayList<>();

            int size = (arrayList.size() > 20) ? 20 : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i += 4) {
                list.add(new Hotel(R.drawable.benninhkieu1, arrayList.get(i)));
                publishProgress(list);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Hotel>[] values) {
            super.onProgressUpdate(values);
            HotelAdapter hotelAdapter = new HotelAdapter(values[0], getApplicationContext());
            recyclerView.setAdapter(hotelAdapter);
        }
    }

    private class LoadEntertainment extends AsyncTask<String, ArrayList<Entertainment>, Void> {
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public LoadEntertainment(Activity act, RecyclerView rv) {
            activity = act;
            recyclerView = rv;
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected Void doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJsonNoId(new JSONArray(arr.get(0)), Config.JSON_ENTERTAINMENT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Entertainment> list = new ArrayList<>();

            int size = (arrayList.size() > 15) ? 15 : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i += 3) {
                list.add(new Entertainment(R.drawable.benninhkieu1, arrayList.get(i)));
                publishProgress(list);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Entertainment>[] values) {
            super.onProgressUpdate(values);
            EntertainmentAdapter entertainmentAdapter = new EntertainmentAdapter(values[0], getApplicationContext());
            recyclerView.setAdapter(entertainmentAdapter);
        }
    }
}
