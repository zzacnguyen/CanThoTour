package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 11/17/2017.
 */

public class Place {
    private int maDD;
    private int hinhDD;
    private String tenDD;

    public Place() {

    }

    public Place(int maDD, int hinhDD, String tenDD) {
        this.maDD = maDD;
        this.hinhDD = hinhDD;
        this.tenDD = tenDD;
    }

    public int getMaDD() {
        return maDD;
    }

    public void setMaDD(int maDD) {
        this.maDD = maDD;
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
}
