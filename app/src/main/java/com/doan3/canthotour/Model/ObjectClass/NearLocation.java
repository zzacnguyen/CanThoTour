package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 12/14/2017.
 */

public class NearLocation {
    private int maDiaDiemLC;
    private String tenDiaDiemLC;
    private String khoangCachLC;
    private Bitmap hinhDiaDiemLC;

    public NearLocation() {
    }

    public int getMaDiaDiemLC() {
        return maDiaDiemLC;
    }

    public void setMaDiaDiemLC(int maDiaDiemLC) {
        this.maDiaDiemLC = maDiaDiemLC;
    }

    public String getTenDiaDiemLC() {
        return tenDiaDiemLC;
    }

    public void setTenDiaDiemLC(String tenDiaDiemLC) {
        this.tenDiaDiemLC = tenDiaDiemLC;
    }

    public String getKhoangCachLC() {
        return khoangCachLC;
    }

    public void setKhoangCachLC(String khoangCachLC) {
        this.khoangCachLC = khoangCachLC;
    }

    public Bitmap getHinhDiaDiemLC() {
        return hinhDiaDiemLC;
    }

    public void setHinhDiaDiemLC(Bitmap hinhDiaDiemLC) {
        this.hinhDiaDiemLC = hinhDiaDiemLC;
    }
}
