package dao;

import csdl.DatabaseConnection;
import model.ThongKe;
import model.CTHoaDon; 
import java.util.Date;
import java.sql.*;
import java.util.*;

public class ThongKeDAO {

    public List<CTHoaDon> getMaHD(String maHoaDon) throws SQLException {
        List<CTHoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.MaHD, hd.NgayLap, sp.TenSP, ct.SoLuong, ct.DonGia, (ct.SoLuong * ct.DonGia) AS ThanhTien " +
                     "FROM HoaDon hd " +
                     "JOIN CTHoaDon ct ON hd.MaHD = ct.MaHD " +
                     "JOIN SanPham sp ON ct.MaSP = sp.MaSP " +
                     "WHERE hd.MaHD = ? " +
                     "ORDER BY hd.NgayLap";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maHD = rs.getString("MaHD");
                Date ngayLap = rs.getDate("NgayLap");
                String tenSanPham = rs.getString("TenSP");
                int soLuong = rs.getInt("SoLuong");
                double donGia = rs.getDouble("DonGia");
                double thanhTien = rs.getDouble("ThanhTien");
                list.add(new CTHoaDon(maHD, ngayLap, tenSanPham, soLuong, donGia, thanhTien));
            }
        }
        return list;
    }
    public List<ThongKe> thongKeTheoNgay() throws SQLException {
        String sql = "SELECT NgayLap, SUM(TongTien) AS TongDoanhThu FROM HoaDon GROUP BY NgayLap ORDER BY NgayLap";
        return layDanhSachThongKe(sql, "NgayLap");
    }

    public List<ThongKe> thongKeTheoSanPham() throws SQLException {
        String sql = "SELECT sp.TenSP, SUM(ct.SoLuong * ct.DonGia) AS TongDoanhThu " +
                     "FROM CTHoaDon ct " +
                     "JOIN SanPham sp ON ct.MaSP = sp.MaSP " +
                     "GROUP BY sp.TenSP";
        return layDanhSachThongKe(sql, "TenSP");
    }

    public List<ThongKe> thongKeTheoTaiKhoan() throws SQLException {
        String sql = "SELECT tk.TenDangNhap, SUM(hd.TongTien) AS TongDoanhThu " +
                     "FROM HoaDon hd " +
                     "JOIN TaiKhoan tk ON hd.MaNV = tk.MaNV " +
                     "GROUP BY tk.TenDangNhap";
        return layDanhSachThongKe(sql, "TenDangNhap");
    }

    public List<ThongKe> thongKeTheoThangNam(int thang, int nam) throws SQLException {
        String sql = "SELECT MONTH(NgayLap) AS Thang, YEAR(NgayLap) AS Nam, SUM(TongTien) AS TongDoanhThu " +
                     "FROM HoaDon " +
                     "WHERE MONTH(NgayLap) = ? AND YEAR(NgayLap) = ? " +
                     "GROUP BY MONTH(NgayLap), YEAR(NgayLap) ORDER BY YEAR(NgayLap), MONTH(NgayLap)";
        List<ThongKe> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String thangNam = rs.getInt("Thang") + "/" + rs.getInt("Nam");
                double tong = rs.getDouble("TongDoanhThu");
                list.add(new ThongKe(thangNam, tong));
            }
        }
        return list;
    }

    public List<CTHoaDon> getChiTietHoaDon(int thang, int nam, String maHoaDon) throws SQLException {
        List<CTHoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.MaHD, hd.NgayLap, sp.TenSP, ct.SoLuong, ct.DonGia, (ct.SoLuong * ct.DonGia) AS ThanhTien " +
                     "FROM HoaDon hd " +
                     "JOIN CTHoaDon ct ON hd.MaHD = ct.MaHD " +
                     "JOIN SanPham sp ON ct.MaSP = sp.MaSP";
        
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (thang != 0) {
            conditions.add("MONTH(hd.NgayLap) = ?");
            parameters.add(thang);
        }
        if (nam != 0) {
            conditions.add("YEAR(hd.NgayLap) = ?");
            parameters.add(nam);
        }
        if (maHoaDon != null && !maHoaDon.trim().isEmpty()) {
            conditions.add("hd.MaHD = ?");
            parameters.add(maHoaDon);
        }

        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        sql += " ORDER BY hd.NgayLap";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                } else if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maHD = rs.getString("MaHD");
                Date ngayLap = rs.getDate("NgayLap");
                String tenSanPham = rs.getString("TenSP");
                int soLuong = rs.getInt("SoLuong");
                double donGia = rs.getDouble("DonGia");
                double thanhTien = rs.getDouble("ThanhTien");
                list.add(new CTHoaDon(maHD, ngayLap, tenSanPham, soLuong, donGia, thanhTien));
            }
        }
        return list;
    }

    private List<ThongKe> layDanhSachThongKe(String sql, String nhanDien) throws SQLException {
        List<ThongKe> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nhan = rs.getString(nhanDien);
                double tong = rs.getDouble("TongDoanhThu");
                list.add(new ThongKe(nhan, tong));
            }
        }
        return list;
    }
}