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

import static com.doan3.canthotour.View.Personal.ActivityLogin.idNguoiDung;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityServiceInfo extends AppCompatActivity {
    Button btnChiaSe, btnLuu, btnLanCan, btnDanhGia, btnXemDanhGia;
    int ma, loaiHinh, idDanhGia;
    String idYeuThich, kinhDo, viDo;
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

        btnChiaSe = findViewById(R.id.btnShareService);
        btnLuu = findViewById(R.id.btnLike);
        btnLanCan = findViewById(R.id.btnNearLocation);
        btnDanhGia = findViewById(R.id.btnReview);
        btnXemDanhGia = findViewById(R.id.btnOpenListReview);

        ma = getIntent().getIntExtra("id", 1);
        String mess = getIntent().getStringExtra("mess");
        if (mess != null) {
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        }
        // region button luu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idNguoiDung == 0) {
                    Intent intent = new Intent(ActivityServiceInfo.this, ActivityLogin.class);
                    intent.putExtra("id", ma);
                    startActivity(intent);
                } else {
                    File path = new File(Environment.getExternalStorageDirectory() + "/canthotour");
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    File file = new File(path, "dsyeuthich.json");
                    JSONArray getJsonInFile;

                    if (btnLuu.getText().equals("THÍCH")) {
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
                                btnLuu.setText("BỎ THÍCH");
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
                                    if (Integer.parseInt(getJsonInFile.getJSONObject(i).getString("id")) != (ma)) {
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
                                new DeleteFavorite().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE + "/" + idYeuThich);
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        btnLuu.setText("THÍCH");
                    }
                }
            }
        });
        // endregion

        btnLanCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityServiceInfo.this, ActivityNearLocation.class);
                intent.putExtra("kinhdo", kinhDo);
                intent.putExtra("vido", viDo);
                intent.putExtra("loaihinh", loaiHinh);
                startActivity(intent);
            }
        });

        btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idNguoiDung == 0) {
                    Intent intent = new Intent(ActivityServiceInfo.this, ActivityLogin.class);
                    intent.putExtra("id", ma);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ActivityServiceInfo.this, ActivityReview.class);
                    intent.putExtra("id", ma);
                    intent.putExtra("iddanhgia", idDanhGia);
                    startActivity(intent);
                }
            }
        });

        btnXemDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityServiceInfo.this, ActivityReviewList.class);
                intent.putExtra("id", ma);
                startActivity(intent);
            }
        });

        load(this, Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + ma);

        menuBotNavBar(this,0);
    }

    public void load(final Activity activity, String url) {
        TextView txtTenDv = activity.findViewById(R.id.textViewServiceName);
        TextView txtGioiThieu = activity.findViewById(R.id.textViewServiceAbout);
        TextView txtGia = activity.findViewById(R.id.textViewCost);
        TextView txtGioMo = activity.findViewById(R.id.textViewTimeOpen);
        TextView txtGioDong = activity.findViewById(R.id.textViewTimeClose);
        TextView txtDiaChi = activity.findViewById(R.id.textViewServiceAddress);
        TextView txtSDT = activity.findViewById(R.id.textViewServicePhone);
        TextView txtWebsite = activity.findViewById(R.id.textViewWebsite);
        ImageView imgChiTiet1Thumb = activity.findViewById(R.id.imgInfo1);
        ImageView imgChiTiet2Thumb = activity.findViewById(R.id.imgInfo2);
        ImageView imgBanner = activity.findViewById(R.id.imgBanner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        TextView fbEvent = findViewById(R.id.fb_event);
        LinearLayout info = findViewById(R.id.info);
        TextView txtDiem = findViewById(R.id.textViewRatingMark);
        RatingBar rbSao = findViewById(R.id.ratingBarStars);

        final ServiceInfo serviceInfo = new ModelService().getServiceInfo(url);

        idYeuThich = serviceInfo.getIdYeuThich();
        idDanhGia = Integer.parseInt(serviceInfo.getIdDanhGia());
        kinhDo = serviceInfo.getKinhDo();
        viDo = serviceInfo.getViDo();

        fbEvent.setSelected(true);

        // region get tên và set màu cho từng dịch vụ
        if (serviceInfo.getTenAU() != null) {
            txtTenDv.setText(serviceInfo.getTenAU());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEat));
            info.setBackgroundColor(getResources().getColor(R.color.tbEat));
            loaiHinh = 1;
            toolbarTitle.setText("Chi tiết quán ăn");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenVC("null");
        } else if (serviceInfo.getTenKS() != null) {
            txtTenDv.setText(serviceInfo.getTenKS());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            info.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            loaiHinh = 2;
            toolbarTitle.setText("Chi tiết khách sạn");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenAU("null");
            serviceInfo.setTenVC("null");
        } else if (serviceInfo.getTenTQ() != null) {
            txtTenDv.setText(serviceInfo.getTenTQ());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            info.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            loaiHinh = 4;
            toolbarTitle.setText("Chi tiết điểm tham quan");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenAU("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenVC("null");
        } else if (serviceInfo.getTenPT() != null) {
            txtTenDv.setText(serviceInfo.getTenPT());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            info.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            loaiHinh = 3;
            toolbarTitle.setText("Chi tiết phương tiện");
            serviceInfo.setTenAU("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenVC("null");
        } else {
            txtTenDv.setText(serviceInfo.getTenVC());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            info.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            loaiHinh = 5;
            toolbarTitle.setText("Chi tiết điểm vui chơi");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenAU("null");
        }
        // endregion

        if (serviceInfo.getLhsk().equals("null")) {
            fbEvent.setVisibility(TextView.GONE);
        } else {
            fbEvent.setVisibility(TextView.VISIBLE);
            fbEvent.setText(serviceInfo.getLhsk());
        }

        if (serviceInfo.getIdNguoiDungYT()) {
            btnLuu.setText("BỎ THÍCH");
        } else {
            btnLuu.setText("THÍCH");
        }

        if (serviceInfo.getIdNguoiDungDG()) {
            btnDanhGia.setText("ĐÃ ĐÁNH GIÁ");
        } else {
            btnDanhGia.setText("ĐÁNH GIÁ");
        }

        txtGioiThieu.setText(serviceInfo.getGioiThieuDV());
        if (serviceInfo.getGiaThapNhat().equals("0") && serviceInfo.getGiaCaoNhat().equals("0")) {
            txtGia.setText("Đang cập nhật");
        } else {
            txtGia.setText("Từ " + serviceInfo.getGiaThapNhat() + "đ" + " đến " + serviceInfo.getGiaCaoNhat() + "đ");
        }
        txtGioMo.setText(serviceInfo.getGioMoCua());
        txtGioDong.setText(serviceInfo.getGioDongCua());
        txtDiaChi.setText(serviceInfo.getDiaChi());
        txtSDT.setText(serviceInfo.getSdt());
        txtWebsite.setText(serviceInfo.getWebsite());
        imgBanner.setImageBitmap(serviceInfo.getBanner());
        imgChiTiet1Thumb.setImageBitmap(serviceInfo.getChiTiet1Thumb());
        imgChiTiet2Thumb.setImageBitmap(serviceInfo.getChiTiet2Thumb());
        txtDiem.setText(String.format("%.1f", serviceInfo.getDiemDG()));
        rbSao.setRating(serviceInfo.getSoSao());

        try {
            saveJson = new JSONObject("{\"id\":\"" + serviceInfo.getId() +
                    "\",\"ks_tenkhachsan\":\"" + serviceInfo.getTenKS() +
                    "\",\"vc_tendiemvuichoi\":\"" + serviceInfo.getTenVC() +
                    "\",\"pt_tenphuongtien\":\"" + serviceInfo.getTenPT() +
                    "\",\"tq_tendiemthamquan\":\"" + serviceInfo.getTenTQ() +
                    "\",\"au_ten\":\"" + serviceInfo.getTenAU() +
                    "\",\"id_hinhanh\":\"" + serviceInfo.getIdHinh() +
                    "\",\"chitiet1\":\"" + serviceInfo.getTenHinh() +
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
