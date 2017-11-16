package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 11/17/2017.
 */

public class DiaDiem {
    private int hinhDD;
    private String tenDD;

    public DiaDiem(int hinhDD, String tenDD) {
        this.hinhDD = hinhDD;
        this.tenDD = tenDD;
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
