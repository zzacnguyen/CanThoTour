package com.doan3.canthotour.View.Notify;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.doan3.canthotour.Adapter.EventAdapter;
import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelEvent;
import com.doan3.canthotour.Model.ObjectClass.Event;
import com.doan3.canthotour.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityNotify extends AppCompatActivity {

    ArrayList<String> finalArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        getNotify(Config.URL_HOST + Config.URL_GET_ALL_EVENTS);

        menuBotNavBar(this, 2);
    }

    private void getNotify(String url) {

        final RecyclerView recyclerView;
        recyclerView = findViewById(R.id.RecyclerView_EventList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Event> events = new ModelEvent().getEventList(this, url);

        final EventAdapter eventAdapter = new EventAdapter(recyclerView, events, getApplicationContext());
        recyclerView.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<Event> finalListService = events;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject
                    (new httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        eventAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (finalListService.size() < Integer.parseInt(finalArr.get(2))) {
                    finalListService.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            eventAdapter.notifyItemInserted(finalListService.size() - 1);
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalListService.remove(finalListService.size() - 1);
                            eventAdapter.notifyItemRemoved(finalListService.size());

                            ArrayList<Event> serviceArrayList = new ModelEvent().
                                    getEventList(getApplicationContext(), finalArr.get(1));
                            finalListService.addAll(serviceArrayList);
                            try {
                                finalArr = JsonHelper.parseJsonNoId(new JSONObject
                                        (new httpGet().execute(finalArr.get(1)).get()), Config.GET_KEY_JSON_LOAD);
                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
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
