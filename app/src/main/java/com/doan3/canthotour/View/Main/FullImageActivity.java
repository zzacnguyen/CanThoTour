package com.doan3.canthotour.View.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.doan3.canthotour.R;

public class FullImageActivity extends AppCompatActivity {

    ImageView imgFullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        imgFullView = findViewById(R.id.imageFullView);
    }


}
