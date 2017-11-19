package com.doan3.canthotour.View.TrangChu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.doan3.canthotour.Adapter.DiaDiemAdapter;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.DiaDiem;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.CaNhan.ActivityCaNhan;
import com.doan3.canthotour.View.ThongBao.ActivityThongBao;
import com.doan3.canthotour.View.YeuThich.ActivityYeuThich;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

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
        ArrayList<DiaDiem> arrayList = new ArrayList<>();
        arrayList.add(new DiaDiem(R.drawable.ben_ninh_kieu, "Bến Ninh Kiều"));
        arrayList.add(new DiaDiem(R.drawable.cho_noi_cai_rang, "Chợ nổi Cái Răng"));
        arrayList.add(new DiaDiem(R.drawable.dinh_binh_thuy, "Đình Bình Thủy"));

        DiaDiemAdapter diaDiemAdapter = new DiaDiemAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(diaDiemAdapter);
    }

    private void initView_AnUong(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_AnUong);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<DiaDiem> arrayList = new ArrayList<>();
        arrayList.add(new DiaDiem(R.drawable.banhxeo7toi, "Bánh xèo 7 Tới"));
        arrayList.add(new DiaDiem(R.drawable.nuoc_mia_my_tho, "Nước mía Mỹ Thơ"));
        arrayList.add(new DiaDiem(R.drawable.comgahungky, "Cơm gà Hùng Ký"));

        DiaDiemAdapter diaDiemAdapter = new DiaDiemAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(diaDiemAdapter);

    }

    private void initView_KhachSan(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_KhachSan);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Add item
        ArrayList<DiaDiem> arrayList = new ArrayList<>();
        arrayList.add(new DiaDiem(R.drawable.muongthanh, "Mường Thanh Hotel"));
        arrayList.add(new DiaDiem(R.drawable.ninhkieu2, "Ninh Kiều 2 Hotel"));
        arrayList.add(new DiaDiem(R.drawable.vinpearl, "Vinpearl Hotel"));

        DiaDiemAdapter diaDiemAdapter = new DiaDiemAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(diaDiemAdapter);

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
