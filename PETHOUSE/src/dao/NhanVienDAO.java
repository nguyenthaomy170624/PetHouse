package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import csdl.DatabaseConnection;

public class NhanVienDAO {

    public List<String> getDanhSachMaNV() throws SQLException {
        List<String> danhSachMaNV = new ArrayList<>();
        String sql = "SELECT MaNV FROM NhanVien";

        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                danhSachMaNV.add(rs.getString("MaNV"));
            }
        }

        return danhSachMaNV;
    }

    public boolean themNhanVien(NhanVien nv) throws SQLException {
        String sql = "INSERT INTO NhanVien VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getSoDienThoai());
            ps.setString(7, nv.getVaiTro());
            ps.setString(8, nv.getEmail());
            ps.setString(9, nv.getCccd());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean capNhatNhanVien(NhanVien nv) throws SQLException {
        String sql = "UPDATE NhanVien SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, SoDienThoai = ?, VaiTro = ?, Email = ?, CCCD = ? WHERE MaNV = ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getSoDienThoai());
            ps.setString(6, nv.getVaiTro());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getCccd());
            ps.setString(9, nv.getMaNV());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean xoaNhanVien(String maNV) throws SQLException {
        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        }
    }

    public NhanVien getNhanVienTheoMa(String maNV) throws SQLException {
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNV);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToNhanVien(rs);
                }
            }
        }
        return null;
    }

    public List<NhanVien> getAllNhanVien() throws SQLException {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToNhanVien(rs));
            }
        }
        return list;
    }

    public List<NhanVien> timNhanVienTheoMa(String maNV) throws SQLException {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE MaNV LIKE ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + maNV + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToNhanVien(rs));
                }
            }
        }
        return list;
    }

    public List<NhanVien> timNhanVienTheoTen(String ten) throws SQLException {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE HoTen LIKE ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + ten + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToNhanVien(rs));
                }
            }
        }
        return list;
    }

    public List<NhanVien> timNhanVienTheoVaiTro(String vaiTro) throws SQLException {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE VaiTro LIKE ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + vaiTro + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToNhanVien(rs));
                }
            }
        }
        return list;
    }

    public List<NhanVien> timNhanVienTatCa(String tuKhoa) throws SQLException {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE MaNV LIKE ? OR HoTen LIKE ? OR VaiTro LIKE ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            String key = "%" + tuKhoa + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToNhanVien(rs));
                }
            }
        }
        return list;
    }

    private NhanVien mapResultSetToNhanVien(ResultSet rs) throws SQLException {
        return new NhanVien(
                rs.getString("MaNV"),
                rs.getString("HoTen"),
                rs.getString("GioiTinh"),
                rs.getDate("NgaySinh"),
                rs.getString("DiaChi"),
                rs.getString("SoDienThoai"),
                rs.getString("VaiTro"),
                rs.getString("Email"),
                rs.getString("CCCD")
        );
    }

    public String getTenNhanVien(String maNV) {
        String sql = "SELECT HoTen FROM NhanVien WHERE MaNV = ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("HoTen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public NhanVien findByMaNV(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
        try ( Connection con = DatabaseConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToNhanVien(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
