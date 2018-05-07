package com.doan3.canthotour.View.Personal;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;


public class ActivityOption extends AppCompatActivity {
    LinearLayout btnNearRadius;
    TextView txtNearRadius;
    ImageView btnBack;
    String radius;
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        btnNearRadius = findViewById(R.id.btnNearLocation);
        txtNearRadius = findViewById(R.id.textViewNearLocation);
        btnBack = findViewById(R.id.btnBack);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.KEY_DISTANCE, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        txtNearRadius.setText(sharedPreferences.getString(Config.KEY_DISTANCE, Config.DEFAULT_DISTANCE + "m"));

        btnNearRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ActivityOption.this);
                dialog.setTitle(getResources().getString(R.string.text_SetDistance));
                dialog.setCancelable(false); //Khóa màn hình ngoài sau khi ấn vàodialog
                dialog.setContentView(R.layout.custom_radius);

                //Ánh xạ các palette trong dialog
                final EditText etDistance = dialog.findViewById(R.id.etRadius);
                Button btnAgree = dialog.findViewById(R.id.btnConfirmRadius);
                Button btnCancel = dialog.findViewById(R.id.btnCancelRadius);

                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        radius = etDistance.getText().toString().trim();
                        editor.putString(Config.KEY_DISTANCE, radius);
                        txtNearRadius.setText(radius + "m");

                        dialog.cancel();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menuBotNavBar(this, 3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btnNearRadius.setOnClickListener(null);
    }
}
