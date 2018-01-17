package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 12/14/2017.
 */

public class NearLocation {
    private int nearLocationId;
    private String nearLocationName;
    private String nearLocationRadius;
    private Bitmap nearLocationImage;

    public NearLocation() {
    }

    public int getNearLocationId() {
        return nearLocationId;
    }

    public void setNearLocationId(int nearLocationId) {
        this.nearLocationId = nearLocationId;
    }

    public String getNearLocationName() {
        return nearLocationName;
    }

    public void setNearLocationName(String nearLocationName) {
        this.nearLocationName = nearLocationName;
    }

    public String getNearLocationRadius() {
        return nearLocationRadius;
    }

    public void setNearLocationRadius(String nearLocationRadius) {
        this.nearLocationRadius = nearLocationRadius;
    }

    public Bitmap getNearLocationImage() {
        return nearLocationImage;
    }

    public void setNearLocationImage(Bitmap nearLocationImage) {
        this.nearLocationImage = nearLocationImage;
    }
}
