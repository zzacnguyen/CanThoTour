package com.doan3.canthotour.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by zzacn on 11/14/2017.
 */

public class JsonHelper {

    public static ArrayList<String> parseJson(JSONObject json, ArrayList<String> arr){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getInt("id")+"");
            for (int i = 0; i < arr.size(); i++){
                stringJson.add(json.getString(arr.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJson(JSONArray json, ArrayList<String> arr) {
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getInt("id")+"");
                for (int j = 0; j < arr.size(); j++){
                    stringJson.add(json.getJSONObject(i).getString(arr.get(j)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
}
