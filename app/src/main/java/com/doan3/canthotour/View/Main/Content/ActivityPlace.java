package com.doan3.canthotour.View.Main.Content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.doan3.canthotour.Adapter.ListOfPlaceAdapter;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;

import java.util.ArrayList;


public class ActivityPlace extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diadanh);

        initView_DiaDanh();

        menuBotNavBar();
    }

    private void menuBotNavBar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityPlace.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityPlace.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityPlace.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityPlace.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    private void initView_DiaDanh(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_DanhSachDiaDanh);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<Place> listPlace = new ArrayList<>();
        listPlace.add(new Place(R.drawable.ben_ninh_kieu, "Bến Ninh Kiều"));
        listPlace.add(new Place(R.drawable.cho_noi_cai_rang, "Chợ nổi Cái Răng"));
        listPlace.add(new Place(R.drawable.dinh_binh_thuy, "Đình Bình Thủy"));

        ListOfPlaceAdapter listOfPlaceAdapter = new ListOfPlaceAdapter(listPlace, getApplicationContext());
        recyclerView.setAdapter(listOfPlaceAdapter);
    }
}
