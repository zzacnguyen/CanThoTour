package com.doan3.canthotour.View.Main.Content;

import android.app.Activity;
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
import com.doan3.canthotour.Adapter.ListOfEatAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Eat;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityEat extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_anuong);

        initView_Eat();

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
                        startActivity(new Intent(ActivityEat.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEat.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEat.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEat.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private void initView_Eat() {
        LoadInfo loadInfo = new LoadInfo(this);
        loadInfo.execute(Config.URL_HOST + Config.URL_GET_ALL_EATS);
    }

    private class LoadInfo extends AsyncTask<String, ArrayList<Eat>, Void> {
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public LoadInfo(Activity act) {
            activity = act;
            recyclerView = findViewById(R.id.RecyclerView_DanhSachAnUong);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected Void doInBackground(String... strings) {
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
            for (int i = 0; i < size; i++) {
                if (i % 4 == 1) {
                    list.add(new Eat(R.drawable.benninhkieu1, arrayList.get(i)));
                    publishProgress(list);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Eat>[] values) {
            super.onProgressUpdate(values);
            ListOfEatAdapter listOfEatAdapter = new ListOfEatAdapter(values[0], getApplicationContext());
            recyclerView.setAdapter(listOfEatAdapter);
        }
    }
}
