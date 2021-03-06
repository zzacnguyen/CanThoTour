package com.doan3.canthotour.View.Main;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;

import static com.doan3.canthotour.Model.ModelService.setImage;
import static com.doan3.canthotour.View.Main.ActivityServiceInfo.imgDetail;

public class FullImageActivity extends AppCompatActivity {

    ImageView imgFullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        imgFullView = findViewById(R.id.imageFullView);

        imgFullView.setImageBitmap(setImage(Config.URL_HOST + imgDetail[0], imgDetail[1], imgDetail[2]));
    }


}
