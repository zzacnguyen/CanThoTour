package com.doan3.canthotour.View.Search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.NearLocationAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import java.util.ArrayList;

public class ActivityNearLocation extends AppCompatActivity {
    String kinhDo, viDo;
    int loaiHinh;
    TextView txtTenDd, txtKhoangCach;
    ImageView imgHinhDd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diadiemlancan);
        txtTenDd = findViewById(R.id.textViewTenLanCan);
        txtKhoangCach = findViewById(R.id.textViewKhoangCach);
        imgHinhDd = findViewById(R.id.imageViewLanCan);

        kinhDo = getIntent().getStringExtra("kinhdo");
        viDo = getIntent().getStringExtra("vido");
        loaiHinh = getIntent().getIntExtra("loaihinh", 1);
        load();

        ActivityServiceInfo.menuBotNavBar(this);
    }

    private void load() {

        ArrayList<NearLocation> favoriteList = new ModelService().getNearLocationList(Config.URL_HOST +
                "timkiem/dichvulancan/location=" + viDo.trim() + "," + kinhDo.trim() + "&type=" +
                loaiHinh + "&radius=500", loaiHinh, this);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_DiaDiemLanCan);
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
