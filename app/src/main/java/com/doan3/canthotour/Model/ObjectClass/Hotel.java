package com.doan3.canthotour.Model.ObjectClass;

/**
 * Created by zzacn on 11/21/2017.
 */

public class Hotel {
    private int maKS;
    private int hinhKS;
    private String tenKS;

    public Hotel(int maKS, int hinhKS, String tenKS) {
        this.maKS = maKS;
        this.hinhKS = hinhKS;
        this.tenKS = tenKS;
    }

    public int getMaKS() {
        return maKS;
    }

    public void setMaKS(int maKS) {
        this.maKS = maKS;
    }

    public int getHinhKS() {
        return hinhKS;
    }

    public void setHinhKS(int hinhKS) {
        this.hinhKS = hinhKS;
    }

    public String getTenKS() {
        return tenKS;
    }

    public void setTenKS(String tenKS) {
        this.tenKS = tenKS;
    }
}
