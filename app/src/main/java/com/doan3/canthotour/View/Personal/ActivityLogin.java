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
    public static int userId = 0;
    public static String userName, userType;
    public static Bitmap avatar;
    EditText etUserId, etPassword;
    Button btnReg, btnLogin;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserId = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegister);

        id = getIntent().getIntExtra("id", 0);
        String mess = getIntent().getStringExtra("mess");
        if (mess != null) {
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etUserId.getText().toString().equals("")) {
                    etUserId.setError("Tài khoản không được để trống");
                } else if (etPassword.getText().toString().equals("")) {
                    etUserId.setError("Mật khẩu không được để trống");
                } else {
                    try {
                        String rs = new Post().execute(Config.URL_HOST + Config.URL_LOGIN,
                                "{" + Config.POST_KEY_LOGIN_REGISTER.get(0) + ":\"" + etUserId.getText().toString() + "\"," +
                                        Config.POST_KEY_LOGIN_REGISTER.get(0) + ":\"" + etPassword.getText().toString() + "\"}").get();
                        JSONObject json = new JSONObject(rs);
                        if (json.getString("status").toString().equals("ERROR")) {
                            Toast.makeText(ActivityLogin.this, "tài khoản hoặc mật khẩu không đúng",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<String> arrayUser =
                                    JsonHelper.parseJson(new JSONObject(json.getString("result")), Config.GET_KEY_JSON_USER);

                            userId = Integer.parseInt(arrayUser.get(0));
                            userName = arrayUser.get(1);
                            userType = Integer.parseInt(arrayUser.get(2)) == 1 ?
                                    getResources().getString(R.string.text_Personal) : getResources().getString(R.string.text_Enterprise);
                            try {
                                avatar = new ModelService.GetImage().execute(arrayUser.get(3)).get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            if (id == 0) {
                                startActivity(new Intent(ActivityLogin.this, ActivityPersonal.class));
                            } else {
                                Intent intent = new Intent(ActivityLogin.this, ActivityServiceInfo.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        }
                    } catch (JSONException | ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        ActivityServiceInfo.menuBotNavBar(this, 3);
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
