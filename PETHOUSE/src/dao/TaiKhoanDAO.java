package dao;

import csdl.DatabaseConnection;
import model.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TaiKhoanDAO {

    public List<TaiKhoan> getDanhSachTaiKhoan() throws SQLException {
        List<TaiKhoan> list = new ArrayList<>();

        String sql = "SELECT nv.HoTen, tk.TenDangNhap, nv.Email, nv.VaiTro, " +
                     "CASE tk.TrangThai WHEN 1 THEN N'Hoạt động' ELSE N'Chưa hoạt động' END AS TrangThai " +
                     "FROM TaiKhoan tk JOIN NhanVien nv ON tk.MaNV = nv.MaNV";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getString("HoTen"),
                    rs.getString("TenDangNhap"),
                    rs.getString("Email"),
                    rs.getString("VaiTro"),
                    rs.getString("TrangThai")
                );
                list.add(tk);
            }
        }

        return list;
    }
   
public TaiKhoan timTaiKhoanTheoTenDangNhap(String tenDangNhap) throws SQLException {
    TaiKhoan taiKhoan = null;
    String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, tenDangNhap);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String maNV = rs.getString("MaNV");
                String matKhau = rs.getString("MatKhau");
                String trangThai = rs.getInt("TrangThai") == 1 ? "Hoạt động" : "Chưa hoạt động";
                taiKhoan = new TaiKhoan(maNV, tenDangNhap, matKhau, trangThai);
            }
        }
    }

    return taiKhoan;
}



public boolean kiemTraTonTaiTaiKhoan(String maNV) throws SQLException {
    String sql = "SELECT * FROM TaiKhoan WHERE MaNV = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, maNV);
        ResultSet rs = ps.executeQuery();
        return rs.next(); 
    }
}

public boolean themTaiKhoan(TaiKhoan tk) throws SQLException {
    String sql = "INSERT INTO TaiKhoan (MaNV, TenDangNhap, MatKhau, TrangThai) VALUES (?, ?, ?, ?)";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, tk.getMaNV());
        ps.setString(2, tk.getTenDangNhap());
        ps.setString(3, tk.getMatKhau());
        ps.setInt(4, tk.getTrangThai().equals("Hoạt động") ? 1 : 0);
        return ps.executeUpdate() > 0;
    }
}

public boolean suaTaiKhoan(TaiKhoan tk) throws SQLException {
    String sql = "UPDATE TaiKhoan SET TenDangNhap = ?, MatKhau = ?, TrangThai = ? WHERE MaNV = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, tk.getTenDangNhap());
        ps.setString(2, tk.getMatKhau());
        ps.setInt(3, tk.getTrangThai().equals("Hoạt động") ? 1 : 0);
        ps.setString(4, tk.getMaNV());
        return ps.executeUpdate() > 0;
    }
}

public boolean xoaTaiKhoan(String maNV) throws SQLException {
    String sql = "DELETE FROM TaiKhoan WHERE MaNV = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, maNV);
        return ps.executeUpdate() > 0;
    }
}
public List<TaiKhoan> getAllTaiKhoan() throws SQLException {
    List<TaiKhoan> list = new ArrayList<>();
    String sql = "SELECT nv.HoTen, tk.TenDangNhap, nv.Email, nv.VaiTro, " +
             "CASE tk.TrangThai WHEN 1 THEN N'Hoạt động' ELSE N'Chưa hoạt động' END AS TrangThai " +
             "FROM TaiKhoan tk JOIN NhanVien nv ON tk.MaNV = nv.MaNV " +
             "WHERE nv.HoTen LIKE ? OR tk.TenDangNhap LIKE ? OR nv.Email LIKE ? " +
             "OR nv.VaiTro LIKE ? OR " +
             "CASE tk.TrangThai WHEN 1 THEN N'Hoạt động' ELSE N'Chưa hoạt động' END LIKE ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            TaiKhoan tk = new TaiKhoan(
                rs.getString("HoTen"),
                rs.getString("TenDangNhap"),
                rs.getString("Email"),
                rs.getString("VaiTro"),
                rs.getString("TrangThai")
            );
            list.add(tk);
        }
    }
    return list;
}

