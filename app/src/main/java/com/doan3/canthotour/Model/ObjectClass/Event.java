package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 12/14/2017.
 */

public class Event {
    private int maSk;
    private String tenSk;
    private String ngaySk;
    private Bitmap hinhSk;

    public Event(){

    }

    public int getMaSk() {
        return maSk;
    }

    public void setMaSk(int maSk) {
        this.maSk = maSk;
    }

    public String getTenSk() {
        return tenSk;
    }

    public void setTenSk(String tenSk) {
        this.tenSk = tenSk;
    }

    public String getNgaySk() {
        return ngaySk;
    }

    public void setNgaySk(String ngaySk) {
        this.ngaySk = ngaySk;
    }

    public Bitmap getHinhSk() {
        return hinhSk;
    }

    public void setHinhSk(Bitmap hinhSk) {
        this.hinhSk = hinhSk;
    }
}
