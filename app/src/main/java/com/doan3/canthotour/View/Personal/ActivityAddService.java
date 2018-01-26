package com.doan3.canthotour.View.Personal;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;

import java.util.ArrayList;
import java.util.Calendar;


public class ActivityAddService extends AppCompatActivity implements View.OnClickListener {

    //REQUEST Code
    final int RESULT_BANNER = 111;
    final int RESULT_INFO1 = 112;
    final int RESULT_INFO2 = 113;

    TextView etOpenTime, etCloseTime;
    ImageView imgBanner, imgInfo1, imgInfo2;
    private int mHour, mMinute;

    ArrayList<Uri> imgService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addservice);

        etOpenTime = findViewById(R.id.etOpenTime);
        etCloseTime = findViewById(R.id.etCloseTime);
        imgBanner = findViewById(R.id.imgPickBanner);
        imgInfo1 = findViewById(R.id.imgPickInfo1);
        imgInfo2 = findViewById(R.id.imgPickInfo2);


        imgService = new ArrayList<>();

        imgBanner.setOnClickListener(this);
        imgInfo1.setOnClickListener(this);
        imgInfo2.setOnClickListener(this);
        etOpenTime.setOnClickListener(this);
        etCloseTime.setOnClickListener(this);

        menuBotNarBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Lấy hình ảnh và đưa lên màn hình
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_BANNER:
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imgBanner.setImageURI(uri);
                    imgService.add(uri);
                }
                break;

            case RESULT_INFO1:
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imgInfo1.setImageURI(uri);
                    imgService.add(uri);
                }
                break;

            case RESULT_INFO2:
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imgInfo2.setImageURI(uri);
                    imgService.add(uri);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) { //Custom sự kiện click

        final Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        switch (view.getId()){

            case R.id.imgPickBanner:
                PickImageFromGallery(RESULT_BANNER);
                break;

            case R.id.imgPickInfo1:
                PickImageFromGallery(RESULT_INFO1);
                break;

            case R.id.imgPickInfo2:
                PickImageFromGallery(RESULT_INFO2);
                break;

            case R.id.etOpenTime: //Set sự kiện click cho textview

                break;

            case R.id.etCloseTime:

                break;
        }
    }

    private void PickImageFromGallery(int requestCode){ //Chọn 1 tấm hình từ thư viện
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn hình..."),requestCode);
    }

    private void menuBotNarBar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityAddService.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityAddService.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityAddService.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityAddService.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

}
