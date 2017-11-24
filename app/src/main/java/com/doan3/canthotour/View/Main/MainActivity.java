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
import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.PlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Eat;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.Content.ActivityPlace;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnDiaDanh;
    private ArrayList<String> diaDanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnDiaDanh = (Button) findViewById(R.id.btnTatCaDiaDanh);

        btnDiaDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityPlace.class));
            }
        });

//        setSupportActionBar(toolbar);

        initView_Place();
//        initView_Eat();
//        initView_Hotel();

        menuBotNavBar();

    }
    //Bottom navigation bar
    private void menuBotNavBar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
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

    //View địa danh
    private void initView_Place(){
        new place().execute(Config.URL_HOST+Config.URL_GET_ALL_PLACES);
    }

    private void initView_Eat(){
        new eat().execute(Config.URL_HOST+Config.URL_GET_ALL_EATS);
    }

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
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(s), Config.JSON_PLACE);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDanh);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Place> listPlace = new ArrayList<>();

                // json địa danh có 8 phần tử, phần tử 1 là tên địa danh nên i % 8 == 1 để lấy tên địa danh
                // giới hạn load 5 phần tử nên 8 * 5 = 40
                // nếu không giới hạn thì thay 40 = arrayList.size()
                for (int i = 0; i < 40; i++){
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
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(s), Config.JSON_EAT);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_AnUong);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Eat> listEat = new ArrayList<>();

                // json địa danh có 8 phần tử, phần tử 1 là tên địa danh nên i % 8 == 1 để lấy tên địa danh
                // giới hạn load 5 phần tử nên 8 * 5 = 40
                // nếu không giới hạn thì thay 40 = arrayList.size()
                for (int i = 0; i < 40; i++){
                    if (i % 8 == 1)
                        listEat.add(new Eat(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                EatAdapter eatAdapter = new EatAdapter(listEat, getApplicationContext());
                recyclerView.setAdapter(eatAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


//    private void initView_Hotel(){
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_KhachSan);
//        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        //Add item
//        ArrayList<Hotel> listHotel = new ArrayList<>();
//        listHotel.add(new Hotel(R.drawable.muongthanh, "Mường Thanh Hotel"));
//        listHotel.add(new Hotel(R.drawable.ninhkieu2, "Ninh Kiều 2 Hotel"));
//        listHotel.add(new Hotel(R.drawable.vinpearl, "Vinpearl Hotel"));
//
//        HotelAdapter hotelAdapter = new HotelAdapter(listHotel, getApplicationContext());
//        recyclerView.setAdapter(hotelAdapter);
//
//    }

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
