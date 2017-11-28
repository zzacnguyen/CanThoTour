package com.doan3.canthotour.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zzacn on 11/14/2017.
 */

public class JsonHelper {

    public static ArrayList<String> parseJson(JSONObject json, ArrayList<String> arr) {
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getInt("id") + "");
            for (int i = 0; i < arr.size(); i++) {
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
                stringJson.add(json.getJSONObject(i).getInt("id") + "");
                for (int j = 0; j < arr.size(); j++) {
                    stringJson.add(json.getJSONObject(i).getString(arr.get(j)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }

    public static JSONArray mergeJson(JSONArray json1, JSONArray json2) {
        for (int i = 0; i < json2.length(); i++) {
            try {
                json1.put(json2.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json1;
    }

    public static JSONArray mergeJson(JSONArray json1, JSONObject json2) {
        return json1.put(json2);
    }

    public static JSONArray mergeJson(JSONObject json1, JSONObject json2) {
        JSONArray jsonArray = null;
        jsonArray.put(json1);
        jsonArray.put(json2);
        return jsonArray;
    }

    public static JSONArray mergeJson(JSONObject json1, JSONArray json2) {
        try {
            return json2.put(0, json1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json2;
    }

    public static void writeJson(String name, JSONObject json) {
        FileWriter file = null;
        try {
            file = new FileWriter(new File(name + ".json"));
            file.write(json.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeJson(String name, JSONArray json) {
        try {
            FileWriter file = new FileWriter(new File(name + ".json"));
            file.write(json.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readJson(String name) {
        try {
            FileInputStream is = new FileInputStream(new File(name + ".json"));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
