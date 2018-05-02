package com.doan3.canthotour.View.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Personal.ActivityLogin;
import com.doan3.canthotour.View.Search.ActivityNearLocation;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Main.MainActivity.menuBotNavBar;
import static com.doan3.canthotour.View.Personal.ActivityPersonal.userId;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityServiceInfo extends AppCompatActivity implements View.OnClickListener {
    public static String[] imgDetail = null;
    Button btnShare, btnLike, btnNear, btnReview, btnShowReview;
    ImageButton btnBack;
    int id, serviceType;
    String idLike, idRating, longitude, latitude;
    JSONObject saveJson;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    public void onClick(View view) {

        Intent iDetail = new Intent(ActivityServiceInfo.this, FullImageActivity.class);

        switch (view.getId()) {
            case R.id.imgInfo1:
                try {
                    imgDetail = new HttpRequestAdapter.httpGet().execute(Config.URL_HOST + Config.URL_GET_LINK_DETAIL_1 + id).get()
                            .replaceAll("\"", "")
                            .split("\\+");
                    startActivity(iDetail);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.imgInfo2:
                try {
                    imgDetail = new HttpRequestAdapter.httpGet().execute(Config.URL_HOST + Config.URL_GET_LINK_DETAIL_2 + id).get()
                            .replaceAll("\"", "")
                            .split("\\+");
                    startActivity(iDetail);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_serviceinfo);

        btnShare = findViewById(R.id.btnShareService);
        btnLike = findViewById(R.id.btnLike);
        btnNear = findViewById(R.id.btnNearLocation);
        btnReview = findViewById(R.id.btnReview);
        btnShowReview = findViewById(R.id.btnOpenListReview);
        btnBack = findViewById(R.id.buttonBack);

        //Init FB share content
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        id = getIntent().getIntExtra("id", 1);
        String mess = getIntent().getStringExtra("mess");
        if (mess != null) {
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        }
        // region button luu
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId == 0) {
                    Intent intent = new Intent(ActivityServiceInfo.this, ActivityLogin.class);
                    // Nếu chưa đăng nhập mà bấm like sẽ mở form đăng nhập và truyền id dịch vụ qua form đăng nhập
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else {
                    File path = new File(Environment.getExternalStorageDirectory() + Config.FOLDER);
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    File file = new File(path, Config.FILE_LIKE);
                    JSONArray getJsonInFile;

                    if (btnLike.getText().equals(getResources().getString(R.string.text_Like))) {
                        try {
                            boolean isExists = true;
                            if (file.exists()) {
                                getJsonInFile = new JSONArray(JsonHelper.readJson(file));
                                for (int i = 0; i < getJsonInFile.length(); i++) {
                                    if (saveJson.toString().equals(getJsonInFile.getJSONObject(i).toString())) {
                                        isExists = false;
                                    }
                                }
                            }
                            if (isExists) {
                                JsonHelper.writeJson(file, saveJson);
                                Toast.makeText(ActivityServiceInfo.this, getResources().getString(R.string.text_Liked),
                                        Toast.LENGTH_SHORT).show();
                                btnLike.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_36dp, 0, 0 );
                                btnLike.setText(getResources().getString(R.string.text_UnLike));
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            boolean isExists = true;
                            if (file.exists()) {
                                JSONArray jsonArray = new JSONArray();
                                getJsonInFile = new JSONArray(JsonHelper.readJson(file));
                                for (int i = 0; i < getJsonInFile.length(); i++) {
                                    if (Integer.parseInt(getJsonInFile.getJSONObject(i).getString("id")) != (id)) {
                                        jsonArray.put(getJsonInFile.getJSONObject(i));
                                    }
                                }
                                if (jsonArray.length() != getJsonInFile.length()) {
                                    file.delete();
                                    if (jsonArray.length() > 0) {
                                        JsonHelper.writeJson(file, jsonArray);
                                    }
                                    isExists = false;
                                    Toast.makeText(ActivityServiceInfo.this, getResources().getString(R.string.text_UnLiked), Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (isExists) {
                                new HttpRequestAdapter.httpDelete().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE + "/" + idLike);
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        btnLike.setText(getResources().getString(R.string.text_Like));
                        btnLike.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_border_36dp, 0, 0 )
                        ;}
                }
            }
        });
        // endregion

        btnNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityServiceInfo.this, ActivityNearLocation.class);
                // truyền kinh độ vĩ độ loại dịch vụ qua cho form tìm kiếm lân cận
                intent.putExtra(Config.KEY_NEAR_LOCATION.get(0), longitude);
                intent.putExtra(Config.KEY_NEAR_LOCATION.get(1), latitude);
                intent.putExtra(Config.KEY_NEAR_LOCATION.get(2), serviceType);
                startActivity(intent);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(ActivityServiceInfo.this, getResources().getString(R.string.toast_ShareSuccessful), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ActivityServiceInfo.this, getResources().getString(R.string.toast_ShareCancel), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(ActivityServiceInfo.this, getResources().getString(R.string.toast_ShareError), Toast.LENGTH_SHORT).show();
                    }
                });

                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("This is use full link")
                        .setContentUrl(Uri.parse("https://vietnamtour.com/"))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId == 0) {
                    Intent intent = new Intent(ActivityServiceInfo.this, ActivityLogin.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else {
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    FragmentReview fragmentReview = new FragmentReview();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id", id);
//                    bundle.putInt("iddanhgia", idRating);
//                    fragmentTransaction.add(R.id.serviceInfoFragment, fragmentReview);
//                    fragmentTransaction.commit();

                    Intent intent = new Intent(ActivityServiceInfo.this, ActivityReview.class);
                    intent.putExtra("id", id);
                    intent.putExtra("iddanhgia", idRating);
                    startActivity(intent);
                }
            }
        });

        btnShowReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityServiceInfo.this, ActivityReviewList.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finishActivity(1);
            }
        });

        load(this, Config.URL_HOST + Config.URL_GET_SERVICE_INFO.get(0) + id + Config.URL_GET_SERVICE_INFO.get(1) + userId);

        menuBotNavBar(this, 0);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void load(final Activity activity, String url) {
        TextView txtServiceName = activity.findViewById(R.id.textViewServiceName);
        TextView txtServiceAbout = activity.findViewById(R.id.textViewServiceAbout);
        TextView txtPrice = activity.findViewById(R.id.textViewCost);
        TextView txtTime = activity.findViewById(R.id.textViewTime);
        TextView txtAddress = activity.findViewById(R.id.textViewServiceAddress);
        TextView txtPhoneNumber = activity.findViewById(R.id.textViewServicePhone);
        TextView txtWebsite = activity.findViewById(R.id.textViewWebsite);
        ImageView imgThumbInfo1 = activity.findViewById(R.id.imgInfo1);
        ImageView imgThumbInfo2 = activity.findViewById(R.id.imgInfo2);
        ImageView imgBanner = activity.findViewById(R.id.imgBanner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        TextView fbEvent = findViewById(R.id.fb_event);
        LinearLayout info = findViewById(R.id.info);
        TextView txtMark = findViewById(R.id.textViewRatingMark);
        TextView textViewLikeCount = findViewById(R.id.textViewLikeCount);
        RatingBar rbStar = findViewById(R.id.ratingBarStars);

        imgThumbInfo1.setOnClickListener(this);
        imgThumbInfo2.setOnClickListener(this);

        final ServiceInfo serviceInfo = new ModelService().getServiceInfo(url);

        idLike = serviceInfo.getIdLike();
        idRating = serviceInfo.getIdRating();
        longitude = serviceInfo.getLongitude();
        latitude = serviceInfo.getLatitude();

        fbEvent.setSelected(true);

        // region get tên và set màu cho từng dịch vụ
        if (serviceInfo.getEatName() != null) {
            txtServiceName.setText(serviceInfo.getEatName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEat));
            info.setBackgroundColor(getResources().getColor(R.color.tbEat));
            serviceType = 1;
            toolbarTitle.setText(getResources().getString(R.string.title_RestaurantDetails));
        } else if (serviceInfo.getHotelName() != null) {
            txtServiceName.setText(serviceInfo.getHotelName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            info.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            serviceType = 2;
            toolbarTitle.setText(getResources().getString(R.string.title_HotelDetails));
        } else if (serviceInfo.getPlaceName() != null) {
            txtServiceName.setText(serviceInfo.getPlaceName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            info.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            serviceType = 4;
            toolbarTitle.setText(getResources().getString(R.string.title_PlaceDetails));
        } else if (serviceInfo.getVehicleName() != null) {
            txtServiceName.setText(serviceInfo.getVehicleName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            info.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            serviceType = 3;
            toolbarTitle.setText(getResources().getString(R.string.title_TransportDetails));
        } else {
            txtServiceName.setText(serviceInfo.getEntertainName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            info.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            serviceType = 5;
            toolbarTitle.setText(getResources().getString(R.string.title_EntertainmentDetails));
        }
        // endregion

        // nếu không có sự kiện thì ẩn thẻ sự kiện
        if (serviceInfo.getEventType().equals(Config.NULL)) {
            fbEvent.setVisibility(TextView.GONE);
        } else {
            fbEvent.setVisibility(TextView.VISIBLE);
            fbEvent.setText(serviceInfo.getEventType());
        }

        // nếu đã thích thì đổi text thành unlike và đổi icon
        if (serviceInfo.getIsLike()) {
            btnLike.setText(getResources().getString(R.string.text_UnLike));
            btnLike.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_36dp, 0, 0 );
        } else {
            btnLike.setText(getResources().getString(R.string.text_Like));
            btnLike.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_border_36dp, 0, 0 );
        }

        // nếu đã đánh giá thì đổi text thành reviewed
        if (serviceInfo.getIsRating()) {
            btnReview.setText(getResources().getString(R.string.text_Reviewed));
        } else {
            btnReview.setText(getResources().getString(R.string.text_Review));
        }

        // set text mô tả dịch vụ
        txtServiceAbout.setText(serviceInfo.getServiceAbout());

        // nếu giá thấp nhất và giá cao nhất = 0 thì settext updating
        if (serviceInfo.getLowestPrice().equals("0") && serviceInfo.getHighestPrice().equals("0")) {
            txtPrice.setText(getResources().getString(R.string.text_Updating));
        } else {
            txtPrice.setText(getResources().getString(R.string.text_From) + " " + serviceInfo.getLowestPrice() + " " + getResources().getString(R.string.text_To) + " " + serviceInfo.getHighestPrice());
        }

        // nếu giờ mở cửa và giờ đóng cửa = đang cập nhật thì settext updating
        if (serviceInfo.getLowestPrice().equals("Đang cập nhật") && serviceInfo.getHighestPrice().equals("Đang cập nhật")) {
            txtPrice.setText(getResources().getString(R.string.text_Updating));
        } else {
            txtPrice.setText(getResources().getString(R.string.text_From) + " " + serviceInfo.getTimeOpen()
                    + " " + getResources().getString(R.string.text_To) + " " + serviceInfo.getTimeClose());
        }
        // set số lượt like
        textViewLikeCount.setText(serviceInfo.getCountLike()+"");
        // set địa chỉ
        txtAddress.setText(serviceInfo.getAddress());
        // set số điện thoại
        txtPhoneNumber.setText(serviceInfo.getPhoneNumber());
        // set website
        txtWebsite.setText(serviceInfo.getWebsite());
        // set hình
        imgBanner.setImageBitmap(serviceInfo.getBanner());
        imgThumbInfo1.setImageBitmap(serviceInfo.getThumbInfo1());
        imgThumbInfo2.setImageBitmap(serviceInfo.getThumbInfo2());
        // set số sao
        txtMark.setText(String.format("%.1f", serviceInfo.getReviewMark()));
        // set số ngôi sao
        rbStar.setRating(serviceInfo.getStars());

        // json yêu thích lưu vào thẻ nhớ
        try {
            saveJson = new JSONObject("{" + Config.POST_KEY_JSON_LIKE_SERVICE.get(0) + ":\"" + serviceInfo.getId() + "\"," +
                    Config.POST_KEY_JSON_LIKE_SERVICE.get(1) + ":\"" + serviceInfo.getHotelName() + "\"," +
                    Config.POST_KEY_JSON_LIKE_SERVICE.get(2) + ":\"" + serviceInfo.getEntertainName() + "\"," +
                    Config.POST_KEY_JSON_LIKE_SERVICE.get(3) + ":\"" + serviceInfo.getVehicleName() + "\"," +
                    Config.POST_KEY_JSON_LIKE_SERVICE.get(4) + ":\"" + serviceInfo.getPlaceName() + "\"," +
                    Config.POST_KEY_JSON_LIKE_SERVICE.get(5) + ":\"" + serviceInfo.getEatName() + "\"," +
                    Config.POST_KEY_JSON_LIKE_SERVICE.get(6) + ":\"" + serviceInfo.getIdImage() + "\"," +
                    Config.POST_KEY_JSON_LIKE_SERVICE.get(7) + ":\"" + serviceInfo.getImageName() + "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.zzacn.sharinglink",
                    PackageManager.GET_SIGNATURES);
            ;
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) { //đếm số lượng trong ngăn xếp còn bao nhiêu số lượng fragment
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btnLike.setOnClickListener(null);
        btnNear.setOnClickListener(null);
        btnShare.setOnClickListener(null);
        btnReview.setOnClickListener(null);
        btnShowReview.setOnClickListener(null);
    }
}