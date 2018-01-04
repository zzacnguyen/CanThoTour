package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityLogin extends AppCompatActivity {
    public static int idNguoiDung = 2;
    EditText etTaiKhoan, etMatKhau;
    Button btnDangKy, btnDangNhap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        etTaiKhoan = findViewById(R.id.etTaiKhoan);
        etMatKhau = findViewById(R.id.etMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String rs = HttpRequestAdapter.httpPost(Config.URL_HOST + Config.URL_LOGIN,
                            new JSONObject("{\"taikhoan\":\"" + etTaiKhoan.getText().toString() +
                                    "\",\"password\":\"" + etMatKhau.getText().toString() + "\"}"));
                    if (rs.startsWith("\"")) {
                        Toast.makeText(ActivityLogin.this, rs.replaceAll("\"", "").toString(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<String> user = JsonHelper.parseJson(new JSONObject(rs), Config.JSON_USER);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDangKy = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(iDangKy);
            }
        });

        ActivityServiceInfo.menuBotNavBar(this);
    }
}
