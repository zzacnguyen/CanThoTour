package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doan3.canthotour.Model.SessionManager;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Search.ActivityAdvancedSearch;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;

public class ActivityPersonal extends AppCompatActivity {

    public static int userId;
    public static String userName, userType;
    public static Bitmap avatar;
    Button btnAddPlace, btnAddService, btnRegEnterprise, btnOption, btnLogin, btnAdvancedSearch, btnLogout, btnTripSchedule;
    TextView txtUserName, txtUserType;
    CircleImageView Cavatar;
    LinearLayout addPlace, addService, regEnterprise, Logout, Login, tripSchedule;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        btnTripSchedule = findViewById(R.id.buttonTripSchedule);
        btnAdvancedSearch = findViewById(R.id.buttonAdvancedSearch);
        btnAddPlace = findViewById(R.id.buttonAddPlace);
        btnAddService = findViewById(R.id.buttonAddService);
        btnRegEnterprise = findViewById(R.id.buttonRegEnterprise);
        btnOption = findViewById(R.id.buttonOption);
        btnLogin = findViewById(R.id.buttonLogin);
        btnLogout = findViewById(R.id.buttonLogout);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserType = findViewById(R.id.txtUserType);
        Cavatar = findViewById(R.id.avatar);
        addPlace = findViewById(R.id.AddPlace);
        addService = findViewById(R.id.AddService);
        regEnterprise = findViewById(R.id.RegEnterprise);
        tripSchedule = findViewById(R.id.TripSchedule);
        Logout = findViewById(R.id.Logout);
        Login = findViewById(R.id.Login);

        txtUserName.setText(userName);
        txtUserType.setText(userType);
        Cavatar.setImageBitmap(avatar);

        if (userId == 0) {
            addPlace.setVisibility(View.GONE);
            addService.setVisibility(View.GONE);
            tripSchedule.setVisibility(View.GONE);
            regEnterprise.setVisibility(View.GONE);
            Logout.setVisibility(View.GONE);
        } else {
            addPlace.setVisibility(View.VISIBLE);
            addService.setVisibility(View.VISIBLE);
            regEnterprise.setVisibility(View.VISIBLE);
            tripSchedule.setVisibility(View.VISIBLE);
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
        btnAdvancedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ActivityPersonal.this, ActivityAdvancedSearch.class),
                        1);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ActivityPersonal.this, ActivityLogin.class), 1);
            }
        });
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iThemDiaDiem = new Intent(ActivityPersonal.this, ActivityAddPlace.class);
                startActivity(iThemDiaDiem);
            }
        });

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iThemDichVu = new Intent(ActivityPersonal.this, ActivityAddService.class);
                startActivity(iThemDichVu);
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
                txtUserName.setText(userName);
                txtUserType.setText(userType);
                Cavatar.setImageBitmap(avatar);
                sessionManager.logoutUser();
                startActivity(new Intent(ActivityPersonal.this, ActivityPersonal.class));
            }
        });

        menuBotNavBar(this, 3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btnAddPlace.setOnClickListener(null);
        btnAddService.setOnClickListener(null);
        btnAdvancedSearch.setOnClickListener(null);
        btnLogin.setOnClickListener(null);
        btnLogout.setOnClickListener(null);
        btnOption.setOnClickListener(null);
        btnRegEnterprise.setOnClickListener(null);
        btnTripSchedule.setOnClickListener(null);

    }
}
