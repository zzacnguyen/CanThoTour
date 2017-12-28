package com.doan3.canthotour;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    public static final String URL_HOST = "http://192.168.1.15/doan3_canthotour/public/";
    public static final String URL_GET_ALL_TYPE_OF_EVENTS = "loaihinhsukien";
    public static final String URL_GET_ALL_EATS = "anuong";
    public static final String URL_GET_ALL_COMMENTS = "binhluan";
    public static final String URL_GET_ALL_SCHEDULE_DETAILS = "chitietlichtrinh";
    public static final String URL_GET_ALL_RATES = "danhgia";
    public static final String URL_GET_ALL_PLACES = "thamquan";
    public static final String URL_GET_ALL_HOTELS = "khachsan";
    public static final String URL_GET_ALL_SCHEDULES = "lichtrinh";
    public static final String URL_GET_ALL_SERVICES = "dichvu";
    public static final String URL_GET_ALL_USERS = "nguoidung";
    public static final String URL_GET_ALL_VEHICLES = "phuongtien";
    public static final String URL_GET_ALL_EVENTS = "sukien";
    public static final String URL_GET_ALL_ENTERTAINMENTS = "vuichoi";
    public static final String URL_GET_ALL_FAVORITE = "yeuthich";
    public static final String URL_GET_ICON = "lay-mot-hinh-icon/";
    public static final String URL_GET_THUMB_1 = "lay-mot-hinh-thumb-1/";
    public static final String URL_GET_THUMB_2 = "lay-mot-hinh-thumb-2/";
    public static final String URL_GET_BANNER = "lay-mot-hinh-banner/";

    public static final ArrayList<String> JSON_USER =
            new ArrayList<String>(Arrays.asList("nd_tendoanhnghiep", "nd_tendangnhap", "nd_sodienthoai", "nd_matkhau",
                    "nd_website", "nd_email", "nd_diachi", "nd_quocgia", "nd_ngonngu", "nd_ghichu", "nd_loainguoidung"));

    public static final ArrayList<String> JSON_EAT =
            new ArrayList<String>(Arrays.asList("au_ten", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_COMMENT =
            new ArrayList<String>(Arrays.asList("bl_binhluan", "bl_trangthai", "dv_iddichvu", "nd_idnguoidung"));

    public static final ArrayList<String> JSON_PLACE =
            new ArrayList<String>(Arrays.asList("tq_tendiemthamquan", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_SERVICE_INFO =
            new ArrayList<String>(Arrays.asList("ks_tenkhachsan", "ks_website", "vc_tendiemvuichoi", "pt_tenphuongtien",
                    "tq_tendiemthamquan", "au_ten", "dv_gioithieu", "dv_giomocua", "dv_giodongcua", "dv_giathapnhat",
                    "dv_giacaonhat", "dd_diachi", "dd_sodienthoai", "lhsk_ten", "id_nd_yeuthich_dv", "id_yeuthich"));

    public static final ArrayList<String> JSON_HOTEL =
            new ArrayList<String>(Arrays.asList("ks_tenkhachsan", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_VEHICLE =
            new ArrayList<String>(Arrays.asList("pt_tenphuongtien", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_EVENT =
            new ArrayList<String>(Arrays.asList("sk_tensukien", "sk_ngaybatdau", "sk_ngayketthuc",
                    "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_ENTERTAINMENT =
            new ArrayList<String>(Arrays.asList("vc_tendiemvuichoi", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_FAVORITE =
            new ArrayList<String>(Arrays.asList("ks_tenkhachsan", "vc_tendiemvuichoi", "pt_tenphuongtien",
                    "tq_tendiemthamquan", "au_ten", "id_hinhanh", "chitiet1"));

    public static final ArrayList<String> JSON_LOAD =
            new ArrayList<String>(Arrays.asList("data", "next_page_url", "total"));

    public static final ArrayList<String> JSON_NEAR_LOCATION =
            new ArrayList<String>(Arrays.asList("ks_tenkhachsan", "vc_tendiemvuichoi", "pt_tenphuongtien",
                    "au_ten", "id_hinhanh", "chitiet1"));
}
