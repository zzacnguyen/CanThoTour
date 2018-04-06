package com.doan3.canthotour.View.Personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

public class ActivityAddTripSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtripschedule);

        ActivityServiceInfo.menuBotNavBar(this, 3);
    }
}
