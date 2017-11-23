package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 11/21/2017.
 */

public class Hotel {
    private int hinhKS;
    private String tenKS;

    public Hotel(int hinhKS, String tenKS) {
        this.hinhKS = hinhKS;
        this.tenKS = tenKS;
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
