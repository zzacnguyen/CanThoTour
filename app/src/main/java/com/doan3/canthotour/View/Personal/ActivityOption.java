package com.doan3.canthotour.View.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.doan3.canthotour.R;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;


public class ActivityOption extends AppCompatActivity {
    ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menuBotNavBar(this, 3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
