package com.doan3.canthotour.View.Personal;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.doan3.canthotour.R;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;


public class ActivityAddService extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    public static ArrayList<String> jsonServiceToString = new ArrayList<>();
    //REQUEST Code
    final int RESULT_BANNER = 111,
            RESULT_INFO1 = 112,
            RESULT_INFO2 = 113,
            REQUEST_CAMERA_CAPTURE = 110;
    TextView txtOpenTime, txtCloseTime, btnDone, btnCancel;
    EditText etServiceName, etWebsite, etServicePhone, etServiceAbout, etLowestPrice, etHighestPrice, etNumberStar;
    ImageView imgBanner, imgInfo1, imgInfo2;
    ImageButton ibCamera;
    String mCurrentPhotoPath;
    private int mHour, mMinute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addservice);

        txtOpenTime = findViewById(R.id.txtOpenTime);
        txtCloseTime = findViewById(R.id.txtCloseTime);
        imgBanner = findViewById(R.id.imgPickBanner);
        imgInfo1 = findViewById(R.id.imgPickInfo1);
        imgInfo2 = findViewById(R.id.imgPickInfo2);
        ibCamera = findViewById(R.id.ibCamera);
        btnDone = findViewById(R.id.btnConfirmService);
        btnCancel = findViewById(R.id.btnCancelService);
        etServiceName = findViewById(R.id.etServiceName);
        etWebsite = findViewById(R.id.etWebsite);
        etServicePhone = findViewById(R.id.etServicePhone);
        etServiceAbout = findViewById(R.id.etServiceAbout);
        etLowestPrice = findViewById(R.id.etLowestPrice);
        etHighestPrice = findViewById(R.id.etHighestPrice);
        etNumberStar = findViewById(R.id.etNumberOfStar);

        imgBanner.setOnClickListener(this);
        imgInfo1.setOnClickListener(this);
        imgInfo2.setOnClickListener(this);
        txtOpenTime.setOnClickListener(this);
        txtCloseTime.setOnClickListener(this);

        ibCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        final int type = getIntent().getIntExtra("type", 0);
        if (type != 2) {
            etNumberStar.setVisibility(View.GONE);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date timeOpen = null, timeClose = null;
                try {
                    timeOpen = sdf.parse(txtOpenTime.getText().toString());
                    timeClose = sdf.parse(txtCloseTime.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (Integer.parseInt(etLowestPrice.getText().toString()) > Integer.parseInt(etHighestPrice.getText().toString())) {
                    etLowestPrice.setError(getResources().getString(R.string.text_LowestPriceIsNotHigherThanHighestPrice));
                } else if (timeClose != null && timeClose.before(timeOpen)) {
                    txtCloseTime.setError(getResources().getString(R.string.text_TimeOpenMustBeEarlierThanTimeClose));
                } else if (etServiceName.getText().toString().equals("")) {
                    etServiceName.setError(getResources().getString(R.string.text_ServiceNameIsNotAllowedToBeEmpty));
                } else if (etWebsite.getText().toString().equals("")) {
                    etWebsite.setError(getResources().getString(R.string.text_WebsiteIsNotAllowedToBeEmpty));
                } else if (etNumberStar.getText().toString().equals("") && etNumberStar.getVisibility() != View.GONE) {
                    etNumberStar.setError(getResources().getString(R.string.text_NumberStarIsNotAllowedToBeEmpty));
                } else if (etServiceAbout.getText().toString().equals("")) {
                    etServiceAbout.setError(getResources().getString(R.string.text_DescriptionIsNotAllowedToBeEmpty));
                } else {
                    jsonServiceToString = new ArrayList<>();

                    // mô tả dịch vụ
                    jsonServiceToString.add(etServiceAbout.getText().toString());
                    // giờ mở cửa
                    jsonServiceToString.add(txtOpenTime.getText().toString());
                    // giờ đóng cửa
                    jsonServiceToString.add(txtCloseTime.getText().toString());
                    // giá cao nhất
                    jsonServiceToString.add(etHighestPrice.getText().toString());
                    // giá thấp nhát
                    jsonServiceToString.add(etLowestPrice.getText().toString());
                    // số điện thoại
                    jsonServiceToString.add(etServicePhone.getText().toString());
                    // loại hình
                    jsonServiceToString.add(type + "");
                    // website
                    jsonServiceToString.add(etWebsite.getText().toString());
                    // tên dịch vụ
                    jsonServiceToString.add(etServiceName.getText().toString());
                    // nếu ô nhập số sao không bị ẩn = dịch vụ khách sạn
                    if (etNumberStar.getVisibility() != View.GONE) {
                        // số sao khách sạn
                        jsonServiceToString.add(etNumberStar.getText().toString());
                    }
                    finish();
                    finishActivity(2);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finishActivity(2);
            }
        });
        menuBotNavBar(this, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Lấy hình ảnh và đưa lên màn hình
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic();
        }

        switch (requestCode) {
            case RESULT_BANNER:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgBanner.setImageBitmap(bitmap);
                        bitmapArrayList.add(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case RESULT_INFO1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgInfo1.setImageBitmap(bitmap);
                        bitmapArrayList.add(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case RESULT_INFO2:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgInfo2.setImageBitmap(bitmap);
                        bitmapArrayList.add(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) { //Custom sự kiện click

        final Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        switch (view.getId()) { //Bắt sự kiện click dựa trên id của giao diện, ko phải id của biến

            case R.id.imgPickBanner:
                PickImageFromGallery(RESULT_BANNER);
                break;

            case R.id.imgPickInfo1:
                PickImageFromGallery(RESULT_INFO1);
                break;

            case R.id.imgPickInfo2:
                PickImageFromGallery(RESULT_INFO2);
                break;

            case R.id.txtOpenTime: //Set sự kiện click cho textview
                TimePickerDialog openTimePickerDialog = new TimePickerDialog(ActivityAddService.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtOpenTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, true);

                openTimePickerDialog.show();
                break;

            case R.id.txtCloseTime:
                TimePickerDialog closeTimePickerDialog = new TimePickerDialog(ActivityAddService.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtCloseTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, true);

                closeTimePickerDialog.show();
                break;
        }
    }

    private void PickImageFromGallery(int requestCode) { //Chọn 1 tấm hình từ thư viện
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình..."), requestCode);
    }

    private File createImageFile() throws IOException {
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                "example",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
