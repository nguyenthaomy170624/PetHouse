/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class SanPham {
    private String maSP, tenSP, maLoai, moTa, anh;
    private double giaBan;
    private int soLuong;

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public SanPham(String maSP, String tenSP, double giaBan, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public SanPham(String maSP, String tenSP, String maLoai, double giaBan, int soLuong, String moTa, String anh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.anh = anh;
    }

    public String getMaSP() { return maSP; }
    public String getTenSP() { return tenSP; }
    public String getMaLoai() { return maLoai; }
    public double getGiaBan() { return giaBan; }
    public int getSoLuong() { return soLuong; }
    public String getMoTa() { return moTa; }
    public String getAnh() { return anh; }
}




