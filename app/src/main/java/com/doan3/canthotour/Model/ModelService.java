package com.doan3.canthotour.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGet;
import com.doan3.canthotour.Adapter.HttpRequestAdapter.httpGetImage;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.ObjectClass.Event;
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

        ArrayList<String> arrayService;
        String stringNameOfTheEventType;
        ServiceInfo serviceInfo = new ServiceInfo();
        Boolean isLike, isRating;

        try {
            // get thông tin dịch vụ chuyển về dạng jsonarray
            String data = new httpGet().execute(url).get();
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonResult = jsonArray.getJSONObject(0);

            // lây thông tin người dùng đã thích dịch vụ hay chưa
            isLike = jsonResult.getString(Config.KEY_SERVICE_INFO.get(0)).equals("1");

            // lây thông tin người dùng đã đánh giá dịch vụ hay chưa
            isRating = jsonResult.getString(Config.KEY_SERVICE_INFO.get(3)).equals("1");

            // lấy thông tin chi tiết dịch vụ chuyển vào array
            JSONArray jsonService = new JSONArray(jsonResult.getString(Config.KEY_SERVICE_INFO.get(6)));
            arrayService = parseJson(jsonService.getJSONObject(0), Config.GET_KEY_JSON_SERVICE_INFO);

            // nếu type_event != null thì lấy tên loại hình sự kiện ngược lại cho = null
            if (jsonResult.getString(Config.KEY_SERVICE_INFO.get(7)).equals(Config.NULL)) {
                stringNameOfTheEventType = Config.NULL;
            } else {
                stringNameOfTheEventType = new JSONObject(jsonResult.get(Config.KEY_SERVICE_INFO.get(7)).toString())
                        .getString(Config.KEY_SERVICE_INFO.get(8));
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
            // nếu rating == null thì set số sao = 0;
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
                        serviceInfo.setIsLike(true);
                        isLiked = true;
                    }
                }
            }
            // nếu trong file yêu thích chưa có thì xem trong csdl đã thích hay chưa
            if (!isLiked) {
                if (isLike) {
                    serviceInfo.setIsLike(true);
                    JSONObject jsonIdLike = new JSONObject(jsonResult.getString(Config.KEY_SERVICE_INFO.get(1)));
                    serviceInfo.setIdLike(jsonIdLike.getString(Config.KEY_SERVICE_INFO.get(2)));
                } else {
                    serviceInfo.setIsLike(false);
                    serviceInfo.setIdLike("0");
                }
            }

            if (isRating) {
                serviceInfo.setIsRating(true);
                JSONObject jsonIdRating = new JSONObject(jsonResult.getString(Config.KEY_SERVICE_INFO.get(4)));
                serviceInfo.setIdRating(jsonIdRating.getString(Config.KEY_SERVICE_INFO.get(5)));
            } else {
                serviceInfo.setIsRating(false);
                serviceInfo.setIdRating("0");
            }

            // set số lượt like
            serviceInfo.setCountLike(Integer.parseInt(jsonResult.getString(Config.KEY_SERVICE_INFO.get(9))));
            System.out.println(Integer.parseInt(jsonResult.getString(Config.KEY_SERVICE_INFO.get(9))));

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
            if (urlImageThumb1 != null) {
                serviceInfo.setIdImage(Integer.parseInt(urlImageThumb1[1]));

                // set tên hình
                serviceInfo.setImageName(urlImageThumb1[2]);

                // set hình 1
                serviceInfo.setThumbInfo1(setImage(Config.URL_HOST + urlImageThumb1[0],
                        urlImageThumb1[1], urlImageThumb1[2]));
            }
            // set hình 2
            if (urlImageThumb2 != null) {
                serviceInfo.setThumbInfo2(setImage(Config.URL_HOST + urlImageThumb2[0],
                        urlImageThumb2[1], urlImageThumb2[2]));
            }
            // set hình banner
            if (urlImageBanner != null) {
                serviceInfo.setBanner(setImage(Config.URL_HOST + urlImageBanner[0], urlImageBanner[1], urlImageBanner[2]));
            }

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return serviceInfo;
    }

    public ArrayList<Service> getServiceInMain(String url, ArrayList<String> formatJson) {

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

    public ArrayList<Service> getFullServiceList(String url, ArrayList<String> formatJson) {

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
