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

        // nhận id dịch vụ từ form đăng nhập
        id = getIntent().getIntExtra("id", 0);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json;
                String stt = null, error = null;
                if (etUserName.getText().toString().length() < 5 || etUserName.getText().toString().length() > 25) {
                    etUserName.setError(getResources().getString(R.string.text_YourUsernameMustBeBetween5And25));
                } else if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 26) {
                    etPassword.setError(getResources().getString(R.string.text_YourPasswordMustBeBetween6And20));
                } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    etConfirmPassword.setError(getResources().getString(R.string.text_ThisDoesNotMatchThePasswordEnteredAbove));
                } else if (etUserName.getText().toString().contains(" ")) {
                    etUserName.setError(getResources().getString(R.string.text_TheUsernameCannotContainSpaces));
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
                    // trả id dịch vụ lại cho form đăng nhập
                    intent.putExtra("id", id);
                    // trả thông báo về cho form đăng nhập
                    intent.putExtra("mess", getResources().getString(R.string.text_RegisterSuccess));
                    startActivity(intent);
                } else {
                    if (error != null && error.equals("3")) {
                        etUserName.setError(getResources().getString(R.string.text_ThatUsernameIsAlreadyInUse));
                    }
                }
            }
        });

        menuBotNavBar(this, 3);
    }

}
