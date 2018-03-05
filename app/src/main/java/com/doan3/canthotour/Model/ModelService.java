package com.doan3.canthotour.Model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.ObjectClass.Event;
import com.doan3.canthotour.Model.ObjectClass.NearLocation;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Personal.ActivityLogin.userId;

/**
 * Created by sieut on 12/20/2017.
 */

public class ModelService {
    public static Bitmap setImage(String url, String folderName, String fileName) {
        Bitmap bitmap = null;
        if (!folderName.equals(Config.NULL) && !fileName.equals(Config.NULL)) {
            File path = new File(Environment.getExternalStorageDirectory() + Config.FOLDER_IMAGE + "/" + folderName);
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

    public ServiceInfo getServiceInfo(String url) {
        ArrayList<String> arrayDichVu, arrayIdYt = new ArrayList<>(), arrayIdDg = new ArrayList<>(),
                arrayIdNdYt = new ArrayList<>(), arrayIdNdDg = new ArrayList<>();
        String arrayLhsk = null;
        ServiceInfo serviceInfo = new ServiceInfo();
        try {
            // lấy thông tin ăn uống
            String data = new Load().execute(url).get();
            JSONArray jsonArray = new JSONArray(data);

            JSONArray jsonIdYT = new JSONArray(jsonArray.getJSONObject(0).get(Config.GET_SERVICE_INFO.get(0)).toString());
            for (int i = 0; i < jsonIdYT.length(); i++) {
                arrayIdYt.add(jsonIdYT.getJSONObject(i).getString(Config.GET_SERVICE_INFO.get(1)));
                arrayIdNdYt.add(jsonIdYT.getJSONObject(i).getString(Config.GET_SERVICE_INFO.get(2)));
            }

            JSONArray jsonIdDG = new JSONArray(jsonArray.getJSONObject(1).get(Config.GET_SERVICE_INFO.get(3)).toString());
            for (int i = 0; i < jsonIdDG.length(); i++) {
                arrayIdDg.add(jsonIdDG.getJSONObject(i).getString(Config.GET_SERVICE_INFO.get(4)));
                arrayIdNdDg.add(jsonIdDG.getJSONObject(i).getString(Config.GET_SERVICE_INFO.get(2)));
            }

            JSONArray jsonDichvu = new JSONArray(jsonArray.getJSONObject(2).get(Config.GET_SERVICE_INFO.get(5)).toString());
            arrayDichVu = JsonHelper.parseJson(jsonDichvu.getJSONObject(0), Config.JSON_SERVICE_INFO);

            if (!jsonArray.getJSONObject(3).get(Config.GET_SERVICE_INFO.get(6)).toString().equals(Config.NULL)) {
                arrayLhsk = new JSONObject(jsonArray.getJSONObject(3).get(Config.GET_SERVICE_INFO.get(6)).toString())
                        .getString(Config.GET_SERVICE_INFO.get(7));
            } else {
                arrayLhsk = Config.NULL;
            }

            serviceInfo.setWebsite("Đang cập nhật");
            if (!arrayDichVu.get(1).equals(Config.NULL)) {
                serviceInfo.setHotelName(arrayDichVu.get(1));
                serviceInfo.setWebsite(arrayDichVu.get(2));
            } else if (!arrayDichVu.get(3).equals(Config.NULL)) {
                serviceInfo.setEntertainName(arrayDichVu.get(3));
            } else if (!arrayDichVu.get(4).equals(Config.NULL)) {
                serviceInfo.setVehicleName(arrayDichVu.get(4));
            } else if (!arrayDichVu.get(5).equals(Config.NULL)) {
                serviceInfo.setPlaceName(arrayDichVu.get(5));
            } else if (!arrayDichVu.get(6).equals(Config.NULL)) {
                serviceInfo.setEatName(arrayDichVu.get(6));
            }
            serviceInfo.setId(Integer.parseInt(arrayDichVu.get(0)));
            serviceInfo.setServiceAbout(arrayDichVu.get(7));
            serviceInfo.setTimeOpen(arrayDichVu.get(8));
            serviceInfo.setTimeClose(arrayDichVu.get(9));
            serviceInfo.setLowestPrice(arrayDichVu.get(10));
            serviceInfo.setHighestPrice(arrayDichVu.get(11));
            serviceInfo.setAddress(arrayDichVu.get(12));
            serviceInfo.setPhoneNumber(arrayDichVu.get(13));
            if (arrayDichVu.get(14).equals(Config.NULL)) {
                serviceInfo.setReviewMark((float) 0);
                serviceInfo.setStars(0);
            } else {
                serviceInfo.setReviewMark(Float.parseFloat(arrayDichVu.get(14)));
                serviceInfo.setStars(Float.parseFloat(arrayDichVu.get(14)));
            }
            serviceInfo.setLongitude(arrayDichVu.get(15));
            serviceInfo.setLatitude(arrayDichVu.get(16));

            serviceInfo.setEventType(arrayLhsk);
//            serviceInfo.setIdYeuThich(arrayDichVu.get(15));

            File path = new File(Environment.getExternalStorageDirectory() + Config.FOLDER_IMAGE);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, Config.FILE_NAME);

            boolean isLike = true;
            if (file.exists()) {
                JSONArray jsonFile = new JSONArray(JsonHelper.readJson(file));
                for (int i = 0; i < jsonFile.length(); i++) {
                    if (serviceInfo.getId() == Integer.parseInt(jsonFile.getJSONObject(i).getString("id"))) {
                        serviceInfo.setReviewUserFav(true);
                        isLike = false;
                    }
                }
            }
            if (isLike && arrayIdNdYt.size() > 0) {
                serviceInfo.setReviewUserFav(false);
                for (int i = 0; i < arrayIdNdYt.size(); i++) {
                    if (Integer.parseInt(arrayIdNdYt.get(i)) == userId) {
                        serviceInfo.setReviewUserFav(true);
                        serviceInfo.setIdYeuThich(arrayIdYt.get(i));
                    }
                }
            } else {
                serviceInfo.setReviewUserFav(false);
            }

            if (arrayIdNdDg.size() > 0) {
                serviceInfo.setReviewUserRev(false);
                serviceInfo.setReviewId("0");
                for (int i = 0; i < arrayIdNdDg.size(); i++) {
                    if (Integer.parseInt(arrayIdNdDg.get(i)) == userId) {
                        serviceInfo.setReviewUserRev(true);
                        serviceInfo.setReviewId(arrayIdDg.get(i));
                    }
                }
            } else {
                serviceInfo.setReviewId("0");
                serviceInfo.setReviewUserRev(false);
            }

            // lấy thông tin hình gồm : "url + id + tên hình"
            // xóa dấu " bằng replaceAll
            // tách theo dấu + ra 3 phân tử truyền vào hàm setImage
            String[] urlHinhChiTiet1 = null, urlHinhChiTiet2 = null, urlHinhBanner = null;
            try {
                urlHinhChiTiet1 = new Load().execute(Config.URL_HOST + Config.URL_GET_LINK_THUMB_1 + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
                urlHinhChiTiet2 = new Load().execute(Config.URL_HOST + Config.URL_GET_LINK_THUMB_2 + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
                urlHinhBanner = new Load().execute(Config.URL_HOST + Config.URL_GET_LINK_BANNER + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            serviceInfo.setIdImage(Integer.parseInt(urlHinhChiTiet1[1]));
            serviceInfo.setImageName(urlHinhChiTiet1[2]);
            serviceInfo.setThumbInfo1(setImage(Config.URL_HOST + urlHinhChiTiet1[0],
                    urlHinhChiTiet1[1], urlHinhChiTiet1[2]));
            serviceInfo.setThumbInfo2(setImage(Config.URL_HOST + urlHinhChiTiet2[0],
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

                service.setImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(3),
                        arrayList.get(2), arrayList.get(3)));
                service.setId(Integer.parseInt(arrayList.get(0)));
                service.setName(arrayList.get(1));

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
                service.setImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(3),
                        arrayList.get(2), arrayList.get(3)));
                service.setId(Integer.parseInt(arrayList.get(0)));
                service.setName(arrayList.get(1));

                services.add(service);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return services;
    }

    public ArrayList<Service> getFavoriteList(File file, String url) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            arr = JsonHelper.parseJsonNoId(new JSONObject(new Load().
                    execute(url).get()), Config.JSON_LOAD);
            JSONArray jsonArray;
            if (file.exists()) {
                jsonArray = JsonHelper.mergeJson(new JSONArray(arr.get(0)), new JSONArray(JsonHelper.readJson(file)));
            } else {
                jsonArray = new JSONArray(arr.get(0));
            }

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_FAVORITE);

                //Set hình ảnh
                service.setImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(7),
                        arrayList.get(6), arrayList.get(7)));
                //Set mã dịch vụ
                service.setId(Integer.parseInt(arrayList.get(0)));
                //Set tên dịch vụ yêu thích
                service.setName(!arrayList.get(1).equals(Config.NULL) ? arrayList.get(1) :
                        !arrayList.get(2).equals(Config.NULL) ? arrayList.get(2) :
                                !arrayList.get(3).equals(Config.NULL) ? arrayList.get(3) :
                                        !arrayList.get(4).equals(Config.NULL) ? arrayList.get(4) : arrayList.get(5));

                services.add(service);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return services;
    }

