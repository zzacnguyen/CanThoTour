package com.doan3.canthotour.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ObjectClass.Event;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sieut on 12/20/2017.
 */

public class ModelService {
    public static Bitmap setImage(String url, String folderName, String fileName) {
        Bitmap bitmap = null;
        if (!folderName.equals("null") && !fileName.equals("null")) {
            File path = new File(Environment.getExternalStorageDirectory() + "/canthotour/" + folderName);
            path.mkdirs();
            File file = new File(path, fileName);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeFile(file.toString(), options);
            } else {
                try {
                    bitmap = new GetImage().execute(url).get();
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    //Ở phần này nhiều chỗ t chưa hiểu, thứ 6 t hỏi lại
    public ServiceInfo getServiceInfo(String url) {
        ArrayList<String> arrayList;
        ServiceInfo serviceInfo = new ServiceInfo();
        try {
            // lấy thông tin ăn uống
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

            // lấy thông tin hình gồm : "url + id + tên hình"
            // xóa dấu " bằng replaceAll
            // tách theo dấu + ra 3 phân tử truyền vào hàm setImage
            String[] urlHinhChiTiet1 = null, urlHinhChiTiet2 = null, urlHinhBanner = null;
            try {
                urlHinhChiTiet1 = new Load().execute(Config.URL_HOST + Config.URL_GET_THUMB_1 + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
                urlHinhChiTiet2 = new Load().execute(Config.URL_HOST + Config.URL_GET_THUMB_2 + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
                urlHinhBanner = new Load().execute(Config.URL_HOST + Config.URL_GET_BANNER + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            serviceInfo.setChiTiet1Thumb(setImage(Config.URL_HOST + urlHinhChiTiet1[0],
                    urlHinhChiTiet1[1], urlHinhChiTiet1[2]));
            serviceInfo.setChiTiet2Thumb(setImage(Config.URL_HOST + urlHinhChiTiet2[0],
                    urlHinhChiTiet2[1], urlHinhChiTiet2[2]));
            serviceInfo.setBanner(setImage(Config.URL_HOST + urlHinhBanner[0], urlHinhBanner[1], urlHinhBanner[2]));

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

                service.setHinh(setImage(Config.URL_HOST + "thumbnails/" + arrayList.get(3),
                        arrayList.get(2), arrayList.get(3)));
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
                service.setHinh(setImage(Config.URL_HOST + "thumbnails/" + arrayList.get(3),
                        arrayList.get(2), arrayList.get(3)));
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

                //Set hình ảnh
                service.setHinh(setImage(Config.URL_HOST + "thumbnails/" + arrayList.get(7),
                        arrayList.get(6), arrayList.get(7)));
                //Set mã dịch vụ
                service.setMa(Integer.parseInt(arrayList.get(0)));
                //Set tên dịch vụ yêu thích
                service.setTen(!arrayList.get(1).equals("null") ? arrayList.get(1) :
                        !arrayList.get(2).equals("null") ? arrayList.get(2) :
                                !arrayList.get(3).equals("null") ? arrayList.get(3) :
                                        !arrayList.get(4).equals("null") ? arrayList.get(4) : arrayList.get(5));


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
            //Sử dụng parseJsonNoId vì JSON_LOAD ko có id
            arr = JsonHelper.parseJsonNoId(new JSONObject(new Load().execute(url).get()), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Event event = new Event();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_EVENT); //Parse json event
                event.setMaSk(Integer.parseInt(arrayList.get(0))); //Set mã sự kiện
                event.setTenSk(arrayList.get(1)); //Set tên

                //Set ngày bắt đầu và ngày kết thúc sự kiện
                event.setNgaySk("Từ " + arrayList.get(2) + " đến " + arrayList.get(3));
                event.setHinhSk(setImage(Config.URL_HOST + "thumbnails/" + arrayList.get(5),
                        arrayList.get(4), arrayList.get(5)));
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
}
