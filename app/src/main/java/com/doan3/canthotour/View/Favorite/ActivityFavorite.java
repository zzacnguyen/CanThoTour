package com.doan3.canthotour.View.Favorite;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;
import static com.doan3.canthotour.View.Personal.ActivityPersonal.userId;

public class ActivityFavorite extends AppCompatActivity {
    ArrayList<String> finalArr = new ArrayList<>();
    TextView txtServiceName;
    ImageView imgServiceImage;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        txtServiceName = findViewById(R.id.textViewFavorite);
        imgServiceImage = findViewById(R.id.imageViewFavorite);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        File path = new File(Environment.getExternalStorageDirectory() + Config.FOLDER);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, Config.FILE_LIKE);

        recyclerView = findViewById(R.id.RecyclerView_FavoriteList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(ActivityFavorite.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        load(file, userId);
        if (file.exists()) {
            try {
                JSONArray jsonFile = new JSONArray(JsonHelper.readJson(file));
                for (int i = 0; i < jsonFile.length(); i++) {
                    JSONObject jsonObject = new JSONObject("{" +
                            Config.POST_KEY_JSON_LIKE.get(0) + ":\"" + jsonFile.getJSONObject(i).getString("id") + "\"," +
                            Config.POST_KEY_JSON_LIKE.get(1) + ":\"" + userId + "\"}");
                    new HttpRequestAdapter.httpPost(jsonObject).execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE);
                }
                file.delete();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        menuBotNavBar(this, 1);
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
            finalArr = JsonHelper.parseJsonNoId(new JSONObject(new HttpRequestAdapter.httpGet().execute(Config.URL_HOST +
                    Config.URL_GET_ALL_FAVORITE + "/" + id).get()), Config.GET_KEY_JSON_LOAD);
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
                                        (new HttpRequestAdapter.httpGet().execute(finalArr.get(1)).get()), Config.GET_KEY_JSON_LOAD);
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
}
