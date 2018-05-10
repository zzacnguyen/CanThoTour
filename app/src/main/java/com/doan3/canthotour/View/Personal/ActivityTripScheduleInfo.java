package com.doan3.canthotour.View.Personal;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelFavorite;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityTripScheduleInfo extends AppCompatActivity {
    TextView tvTripName, tvStartDate, tvEndDate;
    RecyclerView recyclerView;
    ArrayList<String> finalArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actitity_trip_schedule_info);
        getTripScheduleDetail();
        menuBotNavBar(this, 3);
    }

    void getTripScheduleDetail() {
        tvTripName = findViewById(R.id.tvTripName);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);

        String[] schedules = getIntent().getStringArrayExtra("schedules");
        recyclerView = findViewById(R.id.RecyclerView_PlaceToVisit);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(ActivityTripScheduleInfo.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getServiceList(schedules[0]);
        tvTripName.setText(schedules[1]);
        tvStartDate.setText(schedules[2]);
        tvEndDate.setText(schedules[3]);
    }

    private void getServiceList(String id) {
        String url = Config.URL_HOST + Config.URL_GET_TRIP_SCHEDULE_INFO + id;
        // dùng chung hàm get danh sách yêu thích vì giống nhau chỉ khác file
        ArrayList<Service> favoriteList = new ModelFavorite().getFavoriteList(new File(""), url);

        final ListOfServiceAdapter listOfServiceAdapter =
                new ListOfServiceAdapter(recyclerView, favoriteList, getApplicationContext());
        recyclerView.setAdapter(listOfServiceAdapter);
        listOfServiceAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<Service> finalListService = favoriteList;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject(new HttpRequestAdapter.httpGet().execute(url).get())
                    , Config.GET_KEY_JSON_LOAD);
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

                            ArrayList<Service> serviceArrayList = new ModelFavorite().
                                    getFavoriteList(new File(""), finalArr.get(1));
                            finalListService.addAll(serviceArrayList);
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
