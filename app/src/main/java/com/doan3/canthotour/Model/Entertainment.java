package com.doan3.canthotour.Model;


public class Entertainment {
    private int maVC;
    private int hinhVC;
    private String tenVC;

    public Entertainment(int maVC, int hinhVC, String tenVC) {
        this.maVC = maVC;
        this.hinhVC = hinhVC;
        this.tenVC = tenVC;
    }

    public int getMaVC() {
        return maVC;
    }

    public void setMaVC(int maVC) {
        this.maVC = maVC;
    }

    public int getHinhVC() {
        return hinhVC;
    }

    public void setHinhVC(int hinhVC) {
        this.hinhVC = hinhVC;
    }

    public String getTenVC() {
        return tenVC;
    }

    public void setTenVC(String tenVC) {
        this.tenVC = tenVC;
    }
}
