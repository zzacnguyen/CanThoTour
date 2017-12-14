package com.doan3.canthotour.View.Favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.util.ArrayList;

public class ActivityFavorite extends AppCompatActivity {

    TextView txtTenDD;
    ImageView imgHinhDD;
    private ArrayList<String> id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeuthich);

        txtTenDD = findViewById(R.id.textViewYeuThich);
        imgHinhDD = findViewById(R.id.imageViewYeuThich);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        new GetId().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE);

        new Load().execute();

        new PostJson().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE);

        menuBotNavBar();
    }


    private class GetId extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONArray jsonArray, jsonGet = new JSONArray();
                // parse json vừa get về bỏ id để đồng bộ với file json trong máy
                ArrayList<String> arrJsonGet =
                        JsonHelper.parseJsonNoId(new JSONArray(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_FAVORITE);
                for (int i = 0; i < arrJsonGet.size(); i += 2) {
                    jsonGet.put(new JSONObject("{\"dd_iddiadiem\":\""
                            + arrJsonGet.get(i) + "\",\"nd_idnguoidung\":\"" + arrJsonGet.get(i + 1) + "\"}"));
                }

                // merge 2 json
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(path, "/dsyeuthich.json");
                if (file.exists()) {
                    jsonArray = JsonHelper.mergeJson(jsonGet, new JSONArray(JsonHelper.readJson(file)));
                } else {
                    jsonArray = jsonGet;
                }
                // parse json ra arraylist
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(jsonArray, Config.JSON_FAVORITE);

                // thêm id địa danh vào biến id
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i % 2 == 0) {
                        id.add(arrayList.get(i));
                    }
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private class Load extends AsyncTask<Void, ArrayList<Favorite>, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<Favorite> arrIdPlace = new ArrayList<>();
            for (int i = 0; i < id.size(); i++) {
                try {
                    String get = HttpRequestAdapter.httpGet(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + id.get(i));
                    ArrayList<String> arrayList = JsonHelper.parseJsonNoId(new JSONArray(get), Config.JSON_PLACE);
                    for (int j = 0; j < arrayList.size(); j+=7) {
                            arrIdPlace.add(new Favorite(R.drawable.benninhkieu1, arrayList.get(j)));
                            publishProgress(arrIdPlace);
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Favorite>[] values) {
            super.onProgressUpdate(values);
            RecyclerView recyclerView = findViewById(R.id.RecyclerView_DanhSachYeuThich);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(ActivityFavorite.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(values[0], getApplicationContext());
            recyclerView.setAdapter(favoriteAdapter);
        }
    }

    private class PostJson extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            JSONArray jsonFile;
            try {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(path, "/dsyeuthich.json");
                if (file.exists()) {
                    jsonFile = new JSONArray(JsonHelper.readJson(file));
                    for (int i = 0; i < jsonFile.length(); i++) {
                        HttpRequestAdapter.httpPost(strings[0], jsonFile.getJSONObject(i));
                    }
                    file.delete();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
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
}
