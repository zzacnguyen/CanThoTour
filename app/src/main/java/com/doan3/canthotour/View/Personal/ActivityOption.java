package com.doan3.canthotour.View.Personal;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.R;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


public class ActivityOption extends AppCompatActivity {
    LinearLayout btnNearRadius;
    TextView txtNearRadius;
    String radius;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        btnNearRadius = findViewById(R.id.btnNearLocation);
        txtNearRadius = findViewById(R.id.textViewNearLocation);

        File path = new File(Environment.getExternalStorageDirectory() + "/canthotour");
        if (!path.exists()) {
            path.mkdirs();
        }
        final File file = new File(path, "khoangcach.json");
        try {
            txtNearRadius.setText(new JSONArray(JsonHelper.readJson(file)).getJSONObject(0).
                    getString("khoangcach")+"m");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnNearRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ActivityOption.this);
                dialog.setTitle(getResources().getString(R.string.text_SetDistance));
                dialog.setCancelable(false); //Khóa màn hình ngoài sau khi ấn vàodialog
                dialog.setContentView(R.layout.custom_radius);

                //Ánh xạ các palette trong dialog
                final EditText etKhoangCach = dialog.findViewById(R.id.etRadius);
                Button btnDongY = dialog.findViewById(R.id.btnConfirmRadius);
                Button btnHuy = dialog.findViewById(R.id.btnCancelRadius);

                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        radius = etKhoangCach.getText().toString().trim();
                        try {
                            file.delete();
                            JsonHelper.writeJson(file, new JSONObject("{\"khoangcach\":\"" + radius + "\"}"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        txtNearRadius.setText(radius + "m");

                        dialog.cancel();
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        menuBotNavBar(this,3);
    }
}