public List<TaiKhoan> timTaiKhoanTheoTenNV(String tenNV) throws SQLException {
    List<TaiKhoan> list = new ArrayList<>();
    String sql = "SELECT nv.HoTen, tk.TenDangNhap, nv.Email, nv.VaiTro, " +
                 "CASE tk.TrangThai WHEN 1 THEN N'Hoạt động' ELSE N'Chưa hoạt động' END AS TrangThai " +
                 "FROM TaiKhoan tk JOIN NhanVien nv ON tk.MaNV = nv.MaNV WHERE nv.HoTen LIKE ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, "%" + tenNV + "%");
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getString("HoTen"),
                    rs.getString("TenDangNhap"),
                    rs.getString("Email"),
                    rs.getString("VaiTro"),
                    rs.getString("TrangThai")
                );
                list.add(tk);
            }
        }
    }
    return list;
}


public List<TaiKhoan> timTaiKhoanTheoVaiTro(String vaiTro) throws SQLException {
    List<TaiKhoan> list = new ArrayList<>();
    String sql = "SELECT nv.HoTen, tk.TenDangNhap, nv.Email, nv.VaiTro, " +
                 "CASE tk.TrangThai WHEN 1 THEN N'Hoạt động' ELSE N'Chưa hoạt động' END AS TrangThai " +
                 "FROM TaiKhoan tk JOIN NhanVien nv ON tk.MaNV = nv.MaNV WHERE nv.VaiTro LIKE ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, "%" + vaiTro + "%");
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getString("HoTen"),
                    rs.getString("TenDangNhap"),
                    rs.getString("Email"),
                    rs.getString("VaiTro"),
                    rs.getString("TrangThai")
                );
                list.add(tk);
            }
        }
    }
    return list;
}
public List<TaiKhoan> QuenMk(String email) throws SQLException {
    List<TaiKhoan> list = new ArrayList<>();
    String sql = "SELECT tk.TenDangNhap, tk.MatKhau " +
                 "FROM TaiKhoan tk " +
                 "JOIN NhanVien nv ON tk.MaNV = nv.MaNV " +
                 "WHERE nv.Email = ?";
    
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, email); 
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getString("TenDangNhap"),
                    rs.getString("MatKhau")
                );
                list.add(tk);
            }
        }
    }
    return list;
}
public TaiKhoan getTaiKhoanTheoMaNV(String maNV) throws SQLException {
    String sql = "SELECT TenDangNhap, MatKhau FROM TaiKhoan WHERE MaNV = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, maNV);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new TaiKhoan(rs.getString("TenDangNhap"), rs.getString("MatKhau"));
            }
        }
    }
    return null;
}
public void capNhatPhanQuyen(String tenDangNhap, String vaiTro, int trangThai) throws SQLException {
    String sql1 = "UPDATE NhanVien SET VaiTro = ? FROM NhanVien NV JOIN TaiKhoan TK ON NV.MaNV = TK.MaNV WHERE TK.TenDangNhap = ?";
    String sql2 = "UPDATE TaiKhoan SET TrangThai = ? WHERE TenDangNhap = ?";

    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false); 
        try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
             PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

          
            stmt1.setString(1, vaiTro);
            stmt1.setString(2, tenDangNhap);
            stmt1.executeUpdate();

           
            stmt2.setInt(1, trangThai);
            stmt2.setString(2, tenDangNhap);
            stmt2.executeUpdate();

            conn.commit(); 
        } catch (SQLException e) {
            conn.rollback(); 
            throw e;
        }
    }
}

    public void capNhatPhanQuyen(String tenDangNhap, String vaiTro, String trangThai) {
        
    }

}
