package model;

import java.util.Date;

public class DuLieuHoaDon {
    private String maHD;
    private Date ngayLap;
    private String maKH;
    private String hoTenKH;
    private String hoTenNV;
    private String maSP;
    private String tenSP;
    private int soLuong;
    private double donGia;
    private String ghiChu;
    private double tongTien;
    private String diaChi;
    private String soDienThoai;
    private double thanhTien;

    public DuLieuHoaDon(String maHD, Date ngayLap, String maKH, String hoTenKH, String hoTenNV,
            String maSP, String tenSP, int soLuong, double donGia, String ghiChu,
            double tongTien, String diaChi, String soDienThoai, double thanhTien) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.maKH = maKH;
        this.hoTenKH = hoTenKH;
        this.hoTenNV = hoTenNV;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.ghiChu = ghiChu;
        this.tongTien = tongTien;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.thanhTien = thanhTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getHoTenKH() {
        return hoTenKH;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public double getThanhTien() {
        return thanhTien;
    }
}