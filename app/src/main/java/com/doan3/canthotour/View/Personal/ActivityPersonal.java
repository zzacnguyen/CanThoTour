package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Model.SessionManager;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Search.ActivityAdvancedSearch;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityPersonal extends AppCompatActivity {

    public static int userId;
    public static String userName, userType;
    public static Bitmap avatar;
    Button btnAddPlace, btnRegEnterprise, btnOption, btnLogin, btnLogout, btnTripSchedule, btnAddEvent;
    TextView txtUserName, txtUserType;
    CircleImageView Cavatar;
    LinearLayout addPlace, regEnterprise, Logout, Login, tripSchedule, addEvent;
    SessionManager sessionManager;
    int REQUEST_CODE_LOGIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        btnTripSchedule = findViewById(R.id.buttonTripSchedule);
        btnAddPlace = findViewById(R.id.buttonAddPlace);
        btnRegEnterprise = findViewById(R.id.buttonRegEnterprise);
        btnOption = findViewById(R.id.buttonOption);
        btnLogin = findViewById(R.id.buttonLogin);
        btnLogout = findViewById(R.id.buttonLogout);
        btnAddEvent = findViewById(R.id.buttonAddEvent);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserType = findViewById(R.id.txtUserType);
        Cavatar = findViewById(R.id.avatar);
        addPlace = findViewById(R.id.AddPlace);
        regEnterprise = findViewById(R.id.RegEnterprise);
        tripSchedule = findViewById(R.id.TripSchedule);
        Logout = findViewById(R.id.Logout);
        Login = findViewById(R.id.Login);
        addEvent = findViewById(R.id.AddEvent);

        if (userId == 0) {
            addPlace.setVisibility(View.GONE);
            tripSchedule.setVisibility(View.GONE);
            regEnterprise.setVisibility(View.GONE);
            Logout.setVisibility(View.GONE);
            addEvent.setVisibility(View.GONE);
        } else {
            Cavatar.setImageBitmap(avatar);
            txtUserName.setText(userName);
            switch (userType) {
                case "1": // cá nhân
                    txtUserType.setText(getResources().getString(R.string.text_Personal));
                    regEnterprise.setVisibility(View.VISIBLE);
                    break;
                case "2": // doanh nghiệp
                    txtUserType.setText(getResources().getString(R.string.text_Enterprise));
                    addPlace.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                    break;
                case "3": // hướng dẫn viên
                    txtUserType.setText(getResources().getString(R.string.text_TourGuide));
                    tripSchedule.setVisibility(View.VISIBLE);
                    break;
                case "4": // cộng tác viên
                    txtUserType.setText(getResources().getString(R.string.text_Partner));
                    addPlace.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                    regEnterprise.setVisibility(View.VISIBLE);
                    break;
                case "5": // mod
                    txtUserType.setText(getResources().getString(R.string.text_Moderator));
                    tripSchedule.setVisibility(View.VISIBLE);
                    addPlace.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                    break;
                case "6": // admin
                    txtUserType.setText(getResources().getString(R.string.text_Admin));
                    tripSchedule.setVisibility(View.VISIBLE);
                    addPlace.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                    break;
                default: // mặc định cá nhân
                    txtUserType.setText(getResources().getString(R.string.text_Personal));
                    regEnterprise.setVisibility(View.VISIBLE);
                    break;
            }

            Logout.setVisibility(View.VISIBLE);
            Login.setVisibility(View.GONE);
        }

        btnTripSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iTripSchedule = new Intent(ActivityPersonal.this, ActivityTripSchedule.class);
                startActivity(iTripSchedule);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ActivityPersonal.this, ActivityLogin.class), REQUEST_CODE_LOGIN);
            }
        });
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iThemDiaDiem = new Intent(ActivityPersonal.this, ActivityAddPlace.class);
                startActivity(iThemDiaDiem);
            }
        });

        btnRegEnterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDangKyDoanhNghiep = new Intent(ActivityPersonal.this, ActivityRegCoop.class);
                startActivity(iDangKyDoanhNghiep);
            }
        });

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCaiDat = new Intent(ActivityPersonal.this, ActivityOption.class);
                startActivity(iCaiDat);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager = new SessionManager(getApplicationContext());
                userId = 0;
                userName = null;
                userType = null;
                avatar = null;
                startActivity(getIntent());
                sessionManager.logoutUser();
                startActivity(new Intent(ActivityPersonal.this, ActivityPersonal.class));
            }
        });

        menuBotNavBar(this, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            if (data.hasExtra("mess")) {
                Toast.makeText(this, data.getStringExtra("mess"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btnAddPlace.setOnClickListener(null);
        btnLogin.setOnClickListener(null);
        btnLogout.setOnClickListener(null);
        btnOption.setOnClickListener(null);
        btnRegEnterprise.setOnClickListener(null);
        btnTripSchedule.setOnClickListener(null);

    }
}
