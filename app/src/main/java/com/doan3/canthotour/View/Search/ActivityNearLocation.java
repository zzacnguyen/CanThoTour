package com.doan3.canthotour.View.Search;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.NearLocationAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.ModelSearch;
import com.doan3.canthotour.Model.ObjectClass.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Personal.ActivityOption;

import java.util.ArrayList;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityNearLocation extends AppCompatActivity {
    TextView txtPlaceName, txtRadius;
    ImageView imgPlacePhoto;
    ImageView btnSetRadius, btnBack;
    String radius;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearlocation);
        txtPlaceName = findViewById(R.id.textViewNearName);
        txtRadius = findViewById(R.id.textViewRadius);
        imgPlacePhoto = findViewById(R.id.imageViewNear);
        btnSetRadius = findViewById(R.id.btnSetRadius);
        btnBack = findViewById(R.id.btnBack);

        String latitude = getIntent().getStringExtra(Config.KEY_NEAR_LOCATION.get(0));
        String longitude = getIntent().getStringExtra(Config.KEY_NEAR_LOCATION.get(1));
        int serviceType = getIntent().getIntExtra(Config.KEY_NEAR_LOCATION.get(2), 1);
        searchNearLocation(latitude, longitude, serviceType);

        btnSetRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ActivityNearLocation.this);
                dialog.setTitle(getResources().getString(R.string.text_SetDistance));
                dialog.setCancelable(false); //Khóa màn hình ngoài sau khi ấn vàodialog
                dialog.setContentView(R.layout.custom_radius);

                //Ánh xạ các palette trong dialog
                final EditText etDistance = dialog.findViewById(R.id.etRadius);
                Button btnAgree = dialog.findViewById(R.id.btnConfirmRadius);
                Button btnCancel = dialog.findViewById(R.id.btnCancelRadius);

//                btnAgree.setOnClickListener(new View.OnClickListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onClick(View view) {
//                        radius = etDistance.getText().toString().trim();
//                        editor.putString(Config.KEY_DISTANCE, radius);
//                        editor.apply();
//                        dialog.cancel();
//                    }
//                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menuBotNavBar(this, 0);
    }

    private void searchNearLocation(String latitude, String longitude, int serviceType) {

        SharedPreferences sharedPreferences = getSharedPreferences(Config.KEY_DISTANCE, MODE_PRIVATE);

        ArrayList<NearLocation> favoriteList = new ModelSearch().getNearLocationList(
                Config.URL_HOST + Config.URL_SEARCH_SERVICE_NEAR.get(0) + latitude.trim() + "," + longitude.trim()
                        + Config.URL_SEARCH_SERVICE_NEAR.get(1) + serviceType + Config.URL_SEARCH_SERVICE_NEAR.get(2)
                        + sharedPreferences.getString(Config.KEY_DISTANCE, Config.DEFAULT_DISTANCE), serviceType, this);

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
