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
import com.doan3.canthotour.Adapter.ListOfPlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ObjectClass.Place;
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


public class ActivityPlace extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diadanh);

        new LoadPlace().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES);

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

    private class LoadPlace extends AsyncTask<String, Void, ArrayList<Place>> {
        ArrayList<String> arr = new ArrayList<>(), arrayList = new ArrayList<>();
        ArrayList<Place> listPlace = new ArrayList<>();
        ListOfPlaceAdapter listOfPlaceAdapter;
        RecyclerView recyclerView;

        @Override
        protected ArrayList<Place> doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_PLACE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Place> list = new ArrayList<>();

            // lấy tên địa điểm vào list và cập nhật lên giao diện
            for (int i = 0; i < arrayList.size(); i += 2) {
                list.add(new Place(Integer.parseInt(arrayList.get(i)), R.drawable.benninhkieu1, arrayList.get(i + 1)));
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places) {
            super.onPostExecute(places);


            recyclerView = findViewById(R.id.RecyclerView_DanhSachDiaDanh);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityPlace.this,
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            listOfPlaceAdapter = new ListOfPlaceAdapter(recyclerView, places, getApplicationContext());
            recyclerView.setAdapter(listOfPlaceAdapter);

            listPlace = places;
            //set load more listener for the RecyclerView adapter
            listOfPlaceAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

                @Override
                public void onLoadMore() {
                    if (listPlace.size() < Integer.parseInt(arr.get(2))) {
                        listPlace.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                listOfPlaceAdapter.notifyItemInserted(listPlace.size() - 1);
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listPlace.remove(listPlace.size() - 1);
                                listOfPlaceAdapter.notifyItemRemoved(listPlace.size());
                                String string = "";
                                try {
                                    string = new ActivityEat.NextPage().execute(arr.get(1)).get();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    arr = JsonHelper.parseJsonNoId(new JSONObject(string), Config.JSON_LOAD);
                                    arrayList = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_PLACE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for (int i = 0; i < arrayList.size(); i += 2) {
                                    listPlace.add(new Place(Integer.parseInt(arrayList.get(i)),
                                            R.drawable.benninhkieu1, arrayList.get(i + 1)));
                                }
                                listOfPlaceAdapter.notifyDataSetChanged();
                                listOfPlaceAdapter.setLoaded();
                            }
                        }, 1000);
                    }
                }
            });
        }
    }

}
