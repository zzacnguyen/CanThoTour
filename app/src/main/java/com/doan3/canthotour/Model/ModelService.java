package com.doan3.canthotour.Model;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ObjectClass.Event;
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
    //Ở phần này nhiều chỗ t chưa hiểu, thứ 6 t hỏi lại
    public ServiceInfo getServiceInfo(String url) {
        ArrayList<String> arrayList;
        ServiceInfo serviceInfo = new ServiceInfo();
        try {
            // lấy thông tin ăn uống
            Bitmap banner, chiTiet1, chiTiet1Thumb, chiTiet2, chiTiet2Thumb;
            String data = new Load().execute(url).get();
            JSONArray jsonArray = new JSONArray(data);

            arrayList = JsonHelper.parseJson(jsonArray.getJSONObject(0), Config.JSON_SERVICE_INFO);

            if (!arrayList.get(1).equals("null")) {
                serviceInfo.setTenKS(arrayList.get(1));
                serviceInfo.setWebsite(arrayList.get(2));
            } else if (!arrayList.get(3).equals("null")) {
                serviceInfo.setTenVC(arrayList.get(3));
            } else if (!arrayList.get(4).equals("null")) {
                serviceInfo.setTenPT(arrayList.get(4));
            } else if (!arrayList.get(5).equals("null")) {
                serviceInfo.setTenTQ(arrayList.get(5));
            } else if (!arrayList.get(6).equals("null")) {
                serviceInfo.setTenAU(arrayList.get(6));
            }
            serviceInfo.setId(Integer.parseInt(arrayList.get(0)));
            serviceInfo.setGioiThieuDV(arrayList.get(7));
            serviceInfo.setGioMoCua(arrayList.get(8));
            serviceInfo.setGioDongCua(arrayList.get(9));
            serviceInfo.setGiaThapNhat(arrayList.get(10));
            serviceInfo.setGiaCaoNhat(arrayList.get(11));
            serviceInfo.setDiaChi(arrayList.get(12));
            serviceInfo.setSdt(arrayList.get(13));
            serviceInfo.setLhsk(arrayList.get(14));

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return serviceInfo;
    }

    public ArrayList<Service> getServiceList(String url, ArrayList<String> formatJson) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            String rs = new Load().execute(url).get();
            arr = JsonHelper.parseJsonNoId(new JSONObject(rs), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            int limit = jsonArray.length() > 5 ? 5 : jsonArray.length();

            for (int i = 0; i < limit; i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, formatJson);

                if (!arrayList.get(3).equals("null")) {
                    service.setHinh(new GetImage().execute(Config.URL_HOST + "thumbnails/" + arrayList.get(3)).get());
                }
                service.setMa(Integer.parseInt(arrayList.get(0)));
                service.setTen(arrayList.get(1));

                services.add(service);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return services;
    }

    public ArrayList<Service> getServiceFullList(String url, ArrayList<String> formatJson) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            arr = JsonHelper.parseJsonNoId(new JSONObject(new Load().execute(url).get()), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, formatJson);
                if (!arrayList.get(3).equals("null")) {
                    service.setHinh(new GetImage().execute(Config.URL_HOST + "thumbnails/" + arrayList.get(3)).get());
                }
                service.setMa(Integer.parseInt(arrayList.get(0)));
                service.setTen(arrayList.get(1));

                services.add(service);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return services;
    }

    public ArrayList<Service> getFavoriteList(File file, int id) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            arr = JsonHelper.parseJsonNoId(new JSONObject(new Load().
                    execute(Config.URL_HOST + Config.URL_GET_ALL_FAVORITE + "/" + id).get()), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_FAVORITE);

                service.setHinh(new GetImage().execute(Config.URL_HOST + "thumbnails/" + arrayList.get(7)).get()); //Set hình ảnh
                service.setMa(Integer.parseInt(arrayList.get(0))); //Set mã dịch vụ
                service.setTen(!arrayList.get(1).equals("null") ? arrayList.get(1) :
                        !arrayList.get(2).equals("null") ? arrayList.get(2) :
                                !arrayList.get(3).equals("null") ? arrayList.get(3) :
                                        !arrayList.get(4).equals("null") ? arrayList.get(4) : arrayList.get(5)); //Set tên dịch vụ yêu thích

                services.add(service);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return services;
    }

    public ArrayList<Event> getEventList(String url) { //Get danh sách thông báo sự kiện

        ArrayList<String> arr, arrayList;
        ArrayList<Event> events = new ArrayList<>();

        try {
            arr = JsonHelper.parseJsonNoId(new JSONObject(new Load().execute(url).get()), Config.JSON_LOAD); //Sử dụng parseJsonNoId vì JSON_LOAD ko có id
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Event event = new Event();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_EVENT); //Parse json event
                event.setMaSk(Integer.parseInt(arrayList.get(0))); //Set mã sự kiện
                event.setTenSk(arrayList.get(1)); //Set tên
                event.setNgaySk("Từ " + arrayList.get(2) + " đến " + arrayList.get(3)); //Set ngày bắt đầu và ngày kết thúc sự kiện
                if (!arrayList.get(5).equals("null")) { //Set hình ảnh
                    event.setHinhSk(new GetImage().execute(Config.URL_HOST + "thumbnails/" + arrayList.get(5)).get());
                }
                events.add(event);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return events;
    }

    //Get dữ liệu
    public static class Load extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpGet(strings[0]);
        }
    }
    //Get dữ liệu hình ảnh. Dữ liệu trả ra là Bitmap, biến truyền vào là có kiểu String
    public static class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            return HttpRequestAdapter.getBitmapFromURL(strings[0]);
        }
    }

//    private class GetImage extends AsyncTask<String, Void, Bitmap> {
//        @Override
//        protected Bitmap doInBackground(String... strings) {
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
//            String url = "https://2.bp.blogspot.com/-dyjXzBrtLG8/WhrTd0W-k9I/AAAAAAAACi8/YzLNj5kTtaEAZ43npwmRFNum7bxvJrqdQCK4BGAYYCw/s1600/naruto_uchiha_itachi_mangekyou_sharingan_112353_3840x2160.jpg";
//            String folderName = "1", fileName = "2.jpg";
//            String filePath = HttpRequestAdapter.httpGetImage(url, folderName, fileName);
//            File imgFile = new File(filePath);
//            Bitmap myBitmap = null;
//            if (imgFile.exists()) {
//                myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            }
//            return myBitmap;
//        }
//    }
}
