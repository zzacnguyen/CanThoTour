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
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityHotelInfo extends AppCompatActivity {
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    TextView txtTenDD, txtGioiThieu, txtWebsite;
    String masp, idService = "", idPlace = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietkhachsan);

        btnLuuDiaDiem = findViewById(R.id.btnLuuKS);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCanKS);
        btnChiaSe = findViewById(R.id.btnChiaSeKS);
        txtTenDD = this.findViewById(R.id.textViewTenDv);
        txtGioiThieu = this.findViewById(R.id.textViewGioiThieuDv);
        txtWebsite = findViewById(R.id.textViewWebsite);

        masp = getIntent().getStringExtra("masp");
        ArrayList<String> arr = new ArrayList<>();
        try {
            arr = new GetId().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        String idService, idPlace;
        String urlService = null, urlPlace = null;
        ArrayList<String> urlHotel = new ArrayList<>();

        // lấy id dịch vụ trong ăn uống, lấy id địa điểm trong dịch vụ
        try {
            urlHotel.add(Config.URL_HOST + Config.URL_GET_ALL_HOTELS + "/" + arr.get(Integer.parseInt(masp)));

            idService = new ActivityEatInfo.GetIdService().execute(urlHotel, Config.JSON_HOTEL).get();
            idPlace = new ActivityEatInfo.GetIdPlace().execute(Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService).get();
            urlService = Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + idService;
            urlPlace = Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + idPlace;

            new ActivityEatInfo.GetIdService().execute(urlHotel, Config.JSON_HOTEL);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        new LoadPlace().execute(urlHotel.get(0));
        new ActivityEatInfo.LoadServiceInfo(this).execute(urlService);
        new ActivityEatInfo.LoadPlaceInfo(this).execute(urlPlace);

        menuBotNavBar();
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
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(new JSONArray(s), Config.JSON_HOTEL);
                txtTenDD.setText(arrayList.get(0));
                txtWebsite.setText(arrayList.get(3));
                txtGioiThieu.setText(arrayList.get(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetId extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> arr = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(HttpRequestAdapter.httpGet(Config.URL_HOST + "lay-id-khach-san"));
                arr = JsonHelper.parseJson(jsonArray, new ArrayList<String>());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arr;
        }
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
                        startActivity(new Intent(ActivityHotelInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityHotelInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityHotelInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityHotelInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

}
