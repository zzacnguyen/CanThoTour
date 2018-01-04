package com.doan3.canthotour.View.Personal;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;


public class ActivityOption extends AppCompatActivity {
    LinearLayout btnKhoangCachLc;
    TextView txtKhoangCachLc;
    String khoangcach;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caidat);

        btnKhoangCachLc = findViewById(R.id.btnKhoangCachLC);
        txtKhoangCachLc = findViewById(R.id.textViewKhoanCachLc);

        btnKhoangCachLc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ActivityOption.this);
                dialog.setTitle("Đặt lại khoảng cách");
                dialog.setCancelable(false); //Khóa màn hình ngoài sau khi ấn vàodialog
                dialog.setContentView(R.layout.custom_khoangcach);

                //Ánh xạ các palette trong dialog
                final EditText etKhoangCach = dialog.findViewById(R.id.etKhoangCach);
                Button btnDongY = dialog.findViewById(R.id.btnDongYKC);
                Button btnHuy = dialog.findViewById(R.id.btnHuyKC);

                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        khoangcach = etKhoangCach.getText().toString().trim();

                        txtKhoangCachLc.setText(khoangcach);

                        dialog.cancel();
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        menuBotNarBar();
    }

    private void menuBotNarBar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityOption.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityOption.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityOption.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityOption.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }
}
