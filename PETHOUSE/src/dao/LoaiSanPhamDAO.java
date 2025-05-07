/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import csdl.DatabaseConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LoaiSanPhamDAO {
    public Map<String, String> getAllLoaiSanPham() {
        Map<String, String> loaiMap = new HashMap<>();
        String sql = "SELECT MaLoai, TenLoai FROM LoaiSanPham";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String maLoai = rs.getString("MaLoai");
                String tenLoai = rs.getString("TenLoai");
                loaiMap.put(tenLoai, maLoai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loaiMap;
    }
}
