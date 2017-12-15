package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 12/14/2017.
 */

public class Event {
    private String tenSk;
    private String ngaySk;
    private int hinhSk;

    public Event(String tenSk, String ngaySk, int hinhSk) {
        this.tenSk = tenSk;
        this.ngaySk = ngaySk;
        this.hinhSk = hinhSk;
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

    public int getHinhSk() {
        return hinhSk;
    }

    public void setHinhSk(int hinhSk) {
        this.hinhSk = hinhSk;
    }
}
