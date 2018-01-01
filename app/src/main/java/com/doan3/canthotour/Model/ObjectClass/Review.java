package com.doan3.canthotour.Model.ObjectClass;

/**
 * Created by zzacn on 12/14/2017.
 */

public class Review {
    private String tenNguoiDung, DanhGia, ngayDanhGia, tieuDe;
    private Float soSao;

    public Review() {
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNgayDanhGia() {
        return ngayDanhGia;
    }

    public void setNgayDanhGia(String ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }

    public Float getSoSao() {
        return soSao;
    }

    public void setSoSao(Float soSao) {
        this.soSao = soSao;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(String danhGia) {
        DanhGia = danhGia;
    }
}
