package com.doan3.canthotour;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    public static final String URL_HOST = "http://192.168.1.15/doan3_canthotour/public/";
    public static final String URL_LOGIN = "login";
    public static final String URL_REGISTER = "dangky";
    public static final String URL_SEARCH_ALL = "timkiem/dichvuall/keyword=";
    public static final String URL_GET_ALL_EATS = "anuong";
    public static final String URL_GET_ALL_REVIEWS = "danhgia";
    public static final String URL_GET_ALL_PLACES = "thamquan";
    public static final String URL_GET_ALL_HOTELS = "khachsan";
    public static final String URL_GET_ALL_SERVICES = "dichvu";
    public static final String URL_GET_ALL_VEHICLES = "phuongtien";
    public static final String URL_GET_ALL_EVENTS = "sukien";
    public static final String URL_GET_ALL_ENTERTAINMENTS = "vuichoi";
    public static final String URL_GET_ALL_FAVORITE = "yeuthich";
    //    public static final String URL_GET_ICON = "lay-mot-hinh-icon/";
    public static final String URL_GET_THUMB_1 = "lay-mot-hinh-thumb-1/";
    public static final String URL_GET_THUMB_2 = "lay-mot-hinh-thumb-2/";
    public static final String URL_GET_BANNER = "lay-mot-hinh-banner/";

    public static final ArrayList<String> JSON_EAT =
            new ArrayList<>(Arrays.asList("au_ten", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_USER =
            new ArrayList<>(Arrays.asList("nd_tendangnhap", "nd_loainguoidung", "nd_avatar"));

    public static final ArrayList<String> JSON_PLACE =
            new ArrayList<>(Arrays.asList("tq_tendiemthamquan", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_SERVICE_INFO =
            new ArrayList<>(Arrays.asList("ks_tenkhachsan", "ks_website", "vc_tendiemvuichoi", "pt_tenphuongtien",
                    "tq_tendiemthamquan", "au_ten", "dv_gioithieu", "dv_giomocua", "dv_giodongcua", "dv_giathapnhat",
                    "dv_giacaonhat", "dd_diachi", "dd_sodienthoai", "danhgia", "dd_kinhdo", "dd_vido"));

    public static final ArrayList<String> JSON_HOTEL =
            new ArrayList<>(Arrays.asList("ks_tenkhachsan", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_VEHICLE =
            new ArrayList<>(Arrays.asList("pt_tenphuongtien", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_EVENT =
            new ArrayList<>(Arrays.asList("sk_tensukien", "sk_ngaybatdau", "sk_ngayketthuc",
                    "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_ENTERTAINMENT =
            new ArrayList<>(Arrays.asList("vc_tendiemvuichoi", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_FAVORITE =
            new ArrayList<>(Arrays.asList("ks_tenkhachsan", "vc_tendiemvuichoi", "pt_tenphuongtien",
                    "tq_tendiemthamquan", "au_ten", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_LOAD =
            new ArrayList<>(Arrays.asList("data", "next_page_url", "total"));

    public static final ArrayList<String> JSON_REVIEW =
            new ArrayList<>(Arrays.asList("nd_tendangnhap", "dg_diem", "dg_tieude", "dg_noidung", "ngaydanhgia"));
    public static final ArrayList<String> JSON_RATE =
            new ArrayList<>(Arrays.asList("dg_diem", "dg_tieude", "dg_noidung"));
}
