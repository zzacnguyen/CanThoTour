package com.doan3.canthotour.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by zzacn on 11/14/2017.
 */

public class JsonHelper {

    //region user
    // parse json user
    public static ArrayList<String> parseJsonUser(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("nd_tendoanhnghiep"));
            stringJson.add(json.getString("nd_tendangnhap"));
            stringJson.add(json.getString("nd_sodienthoai"));
            stringJson.add(json.getString("nd_matkhau"));
            stringJson.add(json.getString("nd_website"));
            stringJson.add(json.getString("nd_email"));
            stringJson.add(json.getString("nd_diachi"));
            stringJson.add(json.getString("nd_quocgia"));
            stringJson.add(json.getString("nd_ngonngu"));
            stringJson.add(json.getString("nd_ghichu"));
            stringJson.add(json.getString("nd_loainguoidung"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonUser(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("nd_tendoanhnghiep"));
                stringJson.add(json.getJSONObject(i).getString("nd_tendangnhap"));
                stringJson.add(json.getJSONObject(i).getString("nd_sodienthoai"));
                stringJson.add(json.getJSONObject(i).getString("nd_matkhau"));
                stringJson.add(json.getJSONObject(i).getString("nd_website"));
                stringJson.add(json.getJSONObject(i).getString("nd_email"));
                stringJson.add(json.getJSONObject(i).getString("nd_diachi"));
                stringJson.add(json.getJSONObject(i).getString("nd_quocgia"));
                stringJson.add(json.getJSONObject(i).getString("nd_ngonngu"));
                stringJson.add(json.getJSONObject(i).getString("nd_ghichu"));
                stringJson.add(json.getJSONObject(i).getString("nd_loainguoidung"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    //region eat
    // parse json eat
    public static ArrayList<String> parseJsonEat(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("au_ten"));
            stringJson.add(json.getString("au_gioithieu"));
            stringJson.add(json.getString("dv_iddichvu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonEat(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("au_ten"));
                stringJson.add(json.getJSONObject(i).getString("au_gioithieu"));
                stringJson.add(json.getJSONObject(i).getString("dv_iddichvu"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region rate
    // parse json rate
    public static ArrayList<String> parseJsonRate(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("dv_iddichvu"));
            stringJson.add(json.getString("nd_idnguoidung"));
            stringJson.add(json.getString("dg_diem"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonRate(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("dv_iddichvu"));
                stringJson.add(json.getJSONObject(i).getString("nd_idnguoidung"));
                stringJson.add(json.getJSONObject(i).getString("dg_diem"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region comment
    // parse json comment
    public static ArrayList<String> parseJsonComment(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("bl_binhluan"));
            stringJson.add(json.getString("bl_trangthai"));
            stringJson.add(json.getString("dv_iddichvu"));
            stringJson.add(json.getString("nd_idnguoidung"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonComment(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("bl_binhluan"));
                stringJson.add(json.getJSONObject(i).getString("bl_trangthai"));
                stringJson.add(json.getJSONObject(i).getString("dv_iddichvu"));
                stringJson.add(json.getJSONObject(i).getString("nd_idnguoidung"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region place
    // parse json place
    public static ArrayList<String> parseJsonPlace(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("dd_tendiadiem"));
            stringJson.add(json.getString("dd_gioithieu"));
            stringJson.add(json.getString("dn_diachi"));
            stringJson.add(json.getString("dd_sodienthoai"));
            stringJson.add(json.getString("dd_kinhdo"));
            stringJson.add(json.getString("dd_vido"));
            stringJson.add(json.getString("nd_idnguoidung"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonPlace(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("dd_tendiadiem"));
                stringJson.add(json.getJSONObject(i).getString("dd_gioithieu"));
                stringJson.add(json.getJSONObject(i).getString("dn_diachi"));
                stringJson.add(json.getJSONObject(i).getString("dd_sodienthoai"));
                stringJson.add(json.getJSONObject(i).getString("dd_kinhdo"));
                stringJson.add(json.getJSONObject(i).getString("dd_vido"));
                stringJson.add(json.getJSONObject(i).getString("nd_idnguoidung"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region schedule detail
    // parse json schedule detail
    public static ArrayList<String> parseJsonScheduleDetail(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("ctlt_gioithieu"));
            stringJson.add(json.getString("ctlt_ngaygiodukien"));
            stringJson.add(json.getString("lt_idlichtrinh"));
            stringJson.add(json.getString("dd_iddiadiem"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonScheduleDetail(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("ctlt_gioithieu"));
                stringJson.add(json.getJSONObject(i).getString("ctlt_ngaygiodukien"));
                stringJson.add(json.getJSONObject(i).getString("lt_idlichtrinh"));
                stringJson.add(json.getJSONObject(i).getString("dd_iddiadiem"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region service
    // parse json service
    public static ArrayList<String> parseJsonService(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("dv_gioithieu"));
            stringJson.add(json.getString("dv_giomocua"));
            stringJson.add(json.getString("dv_giodongcua"));
            stringJson.add(json.getString("dv_giacaonhat"));
            stringJson.add(json.getString("dv_giathapnhat"));
            stringJson.add(json.getString("dd_iddiadiem"));
            stringJson.add(json.getString("dv_trangthai"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonService(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("dv_gioithieu"));
                stringJson.add(json.getJSONObject(i).getString("dv_giomocua"));
                stringJson.add(json.getJSONObject(i).getString("dv_giodongcua"));
                stringJson.add(json.getJSONObject(i).getString("dv_giacaonhat"));
                stringJson.add(json.getJSONObject(i).getString("dv_giathapnhat"));
                stringJson.add(json.getJSONObject(i).getString("dd_iddiadiem"));
                stringJson.add(json.getJSONObject(i).getString("dv_trangthai"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region image
    // parse json image
    public static ArrayList<String> parseJsonImage(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("ha_hinhanh1"));
            stringJson.add(json.getString("ha_hinhanh2"));
            stringJson.add(json.getString("ha_hinhanh3"));
            stringJson.add(json.getString("ha_thumb"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonImage(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh1"));
                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh2"));
                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh3"));
                stringJson.add(json.getJSONObject(i).getString("ha_thumb"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region hotel
    // parse json hotel
    public static ArrayList<String> parseJsonHotel(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("ks_tenkhachsan"));
            stringJson.add(json.getString("ks_website"));
            stringJson.add(json.getString("ks_gioithieu"));
            stringJson.add(json.getString("dv_iddichvu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonHotel(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("ks_tenkhachsan"));
                stringJson.add(json.getJSONObject(i).getString("ks_website"));
                stringJson.add(json.getJSONObject(i).getString("ks_gioithieu"));
                stringJson.add(json.getJSONObject(i).getString("dv_iddichvu"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region schedule
    // parse json schedule
    public static ArrayList<String> parseJsonSchedule(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("lt_ngaylichtrinh"));
            stringJson.add(json.getString("lt_tenlichtrinh"));
            stringJson.add(json.getString("lt_gioithieu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonSchedule(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("lt_ngaylichtrinh"));
                stringJson.add(json.getJSONObject(i).getString("lt_tenlichtrinh"));
                stringJson.add(json.getJSONObject(i).getString("lt_gioithieu"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region type of event
    // parse json type of event
    public static ArrayList<String> parseJsonTypeOfEvent(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getInt("id") + "");
            stringJson.add(json.getString("lhsk_ten"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonTypeOfEvent(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getInt("id") + "");
                stringJson.add(json.getJSONObject(i).getString("lhsk_ten"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region vehicle
    // parse json vehicle
    public static ArrayList<String> parseJsonVehicle(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("pt_tenphuongtien"));
            stringJson.add(json.getString("pt_loaihinh"));
            stringJson.add(json.getString("dv_iddichvu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonVehicle(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("pt_tenphuongtien"));
                stringJson.add(json.getJSONObject(i).getString("pt_loaihinh"));
                stringJson.add(json.getJSONObject(i).getString("dv_iddichvu"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region event
    // parse json event
    public static ArrayList<String> parseJsonEvent(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("sk_tensukien"));
            stringJson.add(json.getString("sk_ngaybatdau"));
            stringJson.add(json.getString("sk_trangthai"));
            stringJson.add(json.getString("dd_iddiadiem"));
            stringJson.add(json.getString("lhsk_idloaihinhsukien"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonEvent(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("sk_tensukien"));
                stringJson.add(json.getJSONObject(i).getString("sk_ngaybatdau"));
                stringJson.add(json.getJSONObject(i).getString("sk_trangthai"));
                stringJson.add(json.getJSONObject(i).getString("dd_iddiadiem"));
                stringJson.add(json.getJSONObject(i).getString("lhsk_idloaihinhsukien"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region tour
    // parse json tour
    public static ArrayList<String> parseJsonTour(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("tq_tendiemthamquan"));
            stringJson.add(json.getString("tq_gioithieu"));
            stringJson.add(json.getString("dv_iddichvu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonTour(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("tq_tendiemthamquan"));
                stringJson.add(json.getJSONObject(i).getString("tq_gioithieu"));
                stringJson.add(json.getJSONObject(i).getString("dv_iddichvu"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

    // region entertainment
    // parse json entertainment
    public static ArrayList<String> parseJsonEntertainment(JSONObject json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            stringJson.add(json.getString("id"));
            stringJson.add(json.getString("vc_tendiemvuichoi"));
            stringJson.add(json.getString("vc_gioithieu"));
            stringJson.add(json.getString("dv_iddichvu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    public static ArrayList<String> parseJsonEntertainment(JSONArray json){
        ArrayList<String> stringJson = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                stringJson.add(json.getJSONObject(i).getString("id"));
                stringJson.add(json.getJSONObject(i).getString("vc_tendiemvuichoi"));
                stringJson.add(json.getJSONObject(i).getString("vc_gioithieu"));
                stringJson.add(json.getJSONObject(i).getString("dv_iddichvu"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringJson;
    }
    // endregion

//    // parse json image
//    public static ArrayList<String> parseJsonImage(JSONObject json){
//        ArrayList<String> stringJson = new ArrayList<>();
//        try {
//            stringJson.add(json.getString("id"));
//            stringJson.add(json.getString("ha_hinhanh1"));
//            stringJson.add(json.getString("ha_hinhanh2"));
//            stringJson.add(json.getString("ha_hinhanh3"));
//            stringJson.add(json.getString("ha_thumb"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return stringJson;
//    }
//    public static ArrayList<String> parseJsonImage(JSONArray json){
//        ArrayList<String> stringJson = new ArrayList<>();
//        try {
//            for (int i = 0; i < json.length(); i++) {
//                stringJson.add(json.getJSONObject(i).getString("id"));
//                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh1"));
//                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh2"));
//                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh3"));
//                stringJson.add(json.getJSONObject(i).getString("ha_thumb"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return stringJson;
//    }
//
//    // parse json image
//    public static ArrayList<String> parseJsonImage(JSONObject json){
//        ArrayList<String> stringJson = new ArrayList<>();
//        try {
//            stringJson.add(json.getString("id"));
//            stringJson.add(json.getString("ha_hinhanh1"));
//            stringJson.add(json.getString("ha_hinhanh2"));
//            stringJson.add(json.getString("ha_hinhanh3"));
//            stringJson.add(json.getString("ha_thumb"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return stringJson;
//    }
//    public static ArrayList<String> parseJsonImage(JSONArray json){
//        ArrayList<String> stringJson = new ArrayList<>();
//        try {
//            for (int i = 0; i < json.length(); i++) {
//                stringJson.add(json.getJSONObject(i).getString("id"));
//                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh1"));
//                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh2"));
//                stringJson.add(json.getJSONObject(i).getString("ha_hinhanh3"));
//                stringJson.add(json.getJSONObject(i).getString("ha_thumb"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return stringJson;
//    }
}
