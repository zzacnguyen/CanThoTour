package com.doan3.canthotour;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    public static final String URL_HOST = "http://192.168.1.2/doan3/public/";
    public static final String URL_GET_ALL_TYPE_OF_EVENTS = "loaihinhsukien";
    public static final String URL_GET_ALL_EATS = "anuong";
    public static final String URL_GET_ALL_COMMENTS = "binhluan";
    public static final String URL_GET_ALL_SCHEDULE_DETAILS = "chitietlichtrinh";
    public static final String URL_GET_ALL_RATES = "danhgia";
    public static final String URL_GET_ALL_PLACES = "diadiem";
    public static final String URL_GET_ALL_SERVICES = "dichvu";
    public static final String URL_GET_ALL_IMAGES = "hinhanh";
    public static final String URL_GET_ALL_HOTELS = "khachsan";
    public static final String URL_GET_ALL_SCHEDULES = "lichtrinh";
    public static final String URL_GET_ALL_USERS = "nguoidung";
    public static final String URL_GET_ALL_VEHICLES = "phuongtien";
    public static final String URL_GET_ALL_EVENTS = "sukien";
    public static final String URL_GET_ALL_TOURS = "thamquan";
    public static final String URL_GET_ALL_ENTERTAINMENTS = "vuichoi";
    public static final String URL_GET_ALL_FAVORITE = "yeuthich";

    public static final ArrayList<String> JSON_USER =
            new ArrayList<String>(Arrays.asList("nd_tendoanhnghiep", "nd_tendangnhap", "nd_sodienthoai", "nd_matkhau",
                    "nd_website", "nd_email", "nd_diachi", "nd_quocgia", "nd_ngonngu", "nd_ghichu", "nd_loainguoidung"));

    public static final ArrayList<String> JSON_EAT =
            new ArrayList<String>(Arrays.asList("au_ten", "au_gioithieu", "dv_iddichvu"));

    public static final ArrayList<String> JSON_COMMENT =
            new ArrayList<String>(Arrays.asList("bl_binhluan", "bl_trangthai", "dv_iddichvu", "nd_idnguoidung"));

    public static final ArrayList<String> JSON_SCHEDULE_DETAIL =
            new ArrayList<String>(Arrays.asList("ctlt_gioithieu", "ctlt_ngaygiodukien", "lt_idlichtrinh", "dd_iddiadiem"));

    public static final ArrayList<String> JSON_RATE =
            new ArrayList<String>(Arrays.asList("dv_iddichvu", "nd_idnguoidung", "dg_diem"));

    public static final ArrayList<String> JSON_PLACE =
            new ArrayList<String>(Arrays.asList("dd_tendiadiem", "dd_gioithieu", "dn_diachi",
                    "dd_sodienthoai", "dd_kinhdo", "dd_vido", "nd_idnguoidung"));

    public static final ArrayList<String> JSON_SERVICE =
            new ArrayList<String>(Arrays.asList("dv_gioithieu", "dv_giomocua", "dv_giodongcua",
                    "dv_giacaonhat", "dv_giathapnhat", "dv_trangthai", "dd_iddiadiem"));

    public static final ArrayList<String> JSON_IMAGE =
            new ArrayList<String>(Arrays.asList("ha_hinhanh1", "ha_hinhanh2", "ha_hinhanh3", "ha_thumb", "ha_icon"));

    public static final ArrayList<String> JSON_HOTEL =
            new ArrayList<String>(Arrays.asList("ks_tenkhachsan", "ks_gioithieu", "dv_iddichvu", "ks_website"));

    public static final ArrayList<String> JSON_SHEDULE =
            new ArrayList<String>(Arrays.asList("lt_ngaylichtrinh", "lt_tenlichtrinh", "lt_gioithieu"));

    public static final ArrayList<String> JSON_TYPE_OF_EVENT =
            new ArrayList<String>(Arrays.asList("lhsk_ten"));

    public static final ArrayList<String> JSON_VEHICLE =
            new ArrayList<String>(Arrays.asList("pt_tenphuongtien", "pt_loaihinh", "dv_iddichvu"));

    public static final ArrayList<String> JSON_EVENT =
            new ArrayList<String>(Arrays.asList("sk_tensukien", "sk_ngaybatdau", "sk_ngayketthuc",
                    "dd_iddiadiem", "lhsk_idloaihinhsukien"));

    public static final ArrayList<String> JSON_TOUR =
            new ArrayList<String>(Arrays.asList("tq_tendiemthamquan", "tq_gioithieu", "dv_iddichvu"));

    public static final ArrayList<String> JSON_ENTERTAINMENT =
            new ArrayList<String>(Arrays.asList("vc_tendiemvuichoi", "vc_gioithieu", "dv_iddichvu"));

    public static final ArrayList<String> JSON_FAVORITE =
            new ArrayList<String>(Arrays.asList("dd_iddiadiem", "nd_idnguoidung"));

    public static final ArrayList<String> JSON_LOAD =
            new ArrayList<String>(Arrays.asList("data", "next_page_url", "total"));

    public static final ArrayList<String> JSON_NEAR_LOCATION =
            new ArrayList<String>(Arrays.asList("dd_tendiadiem","khoangcach", "vido", "kinhdo"));
}
