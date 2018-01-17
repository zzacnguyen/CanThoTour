package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Search.ActivityAdvancedSearch;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doan3.canthotour.View.Personal.ActivityLogin.avatar;
import static com.doan3.canthotour.View.Personal.ActivityLogin.idNguoiDung;
import static com.doan3.canthotour.View.Personal.ActivityLogin.loaiNd;
import static com.doan3.canthotour.View.Personal.ActivityLogin.tenNd;

public class ActivityPersonal extends AppCompatActivity {

    Button btnThemDiaDiem, btnThemDichVu, btnDangKyDoanhNghiep, btnCaiDat, btnDangNhap, btnTimKiemNangCao, btnDangXuat;
    TextView txtTenNd, txtLoaiNd;
    CircleImageView Cavatar;
    LinearLayout themDiaDiem, themDichVu, DangKyDn, DangXuat, DangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        btnTimKiemNangCao = findViewById(R.id.buttonTimKiemNC);
        btnThemDiaDiem = findViewById(R.id.buttonThemDiaDiem);
        btnThemDichVu = findViewById(R.id.buttonThemDichVu);
        btnDangKyDoanhNghiep = findViewById(R.id.buttonDangKyDoanhNghiep);
        btnCaiDat = findViewById(R.id.buttonCaiDat);
        btnDangNhap = findViewById(R.id.buttonDangNhap);
        btnDangXuat = findViewById(R.id.buttonDangXuat);
        txtTenNd = findViewById(R.id.txtTenND);
        txtLoaiNd = findViewById(R.id.txtLoaiND);
        Cavatar = findViewById(R.id.avatar);
        themDiaDiem = findViewById(R.id.ThemDiaDiem);
        themDichVu = findViewById(R.id.ThemDichVu);
        DangKyDn = findViewById(R.id.DangKyDn);
        DangXuat = findViewById(R.id.DangXuat);
        DangNhap = findViewById(R.id.DangNhap);

        txtTenNd.setText(tenNd);
        txtLoaiNd.setText(loaiNd);
        Cavatar.setImageBitmap(avatar);

        if (idNguoiDung == 0) {
            themDiaDiem.setVisibility(View.GONE);
            themDichVu.setVisibility(View.GONE);
            DangKyDn.setVisibility(View.GONE);
            DangXuat.setVisibility(View.GONE);
        } else {
            themDiaDiem.setVisibility(View.VISIBLE);
            themDichVu.setVisibility(View.VISIBLE);
            DangKyDn.setVisibility(View.VISIBLE);
            DangXuat.setVisibility(View.VISIBLE);
            DangNhap.setVisibility(View.GONE);
        }

        btnTimKiemNangCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ActivityPersonal.this, ActivityAdvancedSearch.class),
                        1);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ActivityPersonal.this, ActivityLogin.class), 1);
            }
        });
        btnThemDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iThemDiaDiem = new Intent(ActivityPersonal.this, ActivityAddPlace.class);
                startActivity(iThemDiaDiem);
            }
        });

        btnThemDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iThemDichVu = new Intent(ActivityPersonal.this, ActivityAddService.class);
                startActivity(iThemDichVu);
            }
        });

        btnDangKyDoanhNghiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDangKyDoanhNghiep = new Intent(ActivityPersonal.this, ActivityRegCoop.class);
                startActivity(iDangKyDoanhNghiep);
            }
        });

        btnCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCaiDat = new Intent(ActivityPersonal.this, ActivityOption.class);
                startActivity(iCaiDat);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idNguoiDung = 0;
                tenNd = null;
                loaiNd = null;
                avatar = null;
                txtTenNd.setText(tenNd);
                txtLoaiNd.setText(loaiNd);
                Cavatar.setImageBitmap(avatar);
                startActivity(new Intent(ActivityPersonal.this, ActivityPersonal.class));
            }
        });

        menuBotNarBar();
    }

    private void menuBotNarBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityPersonal.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityPersonal.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityPersonal.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        break;
                }
                return false;
            }
        });
    }
}
