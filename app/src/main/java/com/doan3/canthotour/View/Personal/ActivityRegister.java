package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityRegister extends AppCompatActivity {
    EditText etTaiKhoan, etMatKhau, etNhapLaiMk, etQuocGia, etNgonNgu;
    Button btnDangKy;
    int ma;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etTaiKhoan = findViewById(R.id.etTaiKhoan);
        etMatKhau = findViewById(R.id.etMatKhau);
        etNhapLaiMk = findViewById(R.id.etNhapLaiMk);
        etQuocGia = findViewById(R.id.etQuocGia);
        etNgonNgu = findViewById(R.id.etNgonNgu);
        btnDangKy = findViewById(R.id.btnDangKy);

        ma = getIntent().getIntExtra("id", 0);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json;
                String stt = null, error = null;
                if (etTaiKhoan.getText().toString().length() < 5 || etTaiKhoan.getText().toString().length() > 25) {
                    etTaiKhoan.setError("Tài khoản có độ dài từ 5-25 ký tự");
                } else if (etMatKhau.getText().toString().length() < 6 || etMatKhau.getText().toString().length() > 26) {
                    etMatKhau.setError("Mật khẩu phải có độ dài từ 6-20 ký tự");
                } else if (!etMatKhau.getText().toString().equals(etNhapLaiMk.getText().toString())) {
                    etNhapLaiMk.setError("Mật khẩu vừa nhập không khớp");
                } else if (etTaiKhoan.getText().toString().contains(" ")) {
                    etTaiKhoan.setError("Tài khoản không được chứa khoảng trắng");
                }
                try {
                    json = new JSONObject(new ActivityLogin.Post().execute(Config.URL_HOST + Config.URL_REGISTER,
                            "{\"taikhoan\":\"" + etTaiKhoan.getText().toString() +
                                    "\",\"password\":\"" + etMatKhau.getText().toString() +
                                    "\",\"nd_quocgia\":\"" + etQuocGia.getText().toString() +
                                    "\",\"nd_ngonngu\":\"" + etNgonNgu.getText().toString() + "\"}").get());
                    stt = json.getString("status");
                    error = json.getString("error");
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
                if (stt != null && stt.equals("OK")) {
                    Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                    intent.putExtra("id", ma);
                    intent.putExtra("mess", "Đăng ký thành công");
                    startActivity(intent);
                } else {
                    if (error != null && error.equals("3")) {
                        etTaiKhoan.setError("Tên tài khoản đã tồn tại");
                    }
                }
            }
        });

        ActivityServiceInfo.menuBotNavBar(this,3);
    }

}
