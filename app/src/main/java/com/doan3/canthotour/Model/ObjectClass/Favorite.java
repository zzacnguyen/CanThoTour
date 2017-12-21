package com.doan3.canthotour.Model.ObjectClass;


public class Favorite {
    private int maYT;
    private int hinhYT;
    private String tenYT;

    public Favorite(int maYT, int hinhYT, String tenYT) {
        this.maYT = maYT;
        this.hinhYT = hinhYT;
        this.tenYT = tenYT;
    }

    public int getMaYT() {
        return maYT;
    }

    public void setMaYT(int maYT) {
        this.maYT = maYT;
    }

    public int getHinhYT() {
        return hinhYT;
    }

    public void setHinhYT(int hinhYT) {
        this.hinhYT = hinhYT;
    }

    public String getTenYT() {
        return tenYT;
    }

    public void setTenYT(String tenYT) {
        this.tenYT = tenYT;
    }
}
