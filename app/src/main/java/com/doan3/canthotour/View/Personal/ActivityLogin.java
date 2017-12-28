package com.doan3.canthotour.View.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.doan3.canthotour.R;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityLogin extends AppCompatActivity{
    public static int idNguoiDung = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
    }
}
