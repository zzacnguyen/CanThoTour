package com.doan3.canthotour.View.Main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;
import com.doan3.canthotour.View.Search.ActivityNearLocation;
import com.tooltip.Tooltip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by sieut on 12/6/2017.
 */

public class ActivityServiceInfo extends AppCompatActivity {
    Button btnChiaSe, btnLuu, btnLanCan;
    int ma, id;
    boolean display = true;
    Tooltip tooltip;
    JSONObject saveJson;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdichvu);

        btnChiaSe = findViewById(R.id.btnChiaSeDv);
        btnLuu = findViewById(R.id.btnLuu);
        btnLanCan = findViewById(R.id.btnLanCan);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File path = new File(Environment.getExternalStorageDirectory() + "/canthotour");
                if (!path.exists()) {
                    path.mkdirs();
                }
                File file = new File(path, "dsyeuthich.json");
                JSONArray getJsonInFile;
                boolean isExists = true;
                try {
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
                        Toast.makeText(ActivityServiceInfo.this, "Lưu thành công",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityServiceInfo.this, "Đã lưu trước đó", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnLanCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityServiceInfo.this, ActivityNearLocation.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        ma = getIntent().getIntExtra("masp", 1);

        load(this, Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + ma);

        menuBotNavBar();
    }

    private void menuBotNavBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        startActivity(new Intent(ActivityServiceInfo.this, MainActivity.class));
                        break;
                    case R.id.ic_yeuthich:
                        startActivity(new Intent(ActivityServiceInfo.this, ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        startActivity(new Intent(ActivityServiceInfo.this, ActivityNotify.class));
                        break;
                    case R.id.ic_canhan:
                        startActivity(new Intent(ActivityServiceInfo.this, ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

    public void load(final Activity activity, String url) {
        TextView txtTenDv = activity.findViewById(R.id.textViewTenDv);
        TextView txtGioiThieu = activity.findViewById(R.id.textViewGioiThieuDv);
        TextView txtGiaThap = activity.findViewById(R.id.textViewGiaThap);
        TextView txtGiaCao = activity.findViewById(R.id.textViewGiaCao);
        TextView txtGioMo = activity.findViewById(R.id.textViewGioMc);
        TextView txtGioDong = activity.findViewById(R.id.textViewGioDong);
        TextView txtDiaChi = activity.findViewById(R.id.textViewDiaChiDv);
        TextView txtSDT = activity.findViewById(R.id.textViewSdtDv);
        TextView txtWebsite = activity.findViewById(R.id.textViewWebsite);
        ImageView imgChiTiet1Thumb = activity.findViewById(R.id.imgChiTiet1);
        ImageView imgChiTiet2Thumb = activity.findViewById(R.id.imgChiTiet2);
        ImageView imgBanner = activity.findViewById(R.id.imgBanner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        Button fbEvent = findViewById(R.id.fb_sukien);
        LinearLayout info = findViewById(R.id.info);

        final ServiceInfo serviceInfo = new ModelService().getServiceInfo(url);

        id = serviceInfo.getId();
        if (serviceInfo.getTenAU() != null) {
            txtTenDv.setText(serviceInfo.getTenAU());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbAnUong));
            info.setBackgroundColor(getResources().getColor(R.color.tbAnUong));
            toolbarTitle.setText("Chi tiết quán ăn");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenVC("null");
        } else if (serviceInfo.getTenKS() != null) {
            txtTenDv.setText(serviceInfo.getTenKS());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbKhachSan));
            info.setBackgroundColor(getResources().getColor(R.color.tbKhachSan));
            toolbarTitle.setText("Chi tiết khách sạn");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenAU("null");
            serviceInfo.setTenVC("null");
        } else if (serviceInfo.getTenTQ() != null) {
            txtTenDv.setText(serviceInfo.getTenTQ());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbThamQuan));
            info.setBackgroundColor(getResources().getColor(R.color.tbThamQuan));
            toolbarTitle.setText("Chi tiết điểm tham quan");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenAU("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenVC("null");
        } else if (serviceInfo.getTenPT() != null) {
            txtTenDv.setText(serviceInfo.getTenPT());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbPhuongTien));
            info.setBackgroundColor(getResources().getColor(R.color.tbPhuongTien));
            toolbarTitle.setText("Chi tiết phương tiện");
            serviceInfo.setTenAU("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenVC("null");
        } else {
            txtTenDv.setText(serviceInfo.getTenVC());
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbVuiChoi));
            info.setBackgroundColor(getResources().getColor(R.color.tbVuiChoi));
            toolbarTitle.setText("Chi tiết điểm vui chơi");
            serviceInfo.setTenPT("null");
            serviceInfo.setTenTQ("null");
            serviceInfo.setTenKS("null");
            serviceInfo.setTenAU("null");
        }

        if (serviceInfo.getLhsk().equals("null")) {
            fbEvent.setVisibility(TextView.INVISIBLE);
        } else {
            fbEvent.setText(serviceInfo.getLhsk());
            fbEvent.setVisibility(TextView.VISIBLE);
        }

        txtGioiThieu.setText(serviceInfo.getGioiThieuDV());
        txtGiaThap.setText(serviceInfo.getGiaThapNhat());
        txtGiaCao.setText(serviceInfo.getGiaCaoNhat());
        txtGioMo.setText(serviceInfo.getGioMoCua());
        txtGioDong.setText(serviceInfo.getGioDongCua());
        txtDiaChi.setText(serviceInfo.getDiaChi());
        txtSDT.setText(serviceInfo.getSdt());
        txtWebsite.setText(serviceInfo.getWebsite());
        imgBanner.setImageBitmap(serviceInfo.getBanner());
        imgChiTiet1Thumb.setImageBitmap(serviceInfo.getChiTiet1Thumb());
        imgChiTiet2Thumb.setImageBitmap(serviceInfo.getChiTiet2Thumb());

        try {
            saveJson = new JSONObject("{\"id\":\"" + serviceInfo.getId() +
                    "\",\"ks_tenkhachsan\":\"" + serviceInfo.getTenKS() +
                    "\",\"vc_tendiemvuichoi\":\"" + serviceInfo.getTenVC() +
                    "\",\"pt_tenphuongtien\":\"" + serviceInfo.getTenPT() +
                    "\",\"tq_tendiemthamquan\":\"" + serviceInfo.getTenTQ() +
                    "\",\"an_ten\":\"" + serviceInfo.getTenAU() +
                    "\",\"nd_idnguoidung\":\"1\"" +
                    "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fbEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (display == true) {
                    showTooltip(view, Gravity.BOTTOM);
                    display = false;
                } else {
                    tooltip.dismiss();
                    display = true;
                }
            }

            private void showTooltip(View view, int gravity) {
                tooltip = new Tooltip.Builder(view, R.style.TextAppearance_AppCompat_Light_Widget_PopupMenu_Large)
                        .setText(serviceInfo.getLhsk())
                        .setTextColor(Color.WHITE)
                        .setBackgroundColor(Color.RED)
                        .setGravity(gravity)
                        .setCornerRadius(8f)
                        .setDismissOnClick(true)
                        .show();
            }
        });
    }
}
