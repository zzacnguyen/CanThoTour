package com.doan3.canthotour.View.TrangChu.NoiDung;

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

import com.doan3.canthotour.Adapter.DanhSachDiaDanhAdapter;
import com.doan3.canthotour.Adapter.DiaDanhAdapter;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Model.DiaDanh;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.CaNhan.ActivityCaNhan;
import com.doan3.canthotour.View.ThongBao.ActivityThongBao;
import com.doan3.canthotour.View.TrangChu.MainActivity;
import com.doan3.canthotour.View.YeuThich.ActivityYeuThich;

import java.util.ArrayList;


public class ActivityDiaDanh extends AppCompatActivity {
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
                        startActivity(new Intent(ActivityDiaDanh.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityDiaDanh.this, ActivityYeuThich.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityDiaDanh.this, ActivityThongBao.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityDiaDanh.this, ActivityCaNhan.class));
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
        ArrayList<DiaDanh> listDiaDanh = new ArrayList<>();
        listDiaDanh.add(new DiaDanh(R.drawable.ben_ninh_kieu, "Bến Ninh Kiều"));
        listDiaDanh.add(new DiaDanh(R.drawable.cho_noi_cai_rang, "Chợ nổi Cái Răng"));
        listDiaDanh.add(new DiaDanh(R.drawable.dinh_binh_thuy, "Đình Bình Thủy"));

        DanhSachDiaDanhAdapter danhSachDiaDanhAdapter = new DanhSachDiaDanhAdapter(listDiaDanh, getApplicationContext());
        recyclerView.setAdapter(danhSachDiaDanhAdapter);
    }
}
