package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doan3.canthotour.R;
import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityTripSchedule extends AppCompatActivity {

    FloatingActionButton fabAddTripSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_schedule);

        fabAddTripSchedule = findViewById(R.id.fabAddTripSchedule);

        fabAddTripSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ActivityTripSchedule.this, ActivityAddTripSchedule.class), 1);
            }
        });

        menuBotNavBar(this, 3);
    }
}
