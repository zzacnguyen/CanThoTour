package com.doan3.canthotour.Model;

import android.os.AsyncTask;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/20/2017.
 */

public class ModelService {
    public ServiceInfo getEatInfo(String url, ArrayList<String> formatJson) {
        ArrayList<String> arrayList;
        ServiceInfo serviceInfo = new ServiceInfo();
        try {
            // lấy thông tin ăn uống
            String data = new LoadInfo().execute(url).get();
            JSONArray jsonArray = new JSONArray(data);
            arrayList = JsonHelper.parseJsonNoId(jsonArray.getJSONObject(0), formatJson);
            if (formatJson.equals(Config.JSON_HOTEL)) {
                serviceInfo.setTen(arrayList.get(0));
                serviceInfo.setGioiThieuAU(arrayList.get(1));
                serviceInfo.setIdDV(Integer.parseInt(arrayList.get(2)));
                serviceInfo.setWebsite(arrayList.get(3));
            } else {
                serviceInfo.setTen(arrayList.get(0));
                serviceInfo.setGioiThieuAU(arrayList.get(1));
                serviceInfo.setIdDV(Integer.parseInt(arrayList.get(2)));
            }

            // lấy thông tin dịch vụ
            data = new LoadInfo().execute(Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + serviceInfo.getIdDV()).get();
            jsonArray = new JSONArray(data);
            arrayList.clear();
            arrayList = JsonHelper.parseJsonNoId(jsonArray.getJSONObject(0), Config.JSON_SERVICE);

            serviceInfo.setGioiThieuDV(arrayList.get(0));
            serviceInfo.setGioDongCua(arrayList.get(1));
            serviceInfo.setGioMoCua(arrayList.get(2));
            serviceInfo.setGiaCaoNhat(arrayList.get(3));
            serviceInfo.setGiaThapNhat(arrayList.get(4));
            serviceInfo.setTrangThai(arrayList.get(5));
            serviceInfo.setIdDD(Integer.parseInt(arrayList.get(6)));

            // lấy thông tin địa điểm
            data = new LoadInfo().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + serviceInfo.getIdDD()).get();
            jsonArray = new JSONArray(data);
            arrayList.clear();
            arrayList = JsonHelper.parseJsonNoId(jsonArray.getJSONObject(0), Config.JSON_PLACE);

            serviceInfo.setDiaChi(arrayList.get(2));
            serviceInfo.setSdt(arrayList.get(3));

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return serviceInfo;
    }

    private class LoadInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }
    }

}
