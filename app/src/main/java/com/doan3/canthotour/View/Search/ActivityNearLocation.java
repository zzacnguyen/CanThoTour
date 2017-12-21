package com.doan3.canthotour.View.Search;


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
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.NearLocationAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ActivityNearLocation extends AppCompatActivity {

    public static String urlNearLocation;
    TextView txtTenDd, txtKhoangCach;
    ImageView imgHinhDd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diadiemlancan);
        txtTenDd = findViewById(R.id.textViewTenLanCan);
        txtKhoangCach = findViewById(R.id.textViewKhoangCach);
        imgHinhDd = findViewById(R.id.imageViewLanCan);

        urlNearLocation = getIntent().getStringExtra("url");
        new Load().execute(urlNearLocation);

        menuBotNavBar();
    }

    //Bottom navigation bar
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
                        startActivity(new Intent(ActivityNearLocation.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityNearLocation.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityNearLocation.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityNearLocation.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private class Load extends AsyncTask<String, Void, ArrayList<NearLocation>> {

        @Override
        protected ArrayList<NearLocation> doInBackground(String... strings) {
            ArrayList<NearLocation> nearLocations = new ArrayList<>();
            try {
                String get = HttpRequestAdapter.httpGet(strings[0]);
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(get), Config.JSON_NEAR_LOCATION);
                for (int i = 0; i < arrayList.size(); i += 5) {
                    nearLocations.add(new NearLocation(Integer.parseInt(arrayList.get(i)), arrayList.get(i + 1),
                            arrayList.get(i + 2), R.drawable.benninhkieu1));
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return nearLocations;
        }

        @Override
        protected void onPostExecute(ArrayList<NearLocation> nearLocations) {
            super.onPostExecute(nearLocations);
            RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDiemLanCan);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(ActivityNearLocation.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            NearLocationAdapter nearLocationAdapter = new NearLocationAdapter(nearLocations, getApplicationContext());
            recyclerView.setAdapter(nearLocationAdapter);
            nearLocationAdapter.notifyDataSetChanged();
        }
    }
}
