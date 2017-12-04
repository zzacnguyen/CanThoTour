package com.doan3.canthotour.View.Favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.FavoriteAdapter;
import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Favorite;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityFavorite extends AppCompatActivity {

    TextView txtTenDD;
    ImageView imgHinhDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeuthich);

        txtTenDD = findViewById(R.id.textViewYeuThich);
        imgHinhDD = findViewById(R.id.imageViewYeuThich);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        new favorite().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE);

        menuBotNavBar();
    }

    private void menuBotNavBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityFavorite.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:

                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityFavorite.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityFavorite.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private class favorite extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                JSONArray jsonGet = new JSONArray();
                ArrayList<String> arrJsonGet = JsonHelper.parseJsonNoId(new JSONArray(s), Config.JSON_FAVORITE);
                for (int i = 0; i < arrJsonGet.size(); i+=2){
                    jsonGet.put(new JSONObject("{\"dd_iddiadiem\":\"" + arrJsonGet.get(i) + "\",\"nd_idnguoidung\":\"" + arrJsonGet.get(i+1) + "\"}"));
                }

                JSONArray jsonArray = JsonHelper.mergeJson(jsonGet, new JSONArray(JsonHelper.readJson("dsyeuthich")));
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(jsonArray, Config.JSON_FAVORITE);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_DanhSachYeuThich);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFavorite.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Favorite> listFavorite = new ArrayList<>();

                // load địa danh yêu thích
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i % 2 == 1)
                        listFavorite.add(new Favorite(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(listFavorite, getApplicationContext());
                recyclerView.setAdapter(favoriteAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
