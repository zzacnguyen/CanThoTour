package com.doan3.canthotour.Model;

import android.os.AsyncTask;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ObjectClass.Place;
import com.doan3.canthotour.Model.ObjectClass.PlaceInfo;
import com.doan3.canthotour.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/20/2017.
 */

public class ModelPlace {

    public PlaceInfo getPlaceInfo(int masp) {
        ArrayList<String> arrayList;
        PlaceInfo placeInfo = new PlaceInfo();
        try {
            String data = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + masp).get();
            JSONArray jsonArray = new JSONArray(data);
            arrayList = JsonHelper.parseJson(jsonArray.getJSONObject(0), Config.JSON_PLACE);

            placeInfo.setId(Integer.parseInt(arrayList.get(0)));
            placeInfo.setTen(arrayList.get(1));
            placeInfo.setGioiThieu(arrayList.get(2));
            placeInfo.setDiaChi(arrayList.get(3));
            placeInfo.setSdt(arrayList.get(4));
            placeInfo.setKinhDo(arrayList.get(5));
            placeInfo.setViDo(arrayList.get(6));
            placeInfo.setIdND(Integer.parseInt(arrayList.get(7)));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return placeInfo;
    }

    public ArrayList<Place> getPlaceList() {

        ArrayList<String> arr, arrayList;
        ArrayList<Place> places = new ArrayList<>();
        Place place = new Place();

        try {
            String rs = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES).get();
            arr = JsonHelper.parseJsonNoId(new JSONObject(rs), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            int limit = jsonArray.length() > 5 ? 5 : jsonArray.length();

            for (int i = 0; i < limit; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_PLACE);
                place.setHinhDD(R.drawable.benninhkieu1);
                place.setMaDD(Integer.parseInt(arrayList.get(0)));
                place.setTenDD(arrayList.get(1));

                places.add(place);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return places;
    }

    public ArrayList<Place> getFavoriteList() {

        ArrayList<Place> favorites = new ArrayList<>();
        Place place = new Place();

        try {
            String result = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE).get();
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(jsonObject, Config.JSON_FAVORITE);
                place.setMaDD(Integer.parseInt(arrayList.get(0)));

                String rs = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + place.getMaDD()).get();
                JSONArray json = new JSONArray(rs);
                ArrayList<String> arr = JsonHelper.parseJsonNoId(json.getJSONObject(0), Config.JSON_PLACE);
                place.setTenDD(arr.get(0));
                place.setHinhDD(R.drawable.benninhkieu1);

                favorites.add(place);
                Place place1 = favorites.get(i);
                System.out.println("trong for " + place1.getTenDD());
            }
            for (int i = 0; i < favorites.size(); i++) {
                Place place1 = favorites.get(i);
                System.out.println("ngoài for " + place1.getTenDD());
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return favorites;
    }

    public ArrayList<Place> getFavoriteList(File file) {

        ArrayList<Place> places = new ArrayList<>();
        Place place = new Place();

        try {
            String result = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE).get();
            JSONArray jsonGet = new JSONArray(), jsonArray = new JSONArray();
            if (file.exists()) {
                // parse json vừa get về bỏ id để đồng bộ với file json trong máy
                ArrayList<String> arrJsonGet =
                        JsonHelper.parseJsonNoId(new JSONArray(result), Config.JSON_FAVORITE);
                for (int i = 0; i < arrJsonGet.size(); i += 2) {
                    jsonGet.put(new JSONObject("{\"dd_iddiadiem\":\""
                            + arrJsonGet.get(i) + "\",\"nd_idnguoidung\":\"" + arrJsonGet.get(i + 1) + "\"}"));
                }
                // merge 2 json
                jsonArray = JsonHelper.mergeJson(jsonGet, new JSONArray(JsonHelper.readJson(file)));
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ArrayList<String> arrayList = JsonHelper.parseJsonNoId(jsonObject, Config.JSON_FAVORITE);
                place.setMaDD(Integer.parseInt(arrayList.get(0)));

                String rs = new Load().execute(Config.URL_HOST + Config.URL_GET_ALL_PLACES + "/" + place.getMaDD()).get();
                JSONArray json = new JSONArray(rs);
                ArrayList<String> arr = JsonHelper.parseJsonNoId(json.getJSONObject(0), Config.JSON_PLACE);
                place.setTenDD(arr.get(0));
                place.setHinhDD(R.drawable.benninhkieu1);

                places.add(place);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return places;
    }

    public static class Load extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }
    }
}
