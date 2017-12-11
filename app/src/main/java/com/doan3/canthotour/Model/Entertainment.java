package com.doan3.canthotour.Model;


public class Entertainment {
    private int hinhVC;
    private String tenVC;
    private String diaChiVC;

    public Entertainment(int hinhVC, String tenVC) {
        this.hinhVC = hinhVC;
        this.tenVC = tenVC;
    }

    public Entertainment(int hinhVC, String tenVC, String diaChiVC) {
        this.hinhVC = hinhVC;
        this.tenVC = tenVC;
        this.diaChiVC = diaChiVC;
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

    public String getDiaChiVC() {
        return diaChiVC;
    }

    public void setDiaChiVC(String diaChiVC) {
        this.diaChiVC = diaChiVC;
    }
}
