package com.doan3.canthotour.View.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.doan3.canthotour.R;
import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;


public class ActivityRegCoop extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerprise);

        menuBotNavBar(this,3);
    }

}
