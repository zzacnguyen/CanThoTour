package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 12/14/2017.
 */

public class NearLocation {
    private String tenDiaDiemLC;
    private String khoangCachLC;
    private int hinhDiaDiemLC;

    public NearLocation(String tenDiaDiemLC, String khoangCachLC, int hinhDiaDiemLC) {
        this.tenDiaDiemLC = tenDiaDiemLC;
        this.khoangCachLC = khoangCachLC;
        this.hinhDiaDiemLC = hinhDiaDiemLC;
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
