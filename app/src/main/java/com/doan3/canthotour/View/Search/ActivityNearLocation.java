package com.doan3.canthotour.View.Search;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.NearLocationAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

public class ActivityNearLocation extends AppCompatActivity {
    String longitude, latitude;
    int serviceType;
    TextView txtPlaceName, txtRadius;
    ImageView imgPlacePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearlocation);
        txtPlaceName = findViewById(R.id.textViewNearName);
        txtRadius = findViewById(R.id.textViewRadius);
        imgPlacePhoto = findViewById(R.id.imageViewNear);

        longitude = getIntent().getStringExtra("kinhdo");
        latitude = getIntent().getStringExtra("vido");
        serviceType = getIntent().getIntExtra("loaihinh", 1);
        load();

        ActivityServiceInfo.menuBotNavBar(this,0);
    }

    private void load() {

        File path = new File(Environment.getExternalStorageDirectory() + "/canthotour");
        if (!path.exists()) {
            path.mkdirs();
        }
        final File file = new File(path, "khoangcach.json");
        String radius = null;
        if (file.exists()) {
            try {
                radius = new JSONArray(JsonHelper.readJson(file)).getJSONObject(0).
                        getString("khoangcach");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            radius = "500";
        }
        ArrayList<NearLocation> favoriteList = new ModelService().getNearLocationList(Config.URL_HOST +
                "timkiem/dichvulancan/location=" + latitude.trim() + "," + longitude.trim() + "&type=" +
                serviceType + "&radius=" + radius, serviceType, this);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_NearLocation);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(ActivityNearLocation.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        NearLocationAdapter nearLocationAdapter =
                new NearLocationAdapter(favoriteList, getApplicationContext());
        recyclerView.setAdapter(nearLocationAdapter);
        nearLocationAdapter.notifyDataSetChanged();
    }
}
