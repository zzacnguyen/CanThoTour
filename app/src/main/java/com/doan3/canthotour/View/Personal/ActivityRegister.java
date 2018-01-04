package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import java.util.concurrent.ExecutionException;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityRegister extends AppCompatActivity {
    EditText etTaiKhoan, etMatKhau, etNhapLaiMk, etQuocGia, etNgonNgu;
    Button btnDangKy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        etTaiKhoan = findViewById(R.id.etTaiKhoan);
        etMatKhau = findViewById(R.id.etMatKhau);
        etNhapLaiMk = findViewById(R.id.etNhapLaiMk);
        etQuocGia = findViewById(R.id.etQuocGia);
        etNgonNgu = findViewById(R.id.etNgonNgu);
        btnDangKy = findViewById(R.id.btnDangKy);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rs = null;
                try {
                    rs = new ActivityLogin.Post().execute(Config.URL_HOST + Config.URL_REGISTER,
                            "{\"taikhoan\":\"" + etTaiKhoan.getText().toString() +
                                    "\",\"password\":\"" + etMatKhau.getText().toString() +
                                    "\",\"nd_quocgia\":\"" + etQuocGia.getText().toString() +
                                    "\",\"nd_ngonngu\":\"" + etNgonNgu.getText().toString() + "\"}").get().toString();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (rs.equals("Đăng ký thành công")) {
                    Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                    intent.putExtra("mess", rs);
                    startActivity(intent);
                } else {
                    Toast.makeText(ActivityRegister.this, rs, Toast.LENGTH_SHORT).show();
                }

            }
        });

        ActivityServiceInfo.menuBotNavBar(this);
    }

}
