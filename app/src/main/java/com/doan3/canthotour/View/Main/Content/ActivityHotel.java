package com.doan3.canthotour.View.Main.Content;

import android.app.Activity;
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

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfHotelAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.Hotel;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ActivityHotel extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_khachsan);

        initView_Hotel();

        menuBotNavBar();
    }

    private void initView_Hotel() {
        LoadInfo loadInfo = new LoadInfo(this);
        loadInfo.execute(Config.URL_HOST + Config.URL_GET_ALL_HOTELS);
    }

    private class LoadInfo extends AsyncTask<String, ArrayList<Hotel>, ArrayList<Hotel>> {
        ArrayList<String> arr = new ArrayList<>(), arrayList = new ArrayList<>();
        ArrayList<Hotel> listHotel = new ArrayList<>();
        ListOfHotelAdapter listOfHotelAdapter;
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public LoadInfo(Activity act) {
            activity = act;
            recyclerView = findViewById(R.id.RecyclerView_DanhSachKhachSan);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected ArrayList<Hotel> doInBackground(String... strings) {
            // parse json vừa get về ra arraylist

            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_HOTEL);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Hotel> list = new ArrayList<>();

            for (int i = 0; i < arrayList.size(); i++) {
                if (i % 5 == 1) {
                    list.add(new Hotel(R.drawable.benninhkieu1, arrayList.get(i)));
                    publishProgress(list);
                }
            }
            return list;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Hotel>[] values) {
            super.onProgressUpdate(values);
            listOfHotelAdapter = new ListOfHotelAdapter(recyclerView, values[0], getApplicationContext());
            recyclerView.setAdapter(listOfHotelAdapter);
        }

        @Override
        protected void onPostExecute(ArrayList<Hotel> hotels) {
            super.onPostExecute(hotels);
            listHotel = hotels;
            //set load more listener for the RecyclerView adapter
            listOfHotelAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

                @Override
                public void onLoadMore() {
                    if (listHotel.size() < Integer.parseInt(arr.get(2))) {
                        listHotel.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                listOfHotelAdapter.notifyItemInserted(listHotel.size() - 1);
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listHotel.remove(listHotel.size() - 1);
                                listOfHotelAdapter.notifyItemRemoved(listHotel.size());
                                String string = "";
                                try {
                                    string = new NextPage().execute(arr.get(1)).get();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    arr = JsonHelper.parseJsonNoId(new JSONObject(string), Config.JSON_LOAD);
                                    arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_HOTEL);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (i % 5 == 1)
                                        listHotel.add(new Hotel(R.drawable.benninhkieu1, arrayList.get(i)));
                                }
                                listOfHotelAdapter.notifyDataSetChanged();
                                listOfHotelAdapter.setLoaded();
                            }
                        }, 1000);
                    }
                }
            });
        }
    }

    private class NextPage extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
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
                        startActivity(new Intent(ActivityHotel.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityHotel.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityHotel.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityHotel.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

}
