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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityPlaceInfo extends AppCompatActivity {

    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    TextView txtTenDD, txtDiaChi, txtSDT, txtGioiThieu;
    String masp;
    JSONObject object;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdiadiem);

        btnLuuDiaDiem = findViewById(R.id.btnLuuDiaDiem);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCan);
        btnChiaSe = findViewById(R.id.btnChiaSe);
        txtTenDD = findViewById(R.id.textViewTenDD);
        txtDiaChi = findViewById(R.id.textViewDiaChi);
        txtSDT = findViewById(R.id.textViewSDT);
        txtGioiThieu = findViewById(R.id.textViewGioiThieu);


        masp = getIntent().getStringExtra("masp");
        new place().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + masp);
        btnLuuDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonHelper.writeJson("dsyeuthich", object);
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
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityPlaceInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityPlaceInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityPlaceInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private class place extends AsyncTask<String, Void, String> {
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

                object = new JSONObject("{\"dd_iddiadiem\":\""+arrayList.get(0)+"\",\"nd_idnguoidung\":\"1\"}");
                txtTenDD.setText(arrayList.get(1));
                txtDiaChi.setText(arrayList.get(3));
                txtSDT.setText(arrayList.get(4));
                txtGioiThieu.setText(arrayList.get(2));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
