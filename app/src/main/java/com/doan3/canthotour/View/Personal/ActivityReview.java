package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Personal.ActivityLogin.idNguoiDung;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityReview extends AppCompatActivity {
    Button btnGui, btnHuy;
    TextView txtTieuDe, txtDanhGia;
    RatingBar rbDanhGia;
    int id, idDanhGia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        btnGui = findViewById(R.id.btnGui);
        btnHuy = findViewById(R.id.btnHuy);
        rbDanhGia = findViewById(R.id.rbDanhGia);
        txtTieuDe = findViewById(R.id.txtTieuDe);
        txtDanhGia = findViewById(R.id.txtNhanXet);

        id = getIntent().getIntExtra("id", 1);
        idDanhGia = getIntent().getIntExtra("iddanhgia", 1);
        if (idDanhGia != 0) {
            try {
                String rs =
                        new ModelService.Load().execute(Config.URL_HOST + Config.URL_GET_ALL_REVIEWS + "/" + idDanhGia).get();
                ArrayList<String> arr = JsonHelper.parseJsonNoId(new JSONArray(rs), Config.JSON_RATE);
                rbDanhGia.setRating(Float.parseFloat(arr.get(0)));
                txtTieuDe.setText(arr.get(1));
                txtDanhGia.setText(arr.get(2));
            } catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
            }
        }
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTieuDe.getText().toString().equals("")
                        && txtDanhGia.getText().toString().equals("")
                        && (int) rbDanhGia.getRating() == 0) {
                    Toast.makeText(ActivityReview.this, "Chưa đánh giá không thể gửi", Toast.LENGTH_SHORT).show();
                } else {
                    if (idDanhGia == 0) {
                        new ActivityLogin.Post().execute(Config.URL_HOST + Config.URL_GET_ALL_REVIEWS,
                                "{\"dv_iddichvu\":\"" + id + "\",\"nd_idnguoidung\":\"" + idNguoiDung +
                                        "\",\"dg_diem\":\"" + (int) rbDanhGia.getRating() +
                                        "\",\"dg_tieude\":\"" + txtTieuDe.getText() +
                                        "\",\"dg_noidung\":\"" + txtDanhGia.getText() + "\"}");

                        Intent intent = new Intent(ActivityReview.this, ActivityServiceInfo.class);
                        intent.putExtra("id", id);
                        intent.putExtra("mess", "Đánh giá thành công");
                        startActivity(intent);
                    } else {
                        new Put().execute(Config.URL_HOST + Config.URL_GET_ALL_REVIEWS + "/" + idDanhGia,
                                "{\"dv_iddichvu\":\"" + id + "\",\"nd_idnguoidung\":\"" + idNguoiDung +
                                        "\",\"dg_diem\":\"" + (int) rbDanhGia.getRating() +
                                        "\",\"dg_tieude\":\"" + txtTieuDe.getText() +
                                        "\",\"dg_noidung\":\"" + txtDanhGia.getText() + "\"}");

                        Intent intent = new Intent(ActivityReview.this, ActivityServiceInfo.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
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
        ActivityServiceInfo.menuBotNavBar(this,0);
    }

    private class Put extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String rs = null;
            try {
                rs = HttpRequestAdapter.httpPut(strings[0],
                        new JSONObject(strings[1]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return rs;
        }
    }
}
