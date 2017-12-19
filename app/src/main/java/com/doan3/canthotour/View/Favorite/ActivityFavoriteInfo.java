package com.doan3.canthotour.View.Favorite;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityPlaceInfo;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Search.ActivityNearLocation;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Main.ActivityPlaceInfo.kinhDo;
import static com.doan3.canthotour.View.Main.ActivityPlaceInfo.object;
import static com.doan3.canthotour.View.Main.ActivityPlaceInfo.viDo;

public class ActivityFavoriteInfo extends AppCompatActivity {

    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    String masp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdiadiem);

        btnLuuDiaDiem = findViewById(R.id.btnLuuDiaDiem);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCan);
        btnChiaSe = findViewById(R.id.btnChiaSe);

        masp = getIntent().getStringExtra("masp");
        String id = null;
        try {
            GetIdPlace getIdPlace = new GetIdPlace();
            getIdPlace.execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE + "/" + masp);
            id = getIdPlace.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        new ActivityPlaceInfo.LoadPlace(this).execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + id);

        btnLuuDiaDiem.setClickable(false);

        btnLanCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityFavoriteInfo.this, ActivityNearLocation.class);
                intent.putExtra("url", Config.URL_HOST + "timkiemSort/location=" + kinhDo + "," + viDo + "&radius=500&keyword=can+tho");
                startActivity(intent);
            }
        });

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
                        startActivity(new Intent(ActivityFavoriteInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityFavoriteInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityFavoriteInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityFavoriteInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private class GetIdPlace extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            ArrayList<String> arr = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONArray(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_FAVORITE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arr.get(0);
        }
    }

}
