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
    EditText etUserName, etPassword, etConfirmPassword, etCountry, etLanguage;
    Button btnReg;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etPasswordConfirm);
        etCountry = findViewById(R.id.etCountry);
        etLanguage = findViewById(R.id.etLanguege);
        btnReg = findViewById(R.id.btnRegister);

        id = getIntent().getIntExtra("id", 0);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json;
                String stt = null, error = null;
                if (etUserName.getText().toString().length() < 5 || etUserName.getText().toString().length() > 25) {
                    etUserName.setError("Tài khoản có độ dài từ 5-25 ký tự");
                } else if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 26) {
                    etPassword.setError("Mật khẩu phải có độ dài từ 6-20 ký tự");
                } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    etConfirmPassword.setError("Mật khẩu vừa nhập không khớp");
                } else if (etUserName.getText().toString().contains(" ")) {
                    etUserName.setError("Tài khoản không được chứa khoảng trắng");
                }
                try {
                    json = new JSONObject(new ActivityLogin.Post().execute(Config.URL_HOST + Config.URL_REGISTER,
                            "{\"taikhoan\":\"" + etUserName.getText().toString() +
                                    "\",\"password\":\"" + etPassword.getText().toString() +
                                    "\",\"nd_quocgia\":\"" + etCountry.getText().toString() +
                                    "\",\"nd_ngonngu\":\"" + etLanguage.getText().toString() + "\"}").get());
                    stt = json.getString("status");
                    error = json.getString("error");
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
                if (stt != null && stt.equals("OK")) {
                    Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                    intent.putExtra("id", id);
                    intent.putExtra("mess", "Đăng ký thành công");
                    startActivity(intent);
                } else {
                    if (error != null && error.equals("3")) {
                        etUserName.setError("Tên tài khoản đã tồn tại");
                    }
                }
            }
        });

        ActivityServiceInfo.menuBotNavBar(this,3);
    }

}
