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

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfEatAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ObjectClass.Service;
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


public class ActivityEat extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_anuong);

        new LoadInfo().execute(Config.URL_HOST + Config.URL_GET_ALL_EATS);

        menuBotNavBar();
    }

    public void menuBotNavBar() {
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
                        startActivity(new Intent(ActivityEat.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityEat.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityEat.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityEat.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    public static class NextPage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }
    }

    private class LoadInfo extends AsyncTask<String, Void, ArrayList<Service>> {
        ArrayList<String> arr = new ArrayList<>(), arrayList = new ArrayList<>();
        ArrayList<Service> listService = new ArrayList<>();
        ListOfEatAdapter listOfEatAdapter;
        RecyclerView recyclerView;

        @Override
        protected ArrayList<Service> doInBackground(String... strings) {
            // parse json vừa get về ra arraylist

            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_EAT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Service> list = new ArrayList<>();

            for (int i = 0; i < arrayList.size(); i += 4) {
                list.add(new Service(Integer.parseInt(arrayList.get(i)), R.drawable.benninhkieu1, arrayList.get(i + 1)));
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Service> services) {
            super.onPostExecute(services);

            recyclerView = findViewById(R.id.RecyclerView_DanhSachAnUong);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(ActivityEat.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            listOfEatAdapter = new ListOfEatAdapter(recyclerView, services, getApplicationContext());
            recyclerView.setAdapter(listOfEatAdapter);
            listOfEatAdapter.notifyDataSetChanged();

            listService = services;
            //set load more listener for the RecyclerView adapter
            listOfEatAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

                @Override
                public void onLoadMore() {
                    if (listService.size() < Integer.parseInt(arr.get(2))) {
                        listService.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                listOfEatAdapter.notifyItemInserted(listService.size() - 1);
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listService.remove(listService.size() - 1);
                                listOfEatAdapter.notifyItemRemoved(listService.size());
                                String string = "";
                                try {
                                    string = new NextPage().execute(arr.get(1)).get();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    arr = JsonHelper.parseJsonNoId(new JSONObject(string), Config.JSON_LOAD);
                                    arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_EAT);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for (int i = 0; i < arrayList.size(); i += 4) {
                                    listService.add(new Service(
                                            Integer.parseInt(arrayList.get(i)), R.drawable.benninhkieu1, arrayList.get(i + 1)));
                                }
                                listOfEatAdapter.notifyDataSetChanged();
                                listOfEatAdapter.setLoaded();
                            }
                        }, 1000);
                    }
                }
            });
        }
    }
}
