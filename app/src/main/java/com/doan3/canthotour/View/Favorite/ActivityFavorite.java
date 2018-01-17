package com.doan3.canthotour.View.Favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Personal.ActivityLogin.idNguoiDung;

public class ActivityFavorite extends AppCompatActivity {
    ArrayList<String> finalArr = new ArrayList<>();
    TextView txtTenDD;
    ImageView imgHinhDD;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        txtTenDD = findViewById(R.id.textViewYeuThich);
        imgHinhDD = findViewById(R.id.imageViewYeuThich);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        File path = new File(Environment.getExternalStorageDirectory() + "/canthotour");
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, "dsyeuthich.json");

        recyclerView = findViewById(R.id.RecyclerView_DanhSachYeuThich);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(ActivityFavorite.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        load(file, idNguoiDung);
        if (file.exists()) {
            new PostJson(file).execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE);
        }
        menuBotNavBar();
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

    private void load(final File file, int id) {

        ArrayList<Service> favoriteList = new ModelService().getFavoriteList(file, Config.URL_HOST +
                Config.URL_GET_ALL_FAVORITE + "/" + id);

        final ListOfServiceAdapter listOfServiceAdapter =
                new ListOfServiceAdapter(recyclerView, favoriteList, getApplicationContext());
        recyclerView.setAdapter(listOfServiceAdapter);
        listOfServiceAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<Service> finalListService = favoriteList;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject(new ModelService.Load().execute(Config.URL_HOST +
                    Config.URL_GET_ALL_FAVORITE + "/" + id).get()), Config.JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        listOfServiceAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (finalListService.size() < Integer.parseInt(finalArr.get(2))) {
                    finalListService.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            listOfServiceAdapter.notifyItemInserted(finalListService.size() - 1);
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalListService.remove(finalListService.size() - 1);
                            listOfServiceAdapter.notifyItemRemoved(finalListService.size());

                            ArrayList<Service> serviceArrayList = new ModelService().
                                    getFavoriteList(file, finalArr.get(1));
                            for (int i = 0; i < serviceArrayList.size(); i++) {
                                finalListService.add(serviceArrayList.get(i));
                            }
                            try {
                                finalArr = JsonHelper.parseJsonNoId(new JSONObject
                                        (new ModelService.Load().execute(finalArr.get(1)).get()), Config.JSON_LOAD);
                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                            listOfServiceAdapter.notifyDataSetChanged();
                            listOfServiceAdapter.setLoaded();
                        }
                    }, 1000);
                }
            }
        });
    }

    private class PostJson extends AsyncTask<String, Void, Void> {
        File file;

        private PostJson(File file) {
            this.file = file;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONArray jsonFile = new JSONArray(JsonHelper.readJson(file));
                for (int i = 0; i < jsonFile.length(); i++) {
                    JSONObject jsonObject = new JSONObject("{\"dv_iddichvu\":\"" +
                            jsonFile.getJSONObject(i).getString("id") + "\"" +
                            ",\"nd_idnguoidung\":\"" + idNguoiDung + "\"}");
                    HttpRequestAdapter.httpPost(strings[0], jsonObject);
                }
                file.delete();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
