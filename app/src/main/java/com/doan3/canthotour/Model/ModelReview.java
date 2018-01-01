package com.doan3.canthotour.Model;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ObjectClass.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/20/2017.
 */

public class ModelReview {
    public ArrayList<Review> getReviewList(String url, ArrayList<String> formatJson) {

        ArrayList<String> arr, arrayList;
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            arr = JsonHelper.parseJsonNoId(new JSONObject(new ModelService.Load().execute(url).get()), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Review review = new Review();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJsonNoId(jsonObject, formatJson);
                review.setTenNguoiDung(arrayList.get(0));
                review.setSoSao(Float.parseFloat(arrayList.get(1)));
                review.setTieuDe(arrayList.get(2));
                review.setDanhGia(arrayList.get(3));
                review.setNgayDanhGia(arrayList.get(4));

                reviews.add(review);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
