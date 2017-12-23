package com.doan3.canthotour.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/20/2017.
 */

public class ModelService {
    public ServiceInfo getServiceInfo(String url, ArrayList<String> formatJson) {
        ArrayList<String> arrayList;
        ServiceInfo serviceInfo = new ServiceInfo();
        try {
            // lấy thông tin ăn uống
            Bitmap banner, chiTiet1, chiTiet1Thumb, chiTiet2, chiTiet2Thumb;
            String data = new ModelPlace.Load().execute(url).get();
            JSONArray jsonArray = new JSONArray(data);
//            chiTiet1Thumb = new GetImage().execute().get();
//            serviceInfo.setChiTiet1Thumb(chiTiet1Thumb);
            arrayList = JsonHelper.parseJsonNoId(jsonArray.getJSONObject(0), formatJson);
            if (formatJson.equals(Config.JSON_HOTEL_INFO)) {
                serviceInfo.setTen(arrayList.get(0));
                serviceInfo.setWebsite(arrayList.get(8));
            } else {
                serviceInfo.setTen(arrayList.get(0));
            }

            serviceInfo.setGioiThieuDV(arrayList.get(7));
            serviceInfo.setGioDongCua(arrayList.get(6));
            serviceInfo.setGioMoCua(arrayList.get(5));
            serviceInfo.setGiaCaoNhat(arrayList.get(4));
            serviceInfo.setGiaThapNhat(arrayList.get(3));

            serviceInfo.setDiaChi(arrayList.get(1));
            serviceInfo.setSdt(arrayList.get(2));

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return serviceInfo;
    }

    public ArrayList<Service> getServiceList(String url, ArrayList<String> formatJson) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            String rs = new ModelPlace.Load().execute(url).get();
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

    private class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
//            String image = null;
//            try {
//                image = new ModelPlace.Load().execute(strings[0]).get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            String[] str = image.split("\\+");
//            String url = str[0], folderName = str[1], fileName = str[2];
            String url = "https://2.bp.blogspot.com/-dyjXzBrtLG8/WhrTd0W-k9I/AAAAAAAACi8/YzLNj5kTtaEAZ43npwmRFNum7bxvJrqdQCK4BGAYYCw/s1600/naruto_uchiha_itachi_mangekyou_sharingan_112353_3840x2160.jpg";
            String folderName = "1", fileName = "2";
            String filePath = HttpRequestAdapter.httpGetImage(url, folderName, fileName);
            File imgFile = new File(filePath);
            Bitmap myBitmap = null;
            if (imgFile.exists()) {
                myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
            return myBitmap;
        }
    }
}
