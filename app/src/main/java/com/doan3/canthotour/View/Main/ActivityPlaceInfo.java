package com.doan3.canthotour.View.Main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doan3.canthotour.R;

public class ActivityPlaceInfo extends AppCompatActivity{
    
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdiadiem);
        
        btnLuuDiaDiem = (Button) findViewById(R.id.btnLuuDiaDiem);
        btnLanCan = (Button) findViewById(R.id.btnDiaDiemLanCan);
        btnChiaSe = (Button) findViewById(R.id.btnChiaSe);
        
        btnLuuDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityPlaceInfo.this, "Đã lưu", Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}
