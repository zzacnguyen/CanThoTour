package com.doan3.canthotour.Model;

import android.os.AsyncTask;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;
import com.doan3.canthotour.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            String data = new Load().execute(url).get();
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
            data = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_SERVICES + "/" + serviceInfo.getIdDV()).get();
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
            data = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + serviceInfo.getIdDD()).get();
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

    public ArrayList<Service> getServiceList(String url, ArrayList<String> formatJson) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            String rs = new Load().execute(url).get();
            arr = JsonHelper.parseJsonNoId(new JSONObject(rs), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            int limit = jsonArray.length() > 5 ? 5 : jsonArray.length();

            for (int i = 0; i < limit; i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, formatJson);
                service.setHinh(R.drawable.benninhkieu1);
                service.setId(Integer.parseInt(arrayList.get(0)));
                service.setTen(arrayList.get(1));

                services.add(service);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return services;
    }

    private class Load extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }
    }

}
