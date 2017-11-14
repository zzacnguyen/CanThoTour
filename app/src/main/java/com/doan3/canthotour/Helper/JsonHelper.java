package com.doan3.canthotour.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zzacn on 11/14/2017.
 */

public class JsonHelper {
    public ArrayList<String> parseJsonUser(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("nd_tendangnhap"));
            stringJson.add(json.getString("nd_matkhau"));
            stringJson.add(json.getString("nd_email"));
            stringJson.add(json.getString("nd_tendoanhnghiep"));
            stringJson.add(json.getString("nd_ghichu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
}
