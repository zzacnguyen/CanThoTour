package com.doan3.canthotour.Model;

/**
 * Created by zzacn on 11/21/2017.
 */

public class Eat {
    private int hinhAU;
    private String tenAU;

    public Eat(int hinhAU, String tenAU) {
        this.hinhAU = hinhAU;
        this.tenAU = tenAU;
    }

    public int getHinhAU() {
        return hinhAU;
    }

    public void setHinhAU(int hinhAU) {
        this.hinhAU = hinhAU;
    }

    public String getTenAU() {
        return tenAU;
    }

    public void setTenAU(String tenAU) {
        this.tenAU = tenAU;
    }
}
