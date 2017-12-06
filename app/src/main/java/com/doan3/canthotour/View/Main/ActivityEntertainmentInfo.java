package com.doan3.canthotour.View.Main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityEntertainmentInfo extends AppCompatActivity {
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    TextView txtTenDD, txtDiaChi, txtSDT, txtLoaiHinh, txtGia, txtGioiThieu, txtGio;
    String masp, idService = "", idPlace = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdichvu);

        btnLuuDiaDiem = findViewById(R.id.btnLuuDiaDiemDv);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCanDv);
        btnChiaSe = findViewById(R.id.btnChiaSeDv);
        txtTenDD = findViewById(R.id.textViewTenDv);
        txtDiaChi = findViewById(R.id.textViewDiaChiDv);
        txtSDT = findViewById(R.id.textViewSdtDv);
        txtLoaiHinh = findViewById(R.id.textViewLoaiHinhDv);
        txtGia = findViewById(R.id.textViewGiaDv);
        txtGioiThieu = findViewById(R.id.textViewGioiThieuDv);
        txtGio = findViewById(R.id.textViewGioDv);

        masp = getIntent().getStringExtra("masp");

        new GetIdService().execute(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS + "/" + masp);
        new GetIdPlace().execute();
        new LoadPlace().execute(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS + "/" + masp);
        new LoadServiceInfo().execute();
        new LoadPlaceInfo().execute();

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
                        startActivity(new Intent(ActivityEntertainmentInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEntertainmentInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEntertainmentInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEntertainmentInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private class GetIdService extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONArray json = new JSONArray(HttpRequestAdapter.httpGet(strings[0]));
                ArrayList<String> arrJsonGet = JsonHelper.parseJsonNoId(json, Config.JSON_ENTERTAINMENT);
                idService = arrJsonGet.get(2);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private class GetIdPlace extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONArray json = new JSONArray(HttpRequestAdapter.httpGet(Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService));
                ArrayList<String> arrJsonGet = JsonHelper.parseJsonNoId(json, Config.JSON_SERVICE);
                idPlace = arrJsonGet.get(6);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private class LoadPlace extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(new JSONArray(s), Config.JSON_ENTERTAINMENT);
                txtTenDD.setText(arrayList.get(0));
                txtGioiThieu.setText(arrayList.get(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoadServiceInfo extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpRequestAdapter.httpGet(Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(new JSONArray(s), Config.JSON_SERVICE);
                txtGia.setText(arrayList.get(4) + " - " + arrayList.get(3));
                txtGio.setText(arrayList.get(1) + " - " + arrayList.get(2));
                txtLoaiHinh.setText(arrayList.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoadPlaceInfo extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpRequestAdapter.httpGet(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + idPlace);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // parse json ra arraylist
            try {
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(new JSONArray(s), Config.JSON_PLACE);
                txtDiaChi.setText(arrayList.get(2));
                txtSDT.setText(arrayList.get(3));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
