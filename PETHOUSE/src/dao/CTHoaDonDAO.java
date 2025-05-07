/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import csdl.DatabaseConnection;
import model.CTHoaDon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CTHoaDonDAO {

    public boolean insert(CTHoaDon ct) {
        String sql = "INSERT INTO CTHoaDon (MaHD, MaSP, SoLuong, DonGia, GhiChu) VALUES (?, ?, ?, ?, ?)";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaHD());
            ps.setString(2, ct.getMaSP());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());
            ps.setString(5, ct.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(CTHoaDon ct) {
        String sql = "UPDATE CTHoaDon SET SoLuong=?, DonGia=? WHERE MaHD=? AND MaSP=?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ct.getSoLuong());
            ps.setDouble(2, ct.getDonGia());
            ps.setString(3, ct.getMaHD());
            ps.setString(4, ct.getMaSP());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maHD, String maSP) {
        String sql = "DELETE FROM CTHoaDon WHERE MaHD=? AND MaSP=?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ps.setString(2, maSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<CTHoaDon> getByMaHD(String maHD) {
        List<CTHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM CTHoaDon WHERE MaHD=?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CTHoaDon ct = new CTHoaDon();
                    ct.setMaHD(rs.getString("MaHD"));
                    ct.setMaSP(rs.getString("MaSP"));
                    ct.setSoLuong(rs.getInt("SoLuong"));
                    ct.setDonGia(rs.getDouble("DonGia"));
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public CTHoaDon findByMaHDAndMaSP(String maHD, String maSP) {
        String sql = "SELECT * FROM CTHoaDon WHERE MaHD=? AND MaSP=?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ps.setString(2, maSP);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CTHoaDon ct = new CTHoaDon();
                    ct.setMaHD(rs.getString("MaHD"));
                    ct.setMaSP(rs.getString("MaSP"));
                    ct.setSoLuong(rs.getInt("SoLuong"));
                    ct.setDonGia(rs.getDouble("DonGia"));
                    return ct;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double calculateTotalForInvoice(String maHD) {
        String sql = "SELECT SUM(SoLuong * DonGia) AS TongTien FROM CTHoaDon WHERE MaHD=?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("TongTien");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
