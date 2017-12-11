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
import com.doan3.canthotour.Adapter.ListOfEntertainmentAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.Entertainment;
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


public class ActivityEntertainment extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vuichoi);

        initView_Entertain();

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
                        startActivity(new Intent(ActivityEntertainment.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEntertainment.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEntertainment.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEntertainment.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private void initView_Entertain() {
        LoadInfo loadInfo = new LoadInfo(this);
        loadInfo.execute(Config.URL_HOST + Config.URL_GET_ALL_ENTERTAINMENTS);
    }

    private class LoadInfo extends AsyncTask<String, ArrayList<Entertainment>, ArrayList<Entertainment>> {
        ArrayList<String> arr = new ArrayList<>(), arrayList = new ArrayList<>();
        ArrayList<Entertainment> listEntertainment = new ArrayList<>();
        ListOfEntertainmentAdapter listOfEntertainmentAdapter;
        Activity activity;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;

        // khởi tạo class truyền vào 2 đối số là activity và recyclerview
        public LoadInfo(Activity act) {
            activity = act;
            recyclerView = findViewById(R.id.RecyclerView_DanhSachVuiChoi);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        @Override
        protected ArrayList<Entertainment> doInBackground(String... strings) {
            // parse json vừa get về ra arraylist

            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_ENTERTAINMENT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Entertainment> list = new ArrayList<>();

            for (int i = 0; i < arrayList.size(); i++) {
                if (i % 4 == 1) {
                    list.add(new Entertainment(R.drawable.benninhkieu1, arrayList.get(i)));
                    publishProgress(list);
                }
            }
            return list;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Entertainment>[] values) {
            super.onProgressUpdate(values);
            listOfEntertainmentAdapter = new ListOfEntertainmentAdapter(recyclerView, values[0], getApplicationContext());
            recyclerView.setAdapter(listOfEntertainmentAdapter);
        }

        @Override
        protected void onPostExecute(ArrayList<Entertainment> entertainments) {
            super.onPostExecute(entertainments);
            listEntertainment = entertainments;
            //set load more listener for the RecyclerView adapter
            listOfEntertainmentAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

                @Override
                public void onLoadMore() {
                    if (listEntertainment.size() < Integer.parseInt(arr.get(2))) {
                        listEntertainment.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                listOfEntertainmentAdapter.notifyItemInserted(listEntertainment.size() - 1);
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listEntertainment.remove(listEntertainment.size() - 1);
                                listOfEntertainmentAdapter.notifyItemRemoved(listEntertainment.size());
                                String string = "";
                                try {
                                    string = new ActivityEntertainment.NextPage().execute(arr.get(1)).get();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    arr = JsonHelper.parseJsonNoId(new JSONObject(string), Config.JSON_LOAD);
                                    arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_ENTERTAINMENT);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (i % 4 == 1)
                                        listEntertainment.add(new Entertainment(R.drawable.benninhkieu1, arrayList.get(i)));
                                }
                                listOfEntertainmentAdapter.notifyDataSetChanged();
                                listOfEntertainmentAdapter.setLoaded();
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
}
