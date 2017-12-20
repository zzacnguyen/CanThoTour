package com.doan3.canthotour.View.Main;


import android.app.Activity;
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
import com.doan3.canthotour.View.Search.ActivityNearLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class ActivityPlaceInfo extends AppCompatActivity {

    public static JSONObject object;
    public static String kinhDo;
    public static String viDo;
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    String masp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdiadiem);

        btnLuuDiaDiem = findViewById(R.id.btnLuuDiaDiem);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCan);
        btnChiaSe = findViewById(R.id.btnChiaSe);

        btnLanCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityPlaceInfo.this, ActivityNearLocation.class));
            }
        });

        masp = getIntent().getStringExtra("masp");

        new LoadPlace(this).execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + masp);
        btnLuuDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray getDataJsonFile;
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(path, "/dsyeuthich.json");
                try {
                    if (file.exists()) {
                        getDataJsonFile = new JSONArray(JsonHelper.readJson(file));
                        for (int i = 0; i < getDataJsonFile.length(); i++) {
                            if (!object.toString().equals(getDataJsonFile.getJSONObject(i).toString())) {
                                JsonHelper.writeJson(file, object);
                                Toast.makeText(ActivityPlaceInfo.this,
                                        "Lưu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityPlaceInfo.this,
                                        "Đã lưu trước đó", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        JsonHelper.writeJson(file, object);
                        Toast.makeText(ActivityPlaceInfo.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btnLanCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPlaceInfo.this, ActivityNearLocation.class);
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
                        startActivity(new Intent(ActivityPlaceInfo.this, MainActivity.class));
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

    public static class LoadPlace extends AsyncTask<String, ArrayList<String>, Void> {
        TextView txtTenDD, txtDiaChi, txtSDT, txtGioiThieu;
        Activity activity;

        public LoadPlace(Activity activityt) {
            this.activity = activityt;
            txtTenDD = activity.findViewById(R.id.textViewTenDD);
            txtDiaChi = activity.findViewById(R.id.textViewDiaChi);
            txtSDT = activity.findViewById(R.id.textViewSDT);
            txtGioiThieu = activity.findViewById(R.id.textViewGioiThieu);
        }

        @Override
        protected Void doInBackground(String... strings) {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(HttpRequestAdapter.httpGet(strings[0]));
                ArrayList<String> arrayList = JsonHelper.parseJson(jsonArray, Config.JSON_PLACE);
                publishProgress(arrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<String>[] arrayList) {
            super.onProgressUpdate(arrayList);
            try {
                object = new JSONObject("{\"dd_iddiadiem\":\"" + arrayList[0].get(0) + "\",\"nd_idnguoidung\":\"" + arrayList[0].get(0) + "\"}");
                kinhDo = arrayList[0].get(5);
                viDo = arrayList[0].get(6);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            txtTenDD.setText(arrayList[0].get(1));
            txtDiaChi.setText(arrayList[0].get(3));
            txtSDT.setText(arrayList[0].get(4));
            txtGioiThieu.setText(arrayList[0].get(2));
        }
    }

}
