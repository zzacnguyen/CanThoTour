package com.doan3.canthotour.View.Personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doan3.canthotour.R;
import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityAddTripSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtripschedule);

        menuBotNavBar(this, 3);
    }
}
