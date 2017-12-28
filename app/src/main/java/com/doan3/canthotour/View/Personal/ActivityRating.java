package com.doan3.canthotour.View.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import static com.doan3.canthotour.View.Personal.ActivityLogin.idNguoiDung;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityRating extends AppCompatActivity {
    TextView btnGui;
    RatingBar rbDanhGia;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhgia);
        btnGui = findViewById(R.id.btnGui);
        rbDanhGia = findViewById(R.id.rbDanhGia);

        id = getIntent().getIntExtra("id", 1);
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    HttpRequestAdapter.httpPost(Config.URL_HOST + Config.URL_GET_ALL_RATES + id,
                            new JSONObject("{\"dv_iddichvu\":\"" + id + "\",\"nd_idnguoidung\":\"" + idNguoiDung +
                                    "\",\"dg_diem\":\"" + (int) rbDanhGia.getRating() + "\"}"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ActivityServiceInfo.menuBotNavBar(this);
    }
}
