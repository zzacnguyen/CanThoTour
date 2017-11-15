package com.doan3.canthotour.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zzacn on 11/14/2017.
 */

public class JsonHelper {
    public static ArrayList<String> parseJsonUser(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("nd_tendoanhnghiep"));
            stringJson.add(json.getString("nd_tendangnhap"));
            stringJson.add(json.getString("nd_sodienthoai"));
            stringJson.add(json.getString("nd_matkhau"));
            stringJson.add(json.getString("nd_website"));
            stringJson.add(json.getString("nd_email"));
            stringJson.add(json.getString("nd_diachi"));
            stringJson.add(json.getString("nd_quocgia"));
            stringJson.add(json.getString("nd_ngonngu"));
            stringJson.add(json.getString("nd_ghichu"));
            stringJson.add(json.getString("nd_loainguoidung"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
}
