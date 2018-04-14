package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpPost;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;
import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


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
                    JSONObject jsonPost = new JSONObject(
                            "{" + Config.POST_KEY_REGISTER.get(0) + ":\"" + etUserName.getText().toString() + "\"," +
                            Config.POST_KEY_REGISTER.get(1) + ":\"" + etPassword.getText().toString() +
//                                    "\"," +
//                                    Config.POST_KEY_REGISTER.get(2) + ":\"" + etCountry.getText().toString() + "\"," +
//                                    Config.POST_KEY_REGISTER.get(3) + ":\"" + etLanguage.getText().toString() +
                            "\"}");
                    json = new JSONObject(new httpPost(jsonPost).execute(Config.URL_HOST + Config.URL_REGISTER).get());
                    stt = json.getString(Config.GET_KEY_JSON_LOGIN.get(2));
                    error = json.getString(Config.GET_KEY_JSON_LOGIN.get(1));
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
                if (stt != null && stt.equals(Config.GET_KEY_JSON_LOGIN.get(4))) {
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

        menuBotNavBar(this, 3);
    }

}
