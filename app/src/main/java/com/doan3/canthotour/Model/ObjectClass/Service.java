package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 11/21/2017.
 */

public class Service {
    private int ma;
    private Bitmap hinh;
    private String ten;

    public Service() {

    }

    public Service(int ma, Bitmap hinh, String ten) {
        this.ma = ma;
        this.hinh = hinh;
        this.ten = ten;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public Bitmap getHinh() {
        return hinh;
    }

    public void setHinh(Bitmap hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
