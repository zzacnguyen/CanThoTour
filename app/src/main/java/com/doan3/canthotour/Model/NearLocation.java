package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 12/14/2017.
 */

public class NearLocation {
    private String tenDiaDiemLC;
    private int hinhDiaDiemLC;

    public NearLocation(String tenDiaDiemLC, int hinhDiaDiemLC) {
        this.tenDiaDiemLC = tenDiaDiemLC;
        this.hinhDiaDiemLC = hinhDiaDiemLC;
    }

    public String getTenDiaDiemLC() {
        return tenDiaDiemLC;
    }

    public void setTenDiaDiemLC(String tenDiaDiemLC) {
        this.tenDiaDiemLC = tenDiaDiemLC;
    }

    public int getHinhDiaDiemLC() {
        return hinhDiaDiemLC;
    }

    public void setHinhDiaDiemLC(int hinhDiaDiemLC) {
        this.hinhDiaDiemLC = hinhDiaDiemLC;
    }
}
