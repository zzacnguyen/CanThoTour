package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 11/21/2017.
 */

public class Service {
    private int id;
    private Bitmap image;
    private String name;

    public Service() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
