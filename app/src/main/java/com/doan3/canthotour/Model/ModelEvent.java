package com.doan3.canthotour.Model;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.ObjectClass.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.Helper.JsonHelper.parseJson;
import static com.doan3.canthotour.Helper.JsonHelper.parseJsonNoId;
import static com.doan3.canthotour.Model.ModelService.setImage;

/**
 * Created by sieut on 4/26/2018.
 */

public class ModelEvent {
    public ArrayList<Event> getEventList(String url) { //Get danh sách thông báo sự kiện

        ArrayList<String> arr, arrayList;
        ArrayList<Event> events = new ArrayList<>();

        try {
            //Sử dụng parseJsonNoId vì KEY_JSON_LOAD ko có id
            arr = parseJsonNoId(new JSONObject(new HttpRequestAdapter.httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Event event = new Event();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_EVENT); //Parse json event
                event.setEventId(Integer.parseInt(arrayList.get(0))); //Set mã sự kiện
                event.setEventName(arrayList.get(1)); //Set tên

                //Set ngày bắt đầu và ngày kết thúc sự kiện
                event.setEventDate("Từ " + arrayList.get(2) + " đến " + arrayList.get(3));
                event.setEventImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(5),
                        arrayList.get(4), arrayList.get(5)));
                events.add(event);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return events;
    }
}
