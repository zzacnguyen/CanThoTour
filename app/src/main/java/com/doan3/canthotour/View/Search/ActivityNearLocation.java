package com.doan3.canthotour.View.Search;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.NearLocationAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivityNearLocation extends AppCompatActivity {
    ArrayList<String> finalArr = new ArrayList<>();
    int id;
    TextView txtTenDd, txtKhoangCach;
    ImageView imgHinhDd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diadiemlancan);
        txtTenDd = findViewById(R.id.textViewTenLanCan);
        txtKhoangCach = findViewById(R.id.textViewKhoangCach);
        imgHinhDd = findViewById(R.id.imageViewLanCan);

        id = getIntent().getIntExtra("id", 0);
        load();

        ActivityServiceInfo.menuBotNavBar(this);
    }

    private void load() {

        ArrayList<NearLocation> favoriteList = new ModelService().getNearLocationList(Config.URL_HOST +
                "timkiem/dichvulancan/id=" + id);

        final RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDiemLanCan);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(ActivityNearLocation.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        final NearLocationAdapter nearLocationAdapter =
                new NearLocationAdapter(recyclerView, favoriteList, getApplicationContext());
        recyclerView.setAdapter(nearLocationAdapter);
        nearLocationAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<NearLocation> finalListService = favoriteList;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject(new ModelService.Load().execute(Config.URL_HOST +
                    "timkiem/dichvulancan/id=" + id).get()), Config.JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        nearLocationAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (finalListService.size() < Integer.parseInt(finalArr.get(2))) {
                    finalListService.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            nearLocationAdapter.notifyItemInserted(finalListService.size() - 1);
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalListService.remove(finalListService.size() - 1);
                            nearLocationAdapter.notifyItemRemoved(finalListService.size());

                            ArrayList<NearLocation> serviceArrayList = new ModelService().
                                    getNearLocationList(finalArr.get(1));
                            for (int i = 0; i < serviceArrayList.size(); i++) {
                                finalListService.add(serviceArrayList.get(i));
                            }
                            try {
                                finalArr = JsonHelper.parseJsonNoId(new JSONObject
                                        (new ModelService.Load().execute(finalArr.get(1)).get()), Config.JSON_LOAD);
                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                            nearLocationAdapter.notifyDataSetChanged();
                            nearLocationAdapter.setLoaded();
                        }
                    }, 1000);
                }
            }
        });
    }
}
