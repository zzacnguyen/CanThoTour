package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 11/17/2017.
 */

public class Place {
    private int hinhDD;
    private String tenDD;
    private String diaChiDD;

    public Place(int hinhDD, String tenDD) {
        this.hinhDD = hinhDD;
        this.tenDD = tenDD;
    }

    public Place(int hinhDD, String tenDD, String diaChiDD) {
        this.hinhDD = hinhDD;
        this.tenDD = tenDD;
        this.diaChiDD = diaChiDD;
    }

    public int getHinhDD() {
        return hinhDD;
    }

    public void setHinhDD(int hinhDD) {
        this.hinhDD = hinhDD;
    }

    public String getTenDD() {
        return tenDD;
    }

    public void setTenDD(String tenDD) {
        this.tenDD = tenDD;
    }

    public String getDiaChiDD() { return diaChiDD; }

    public void setMoTaDD(String moTaDD) { this.diaChiDD = diaChiDD; }
}
