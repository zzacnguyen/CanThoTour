package com.doan3.canthotour.View.Main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Adapter.ListOfReviewAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Review;
import com.doan3.canthotour.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.Helper.JsonHelper.parseJsonNoId;
import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityReviewList extends AppCompatActivity {
    ArrayList<String> finalArr = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewlist);

        int id = getIntent().getIntExtra("id", 1);
        load(Config.URL_HOST + Config.URL_GET_ALL_REVIEWS + id, Config.GET_KEY_JSON_ALL_REVIEW);

        menuBotNavBar(this, 0);
    }

    private void load(String url, final ArrayList<String> formatJson) {

        final RecyclerView recyclerView = findViewById(R.id.RecyclerView_ReviewList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Review> reviews = new ModelService().getReviewList(url, formatJson);

        final ListOfReviewAdapter listOfReviewAdapter =
                new ListOfReviewAdapter(recyclerView, reviews, getApplicationContext());
        recyclerView.setAdapter(listOfReviewAdapter);
        listOfReviewAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<Review> finalListService = reviews;
        try {
            finalArr = parseJsonNoId(new JSONObject
                    (new httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        listOfReviewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (finalListService.size() < Integer.parseInt(finalArr.get(2))) {
                    finalListService.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            listOfReviewAdapter.notifyItemInserted(finalListService.size() - 1);
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalListService.remove(finalListService.size() - 1);
                            listOfReviewAdapter.notifyItemRemoved(finalListService.size());

                            ArrayList<Review> reviewArrayList = new ModelService().getReviewList(finalArr.get(1), formatJson);
                            for (int i = 0; i < reviewArrayList.size(); i++) {
                                finalListService.add(reviewArrayList.get(i));
                            }
                            try {
                                finalArr = parseJsonNoId(new JSONObject
                                        (new httpGet().execute(finalArr.get(1)).get()), Config.GET_KEY_JSON_LOAD);
                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                            listOfReviewAdapter.notifyDataSetChanged();
                            listOfReviewAdapter.setLoaded();
                        }
                    }, 1000);
                }
            }
        });
    }
}
