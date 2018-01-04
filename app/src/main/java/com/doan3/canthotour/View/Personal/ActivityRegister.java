package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityRegister extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

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
                        startActivity(new Intent(ActivityRegister.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityRegister.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityRegister.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityRegister.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }
}
