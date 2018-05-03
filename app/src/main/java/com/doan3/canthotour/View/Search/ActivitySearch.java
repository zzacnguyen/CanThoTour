package com.doan3.canthotour.View.Search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Adapter.ListOfServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelFavorite;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivitySearchHistory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Personal.ActivityPersonal.userId;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivitySearch extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> finalArr = new ArrayList<>();
    EditText etSearch;
    Button btnCancel;
    TextView txtSearchHistory;
    ListOfServiceAdapter listOfServiceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.etSearch);
        btnCancel = findViewById(R.id.btnCancel);
        txtSearchHistory = findViewById(R.id.textViewSearchHistory);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    if (!etSearch.getText().toString().equals("")) {
                        searchAll(Config.URL_SEARCH_ALL,
                                etSearch.getText().toString().replaceAll(" ", "\\+"));
                    } else {
                        Toast.makeText(getApplicationContext(), "Chưa nhập", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finishActivity(2);
            }
        });

        txtSearchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAll(Config.URL_GET_HISTORY_SEARCH, String.valueOf(userId));
            }
        });

    }

    private void searchAll(String link, String key) {

        String url = Config.URL_HOST + link + key;
        final RecyclerView recyclerView;
        recyclerView = findViewById(R.id.RecyclerView_SearchList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Service> services = new ModelFavorite().getFavoriteList(new File(""), url);

        listOfServiceAdapter = new ListOfServiceAdapter(recyclerView, services, getApplicationContext());
        recyclerView.setAdapter(listOfServiceAdapter);
        listOfServiceAdapter.notifyDataSetChanged();

        final ArrayList<Service> finalListService = services;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject
                    (new HttpRequestAdapter.httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //set load more listener for the RecyclerView adapter
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        listOfServiceAdapter.setOnLoadMoreListener(null);
        btnCancel.setOnClickListener(null);
        txtSearchHistory.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) { //Tạo sự kiện click cho TextView
        switch (view.getId()) {
            case R.id.textViewSearchHistory:
                Intent iSearchHistory = new Intent(ActivitySearch.this, ActivitySearchHistory.class);
                startActivity(iSearchHistory);
                break;
        }
    }
}
