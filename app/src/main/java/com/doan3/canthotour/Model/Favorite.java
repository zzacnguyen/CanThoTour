package com.doan3.canthotour.Model;


public class Favorite {
    private int hinhYT;
    private String tenYT;

    public Favorite(int hinhYT, String tenYT) {
        this.hinhYT = hinhYT;
        this.tenYT = tenYT;
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
