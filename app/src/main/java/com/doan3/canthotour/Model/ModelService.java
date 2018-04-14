package com.doan3.canthotour.Model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGetImage;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.ObjectClass.Event;
import com.doan3.canthotour.Model.ObjectClass.NearLocation;
import com.doan3.canthotour.Model.ObjectClass.Review;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.Model.ObjectClass.ServiceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.Helper.JsonHelper.mergeJson;
import static com.doan3.canthotour.Helper.JsonHelper.parseJson;
import static com.doan3.canthotour.Helper.JsonHelper.parseJsonNoId;
import static com.doan3.canthotour.Helper.JsonHelper.readJson;
import static com.doan3.canthotour.View.Personal.ActivityPersonal.userId;

/**
 * Created by sieut on 12/20/2017.
 */

public class ModelService {
    public static Bitmap setImage(String url, String folderName, String fileName) {
        Bitmap bitmap = null;
        // nếu có trả về tên hình + id hình để đặt tên cho file + folder
        if (!folderName.equals(Config.NULL) && !fileName.equals(Config.NULL)) {
            File path = new File(Environment.getExternalStorageDirectory().toString() + Config.FOLDER + "/" + folderName);
            path.mkdirs();
            File file = new File(path, fileName);
            if (file.exists()) {
                // nếu file đã tồn tại thì load lên
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeFile(file.toString(), options);
            } else {
                // nếu file không tồn tại thì tải hình về và lưu hình vào bộ nhớ
                try {
                    bitmap = new httpGetImage().execute(url).get();
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

        ArrayList<String> arrayService,
                arrayIdLike = new ArrayList<>(),
                arrayIdReview = new ArrayList<>(),
                arrayIdUserLike = new ArrayList<>(),
                arrayIdUserReview = new ArrayList<>();
        String stringNameOfTheEventType;
        ServiceInfo serviceInfo = new ServiceInfo();

        try {
            // get thông tin dịch vụ chuyển về dạng jsonarray
            String data = new httpGet().execute(url).get();
            JSONArray jsonArray = new JSONArray(data);

            // lấy thông tin yêu thích của dịch vụ chuyển vào array
            JSONArray jsonIdLike = new JSONArray(jsonArray.getJSONObject(0).get(Config.KEY_SERVICE_INFO.get(0)).toString());
            for (int i = 0; i < jsonIdLike.length(); i++) {
                arrayIdLike.add(jsonIdLike.getJSONObject(i).getString(Config.KEY_SERVICE_INFO.get(1)));
                arrayIdUserLike.add(jsonIdLike.getJSONObject(i).getString(Config.KEY_SERVICE_INFO.get(2)));
            }

            // lấy thông tin đánh giá của dịch vụ chuyển vào array
            JSONArray jsonIdReview = new JSONArray(jsonArray.getJSONObject(1).get(Config.KEY_SERVICE_INFO.get(3)).toString());
            for (int i = 0; i < jsonIdReview.length(); i++) {
                arrayIdReview.add(jsonIdReview.getJSONObject(i).getString(Config.KEY_SERVICE_INFO.get(4)));
                arrayIdUserReview.add(jsonIdReview.getJSONObject(i).getString(Config.KEY_SERVICE_INFO.get(2)));
            }

            // lấy thông tin chi tiết dịch vụ chuyển vào array
            JSONArray jsonService = new JSONArray(jsonArray.getJSONObject(2).get(Config.KEY_SERVICE_INFO.get(5)).toString());
            arrayService = parseJson(jsonService.getJSONObject(0), Config.GET_KEY_JSON_SERVICE_INFO);

            // nếu type_event != null thì lấy tên loại hình sự kiện ngược lại cho = null
            if (!jsonArray.getJSONObject(3).get(Config.KEY_SERVICE_INFO.get(6)).toString().equals(Config.NULL)) {
                stringNameOfTheEventType = new JSONObject(jsonArray.getJSONObject(3).get(Config.KEY_SERVICE_INFO.get(6)).toString())
                        .getString(Config.KEY_SERVICE_INFO.get(7));
            } else {
                stringNameOfTheEventType = Config.NULL;
            }

            // set id dịch vụ
            serviceInfo.setId(Integer.parseInt(arrayService.get(0)));
            // set tên dịch vụ
            if (!arrayService.get(1).equals(Config.NULL)) {
                serviceInfo.setHotelName(arrayService.get(1));
            } else if (!arrayService.get(2).equals(Config.NULL)) {
                serviceInfo.setEntertainName(arrayService.get(2));
            } else if (!arrayService.get(3).equals(Config.NULL)) {
                serviceInfo.setVehicleName(arrayService.get(3));
            } else if (!arrayService.get(4).equals(Config.NULL)) {
                serviceInfo.setPlaceName(arrayService.get(4));
            } else if (!arrayService.get(5).equals(Config.NULL)) {
                serviceInfo.setEatName(arrayService.get(5));
            }
            // set website
            serviceInfo.setWebsite(arrayService.get(6));
            // set giới thiệu
            serviceInfo.setServiceAbout(arrayService.get(7));
            // set giờ mở cửa
            serviceInfo.setTimeOpen(arrayService.get(8));
            // set giờ đóng cửa
            serviceInfo.setTimeClose(arrayService.get(9));
            // set giá thấp nhất
            serviceInfo.setLowestPrice(arrayService.get(10));
            // set giá cao nhất
            serviceInfo.setHighestPrice(arrayService.get(11));
            // set địa chỉ
            serviceInfo.setAddress(arrayService.get(12));
            // set số điện thoại
            serviceInfo.setPhoneNumber(arrayService.get(13));
            // set đánh giá
            // nếu rating == null người lại set số rating
            if (arrayService.get(14).equals(Config.NULL)) {
                serviceInfo.setReviewMark((float) 0);
                serviceInfo.setStars(0);
            } else {
                serviceInfo.setReviewMark(Float.parseFloat(arrayService.get(14)));
                serviceInfo.setStars(Float.parseFloat(arrayService.get(14)));
            }
            // set kinh độ
            serviceInfo.setLongitude(arrayService.get(15));
            // set vĩ độ
            serviceInfo.setLatitude(arrayService.get(16));
            // set loại hình sự kiện
            serviceInfo.setEventType(stringNameOfTheEventType);

            // khởi tạo đường dẫn tới file yêu thích
            File path = new File(Environment.getExternalStorageDirectory() + Config.FOLDER);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, Config.FILE_LIKE);

            boolean isLiked = false;
            // nếu file tồn tại thì đọc file lên
            if (file.exists()) {
                JSONArray jsonFile = new JSONArray(readJson(file));
                for (int i = 0; i < jsonFile.length(); i++) {
                    // nếu id dịch vụ == id dịch vụ trong file yêu thích => người dùng đã thích
                    if (serviceInfo.getId() == Integer.parseInt(jsonFile.getJSONObject(i).getString("id"))) {
                        serviceInfo.setReviewUserFav(true);
                        isLiked = true;
                    }
                }
            }
            // nếu người dùng chưa thích
            if (!isLiked && arrayIdUserLike.size() > 0) {
                serviceInfo.setReviewUserFav(false);
                for (int i = 0; i < arrayIdUserLike.size(); i++) {
                    if (Integer.parseInt(arrayIdUserLike.get(i)) == userId) {
                        serviceInfo.setReviewUserFav(true);
                        serviceInfo.setIdLike(arrayIdLike.get(i));
                    }
                }
            } else {
                serviceInfo.setReviewUserFav(false);
            }

            serviceInfo.setIdReview("0");
            serviceInfo.setReviewUserRev(false);
            //nếu có người dùng đánh giá
            if (arrayIdUserReview.size() > 0) {
                for (int i = 0; i < arrayIdUserReview.size(); i++) {
                    // nếu người dùng hiện tại có id = 1 id người dùng đã đánh giá trong mảng id người dùng đã đánh giá
                    if (userId == Integer.parseInt(arrayIdUserReview.get(i))) {
                        serviceInfo.setReviewUserRev(true);
                        serviceInfo.setIdReview(arrayIdReview.get(i));
                    }
                }
            }

            // lấy thông tin hình gồm : "url + id + tên hình"
            // xóa dấu " bằng replaceAll
            // tách theo dấu + ra 3 phân tử truyền vào hàm setImage
            String[] urlImageThumb1 = null, urlImageThumb2 = null, urlImageBanner = null;
            try {
                urlImageThumb1 = new httpGet().execute(Config.URL_HOST + Config.URL_GET_LINK_THUMB_1 + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
                urlImageThumb2 = new httpGet().execute(Config.URL_HOST + Config.URL_GET_LINK_THUMB_2 + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
                urlImageBanner = new httpGet().execute(Config.URL_HOST + Config.URL_GET_LINK_BANNER + serviceInfo.getId())
                        .get().replaceAll("\"", "").split("\\+");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            // set id hình
            serviceInfo.setIdImage(Integer.parseInt(urlImageThumb1[1]));
            // set tên hình
            serviceInfo.setImageName(urlImageThumb1[2]);
            // set hình
            serviceInfo.setThumbInfo1(setImage(Config.URL_HOST + urlImageThumb1[0],
                    urlImageThumb1[1], urlImageThumb1[2]));
            serviceInfo.setThumbInfo2(setImage(Config.URL_HOST + urlImageThumb2[0],
                    urlImageThumb2[1], urlImageThumb2[2]));
            serviceInfo.setBanner(setImage(Config.URL_HOST + urlImageBanner[0], urlImageBanner[1], urlImageBanner[2]));

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return serviceInfo;
    }

    public ArrayList<Service> getServiceList(String url, ArrayList<String> formatJson) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            String rs = new httpGet().execute(url).get();
            arr = parseJsonNoId(new JSONObject(rs), Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            int limit = jsonArray.length() > 5 ? 5 : jsonArray.length();

            for (int i = 0; i < limit; i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = parseJson(jsonObject, formatJson);

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
            arr = parseJsonNoId(new JSONObject(new httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = parseJson(jsonObject, formatJson);
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
            arr = parseJsonNoId(new JSONObject(new httpGet().
                    execute(url).get()), Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray;
            if (file.exists()) {
                jsonArray = mergeJson(new JSONArray(arr.get(0)), new JSONArray(readJson(file)));
            } else {
                jsonArray = new JSONArray(arr.get(0));
            }

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_FAVORITE);

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

    public ArrayList<NearLocation> getNearLocationList(String url, int type, Activity activity) {

        ArrayList<String> arrayList, keyJson;
        ArrayList<NearLocation> services = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(new httpGet().execute(url).get());
            // nếu status = not found thì hiển thị không tìm thấy
            if (jsonObject.getString(Config.GET_KEY_SEARCH_NEAR.get(1)).equals(Config.GET_KEY_SEARCH_NEAR.get(2))) {
                Toast.makeText(activity, "Không tìm thấy dịch vụ lân cận", Toast.LENGTH_SHORT).show();
            } else {
                // nếu tìm thấy thì thêm vào model nearlocation
                JSONArray jsonArray = jsonObject.getJSONArray(Config.GET_KEY_SEARCH_NEAR.get(0));
                for (int i = 0; i < jsonArray.length(); i += 2) {

                    NearLocation service = new NearLocation();
                    JSONObject jsonObjectData = jsonArray.getJSONObject(i);

                    /* nếu loại hình = 1 lấy key json ăn uống
                     nếu = 2 lấy key json khách sạn
                     nếu = 3 lấy key json phương tiện
                     nếu = 4 lấy key json tham quan
                     nếu = 5 lấy key json vui chơi*/
                    if (type == 1) {
                        keyJson = Config.GET_KEY_JSON_EAT;
                        keyJson.add(Config.KEY_DISTANCE);
                    } else if (type == 2) {
                        keyJson = Config.GET_KEY_JSON_HOTEL;
                        keyJson.add(Config.KEY_DISTANCE);
                    } else if (type == 3) {
                        keyJson = Config.GET_KEY_JSON_VEHICLE;
                        keyJson.add(Config.KEY_DISTANCE);
                    } else if (type == 4) {
                        keyJson = Config.GET_KEY_JSON_PLACE;
                        keyJson.add(Config.KEY_DISTANCE);
                    } else {
                        keyJson = Config.GET_KEY_JSON_ENTERTAINMENT;
                        keyJson.add(Config.KEY_DISTANCE);
                    }

                    arrayList = parseJson(jsonObjectData, keyJson);

                    //Set hình ảnh
                    service.setNearLocationImage(setImage(Config.URL_HOST + Config.URL_GET_THUMB + arrayList.get(3),
                            arrayList.get(2), arrayList.get(3)));
                    //Set mã dịch vụ
                    service.setNearLocationId(Integer.parseInt(arrayList.get(0)));
                    //Set tên dịch vụ
                    service.setNearLocationName(arrayList.get(1));
                    // set khoảng cách
                    service.setNearLocationDistance(arrayList.get(4));

                    services.add(service);
                }
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return services;
    }

    public ArrayList<Service> getAdvancedSearchList(String url, int type) {

        ArrayList<String> arr, arrayList;
        ArrayList<Service> services = new ArrayList<>();

        try {
            arr = parseJsonNoId(new JSONObject(new httpGet().
                    execute(url).get()), Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Service service = new Service();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (type == 1) {
                    arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_EAT);
                } else if (type == 2) {
                    arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_HOTEL);
                } else if (type == 3) {
                    arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_VEHICLE);
                } else if (type == 4) {
                    arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_PLACE);
                } else {
                    arrayList = parseJson(jsonObject, Config.GET_KEY_JSON_ENTERTAINMENT);
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
            //Sử dụng parseJsonNoId vì KEY_JSON_LOAD ko có id
            arr = parseJsonNoId(new JSONObject(new httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
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

    public ArrayList<Review> getReviewList(String url, ArrayList<String> formatJson) {

        ArrayList<String> arr, arrayList;
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            arr = parseJsonNoId(new JSONObject(new httpGet().execute(url).get()),
                    Config.GET_KEY_JSON_LOAD);
            JSONArray jsonArray = new JSONArray(arr.get(0));

            for (int i = 0; i < jsonArray.length(); i++) {

                Review review = new Review();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList = parseJsonNoId(jsonObject, formatJson);
                review.setUserName(arrayList.get(0));
                review.setStars(Float.parseFloat(arrayList.get(1)));
                review.setTitle(arrayList.get(2));
                review.setReview(arrayList.get(3));
                review.setDateReview(arrayList.get(4));

                reviews.add(review);
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
