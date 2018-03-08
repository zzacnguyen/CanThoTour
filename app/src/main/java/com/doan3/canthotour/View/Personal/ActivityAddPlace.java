package com.doan3.canthotour.View.Personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;
import com.doan3.canthotour.View.Main.MainActivity;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Personal.ActivityLogin.userId;

/**
 * Created by zzacn on 12/7/2017.
 */

public class ActivityAddPlace extends AppCompatActivity {

    private final int REQUEST_CODE_PLACEPICKER = 1;
    TextView txtLat, txtLong, btnSend;
    EditText etAddress, etPlaceName, etPlacePhone, etPlaceAbout;
    Button btnPlacePicker;
    LinearLayout linearPlace, linearEat, linearHotel, linearEntertaiment, linearVehicle;
    String idPlace;
    boolean isPosted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocation);

        txtLat = findViewById(R.id.txtLatitude);
        txtLong = findViewById(R.id.txtLongitude);
        etAddress = findViewById(R.id.etAddress);
        etPlaceName = findViewById(R.id.etPlaceName);
        etPlacePhone = findViewById(R.id.etPlacePhone);
        etPlaceAbout = findViewById(R.id.etPlaceAbout);
        btnPlacePicker = findViewById(R.id.btnPlacePicker);
        btnSend = findViewById(R.id.btnSendLocation);
        linearPlace = findViewById(R.id.linearPlace);
        linearEat = findViewById(R.id.linearEat);
        linearHotel = findViewById(R.id.linearHotel);
        linearEntertaiment = findViewById(R.id.linearEntertainment);
        linearVehicle = findViewById(R.id.linearVehicle);

        linearPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAddService(4);
            }
        });

        linearEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAddService(1);
            }
        });

        linearHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAddService(2);
            }
        });

        linearEntertaiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAddService(5);
            }
        });

        linearVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAddService(3);
            }
        });

        btnPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlacePickerActivity();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ActivityServiceInfo.menuBotNavBar(this, 3);
    }

    private void openActivityAddService(int i) {
        Intent intent = new Intent(ActivityAddPlace.this, ActivityAddService.class);
        intent.putExtra("type", i);
        if (!isPosted){
            try {
                idPlace = new ActivityLogin.Post().execute(Config.URL_HOST + Config.URL_POST_PLACE,
                        "{" + Config.JSON_ADD_PLACE.get(0) + ":\"" + etPlaceName + "\"," +
                                Config.JSON_ADD_PLACE.get(1) + ":\"" + etPlaceAbout + "\"" +
                                Config.JSON_ADD_PLACE.get(2) + ":\"" + etAddress + "\"" +
                                Config.JSON_ADD_PLACE.get(3) + ":\"" + etPlacePhone + "\"" +
                                Config.JSON_ADD_PLACE.get(4) + ":\"" + txtLat + "\"" +
                                Config.JSON_ADD_PLACE.get(5) + ":\"" + txtLong + "\"" +
                                Config.JSON_ADD_PLACE.get(6) + ":\"" + userId + "\"" + "}").get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            isPosted = true;
        }
        intent.putExtra("idPlace", idPlace);
        startActivity(intent);
    }

    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);

        Double latitude = placeSelected.getLatLng().latitude;
        Double longitude = placeSelected.getLatLng().longitude;

        txtLat.setText(String.valueOf(latitude).substring(0, 9));
        txtLong.setText(String.valueOf(longitude).substring(0, 10));
        etAddress.setText(placeSelected.getAddress().toString());
        etPlaceName.setText(placeSelected.getName().toString());
    }
}
