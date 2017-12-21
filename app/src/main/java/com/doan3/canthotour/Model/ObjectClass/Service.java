package com.doan3.canthotour.Model.ObjectClass;

/**
 * Created by zzacn on 11/21/2017.
 */

public class Service {
    private int id;
    private int hinh;
    private String ten;

    public Service() {

    }

    public Service(int ma, int hinh, String ten) {
        this.id = ma;
        this.hinh = hinh;
        this.ten = ten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
