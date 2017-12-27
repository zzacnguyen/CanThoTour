package com.doan3.canthotour.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by zzacn on 11/17/2017.
 */

public class ServiceInfo {
    Bitmap banner, chiTiet1Thumb, chiTiet2Thumb;
    private int id, idHinh;
    private String tenAU, tenKS, tenTQ, tenPT, tenVC, gioiThieuDV, tenHinh,
            gioDongCua, gioMoCua, giaCaoNhat, giaThapNhat, diaChi, sdt, website, lhsk, idNguoiDung, idYeuThich;

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(String idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getIdYeuThich() {
        return idYeuThich;
    }

    public void setIdYeuThich(String idYeuThich) {
        this.idYeuThich = idYeuThich;
    }

    public int getIdHinh() {
        return idHinh;
    }

    public void setIdHinh(int idHinh) {
        this.idHinh = idHinh;
    }

    public String getTenHinh() {
        return tenHinh;
    }

    public void setTenHinh(String tenHinh) {
        this.tenHinh = tenHinh;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenAU() {
        return tenAU;
    }

    public void setTenAU(String tenAU) {
        this.tenAU = tenAU;
    }

    public String getTenKS() {
        return tenKS;
    }

    public void setTenKS(String tenKS) {
        this.tenKS = tenKS;
    }

    public String getTenTQ() {
        return tenTQ;
    }

    public void setTenTQ(String tenTQ) {
        this.tenTQ = tenTQ;
    }

    public String getTenPT() {
        return tenPT;
    }

    public void setTenPT(String tenPT) {
        this.tenPT = tenPT;
    }

    public String getTenVC() {
        return tenVC;
    }

    public void setTenVC(String tenVC) {
        this.tenVC = tenVC;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLhsk() {
        return lhsk;
    }

    public void setLhsk(String lhsk) {
        this.lhsk = lhsk;
    }
}
