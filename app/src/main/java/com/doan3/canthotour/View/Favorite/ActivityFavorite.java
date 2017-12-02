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
import com.doan3.canthotour.Adapter.PlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Main.MainActivity;

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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        new place().execute();

        menuBotNavBar();
    }

    private class place extends AsyncTask<String,Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return JsonHelper.readJson("dsyeuthich");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(s), Config.JSON_FAVORITE);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_DanhSachYeuThich);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFavorite.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                ArrayList<Place> listPlace = new ArrayList<>();

                // json địa danh có 3 phần tử, phần tử 1 là tên địa danh nên i % 3 == 1 để lấy tên địa danh
                // giới hạn load 5 phần tử nên 3 * 5 = 15
                // nếu không giới hạn thì thay 15 = arrayList.size()
                int size = (arrayList.size() > 15)? 15 : arrayList.size();
                for (int i = 0; i < size; i++){
                    if (i % 3 == 1)
                        listPlace.add(new Place(R.drawable.benninhkieu1, arrayList.get(i)));
                }

                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(listPlace, getApplicationContext());
                recyclerView.setAdapter(favoriteAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void menuBotNavBar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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
}
