/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import java.util.*;
import model.SanPham;
import csdl.DatabaseConnection;

public class SanPhamDAO {
    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getString("MaSP"), rs.getString("TenSP"), rs.getString("MaLoai"),
                    rs.getDouble("GiaBan"), rs.getInt("SoLuong"), rs.getString("MoTa"), rs.getString("Anh")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void themSanPham(SanPham sp) {
        String sql = "INSERT INTO SanPham (MaSP, TenSP, MaLoai, GiaBan, SoLuong, MoTa, Anh) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getMaLoai());
            ps.setDouble(4, sp.getGiaBan());
            ps.setInt(5, sp.getSoLuong());
            ps.setString(6, sp.getMoTa());
            ps.setString(7, sp.getAnh());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void capNhatSanPham(SanPham sp) {
        String sql = "UPDATE SanPham SET TenSP=?, MaLoai=?, GiaBan=?, SoLuong=?, MoTa=?, Anh=? WHERE MaSP=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setString(2, sp.getMaLoai());
            ps.setDouble(3, sp.getGiaBan());
            ps.setInt(4, sp.getSoLuong());
            ps.setString(5, sp.getMoTa());
            ps.setString(6, sp.getAnh());
            ps.setString(7, sp.getMaSP());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xoaSanPham(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
public List<SanPham> timSanPhamTheoLoai(String maLoai) {
    List<SanPham> list = new ArrayList<>();
    String sql = "SELECT * FROM SanPham WHERE MaLoai = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, maLoai);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getString("MaSP"), rs.getString("TenSP"), rs.getString("MaLoai"),
                    rs.getDouble("GiaBan"), rs.getInt("SoLuong"), rs.getString("MoTa"), rs.getString("Anh")
                );
                list.add(sp);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    public List<SanPham> timSanPhamTheoTen(String ten) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + ten + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SanPham sp = new SanPham(
                        rs.getString("MaSP"), rs.getString("TenSP"), rs.getString("MaLoai"),
                        rs.getDouble("GiaBan"), rs.getInt("SoLuong"), rs.getString("MoTa"), rs.getString("Anh")
                    );
                    list.add(sp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public SanPham findByMaSP(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE MaSP = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SanPham(
                        rs.getString("MaSP"), rs.getString("TenSP"), rs.getString("MaLoai"),
                        rs.getDouble("GiaBan"), rs.getInt("SoLuong"), rs.getString("MoTa"), rs.getString("Anh")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
