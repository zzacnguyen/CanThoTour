package com.doan3.canthotour.View.Main;

import android.app.Activity;
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
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityEatInfo extends AppCompatActivity {
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    String masp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdichvu);

        btnLuuDiaDiem = findViewById(R.id.btnLuuDiaDiemDv);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCanDv);
        btnChiaSe = findViewById(R.id.btnChiaSeDv);

        masp = getIntent().getStringExtra("masp");
        ArrayList<String> arr = new ArrayList<>();
        try {
            arr = new GetId().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        String idService, idPlace;
        String urlService = null, urlPlace = null;
        ArrayList<String> urlEat = new ArrayList<>();

        // lấy id dịch vụ trong ăn uống, lấy id địa điểm trong dịch vụ
        try {
            urlEat.add(Config.URL_HOST + Config.URL_GET_ALL_EATS + "/" + arr.get(Integer.parseInt(masp)));

            // gọi class GetIdService để lấy id dịch vụ
            idService = new GetIdService().execute(urlEat, Config.JSON_EAT).get();
            // gọi class GetIdPlace để lấy id dịch vụ
            idPlace = new GetIdPlace().execute(Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService).get();

            urlService = Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService;
            urlPlace = Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + idPlace;

            new GetIdService().execute(urlEat, Config.JSON_EAT);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        new LoadPlace(this).execute(urlEat, Config.JSON_EAT);
        new LoadServiceInfo(this).execute(urlService);
        new LoadPlaceInfo(this).execute(urlPlace);

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
                        startActivity(new Intent(ActivityEatInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEatInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEatInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEatInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    public static class GetIdService extends AsyncTask<ArrayList<String>, Void, String> {
        //Lấy id của dv_iddichvu trong eat, hotel, entertainment
        @Override
        // ArrayList<String> ... strings truyền vào 1 mảng các ArrayList<String>
        protected String doInBackground(ArrayList<String>... strings) {
            ArrayList<String> arrJsonGet = new ArrayList<>();
            try {
                JSONArray json = new JSONArray(HttpRequestAdapter.httpGet(strings[0].get(0)));
                arrJsonGet = JsonHelper.parseJsonNoId(json, strings[1]);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return arrJsonGet.get(2);
        }
    }

    public static class GetIdPlace extends AsyncTask<String, Void, String> {
        //Lấy id của dd_iddiadiem trong bảng dịch vụ
        @Override
        protected String doInBackground(String... strings) {
            ArrayList<String> arrJsonGet = new ArrayList<>();
            try {
                JSONArray json = new JSONArray(HttpRequestAdapter.httpGet(strings[0]));
                arrJsonGet = JsonHelper.parseJsonNoId(json, Config.JSON_SERVICE);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return arrJsonGet.get(6);
        }
    }

    public static class LoadPlace extends AsyncTask<ArrayList<String>, ArrayList<String>, Void> {
        //Load tên địa điểm và giới thiệu
        Activity activity;
        TextView txtTenDD, txtGioiThieu;

        public LoadPlace(Activity atc) {
            activity = atc;
            txtTenDD = activity.findViewById(R.id.textViewTenDv);
            txtGioiThieu = activity.findViewById(R.id.textViewGioiThieuDv);
        }

        @Override
        protected Void doInBackground(ArrayList<String>... strings) {
            try {
                JSONArray jsonArray = new JSONArray(HttpRequestAdapter.httpGet(strings[0].get(0)));
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(jsonArray, strings[1]);
                publishProgress(arrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<String>... values) {
            //Đưa dữ liệu ra màn hình
            super.onProgressUpdate(values);
            txtTenDD.setText(values[0].get(0));
            txtGioiThieu.setText(values[0].get(1));
        }
    }

    public static class LoadServiceInfo extends AsyncTask<String, ArrayList<String>, Void> {
        Activity activity;
        TextView txtGia, txtGio, txtLoaiHinh;

        public LoadServiceInfo(Activity atc) {
            activity = atc;
            txtGia = activity.findViewById(R.id.textViewGiaDv);
            txtGio = activity.findViewById(R.id.textViewGioDv);
            txtLoaiHinh = activity.findViewById(R.id.textViewLoaiHinhDv);
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONArray jsonArray = new JSONArray(HttpRequestAdapter.httpGet(strings[0]));
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(jsonArray, Config.JSON_SERVICE);
                publishProgress(arrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<String>[] values) {
            super.onProgressUpdate(values);
            txtGia.setText(values[0].get(4) + " - " + values[0].get(3));
            txtGio.setText(values[0].get(1) + " - " + values[0].get(2));
            txtLoaiHinh.setText(values[0].get(0));
        }
    }

    public static class LoadPlaceInfo extends AsyncTask<String, ArrayList<String>, Void> {
        Activity activity;
        TextView txtDiaChi, txtSDT;

        public LoadPlaceInfo(Activity atc) {
            activity = atc;
            txtDiaChi = activity.findViewById(R.id.textViewDiaChiDv);
            txtSDT = activity.findViewById(R.id.textViewSdtDv);
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONArray jsonArray = new JSONArray(HttpRequestAdapter.httpGet(strings[0]));
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(jsonArray, Config.JSON_PLACE);
                publishProgress(arrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<String>[] values) {
            super.onProgressUpdate(values);
            txtDiaChi.setText(values[0].get(2));
            txtSDT.setText(values[0].get(3));
        }
    }

    private class GetId extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> arr = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(HttpRequestAdapter.httpGet(Config.URL_HOST + "lay-id-an-uong"));
                arr = JsonHelper.parseJson(jsonArray, new ArrayList<String>());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arr;
        }
    }
}
