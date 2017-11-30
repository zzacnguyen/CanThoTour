package com.doan3.canthotour.View.Main.Content;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.doan3.canthotour.Adapter.HotelAdapter;
import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfHotelAdapter;
import com.doan3.canthotour.Adapter.ListOfPlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Hotel;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class ActivityHotel extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_khachsan);

        initView_Hotel();

        menuBotNavBar();
    }

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
                        startActivity(new Intent(ActivityHotel.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityHotel.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityHotel.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityHotel.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private void initView_Hotel(){
        new hotel().execute(Config.URL_HOST+Config.URL_GET_ALL_HOTELS);
    }

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
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(s), Config.JSON_HOTEL);

                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_DanhSachKhachSan);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityHotel.this, LinearLayoutManager.HORIZONTAL, false);
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

                ListOfHotelAdapter listOfHotelAdapter = new ListOfHotelAdapter(listHotel, getApplicationContext());
                recyclerView.setAdapter(listOfHotelAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
