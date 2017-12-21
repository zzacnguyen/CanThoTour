package com.doan3.canthotour.View.Main;

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
import com.doan3.canthotour.Model.ObjectClass.Eat;
import com.doan3.canthotour.Model.ObjectClass.Entertainment;
import com.doan3.canthotour.Model.ObjectClass.Hotel;
import com.doan3.canthotour.Model.ModelPlace;
import com.doan3.canthotour.Model.ObjectClass.Place;
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

        // region click button
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
        // endregion

        // load place
        loadPlace();

        // load eat
        new LoadEat().execute(Config.URL_HOST + Config.URL_GET_ALL_EATS);

        // load entertainment
        new LoadEntertainment().execute(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);

        // load hotel
        new LoadHotel().execute(Config.URL_HOST + Config.URL_GET_ALL_HOTELS);

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

    //Custom view place
    private void loadPlace() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDanh);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Place> places = new ModelPlace().getPlaceList();

        PlaceAdapter placeAdapter = new PlaceAdapter(places, getApplicationContext());
        recyclerView.setAdapter(placeAdapter);
        placeAdapter.notifyDataSetChanged();
    }

    //Custom view eat
    private class LoadEat extends AsyncTask<String, Void, ArrayList<Eat>> {
        RecyclerView recyclerView;

        private LoadEat() {
            recyclerView = findViewById(R.id.RecyclerView_AnUong);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected ArrayList<Eat> doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_EAT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Eat> list = new ArrayList<>();

            int size = (arrayList.size() > 20) ? 20 : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i += 4) {
                list.add(new Eat(Integer.parseInt(arrayList.get(i)), R.drawable.benninhkieu1, arrayList.get(i + 1)));
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Eat> items) {
            super.onPostExecute(items);
            EatAdapter eatAdapter =
                    new EatAdapter(items, getApplicationContext());
            recyclerView.setAdapter(eatAdapter);
            eatAdapter.notifyDataSetChanged();
        }
    }

    //Custom view entertainment
    private class LoadEntertainment extends AsyncTask<String, Void, ArrayList<Entertainment>> {
        RecyclerView recyclerView;

        private LoadEntertainment() {
            recyclerView = findViewById(R.id.RecyclerView_VuiChoi);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected ArrayList<Entertainment> doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_ENTERTAINMENT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Entertainment> list = new ArrayList<>();

            int size = (arrayList.size() > 20) ? 20 : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i += 4) {
                list.add(new Entertainment(Integer.parseInt(arrayList.get(i)), R.drawable.benninhkieu1, arrayList.get(i + 1)));
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Entertainment> items) {
            super.onPostExecute(items);
            EntertainmentAdapter entertainmentAdapter =
                    new EntertainmentAdapter(items, getApplicationContext());
            recyclerView.setAdapter(entertainmentAdapter);
            entertainmentAdapter.notifyDataSetChanged();
        }
    }

    //Custom view hotel
    private class LoadHotel extends AsyncTask<String, Void, ArrayList<Hotel>> {
        RecyclerView recyclerView;

        private LoadHotel() {
            recyclerView = findViewById(R.id.RecyclerView_KhachSan);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected ArrayList<Hotel> doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            ArrayList<String> arr, arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_HOTEL);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Hotel> list = new ArrayList<>();

            int size = (arrayList.size() > 25) ? 25 : arrayList.size();
            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < size; i += 5) {
                list.add(new Hotel(Integer.parseInt(arrayList.get(i)), R.drawable.benninhkieu1, arrayList.get(i + 1)));
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Hotel> items) {
            super.onPostExecute(items);
            HotelAdapter hotelAdapter =
                    new HotelAdapter(items, getApplicationContext());
            recyclerView.setAdapter(hotelAdapter);
            hotelAdapter.notifyDataSetChanged();
        }
    }
}
