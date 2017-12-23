package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 11/17/2017.
 */

public class PlaceInfo {
    Bitmap banner, chiTiet1Thumb, chiTiet2Thumb;
    private int id, idND;
    private String ten, gioiThieu, diaChi, sdt, kinhDo, viDo, tenSK;

    public Bitmap getBanner() {
        return banner;
    }

    public void setBanner(Bitmap banner) {
        this.banner = banner;
    }

    public Bitmap getChiTiet1Thumb() {
        return chiTiet1Thumb;
    }

    public void setChiTiet1Thumb(Bitmap chiTiet1Thumb) {
        this.chiTiet1Thumb = chiTiet1Thumb;
    }

    public Bitmap getChiTiet2Thumb() {
        return chiTiet2Thumb;
    }

    public void setChiTiet2Thumb(Bitmap chiTiet2Thumb) {
        this.chiTiet2Thumb = chiTiet2Thumb;
    }

    public String getTenSK() {
        return tenSK;
    }

    public void setTenSK(String tenSK) {
        this.tenSK = tenSK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdND() {
        return idND;
    }

    public void setIdND(int idND) {
        this.idND = idND;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(String kinhDo) {
        this.kinhDo = kinhDo;
    }

    public String getViDo() {
        return viDo;
    }

    public void setViDo(String viDo) {
        this.viDo = viDo;
    }
}
