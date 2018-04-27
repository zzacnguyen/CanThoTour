package com.doan3.canthotour.Model;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.ObjectClass.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.Helper.JsonHelper.mergeJson;
import static com.doan3.canthotour.Helper.JsonHelper.parseJson;
import static com.doan3.canthotour.Helper.JsonHelper.parseJsonNoId;
import static com.doan3.canthotour.Helper.JsonHelper.readJson;
import static com.doan3.canthotour.Model.ModelService.setImage;

/**
 * Created by sieut on 4/26/2018.
 */

public class ModelFavorite {
    public ArrayList<Service> getFavoriteList(File file, String url) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            arr = parseJsonNoId(new JSONObject(new HttpRequestAdapter.httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray;
            if (file.exists()) {
                jsonArray = mergeJson(new JSONArray(arr.get(0)), new JSONArray(readJson(file)));
            } else {
                jsonArray = new JSONArray(arr.get(0));
            }

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_SERVICE_LIST);

                //Set hình ảnh
                service.setImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(7),
                        arrayList.get(6), arrayList.get(7)));
                //Set mã dịch vụ
                service.setId(Integer.parseInt(arrayList.get(0)));
                //Set tên dịch vụ yêu thích
                service.setName(!arrayList.get(1).equals(Config.NULL) ? arrayList.get(1) :
                        !arrayList.get(2).equals(Config.NULL) ? arrayList.get(2) :
                                !arrayList.get(3).equals(Config.NULL) ? arrayList.get(3) :
                                        !arrayList.get(4).equals(Config.NULL) ? arrayList.get(4) : arrayList.get(5));

                services.add(service);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return services;
    }
}
