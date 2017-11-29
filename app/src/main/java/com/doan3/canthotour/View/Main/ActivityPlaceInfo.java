package com.doan3.canthotour.View.Main;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.PlaceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.Content.ActivityPlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityPlaceInfo extends AppCompatActivity{
    
    Button btnLuuDiaDiem, btnLanCan, btnChiaSe;
    TextView txtTenDD, txtDiaChi, txtSDT, txtGioiThieu;
    String masp;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdiadiem);
        
        btnLuuDiaDiem = (Button) findViewById(R.id.btnLuuDiaDiem);
        btnLanCan = (Button) findViewById(R.id.btnDiaDiemLanCan);
        btnChiaSe = (Button) findViewById(R.id.btnChiaSe);
        txtTenDD = (TextView)findViewById(R.id.textViewTenDD);
        txtDiaChi = findViewById(R.id.textViewDiaChi);
        txtSDT = findViewById(R.id.textViewSDT);
        txtGioiThieu = findViewById(R.id.textViewGioiThieu);


        masp = getIntent().getStringExtra("masp");
        new place().execute(Config.URL_HOST+Config.URL_GET_ALL_PLACES + "/" + masp);
        btnLuuDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityPlaceInfo.this, "Đã lưu", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    private class place extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // parse json ra arraylist
                ArrayList<String> arrayList = JsonHelper.parseJson(new JSONObject(s), Config.JSON_PLACE);

                txtTenDD.setText(arrayList.get(1));
                txtDiaChi.setText(arrayList.get(3));
                txtSDT.setText(arrayList.get(4));
                txtGioiThieu.setText(arrayList.get(2));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
