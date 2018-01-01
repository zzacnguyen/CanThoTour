package com.doan3.canthotour.View.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class ActivityReview extends AppCompatActivity {
    Button btnGui, btnHuy;
    TextView txtTieuDe, txtDanhGia;
    RatingBar rbDanhGia;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhgia);
        btnGui = findViewById(R.id.btnGui);
        btnHuy = findViewById(R.id.btnHuy);
        rbDanhGia = findViewById(R.id.rbDanhGia);
        txtTieuDe = findViewById(R.id.txtTieuDe);
        txtDanhGia = findViewById(R.id.txtNhanXet);

        id = getIntent().getIntExtra("id", 1);
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTieuDe.getText().toString().equals("")
                        && txtDanhGia.getText().toString().equals("")
                        && (int) rbDanhGia.getRating() == 0) {
                    Toast.makeText(ActivityReview.this, "Chưa đánh giá không thể gửi", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        HttpRequestAdapter.httpPost(Config.URL_HOST + Config.URL_GET_ALL_REVIEWS + id,
                                new JSONObject("{\"dv_iddichvu\":\"" + id + "\",\"nd_idnguoidung\":\"" + idNguoiDung +
                                        "\",\"dg_diem\":\"" + (int) rbDanhGia.getRating() +
                                        "\",\"dg_tieude\":\"" + txtTieuDe.getText() +
                                        "\",\"dg_noidung\":\"" + txtDanhGia.getText() +
                                        "\"}"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finish();
                    finishActivity(1);
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finishActivity(1);
            }
        });
        ActivityServiceInfo.menuBotNavBar(this);
    }
}
