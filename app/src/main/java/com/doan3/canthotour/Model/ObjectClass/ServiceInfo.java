package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 11/17/2017.
 */

public class ServiceInfo {
    private int id, idDV, idDD;
    private String ten, gioiThieu, gioiThieuDV, gioDongCua, gioMoCua, giaCaoNhat, giaThapNhat, trangThai, diaChi, sdt, website;
    private Bitmap banner, chiTiet1, chiTiet1Thumb, chiTiet2, chiTiet2Thumb;

    public Bitmap getChiTiet1() {
        return chiTiet1;
    }

    public void setChiTiet1(Bitmap chiTiet1) {
        this.chiTiet1 = chiTiet1;
    }

    public Bitmap getChiTiet1Thumb() {
        return chiTiet1Thumb;
    }

    public void setChiTiet1Thumb(Bitmap chiTiet1Thumb) {
        this.chiTiet1Thumb = chiTiet1Thumb;
    }

    public Bitmap getChiTiet2() {
        return chiTiet2;
    }

    public void setChiTiet2(Bitmap chiTiet2) {
        this.chiTiet2 = chiTiet2;
    }

    public Bitmap getChiTiet2Thumb() {
        return chiTiet2Thumb;
    }

    public void setChiTiet2Thumb(Bitmap chiTiet2Thumb) {
        this.chiTiet2Thumb = chiTiet2Thumb;
    }

    public Bitmap getBanner() {
        return banner;
    }

    public void setBanner(Bitmap banner) {
        this.banner = banner;
    }

    public Bitmap getchiTiet1() {
        return chiTiet1;
    }

    public void setchiTiet1(Bitmap chiTiet1) {
        this.chiTiet1 = chiTiet1;
    }

    public Bitmap getchiTiet2() {
        return chiTiet2;
    }

    public void setchiTiet2(Bitmap chiTiet2) {
        this.chiTiet2 = chiTiet2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    public int getIdDV() {
        return idDV;
    }

    public void setIdDV(int idDV) {
        this.idDV = idDV;
    }

    public int getIdDD() {
        return idDD;
    }

    public void setIdDD(int idDD) {
        this.idDD = idDD;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public String getGioiThieuDV() {
        return gioiThieuDV;
    }

    public void setGioiThieuDV(String gioiThieuDV) {
        this.gioiThieuDV = gioiThieuDV;
    }

    public String getGioDongCua() {
        return gioDongCua;
    }

    public void setGioDongCua(String gioDongCua) {
        this.gioDongCua = gioDongCua;
    }

    public String getGioMoCua() {
        return gioMoCua;
    }

    public void setGioMoCua(String gioMoCua) {
        this.gioMoCua = gioMoCua;
    }

    public String getGiaCaoNhat() {
        return giaCaoNhat;
    }

    public void setGiaCaoNhat(String giaCaoNhat) {
        this.giaCaoNhat = giaCaoNhat;
    }

    public String getGiaThapNhat() {
        return giaThapNhat;
    }

    public void setGiaThapNhat(String giaThapNhat) {
        this.giaThapNhat = giaThapNhat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
