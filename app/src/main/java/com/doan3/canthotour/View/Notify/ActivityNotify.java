package com.doan3.canthotour.View.Notify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.EventAdapter;
import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Event;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivityNotify extends AppCompatActivity {

    TextView txtTenSk, txtNgaySk;
    ImageView imgHinhSk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongbao);

        txtTenSk = findViewById(R.id.textViewTenSk);
        txtNgaySk = findViewById(R.id.textViewNgaySk);
        imgHinhSk = findViewById(R.id.imageViewSuKien);

        new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_EVENTS);
        menuBotNavBar();
    }

    private void menuBotNavBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityNotify.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityNotify.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:

                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityNotify.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private class Load extends AsyncTask<String, ArrayList<Event>, ArrayList<Event>> {
        ArrayList<String> arr = new ArrayList<>(), arrayList = new ArrayList<>();
        ArrayList<Event> listEvent = new ArrayList<>();
        EventAdapter eventAdapter;
        RecyclerView recyclerView;

        @Override
        protected ArrayList<Event> doInBackground(String... strings) {

            // parse json vừa get về ra arraylist
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                arrayList = JsonHelper.parseJsonNoId(new JSONArray(arr.get(0)), Config.JSON_EVENT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Event> list = new ArrayList<>();

            for (int i = 0; i < arrayList.size(); i += 5) {
                list.add(new Event(Integer.parseInt(arrayList.get(i + 3)), arrayList.get(i),
                        "Từ " + arrayList.get(i + 1) + " đến " + arrayList.get(i + 2), R.drawable.benninhkieu1));
            }
            return list;
        }

        @Override
        protected void onPostExecute(final ArrayList<Event> events) {
            super.onPostExecute(events);

            recyclerView = findViewById(R.id.RecyclerView_DanhSachSuKien);
            recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                    (ActivityNotify.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            eventAdapter = new EventAdapter(recyclerView, events, getApplicationContext());
            recyclerView.setAdapter(eventAdapter);
            eventAdapter.notifyDataSetChanged();


            listEvent = events;
            //set load more listener for the RecyclerView adapter
            eventAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

                @Override
                public void onLoadMore() {
                    if (listEvent.size() < Integer.parseInt(arr.get(2))) {
                        listEvent.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                eventAdapter.notifyItemInserted(listEvent.size() - 1);
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listEvent.remove(listEvent.size() - 1);
                                eventAdapter.notifyItemRemoved(listEvent.size());
                                String string = "";
                                try {
                                    string = new ModelService.Load().execute(arr.get(1)).get();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    arr = JsonHelper.parseJsonNoId(new JSONObject(string), Config.JSON_LOAD);
                                    arrayList = JsonHelper.parseJsonNoId(new JSONArray(arr.get(0)), Config.JSON_EVENT);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for (int i = 0; i < arrayList.size(); i += 5) {
                                    listEvent.add(new Event(Integer.parseInt(arrayList.get(i + 3)), arrayList.get(i),
                                            arrayList.get(i + 1) + " -> " + arrayList.get(i + 2),
                                            R.drawable.benninhkieu1));
                                }
                                eventAdapter.notifyDataSetChanged();
                                eventAdapter.setLoaded();
                            }
                        }, 1000);
                    }
                }
            });
        }
    }
}
