package com.doan3.canthotour.View.Main;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ModelPlace;
import com.doan3.canthotour.Model.ObjectClass.PlaceInfo;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Search.ActivityNearLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ActivityPlaceInfo extends AppCompatActivity {

    public static String kinhDo;
    public static String viDo;
    JSONObject object;
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    TextView fbSuKien;
    boolean sukien = true;
    int ma;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdiadiem);

        btnLuuDiaDiem = findViewById(R.id.btnLuuDiaDiem);
        btnLanCan = findViewById(R.id.btnDiaDiemLanCan);
        btnChiaSe = findViewById(R.id.btnChiaSe);
        fbSuKien = findViewById(R.id.fb_sukien);

        displayEvent();

        btnLanCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityPlaceInfo.this, ActivityNearLocation.class));
            }
        });

        ma = getIntent().getIntExtra("masp", 1);

        load(this, ma);
        btnLuuDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray getDataJsonFile;
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(path, "/dsyeuthich.json");
                try {
                    if (file.exists()) {
                        getDataJsonFile = new JSONArray(JsonHelper.readJson(file));
                        for (int i = 0; i < getDataJsonFile.length(); i++) {
                            if (!object.toString().equals(getDataJsonFile.getJSONObject(i).toString())) {
                                JsonHelper.writeJson(file, object);
                                Toast.makeText(ActivityPlaceInfo.this,
                                        "Lưu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityPlaceInfo.this,
                                        "Đã lưu trước đó", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        JsonHelper.writeJson(file, object);
                        Toast.makeText(ActivityPlaceInfo.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btnLanCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPlaceInfo.this, ActivityNearLocation.class);
                intent.putExtra("url", Config.URL_HOST + "timkiemSort/location=" + kinhDo + "," + viDo + "&radius=500&keyword=can+tho");
                startActivity(intent);
            }
        });


        menuBotNavBar();
    }

    void displayEvent(){
        if (sukien == true){
            fbSuKien.setText("Sự kiện");
            fbSuKien.setVisibility(TextView.VISIBLE); //Ẩn thì thay VISIBLE bằng INVISIBLE
        }
    }
    private void menuBotNavBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityPlaceInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityPlaceInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityPlaceInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityPlaceInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    public void load(Activity activity, int ma) {

        TextView txtTenDD = activity.findViewById(R.id.textViewTenDD);
        TextView txtDiaChi = activity.findViewById(R.id.textViewDiaChi);
        TextView txtSDT = activity.findViewById(R.id.textViewSDT);
        TextView txtGioiThieu = activity.findViewById(R.id.textViewGioiThieu);

        PlaceInfo placeInfo = new ModelPlace().getPlaceInfo(ma);
        try {
            object = new JSONObject("{\"dd_iddiadiem\":\"" + placeInfo.getId() + "\"" +
                    ",\"nd_idnguoidung\":\"" + placeInfo.getIdND() + "\"" +
                    ",\"dd_tendiadiem\":\"" + placeInfo.getTen() + "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        kinhDo = placeInfo.getKinhDo();
        viDo = placeInfo.getViDo();
        txtTenDD.setText(placeInfo.getTen());
        txtDiaChi.setText(placeInfo.getDiaChi());
        txtSDT.setText(placeInfo.getSdt());
        txtGioiThieu.setText(placeInfo.getGioiThieu());
    }
}
