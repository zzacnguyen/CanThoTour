package com.doan3.canthotour.Model;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.ObjectClass.TripSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import static com.doan3.canthotour.Helper.JsonHelper.parseJson;
import static com.doan3.canthotour.Helper.JsonHelper.parseJsonNoId;



public class ModelTripSchedule {

    public ArrayList<TripSchedule> getTripScheduleList (String url, ArrayList<String> formatJSON){

        ArrayList<String> arr, dataJson;
        ArrayList<TripSchedule> tripSchedules = new ArrayList<>();

        try {
            String result = new HttpRequestAdapter.httpGet().execute(url).get();
            arr = parseJsonNoId(new JSONObject(result), Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++){

                TripSchedule tripSchedule = new TripSchedule();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dataJson = parseJson(jsonObject, formatJSON);

                tripSchedule.setTripID(Integer.parseInt(dataJson.get(0)));
                tripSchedule.setTripName(dataJson.get(1));
                tripSchedule.setTripStartDate(dataJson.get(2));
                tripSchedule.setTripEndDate(dataJson.get(3));

                tripSchedules.add(tripSchedule);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tripSchedules;
    }

}
