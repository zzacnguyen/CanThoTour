package com.doan3.canthotour.View.Personal.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfTripScheduleAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelTripSchedule;
import com.doan3.canthotour.Model.ObjectClass.TripSchedule;
import com.doan3.canthotour.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.Helper.JsonHelper.parseJsonNoId;
import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;
import static com.doan3.canthotour.View.Personal.ActivityPersonal.userId;

public class ActivityTripSchedule extends AppCompatActivity {

    FloatingActionButton fabAddTripSchedule;
    ArrayList<String> finalArr = new ArrayList<>();
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_schedule_list);

        fabAddTripSchedule = findViewById(R.id.fabAddTripSchedule);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fabAddTripSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ActivityTripSchedule.this, ActivityAddTripSchedule.class), 1);
            }
        });
        loadTripList(Config.URL_HOST + Config.URL_GET_TRIP_SCHEDULE);
        menuBotNavBar(this, 3);
    }

    private void loadTripList(String url) {
        url += userId;
        final RecyclerView recyclerView = findViewById(R.id.RecyclerView_TripScheduleList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<TripSchedule> tripScheduleList = new ModelTripSchedule().getTripScheduleList(url);

        final ListOfTripScheduleAdapter listOfTripScheduleAdapter =
                new ListOfTripScheduleAdapter(recyclerView, getApplicationContext(), tripScheduleList);
        recyclerView.setAdapter(listOfTripScheduleAdapter);
        listOfTripScheduleAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<TripSchedule> finalListService = tripScheduleList;
        try {
            finalArr = parseJsonNoId(new JSONObject
                    (new HttpRequestAdapter.httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        listOfTripScheduleAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (finalListService.size() < Integer.parseInt(finalArr.get(2))) {
                    finalListService.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            listOfTripScheduleAdapter.notifyItemInserted(finalListService.size() - 1);
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalListService.remove(finalListService.size() - 1);
                            listOfTripScheduleAdapter.notifyItemRemoved(finalListService.size());

                            ArrayList<TripSchedule> tripscheduleArrayList = new ModelTripSchedule().getTripScheduleList(finalArr.get(1));
                            finalListService.addAll(tripscheduleArrayList);
                            try {
                                finalArr = parseJsonNoId(new JSONObject
                                        (new HttpRequestAdapter.httpGet().execute(finalArr.get(1)).get()), Config.GET_KEY_JSON_LOAD);
                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                            listOfTripScheduleAdapter.notifyDataSetChanged();
                            listOfTripScheduleAdapter.setLoaded();
                        }
                    }, 1000);
                }
            }
        });

    }

}
