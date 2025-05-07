/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Date;

public class CTHoaDon {

    private String maHD, maSP, ghiChu;
    private int soLuong;
    private double donGia;
    private Date ngayLap;
    private String tenSanPham;
    private double thanhTien;
    public CTHoaDon() {
    }

    public CTHoaDon(String maHoaDon, Date ngayLap, String tenSanPham, int soLuong, double donGia, double thanhTien) {
        this.maHD = maHoaDon;
        this.ngayLap = ngayLap;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public CTHoaDon(String maHD, int soLuong, double donGia, Date ngayLap, String tenSanPham, double thanhTien) {
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.ngayLap = ngayLap;
        this.tenSanPham = tenSanPham;
        this.thanhTien = thanhTien;
    }

   

    

    public Date getNgayLap() {
        return ngayLap;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public CTHoaDon(String maHD, String maSP, int soLuong, double donGia) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return soLuong * donGia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
    
}
