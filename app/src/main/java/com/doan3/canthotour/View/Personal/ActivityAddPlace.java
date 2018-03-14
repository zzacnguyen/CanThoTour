package com.doan3.canthotour.View.Personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Personal.ActivityAddService.bitmapArrayList;
import static com.doan3.canthotour.View.Personal.ActivityAddService.jsonServiceToString;
import static com.doan3.canthotour.View.Personal.ActivityLogin.userId;


public class ActivityAddPlace extends AppCompatActivity {

    private final int REQUEST_CODE_PLACEPICKER = 1;
    TextView txtLat, txtLong, btnSend, btnCancel;
    EditText etAddress, etPlaceName, etPlacePhone, etPlaceAbout;
    Button btnPlacePicker;
    LinearLayout linearPlace, linearEat, linearHotel, linearEntertaiment, linearVehicle;
    String idPlace, idService;
    MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

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
        btnCancel = findViewById(R.id.btnCancelLocation);
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
                try {
                    idPlace = new ActivityLogin.Post().execute(Config.URL_HOST + Config.URL_POST_PLACE,
                            "{" + Config.JSON_ADD_PLACE.get(0) + ":\"" + etPlaceName.getText().toString() + "\"," +
                                    Config.JSON_ADD_PLACE.get(1) + ":\"" + etPlaceAbout.getText().toString() + "\"," +
                                    Config.JSON_ADD_PLACE.get(2) + ":\"" + etAddress.getText().toString() + "\"," +
                                    Config.JSON_ADD_PLACE.get(3) + ":\"" + etPlacePhone.getText().toString() + "\"," +
                                    Config.JSON_ADD_PLACE.get(4) + ":\"" + txtLat.getText().toString() + "\"," +
                                    Config.JSON_ADD_PLACE.get(5) + ":\"" + txtLong.getText().toString() + "\"," +
                                    Config.JSON_ADD_PLACE.get(6) + ":\"" + "1" + "\"" + "}").get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                try {
                    String name;
                    if (jsonServiceToString.get(6).equals("1")) {
                        name = Config.JSON_ADD_SERVICE_EAT.get(0) + ":\"" + jsonServiceToString.get(7) + "\"";
                    } else if (jsonServiceToString.get(6).equals("2")) {
                        name = Config.JSON_ADD_SERVICE_HOTEL.get(0) + ":\"" + jsonServiceToString.get(7) + "\"," +
                                Config.JSON_ADD_SERVICE_HOTEL.get(1) + ":\"" + jsonServiceToString.get(8) + "\"," +
                                Config.JSON_ADD_SERVICE_HOTEL.get(2) + ":\"" + jsonServiceToString.get(9) + "\"";
                    } else if (jsonServiceToString.get(6).equals("3")) {
                        name = Config.JSON_ADD_SERVICE_TRANSPORT.get(0) + ":\"" + jsonServiceToString.get(7) + "\"";
                    } else if (jsonServiceToString.get(6).equals("4")) {
                        name = Config.JSON_ADD_SERVICE_SIGHTSEEING.get(0) + ":\"" + jsonServiceToString.get(7) + "\"";
                    } else {
                        name = Config.JSON_ADD_SERVICE_ENTERTAINMENTS.get(0) + ":\"" + jsonServiceToString.get(7) + "\"";
                    }
                    idService = new ActivityLogin.Post().execute(Config.URL_HOST + Config.URL_GET_ALL_SERVICES,
                            "{" + Config.JSON_ADD_SERVICE.get(0) + ":\"" + jsonServiceToString.get(0) + "\"," +
                                    Config.JSON_ADD_SERVICE.get(1) + ":\"" + jsonServiceToString.get(1) + "\"," +
                                    Config.JSON_ADD_SERVICE.get(2) + ":\"" + jsonServiceToString.get(2) + "\"," +
                                    Config.JSON_ADD_SERVICE.get(3) + ":\"" + jsonServiceToString.get(3) + "\"," +
                                    Config.JSON_ADD_SERVICE.get(4) + ":\"" + jsonServiceToString.get(4) + "\"," +
                                    Config.JSON_ADD_SERVICE.get(5) + ":\"" + jsonServiceToString.get(5) + "\"," +
                                    Config.JSON_ADD_SERVICE.get(6) + ":\"" + jsonServiceToString.get(6) + "\"," +
                                    Config.JSON_ADD_SERVICE.get(7) + ":\"" +
                                    idPlace.replaceAll("\"", "").split(":")[1] + "\"," + name + "}").get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream ban = new ByteArrayOutputStream();
                bitmapArrayList.get(0).compress(Bitmap.CompressFormat.JPEG, 100, ban);
                ContentBody contentBanner = new ByteArrayBody(ban.toByteArray(), "a.jpg");

                ByteArrayOutputStream de1 = new ByteArrayOutputStream();
                bitmapArrayList.get(1).compress(Bitmap.CompressFormat.JPEG, 100, de1);
                ContentBody contentDetails1 = new ByteArrayBody(de1.toByteArray(), "b.jpg");

                ByteArrayOutputStream de2 = new ByteArrayOutputStream();
                bitmapArrayList.get(2).compress(Bitmap.CompressFormat.JPEG, 100, de2);
                ContentBody contentDetails2 = new ByteArrayBody(de2.toByteArray(), "c.jpg");

                reqEntity.addPart("banner", contentBanner);
                reqEntity.addPart("details1", contentDetails1);
                reqEntity.addPart("details2", contentDetails2);
                try {
                    String response = new PostImage().execute(Config.URL_HOST + Config.URL_POST_IMAGE
                            + idService.replaceAll("\"", "").split(":")[1]).get();
                    System.out.println(response);
                    if (response.equals("\"status:200\"")) {
                        Toast.makeText(ActivityAddPlace.this, "Thành công", Toast.LENGTH_SHORT).show();
                        bitmapArrayList.clear();
                        finish();
                        finishActivity(1);
                    } else {
                        Toast.makeText(ActivityAddPlace.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finishActivity(1);
            }
        });
        ActivityServiceInfo.menuBotNavBar(this, 3);
    }

    private void openActivityAddService(int i) {
        Intent intent = new Intent(ActivityAddPlace.this, ActivityAddService.class);
        intent.putExtra("type", i);
        startActivityForResult(intent, 2);
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
        String placeName = placeSelected.getName().toString();

        txtLat.setText(String.valueOf(latitude).substring(0, 9));
        txtLong.setText(String.valueOf(longitude).substring(0, 10));
        etAddress.setText(placeSelected.getAddress().toString());
        if (!placeName.contains("\'")) {
            etPlaceName.setText(placeSelected.getName().toString());
        }
    }

    private class PostImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpPostImage(strings[0], reqEntity);
        }
    }
}
