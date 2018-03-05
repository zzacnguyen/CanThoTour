package com.doan3.canthotour;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    public static final String URL_HOST = "http://192.168.1.114/doan3_canthotour/public/";
    public static final String URL_LOGIN = "login";
    public static final String URL_REGISTER = "dangky";
    public static final String URL_SEARCH = "timkiem/dichvunangcao/idtype=";
    public static final String URL_SEARCH_ALL = "timkiem/dichvuall/keyword=";
    public static final String URL_GET_ALL_EATS = "eating";
    public static final String URL_GET_ALL_REVIEWS = "visitor-ratings";
    public static final String URL_GET_ALL_PLACES = "sightseeing";
    public static final String URL_GET_ALL_HOTELS = "hotels";
    public static final String URL_GET_ALL_SERVICES = "service";
    public static final String URL_GET_ALL_VEHICLES = "transport";
    public static final String URL_GET_ALL_EVENTS = "events";
    public static final String URL_GET_ALL_ENTERTAINMENTS = "entertainments";
    public static final String URL_GET_ALL_FAVORITE = "like";
    public static final String URL_GET_LINK_THUMB_1 = "get-thumb-1/";
    public static final String URL_GET_LINK_THUMB_2 = "get-thumb-2/";
    public static final String URL_GET_LINK_BANNER = "get-banner/";
    public static final String URL_GET_THUMB = "thumbnails/";

    public static final String FOLDER_IMAGE = "/vietnamtour";
    public static final String FILE_NAME = "dsyeuthich.json";
    public static final String NULL = "null";

    public static final ArrayList<String> GET_SERVICE_INFO =
            new ArrayList<>(Arrays.asList("like", "like_id", "user_id", "rating", "id_rating", "service", "type_event", "tpe_name"));

    public static final ArrayList<String> ADD_PLACE =
            new ArrayList<>(Arrays.asList("pl_name", "pl_details", "pl_address", "pl_phone_number", "pl_latitude", "pl_longtitude", "user_id"));

    public static final ArrayList<String> JSON_EAT =
            new ArrayList<>(Arrays.asList("eat_name", "image_id", "image_details_1"));

    public static final ArrayList<String> JSON_USER =
            new ArrayList<>(Arrays.asList("username", "user_status", "user_avatar"));

    public static final ArrayList<String> JSON_PLACE =
            new ArrayList<>(Arrays.asList("sightseeing_name", "image_id", "image_details_1"));

    public static final ArrayList<String> JSON_SERVICE_INFO =
            new ArrayList<>(Arrays.asList("hotel_name", "hotel_website", "entertainments_name", "transport_name",
                    "sightseeing_name", "eat_name", "sv_description", "sv_open", "sv_close", "sv_lowest_price",
                    "sv_highest_price", "pl_address", "pl_phone_number", "rating", "pl_latitude", "pl_longitude"));

    public static final ArrayList<String> JSON_HOTEL =
            new ArrayList<>(Arrays.asList("hotel_name", "image_id", "image_details_1"));

    public static final ArrayList<String> JSON_VEHICLE =
            new ArrayList<>(Arrays.asList("transport_name", "image_id", "image_details_1"));

    public static final ArrayList<String> JSON_EVENT =
            new ArrayList<>(Arrays.asList("event_name", "event_start", "event_end",
                    "image_id", "image_details_1"));

    public static final ArrayList<String> JSON_ENTERTAINMENT =
            new ArrayList<>(Arrays.asList("entertainments_name", "image_id", "image_details_1"));

    public static final ArrayList<String> JSON_FAVORITE =
            new ArrayList<>(Arrays.asList("hotel_name", "entertainments_name", "transport_name",
                    "sightseeing_name", "eat_name", "image_id", "image_details_1"));

    public static final ArrayList<String> JSON_LOAD =
            new ArrayList<>(Arrays.asList("data", "next_page_url", "total"));

    public static final ArrayList<String> JSON_REVIEW =
            new ArrayList<>(Arrays.asList("username", "vr_rating", "vr_title", "vr_ratings_details", "date_rating"));
    public static final ArrayList<String> JSON_RATE =
            new ArrayList<>(Arrays.asList("vr_rating", "vr_title", "vr_ratings_details"));
}
