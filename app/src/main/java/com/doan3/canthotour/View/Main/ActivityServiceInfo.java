package com.doan3.canthotour.View.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityLogin;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Personal.ActivityReview;
import com.doan3.canthotour.View.Personal.ActivityReviewList;
import com.doan3.canthotour.View.Search.ActivityNearLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.doan3.canthotour.View.Personal.ActivityLogin.userId;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityServiceInfo extends AppCompatActivity {
    Button btnShare, btnLike, btnNear, btnReview, btnShowReview;
    int id, serviceType, reviewId;
    String favoriteId, longitude, latitude;
    JSONObject saveJson;

    public static void menuBotNavBar(final Activity activity,int i) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(i);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        activity.startActivity(new Intent(activity, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        activity.startActivity(new Intent(activity, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        activity.startActivity(new Intent(activity, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfo);

        btnShare = findViewById(R.id.btnShareService);
        btnLike = findViewById(R.id.btnLike);
        btnNear = findViewById(R.id.btnNearLocation);
        btnReview = findViewById(R.id.btnReview);
        btnShowReview = findViewById(R.id.btnOpenListReview);

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
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else {
                    File path = new File(Environment.getExternalStorageDirectory() + "/canthotour");
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    File file = new File(path, "dsyeuthich.json");
                    JSONArray getJsonInFile;

                    if (btnLike.getText().equals("THÍCH")) {
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
                                Toast.makeText(ActivityServiceInfo.this, "Đã thích",
                                        Toast.LENGTH_SHORT).show();
                                btnLike.setText("BỎ THÍCH");
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
                                    Toast.makeText(ActivityServiceInfo.this, "Đã bỏ thích", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (isExists) {
                                new DeleteFavorite().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE + "/" + favoriteId);
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        btnLike.setText("THÍCH");
                    }
                }
            }
        });
        // endregion

        btnNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityServiceInfo.this, ActivityNearLocation.class);
                intent.putExtra("kinhdo", longitude);
                intent.putExtra("vido", latitude);
                intent.putExtra("loaihinh", serviceType);
                startActivity(intent);
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
                    Intent intent = new Intent(ActivityServiceInfo.this, ActivityReview.class);
                    intent.putExtra("id", id);
                    intent.putExtra("iddanhgia", reviewId);
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

        load(this, Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + id);

        menuBotNavBar(this,0);
    }

    public void load(final Activity activity, String url) {
        TextView txtServiceName = activity.findViewById(R.id.textViewServiceName);
        TextView txtServiceAbout = activity.findViewById(R.id.textViewServiceAbout);
        TextView txtPrice = activity.findViewById(R.id.textViewCost);
        TextView txtTimeOpen = activity.findViewById(R.id.textViewTimeOpen);
        TextView txtTimeClose = activity.findViewById(R.id.textViewTimeClose);
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
        RatingBar rbStar = findViewById(R.id.ratingBarStars);

        final ServiceInfo serviceInfo = new ModelService().getServiceInfo(url);

        favoriteId = serviceInfo.getIdYeuThich();
        reviewId = Integer.parseInt(serviceInfo.getReviewId());
        longitude = serviceInfo.getLongitude();
        latitude = serviceInfo.getLatitude();

        fbEvent.setSelected(true);

        // region get tên và set màu cho từng dịch vụ
        if (serviceInfo.getEatName() != null) {
            txtServiceName.setText(serviceInfo.getEatName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEat));
            info.setBackgroundColor(getResources().getColor(R.color.tbEat));
            serviceType = 1;
            toolbarTitle.setText("Chi tiết quán ăn");
            serviceInfo.setVehicleName("null");
            serviceInfo.setPlaceName("null");
            serviceInfo.setHotelName("null");
            serviceInfo.setEntertainName("null");
        } else if (serviceInfo.getHotelName() != null) {
            txtServiceName.setText(serviceInfo.getHotelName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            info.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            serviceType = 2;
            toolbarTitle.setText("Chi tiết khách sạn");
            serviceInfo.setVehicleName("null");
            serviceInfo.setPlaceName("null");
            serviceInfo.setEatName("null");
            serviceInfo.setEntertainName("null");
        } else if (serviceInfo.getPlaceName() != null) {
            txtServiceName.setText(serviceInfo.getPlaceName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            info.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            serviceType = 4;
            toolbarTitle.setText("Chi tiết điểm tham quan");
            serviceInfo.setVehicleName("null");
            serviceInfo.setEatName("null");
            serviceInfo.setHotelName("null");
            serviceInfo.setEntertainName("null");
        } else if (serviceInfo.getVehicleName() != null) {
            txtServiceName.setText(serviceInfo.getVehicleName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            info.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            serviceType = 3;
            toolbarTitle.setText("Chi tiết phương tiện");
            serviceInfo.setEatName("null");
            serviceInfo.setPlaceName("null");
            serviceInfo.setHotelName("null");
            serviceInfo.setEntertainName("null");
        } else {
            txtServiceName.setText(serviceInfo.getEntertainName());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            info.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            serviceType = 5;
            toolbarTitle.setText("Chi tiết điểm vui chơi");
            serviceInfo.setVehicleName("null");
            serviceInfo.setPlaceName("null");
            serviceInfo.setHotelName("null");
            serviceInfo.setEatName("null");
        }
        // endregion

        if (serviceInfo.getEventType().equals("null")) {
            fbEvent.setVisibility(TextView.GONE);
        } else {
            fbEvent.setVisibility(TextView.VISIBLE);
            fbEvent.setText(serviceInfo.getEventType());
        }

        if (serviceInfo.getReviewUserFav()) {
            btnLike.setText("BỎ THÍCH");
        } else {
            btnLike.setText("THÍCH");
        }

        if (serviceInfo.getReviewUserRev()) {
            btnReview.setText("ĐÃ ĐÁNH GIÁ");
        } else {
            btnReview.setText("ĐÁNH GIÁ");
        }

        txtServiceAbout.setText(serviceInfo.getServiceAbout());
        if (serviceInfo.getLowestPrice().equals("0") && serviceInfo.getHighestPrice().equals("0")) {
            txtPrice.setText("Đang cập nhật");
        } else {
            txtPrice.setText("Từ " + serviceInfo.getLowestPrice() + "đ" + " đến " + serviceInfo.getHighestPrice() + "đ");
        }
        txtTimeOpen.setText(serviceInfo.getTimeOpen());
        txtTimeClose.setText(serviceInfo.getTimeClose());
        txtAddress.setText(serviceInfo.getAddress());
        txtPhoneNumber.setText(serviceInfo.getPhoneNumber());
        txtWebsite.setText(serviceInfo.getWebsite());
        imgBanner.setImageBitmap(serviceInfo.getBanner());
        imgThumbInfo1.setImageBitmap(serviceInfo.getThumbInfo1());
        imgThumbInfo2.setImageBitmap(serviceInfo.getThumbInfo2());
        txtMark.setText(String.format("%.1f", serviceInfo.getReviewMark()));
        rbStar.setRating(serviceInfo.getStars());

        try {
            saveJson = new JSONObject("{\"id\":\"" + serviceInfo.getId() +
                    "\",\"ks_tenkhachsan\":\"" + serviceInfo.getHotelName() +
                    "\",\"vc_tendiemvuichoi\":\"" + serviceInfo.getEntertainName() +
                    "\",\"pt_tenphuongtien\":\"" + serviceInfo.getVehicleName() +
                    "\",\"tq_tendiemthamquan\":\"" + serviceInfo.getPlaceName() +
                    "\",\"au_ten\":\"" + serviceInfo.getEatName() +
                    "\",\"id_hinhanh\":\"" + serviceInfo.getIdImage() +
                    "\",\"chitiet1\":\"" + serviceInfo.getImageName() +
                    "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class DeleteFavorite extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpDelete(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("success")) {
                Toast.makeText(ActivityServiceInfo.this, "Đã bỏ thích", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ActivityServiceInfo.this, "Không thể bỏ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
