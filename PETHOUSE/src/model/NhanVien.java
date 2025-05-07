package model;

import java.util.Date;

public class NhanVien {
    private String maNV, hoTen, gioiTinh, diaChi, soDienThoai, vaiTro, email, cccd;
    private Date ngaySinh;

    public NhanVien() {
     
    }

    public NhanVien(String maNV, String hoTen, String gioiTinh, Date ngaySinh,
                    String diaChi, String soDienThoai, String vaiTro, String email, String cccd) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.vaiTro = vaiTro;
        this.email = email;
        this.cccd = cccd;
    }

    // Getters
    public String getMaNV() {
        return maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public String getEmail() {
        return email;
    }

    public String getCccd() {
        return cccd;
    }

    // Setters
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}
