package dao;

import csdl.DatabaseConnection;
import model.HoaDon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDao {

    public boolean insert(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (MaHD, MaKH, MaNV, NgayLap, TongTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hd.getMaHD());
            ps.setString(2, hd.getMaKH());
            ps.setString(3, hd.getMaNV());
            ps.setDate(4, hd.getNgayLap());
            ps.setDouble(5, hd.getTongTien());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setNgayLap(rs.getDate("NgayLap"));
                hd.setTongTien(rs.getDouble("TongTien"));
                list.add(hd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(String maHD) {
        String sql = "DELETE FROM HoaDon WHERE MaHD = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHD);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(HoaDon hd) {
        String sql = "UPDATE HoaDon SET MaKH=?, MaNV=?, NgayLap=?, TongTien=? WHERE MaHD=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hd.getMaKH());
            ps.setString(2, hd.getMaNV());
            ps.setDate(3, hd.getNgayLap());
            ps.setDouble(4, hd.getTongTien());
            ps.setString(5, hd.getMaHD());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HoaDon findByMaHD(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE MaHD = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new HoaDon(
                    rs.getString("MaHD"),
                    rs.getString("MaKH"),
                    rs.getString("MaNV"),
                    rs.getDate("NgayLap"),
                    rs.getDouble("TongTien")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
