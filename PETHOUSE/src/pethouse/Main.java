package pethouse;

import csdl.DatabaseConnection;
import panel.NhanVienPanel;
import panel.KhachHangPanel;
import panel.SanPhamPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import dao.NhanVienDAO;
import model.NhanVien;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import panel.LapHoaDonPanel;
import panel.TaiKhoanPanel;
import panel.ThongKePanel;
import panel.ThongTin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends javax.swing.JFrame {

    private String maNV;
    private JButton btnNhanVien;
    private JButton btnKhachHang;
    private JButton btnSanPham;
    private JButton btnHoaDon;
    private JButton btnPhieuNhap;
    private JButton btnTaiKhoan;
    private JButton btnDangXuat;
    private JButton btnThongKe;
    private JButton btnThongTin;
    private JLabel jLabel1;
    private JLabel vaitro;
    private JLabel hovaten;
    private JPanel panelContent;
    private JPanel panelMenu;
    private String currentUser;

    public Main(String maNV) throws SQLException {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.maNV = maNV;
        initializeComponents();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        NhanVienDAO dao = new NhanVienDAO();
        NhanVien nv = dao.getNhanVienTheoMa(maNV);
        if (nv != null) {
            hovaten.setText(nv.getHoTen());
            vaitro.setText(nv.getVaiTro());
        } else {
            hovaten.setText("Không tìm thấy");
            vaitro.setText("N/A");
        }

        hienThiAnhWelcome();
        if (nv != null) {
            hovaten.setText(nv.getHoTen());
            vaitro.setText(nv.getVaiTro());

            String role = nv.getVaiTro();

            if (role.equalsIgnoreCase("Admin")) {
            } else if (role.equalsIgnoreCase("Nhân viên bán hàng")) {
                btnNhanVien.setVisible(false);
                btnTaiKhoan.setVisible(false);
                btnThongKe.setVisible(false);
            }
        } else {
            hovaten.setText("Không tìm thấy");
            vaitro.setText("N/A");
        }

    }

    private void hienThiAnhWelcome() {
        panelContent.removeAll();
        panelContent.setLayout(new BorderLayout());

        JLabel lblAnh = new JLabel();
        lblAnh.setHorizontalAlignment(JLabel.CENTER);
        lblAnh.setVerticalAlignment(JLabel.CENTER);

        panelContent.add(lblAnh, BorderLayout.CENTER);
        panelContent.revalidate();
        panelContent.repaint();

        panelContent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/HinhAnh/banner1.png"));
                Image img = icon.getImage().getScaledInstance(
                        panelContent.getWidth(), panelContent.getHeight(), Image.SCALE_SMOOTH
                );
                lblAnh.setIcon(new ImageIcon(img));
            }
        });
    }

    public void setForm(JPanel panel) {
        panelContent.removeAll();
        panelContent.setLayout(new BorderLayout());
        panelContent.add(panel, BorderLayout.CENTER);
        panelContent.revalidate();
        panelContent.repaint();
    }

    private void initializeComponents() {

        panelMenu = new JPanel();
        btnNhanVien = new JButton();
        btnKhachHang = new JButton();
        btnSanPham = new JButton();
        btnHoaDon = new JButton();
        btnPhieuNhap = new JButton();
        btnTaiKhoan = new JButton();
        btnDangXuat = new JButton();
        btnThongKe = new JButton();
        btnThongTin = new JButton();
        jLabel1 = new JLabel();
        hovaten = new JLabel();
        vaitro = new JLabel();
        panelContent = new JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Panel Menu
        panelMenu.setBackground(new Color(0, 153, 255));
        panelMenu.setPreferredSize(new Dimension(250, 0));
        panelMenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(15, 10, 15, 10);

        // Tiêu đề
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 36));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setText("PET HOUSE");
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 30, 10);
        panelMenu.add(jLabel1, gbc);

        // Thông tin người dùng
        hovaten.setFont(new Font("Tahoma", Font.BOLD, 18));
        hovaten.setForeground(Color.WHITE);
        hovaten.setHorizontalAlignment(SwingConstants.CENTER);
        hovaten.setText("Họ và tên");
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        panelMenu.add(hovaten, gbc);

        vaitro.setFont(new Font("Tahoma", Font.PLAIN, 16));
        vaitro.setForeground(Color.WHITE);
        vaitro.setHorizontalAlignment(SwingConstants.CENTER);
        vaitro.setText("Chức vụ");
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 20, 10);
        panelMenu.add(vaitro, gbc);

        // Các nút chức năng
        gbc.insets = new Insets(10, 10, 10, 10);

        btnNhanVien.setBackground(new Color(0, 51, 255));
        btnNhanVien.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNhanVien.setForeground(Color.WHITE);
        btnNhanVien.setText("Quản lý nhân viên");
        btnNhanVien.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnNhanVien.setFocusPainted(false);
        btnNhanVien.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNhanVien.addActionListener(evt -> btnNhanVienActionPerformed(evt));
        gbc.gridy = 3;
        panelMenu.add(btnNhanVien, gbc);

        btnKhachHang.setBackground(new Color(0, 51, 255));
        btnKhachHang.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnKhachHang.setForeground(Color.WHITE);
        btnKhachHang.setText("Quản lý khách hàng");
        btnKhachHang.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnKhachHang.setFocusPainted(false);
        btnKhachHang.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKhachHang.addActionListener(evt -> btnKhachHangActionPerformed(evt));
        gbc.gridy = 4;
        panelMenu.add(btnKhachHang, gbc);

        btnSanPham.setBackground(new Color(0, 51, 255));
        btnSanPham.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSanPham.setForeground(Color.WHITE);
        btnSanPham.setText("Quản lý sản phẩm");
        btnSanPham.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnSanPham.setFocusPainted(false);
        btnSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSanPham.addActionListener(evt -> btnSanPhamActionPerformed(evt));
        gbc.gridy = 5;
        panelMenu.add(btnSanPham, gbc);

        btnHoaDon.setBackground(new Color(0, 51, 255));
        btnHoaDon.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnHoaDon.setForeground(Color.WHITE);
        btnHoaDon.setText("Quản lý hóa đơn");
        btnHoaDon.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnHoaDon.setFocusPainted(false);
        btnHoaDon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHoaDon.addActionListener(evt -> btnHoaDonActionPerformed(evt));
        gbc.gridy = 6;
        panelMenu.add(btnHoaDon, gbc);
