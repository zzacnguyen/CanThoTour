package com.doan3.canthotour.View.Main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.doan3.canthotour.Adapter.EatAdapter;
import com.doan3.canthotour.Adapter.HotelAdapter;
import com.doan3.canthotour.Adapter.PlaceAdapter;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.Eat;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.Model.Hotel;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Main.Content.ActivityPlace;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnDiaDanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnDiaDanh = (Button) findViewById(R.id.btnTatCaDiaDanh);

        btnDiaDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityPlace.class));
            }
        });

//        setSupportActionBar(toolbar);

        initView_Place();
        initView_Eat();
        initView_Hotel();

        menuBotNavBar();

    }
    //Bottom navigation bar
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
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(MainActivity.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(MainActivity.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(MainActivity.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    //region Nội dung chính (content-view)

    //View địa danh
    private void initView_Place(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_DiaDanh);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<Place> listPlace = new ArrayList<>();
        listPlace.add(new Place(R.drawable.ben_ninh_kieu, "Bến Ninh Kiều"));
        listPlace.add(new Place(R.drawable.cho_noi_cai_rang, "Chợ nổi Cái Răng"));
        listPlace.add(new Place(R.drawable.dinh_binh_thuy, "Đình Bình Thủy"));

        PlaceAdapter placeAdapter = new PlaceAdapter(listPlace, getApplicationContext());
        recyclerView.setAdapter(placeAdapter);
    }

    private void initView_Eat(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_AnUong);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<Eat> listEat = new ArrayList<>();
        listEat.add(new Eat(R.drawable.banhxeo7toi, "Bánh xèo 7 Tới"));
        listEat.add(new Eat(R.drawable.nuoc_mia_my_tho, "Nước mía Mỹ Thơ"));
        listEat.add(new Eat(R.drawable.comgahungky, "Cơm gà Hùng Ký"));

        EatAdapter eatAdapter = new EatAdapter(listEat, getApplicationContext());
        recyclerView.setAdapter(eatAdapter);
    }

    private void initView_Hotel(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_KhachSan);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<Hotel> listHotel = new ArrayList<>();
        listHotel.add(new Hotel(R.drawable.muongthanh, "Mường Thanh Hotel"));
        listHotel.add(new Hotel(R.drawable.ninhkieu2, "Ninh Kiều 2 Hotel"));
        listHotel.add(new Hotel(R.drawable.vinpearl, "Vinpearl Hotel"));

        HotelAdapter hotelAdapter = new HotelAdapter(listHotel, getApplicationContext());
        recyclerView.setAdapter(hotelAdapter);

    }

    //endregion


    //region Topbar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
