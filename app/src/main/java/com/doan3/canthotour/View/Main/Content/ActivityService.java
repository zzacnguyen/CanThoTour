package com.doan3.canthotour.View.Main.Content;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.ListOfServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ActivityService extends AppCompatActivity {
    ArrayList<String> finalArr = new ArrayList<>();
    ArrayList<String> formatJson = new ArrayList<>();
    Toolbar toolbar;
    TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_service);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);

        String url = getIntent().getStringExtra("url");
        if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_EATS)) { //Kiểm tra từng đường dẫn url
            formatJson = Config.JSON_EAT;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEat));
            toolbarTitle.setText("Danh sách quán ăn");
        } else if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_PLACES)) {
            formatJson = Config.JSON_PLACE;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            toolbarTitle.setText("Danh sách điểm tham quan");
        } else if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_HOTELS)) {
            formatJson = Config.JSON_HOTEL;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            toolbarTitle.setText("Danh sách khách sạn");
        } else if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_VEHICLES)) {
            formatJson = Config.JSON_VEHICLE;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            toolbarTitle.setText("Danh sách phương tiện");
        } else {
            formatJson = Config.JSON_ENTERTAINMENT;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            toolbarTitle.setText("Danh sách điểm vui chơi");
        }

        load(url, formatJson);

        ActivityServiceInfo.menuBotNavBar(this,0);
    }

    private void load(String url, final ArrayList<String> formatJson) {

        final ListOfServiceAdapter listOfServiceAdapter;
        final RecyclerView recyclerView;
        recyclerView = findViewById(R.id.RecyclerView_ServiceList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Service> services = new ModelService().getServiceFullList(url, formatJson);

        listOfServiceAdapter = new ListOfServiceAdapter(recyclerView, services, getApplicationContext());
        recyclerView.setAdapter(listOfServiceAdapter);
        listOfServiceAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<Service> finalListService = services;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject
                    (new ModelService.Load().execute(url).get()), Config.JSON_LOAD);
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
                                    getServiceFullList(finalArr.get(1), formatJson);
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
}
