package com.doan3.canthotour.Model.ObjectClass;

/**
 * Created by zzacn on 12/14/2017.
 */

public class NearLocation {
    private int maDiaDiemLC;
    private String tenDiaDiemLC;
    private String khoangCachLC;
    private int hinhDiaDiemLC;

    public NearLocation(int maDiaDiemLC, String tenDiaDiemLC, String khoangCachLC, int hinhDiaDiemLC) {
        this.maDiaDiemLC = maDiaDiemLC;
        this.tenDiaDiemLC = tenDiaDiemLC;
        this.khoangCachLC = khoangCachLC;
        this.hinhDiaDiemLC = hinhDiaDiemLC;
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

    public int getHinhDiaDiemLC() {
        return hinhDiaDiemLC;
    }

    public void setHinhDiaDiemLC(int hinhDiaDiemLC) {
        this.hinhDiaDiemLC = hinhDiaDiemLC;
    }
}
