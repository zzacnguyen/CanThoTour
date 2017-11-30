package com.doan3.canthotour.View.Main.Content;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfPlaceAdapter;
import com.doan3.canthotour.Adapter.PlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class ActivityPlace extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diadanh);

        initView_Place();

        menuBotNavBar();
    }

    private void menuBotNavBar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityPlace.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityPlace.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityPlace.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityPlace.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }



    private void initView_Place(){
        new place().execute(Config.URL_HOST+Config.URL_GET_ALL_PLACES);
    }

    private class place extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                final ArrayList<String> arrayList = JsonHelper.parseJson(new JSONArray(s), Config.JSON_PLACE);

                RecyclerView recyclerView = findViewById(R.id.RecyclerView_DanhSachDiaDanh);
                recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityPlace.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                //Add item
                final ArrayList<Place> listPlace = new ArrayList<>();

                // json địa danh có 8 phần tử, phần tử 1 là tên địa danh nên i % 8 == 1 để lấy tên địa danh
                int size = (arrayList.size() > 40)? 40 : arrayList.size();
                for (int i = 0; i < size; i++){
                    if (i % 8 == 1)
                        listPlace.add(new Place(R.drawable.benninhkieu1, arrayList.get(i)));
                }
                for (int i = 0; i < arrayList.size(); i++){
                    if (i % 8 == 1)
                        listPlace.add(new Place(R.drawable.benninhkieu1, arrayList.get(i),arrayList.get(i+2)));
                }

                final ListOfPlaceAdapter listOfPlaceAdapter = new ListOfPlaceAdapter(recyclerView, listPlace, getApplicationContext());
                recyclerView.setAdapter(listOfPlaceAdapter);

                //set load more listener for the RecyclerView adapter
                listOfPlaceAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (listPlace.size() <= 20) {
                            listPlace.add(null);
                            listOfPlaceAdapter.notifyItemInserted(listPlace.size() - 1);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listPlace.remove(listPlace.size() - 1);
                                    listOfPlaceAdapter.notifyItemRemoved(listPlace.size());

                                    //Generating more data
                                    int index = listPlace.size();
                                    int end = index + 10;
                                    for (int i = index; i < end; i++) {
                                        if (i % 8 == 1)
                                            listPlace.add(new Place(R.drawable.benninhkieu1, arrayList.get(i),arrayList.get(i+2)));
                                    }
                                    listOfPlaceAdapter.notifyDataSetChanged();
                                    listOfPlaceAdapter.setLoaded();
                                }
                            }, 5000);
                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