//hỏng làm kịp hic
//        btnPhieuNhap.setBackground(new Color(0, 51, 255));
//        btnPhieuNhap.setFont(new Font("Tahoma", Font.BOLD, 16));
//        btnPhieuNhap.setForeground(Color.WHITE);
//        btnPhieuNhap.setText("Quản lý phiếu nhập");
//        btnPhieuNhap.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
//        btnPhieuNhap.setFocusPainted(false);
//        btnPhieuNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        gbc.gridy = 7;
//        panelMenu.add(btnPhieuNhap, gbc);

        btnTaiKhoan.setBackground(new Color(0, 51, 255));
        btnTaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnTaiKhoan.setForeground(Color.WHITE);
        btnTaiKhoan.setText("Quản lý tài khoản");
        btnTaiKhoan.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnTaiKhoan.setFocusPainted(false);
        btnTaiKhoan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTaiKhoan.addActionListener(evt -> btnTaiKhoanActionPerformed(evt));
        gbc.gridy = 8;
        panelMenu.add(btnTaiKhoan, gbc);

        btnThongKe.setBackground(new Color(0, 51, 255));
        btnThongKe.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setText("Thống kê");
        btnThongKe.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnThongKe.setFocusPainted(false);
        btnThongKe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThongKe.addActionListener(evt -> btnThongKeActionPerformed(evt));
        gbc.gridy = 9;
        panelMenu.add(btnThongKe, gbc);

        btnThongTin.setBackground(new Color(0, 51, 255));
        btnThongTin.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnThongTin.setForeground(Color.WHITE);
        btnThongTin.setText("Thông tin");
        btnThongTin.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnThongTin.setFocusPainted(false);
        btnThongTin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThongTin.addActionListener(evt -> btnThongTinActionPerformed(evt));
        gbc.gridy = 10;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelMenu.add(btnThongTin, gbc);

        btnDangXuat.setBackground(new Color(255, 51, 51));
        btnDangXuat.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangXuat.addActionListener(evt -> btnDangXuatActionPerformed(evt));
        gbc.gridy = 11;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelMenu.add(btnDangXuat, gbc);

        gbc.weighty = 1.0;
        getContentPane().add(panelMenu, BorderLayout.WEST);

        panelContent.setBackground(Color.WHITE);
        panelContent.setLayout(new BorderLayout());
        getContentPane().add(panelContent, BorderLayout.CENTER);

        pack();
    }

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {

        setForm(new NhanVienPanel());

    }

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {

        setForm(new KhachHangPanel());

    }

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {

        setForm(new SanPhamPanel());

    }

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {

        setForm(new LapHoaDonPanel());

    }

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {

        setForm(new TaiKhoanPanel());

    }

    private void btnThongTinActionPerformed(java.awt.event.ActionEvent evt) {
        setForm(new ThongTin(maNV));
    }

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {

        setForm(new ThongKePanel());

    }

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DangNhap dangnhapFrame = new DangNhap();
            dangnhapFrame.setLocationRelativeTo(null);
            dangnhapFrame.setVisible(true);
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int confirm = JOptionPane.showConfirmDialog(
                Main.this,
                "Bạn có chắc chắn muốn đóng chương trình không?",
                "Xác nhận đóng",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Main("NV001").setVisible(true);
            } catch (SQLException e) {
                System.err.println("Lỗi khởi tạo Main: " + e.getMessage());
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