    public ArrayList<NearLocation> getNearLocationList(String url, int loaiHinh, Activity activity) {

        ArrayList<String> arrayList;
        ArrayList<NearLocation> services = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(new Load().execute(url).get());
            if (jsonArray.length() < 2) {
                Toast.makeText(activity, "Không tìm thấy dịch vụ lân cận", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < jsonArray.length(); i += 2) {

                    NearLocation service = new NearLocation();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (loaiHinh == 1) {
                        arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_EAT);
                    } else if (loaiHinh == 2) {
                        arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_HOTEL);
                    } else if (loaiHinh == 3) {
                        arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_VEHICLE);
                    } else if (loaiHinh == 4) {
                        arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_PLACE);
                    } else {
                        arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_ENTERTAINMENT);
                    }

                    //Set hình ảnh
                    service.setNearLocationImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(3),
                            arrayList.get(2), arrayList.get(3)));
                    //Set mã dịch vụ
                    service.setNearLocationId(Integer.parseInt(arrayList.get(0)));
                    //Set tên dịch vụ yêu thích
                    service.setNearLocationName(arrayList.get(1));
                    JSONObject jsonKC = jsonArray.getJSONObject(i + 1);
                    service.setNearLocationRadius(jsonKC.getString("khoangcach").toString());

                    services.add(service);
                }
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return services;
    }

    public ArrayList<Service> getAdvancedSearchList(String url, int loaiHinh) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            arr = JsonHelper.parseJsonNoId(new JSONObject(new Load().
                    execute(url).get()), Config.JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (loaiHinh == 1) {
                    arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_EAT);
                } else if (loaiHinh == 2) {
                    arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_HOTEL);
                } else if (loaiHinh == 3) {
                    arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_VEHICLE);
                } else if (loaiHinh == 4) {
                    arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_PLACE);
                } else {
                    arrayList = JsonHelper.parseJson(jsonObject, Config.JSON_ENTERTAINMENT);
                }

                //Set hình ảnh
                service.setImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(3),
                        arrayList.get(2), arrayList.get(3)));
                //Set mã dịch vụ
                service.setId(Integer.parseInt(arrayList.get(0)));
                //Set tên dịch vụ yêu thích
                service.setName(arrayList.get(1));

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
