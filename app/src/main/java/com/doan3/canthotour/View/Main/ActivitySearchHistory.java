package com.doan3.canthotour.View.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doan3.canthotour.R;

import static com.doan3.canthotour.View.Main.ActivityServiceInfo.menuBotNavBar;

public class ActivitySearchHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        menuBotNavBar(this, 0);
    }
}
