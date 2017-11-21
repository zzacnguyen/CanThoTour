package com.doan3.canthotour.View.TrangChu;

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

import com.doan3.canthotour.Adapter.AnUongAdapter;
import com.doan3.canthotour.Adapter.DiaDanhAdapter;
import com.doan3.canthotour.Adapter.KhachSan;
import com.doan3.canthotour.Adapter.KhachSanAdapter;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.AnUong;
import com.doan3.canthotour.Model.DiaDanh;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.CaNhan.ActivityCaNhan;
import com.doan3.canthotour.View.ThongBao.ActivityThongBao;
import com.doan3.canthotour.View.TrangChu.NoiDung.ActivityDiaDanh;
import com.doan3.canthotour.View.YeuThich.ActivityYeuThich;

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
                startActivity(new Intent(MainActivity.this, ActivityDiaDanh.class));
            }
        });

//        setSupportActionBar(toolbar);

        initView_DiaDanh();
        initView_AnUong();
        initView_KhachSan();

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
                        startActivity(new Intent(MainActivity.this, ActivityYeuThich.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(MainActivity.this, ActivityThongBao.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(MainActivity.this, ActivityCaNhan.class));
                        break;
                }
                return false;
            }
        });
    }

    //region Nội dung chính (content-view)

    //View địa danh
    private void initView_DiaDanh(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_DiaDanh);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<DiaDanh> listDiaDanh = new ArrayList<>();
        listDiaDanh.add(new DiaDanh(R.drawable.ben_ninh_kieu, "Bến Ninh Kiều"));
        listDiaDanh.add(new DiaDanh(R.drawable.cho_noi_cai_rang, "Chợ nổi Cái Răng"));
        listDiaDanh.add(new DiaDanh(R.drawable.dinh_binh_thuy, "Đình Bình Thủy"));

        DiaDanhAdapter diaDanhAdapter = new DiaDanhAdapter(listDiaDanh, getApplicationContext());
        recyclerView.setAdapter(diaDanhAdapter);
    }

    private void initView_AnUong(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_AnUong);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<AnUong> listAnUong = new ArrayList<>();
        listAnUong.add(new AnUong(R.drawable.banhxeo7toi, "Bánh xèo 7 Tới"));
        listAnUong.add(new AnUong(R.drawable.nuoc_mia_my_tho, "Nước mía Mỹ Thơ"));
        listAnUong.add(new AnUong(R.drawable.comgahungky, "Cơm gà Hùng Ký"));

        AnUongAdapter anUongAdapter = new AnUongAdapter(listAnUong, getApplicationContext());
        recyclerView.setAdapter(anUongAdapter);
    }

    private void initView_KhachSan(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_KhachSan);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<KhachSan> listKhachSan = new ArrayList<>();
        listKhachSan.add(new KhachSan(R.drawable.muongthanh, "Mường Thanh Hotel"));
        listKhachSan.add(new KhachSan(R.drawable.ninhkieu2, "Ninh Kiều 2 Hotel"));
        listKhachSan.add(new KhachSan(R.drawable.vinpearl, "Vinpearl Hotel"));

        KhachSanAdapter khachSanAdapter = new KhachSanAdapter(listKhachSan, getApplicationContext());
        recyclerView.setAdapter(khachSanAdapter);

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
