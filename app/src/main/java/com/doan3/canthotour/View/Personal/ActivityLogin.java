package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityLogin extends AppCompatActivity {
    public static int idNguoiDung = 0;
    public static String tenNd, loaiNd;
    public static Bitmap avatar;
    EditText etTaiKhoan, etMatKhau;
    Button btnDangKy, btnDangNhap;
    int ma;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        etTaiKhoan = findViewById(R.id.etTaiKhoan);
        etMatKhau = findViewById(R.id.etMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);

        ma = getIntent().getIntExtra("id", 0);
        String mess = getIntent().getStringExtra("mess");
        if (mess != null) {
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        }

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String rs = new Post().execute(Config.URL_HOST + Config.URL_LOGIN,
                            "{\"taikhoan\":\"" + etTaiKhoan.getText().toString() +
                                    "\",\"password\":\"" + etMatKhau.getText().toString() + "\"}").get();
                    JSONObject json = new JSONObject(rs);
                    if (json.getString("status").toString().equals("ERROR")) {
                        Toast.makeText(ActivityLogin.this, "tài khoản hoặc mật khẩu không đúng",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<String> arrayUser =
                                JsonHelper.parseJson(new JSONObject(json.getString("result")), Config.JSON_USER);

                        idNguoiDung = Integer.parseInt(arrayUser.get(0));
                        tenNd = arrayUser.get(1);
                        loaiNd = Integer.parseInt(arrayUser.get(2)) == 1 ? "cá nhân" : "doanh nghiệp";
                        try {
                            avatar = new ModelService.GetImage().execute(arrayUser.get(3)).get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        if (ma == 0) {
                            startActivity(new Intent(ActivityLogin.this, ActivityPersonal.class));
                        } else {
                            Intent intent = new Intent(ActivityLogin.this, ActivityServiceInfo.class);
                            intent.putExtra("id", ma);
                            startActivity(intent);
                        }
                    }
                } catch (JSONException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, ActivityRegister.class));
            }
        });

        ActivityServiceInfo.menuBotNavBar(this);
    }

    public static class Post extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String rs = null;
            try {
                rs = HttpRequestAdapter.httpPost(strings[0],
                        new JSONObject(strings[1]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return rs;
        }
    }
}
