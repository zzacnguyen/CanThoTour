package com.doan3.canthotour.View.Main.Content;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Adapter.ListOfServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.BottomNavigationViewHelper;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavorite;
import com.doan3.canthotour.View.Notify.ActivityNotify;
import com.doan3.canthotour.View.Personal.ActivityPersonal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import q.rorbin.badgeview.QBadgeView;

import static com.doan3.canthotour.View.Main.MainActivity.badgeNumber;
import static com.doan3.canthotour.View.Main.MainActivity.fragment;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by zzacn on 4/11/2018.
 */

public class FragmentService extends Fragment {

    ArrayList<String> finalArr = new ArrayList<>();
    ArrayList<String> formatJson = new ArrayList<>();
    Toolbar toolbar;
    TextView toolbarTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbarTitle = view.findViewById(R.id.toolbarTitle);

        Bundle bundle = getArguments();

        String url = bundle.getString("url");
        Log.d("URL", url);

        if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_EATS)) { //Kiểm tra từng đường dẫn url
            formatJson = Config.GET_KEY_JSON_EAT;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEat));
            toolbarTitle.setText(getResources().getString(R.string.title_ListOfRestaurant));
        } else if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_PLACES)) {
            formatJson = Config.GET_KEY_JSON_PLACE;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbPlace));
            toolbarTitle.setText(getResources().getString(R.string.title_ListOfPlaceToVisit));
        } else if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_HOTELS)) {
            formatJson = Config.GET_KEY_JSON_HOTEL;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbHotel));
            toolbarTitle.setText(getResources().getString(R.string.title_ListOfHotel));
        } else if (url.equals(Config.URL_HOST + Config.URL_GET_ALL_VEHICLES)) {
            formatJson = Config.GET_KEY_JSON_VEHICLE;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbVehicle));
            toolbarTitle.setText(getResources().getString(R.string.title_ListOfTransport));
        } else {
            formatJson = Config.GET_KEY_JSON_ENTERTAINMENT;
            toolbar.setBackgroundColor(getResources().getColor(R.color.tbEntertain));
            toolbarTitle.setText(getResources().getString(R.string.title_ListOfEntertainment));
        }

        load(url, formatJson, view);

        bottomNavBar(view);
        return view;
    }

    private void load(String url, final ArrayList<String> formatJson, View view) { //Khai báo view

        final ListOfServiceAdapter listOfServiceAdapter;
        final RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.RecyclerView_ServiceList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Service> services = new ModelService().getServiceFullList(url, formatJson);

        listOfServiceAdapter = new ListOfServiceAdapter(recyclerView, services, getApplicationContext());
        recyclerView.setAdapter(listOfServiceAdapter);
        listOfServiceAdapter.notifyDataSetChanged();

        //set load more listener for the RecyclerView adapter
        final ArrayList<Service> finalListService = services;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject
                    (new httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        listOfServiceAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (finalListService.size() < Integer.parseInt(finalArr.get(2))) {
                    finalListService.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            listOfServiceAdapter.notifyItemInserted(finalListService.size() - 1);
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalListService.remove(finalListService.size() - 1);
                            listOfServiceAdapter.notifyItemRemoved(finalListService.size());

                            ArrayList<Service> serviceArrayList = new ModelService().
                                    getServiceFullList(finalArr.get(1), formatJson);
                            for (int i = 0; i < serviceArrayList.size(); i++) {
                                finalListService.add(serviceArrayList.get(i));
                            }
                            try {
                                finalArr = JsonHelper.parseJsonNoId(new JSONObject
                                        (new httpGet().execute(finalArr.get(1)).get()), Config.GET_KEY_JSON_LOAD);
                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                            listOfServiceAdapter.notifyDataSetChanged();
                            listOfServiceAdapter.setLoaded();
                        }
                    }, 1000);
                }
            }
        });
    }

    private void bottomNavBar(final View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavView_Bar); //Bottom navigation view
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0); //Hiển thị ở trang chủ
        View v = bottomNavigationMenuView.getChildAt(2); //Hiển thị dấu chấm đỏ khi có thông báo

        try {
            String getBadgeNumber = new httpGet().execute(Config.URL_HOST + Config.URL_GET_EVENT_NUMBER).get();
            badgeNumber = Integer.parseInt(getBadgeNumber);
            Log.d("badge number", getBadgeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        new QBadgeView(getActivity()).bindTarget(v)
                .setBadgeNumber(badgeNumber)  //Set số thông báo hiển thị
                .setBadgeGravity(Gravity.START | Gravity.TOP)
                .setGravityOffset(26, 0, true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_trangchu:
                        getActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
                        break;
                    case R.id.ic_yeuthich:
                        getActivity().startActivity(new Intent(getApplicationContext(), ActivityFavorite.class));
                        break;
                    case R.id.ic_thongbao:
                        Intent iNotify = new Intent(getApplicationContext(), ActivityNotify.class);
                        badgeNumber = 0; //Ẩn thông báo đi khi đã ấn vào
                        getActivity().startActivity(iNotify);
                        break;
                    case R.id.ic_canhan:
                        getActivity().startActivity(new Intent(getApplicationContext(), ActivityPersonal.class));
                        break;
                }
                return false;
            }
        });
    }

}
