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

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfEntertainmentAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Entertainment;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityEntertainment extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vuichoi);

        initView_Entertain();

        menuBotNavBar();
    }

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
                        startActivity(new Intent(ActivityEntertainment.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEntertainment.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEntertainment.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEntertainment.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private void initView_Entertain() {
        new entertain().execute(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
    }

    private class entertain extends AsyncTask<String, Void, String> {
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

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_DanhSachVuiChoi);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityEntertainment.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Entertainment> listEntertainment = new ArrayList<>();

                // json vui chơi có 4 phần tử, phần tử 1 là tên địa điểm vui chơi nên i % 4 == 1 để lấy tên địa điểm vui chơi
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i % 4 == 1)
                        listEntertainment.add(new Entertainment(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                ListOfEntertainmentAdapter listOfEntertainmentAdapter = new ListOfEntertainmentAdapter(listEntertainment, getApplicationContext());
                recyclerView.setAdapter(listOfEntertainmentAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
