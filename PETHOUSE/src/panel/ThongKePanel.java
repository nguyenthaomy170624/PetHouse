package panel;

import dao.ThongKeDAO;
import model.ThongKe;
import model.CTHoaDon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.jfree.chart.ChartUtils;

public class ThongKePanel extends JPanel {

    private JTabbedPane tabbedPane;
    private JButton exportButton;
    private JComboBox<String> monthComboBox, yearComboBox;
    private JTextField tenNguoiXuatField;

    public ThongKePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        actionPanel.setBackground(new Color(255, 255, 255));
        actionPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        monthComboBox = new JComboBox<>(new String[]{"Tất cả", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"});
        yearComboBox = new JComboBox<>(new String[]{"Tất cả", "2023", "2024", "2025"});
        monthComboBox.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        yearComboBox.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        monthComboBox.setBackground(new Color(255, 255, 255));
        yearComboBox.setBackground(new Color(255, 255, 255));

        ActionListener filterListener = e -> {
            int month = monthComboBox.getSelectedIndex();
            String yearStr = (String) yearComboBox.getSelectedItem();
            int year = yearStr.equals("Tất cả") ? 0 : Integer.parseInt(yearStr);
            filterData(month, year);
        };
        monthComboBox.addActionListener(filterListener);
        yearComboBox.addActionListener(filterListener);

        JLabel monthLabel = new JLabel("Tháng:");
        monthLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        JLabel yearLabel = new JLabel("Năm:");
        yearLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        JLabel tenNguoiXuatLabel = new JLabel("Người xuất báo cáo:");
        tenNguoiXuatLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tenNguoiXuatField = new JTextField(15);
        tenNguoiXuatField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        actionPanel.add(monthLabel);
        actionPanel.add(monthComboBox);
        actionPanel.add(yearLabel);
        actionPanel.add(yearComboBox);
        actionPanel.add(tenNguoiXuatLabel);
        actionPanel.add(tenNguoiXuatField);

        exportButton = new JButton("Xuất Báo Cáo");
        styleButton(exportButton);
        exportButton.addActionListener(e -> {
            exportButton.setEnabled(false);
            try {
                exportReport();
            } catch (SQLException ex) {
                Logger.getLogger(ThongKePanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất báo cáo: " , "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                exportButton.setEnabled(true);
            }
        });
        actionPanel.add(exportButton);

        add(actionPanel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tabbedPane.setBackground(new Color(255, 255, 255));
        tabbedPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        tabbedPane.addTab("Theo Ngày", taoBieuDoTheoLoai("ngay"));
        tabbedPane.addTab("Theo Sản Phẩm", taoBieuDoTheoLoai("sanpham"));
        tabbedPane.addTab("Theo Tài Khoản", taoBieuDoTheoLoai("taikhoan"));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(25, 118, 210));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 150, 243));
            }
        });
    }

    private void updateChartTabs() {
        tabbedPane.removeAll();
        tabbedPane.addTab("Theo Ngày", taoBieuDoTheoLoai("ngay"));
        tabbedPane.addTab("Theo Sản Phẩm", taoBieuDoTheoLoai("sanpham"));
        tabbedPane.addTab("Theo Tài Khoản", taoBieuDoTheoLoai("taikhoan"));
        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

    private void filterData(int month, int year) {
        ThongKeDAO dao = new ThongKeDAO();
        List<ThongKe> ds;

        try {
            ds = (month == 0 && year == 0) ? dao.thongKeTheoNgay() : dao.thongKeTheoThangNam(month, year);
            updateChartTabs();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc dữ liệu: " , "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel taoBieuDoTheoLoai(String loai) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ThongKeDAO dao = new ThongKeDAO();
        List<ThongKe> ds;

        try {
            int month = monthComboBox.getSelectedIndex();
            String yearStr = (String) yearComboBox.getSelectedItem();
            int year = yearStr.equals("Tất cả") ? 0 : Integer.parseInt(yearStr);

            if (month == 0 && year == 0) {
                if (loai.equals("ngay")) {
                    ds = dao.thongKeTheoNgay();
                } else if (loai.equals("sanpham")) {
                    ds = dao.thongKeTheoSanPham();
                } else {
                    ds = dao.thongKeTheoTaiKhoan();
                }
            } else {
                ds = dao.thongKeTheoThangNam(month, year);
            }

            for (ThongKe tk : ds) {
                dataset.addValue(tk.getTongDoanhThu(), "Doanh Thu", tk.getNhan());
            }

            JFreeChart chart = createBarChart(dataset, loai);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setBackground(new Color(255, 255, 255));
            panel.add(chartPanel, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Không thể tải biểu đồ. Vui lòng thử lại sau.", SwingConstants.CENTER);
            errorLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            errorLabel.setForeground(new Color(211, 47, 47));
            panel.add(errorLabel, BorderLayout.CENTER);
        }
        return panel;
    }

    private void exportReport() throws SQLException {
        ThongKeDAO dao = new ThongKeDAO();
        int month = monthComboBox.getSelectedIndex();
        String yearStr = (String) yearComboBox.getSelectedItem();
        int year = yearStr.equals("Tất cả") ? 0 : Integer.parseInt(yearStr);

        List<CTHoaDon> dsChiTiet = dao.getChiTietHoaDon(month, year, null);

        if (dsChiTiet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất báo cáo!", "Thông Báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double totalRevenue = dsChiTiet.stream().mapToDouble(CTHoaDon::getThanhTien).sum();

        SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = "ThongKeDoanhThu_" + sdfFile.format(new Date()) + ".pdf";
        File pdfFile = new File(fileName);

        if (pdfFile.exists() && !pdfFile.canWrite()) {
            JOptionPane.showMessageDialog(this, "File " + fileName + " đang được sử dụng. Vui lòng đóng file và thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create temporary chart image
            String chartImagePath = null;
            File chartFile = null;
            try {
                List<ThongKe> dsThongKe = (month == 0 && year == 0) ? dao.thongKeTheoNgay() : dao.thongKeTheoThangNam(month, year);
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (ThongKe tk : dsThongKe) {
                    dataset.addValue(tk.getTongDoanhThu(), "Doanh Thu", tk.getNhan());
                }

                JFreeChart chart = ChartFactory.createBarChart(
                        "",
                        "Loại",
                        "Doanh Thu",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false, false, false
                );

                chartFile = File.createTempFile("temp_chart", ".png");
                ChartUtils.saveChartAsPNG(chartFile, chart, 500, 300);
                chartImagePath = chartFile.getAbsolutePath();
                System.out.println("Chart created at: " + chartImagePath);
            } catch (Exception e) {
                System.err.println("Error creating chart: " + e.getMessage());
                e.printStackTrace();
            }

            // Set up report parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("TenNguoiXuat", tenNguoiXuatField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            parameters.put("NgayXuat", sdf.format(new Date()));
            parameters.put("ThoiGianThongKe", month == 0 && yearStr.equals("Tất cả") ? "Tất cả" : monthComboBox.getSelectedItem() + " " + yearStr);
            parameters.put("TongDoanhThu", totalRevenue);

            if (chartImagePath != null) {
                parameters.put("ChartImagePath", chartImagePath);
            }

            JasperReport jasperReport = null;
            try {
                InputStream inputStream = getClass().getResourceAsStream("/report/ThongKeReport.jrxml");
                if (inputStream != null) {
                    jasperReport = JasperCompileManager.compileReport(inputStream);
                    System.out.println("Report template loaded from resources");
                } else {
                    File reportFile = new File("report/ThongKeReport.jrxml");
                    if (reportFile.exists()) {
                        jasperReport = JasperCompileManager.compileReport(reportFile.getAbsolutePath());
                        System.out.println("Report template loaded from file system");
                    } else {
                        throw new Exception("Report template not found in resources or file system");
                    }
                }
            } catch (Exception e) {
                System.err.println("Error loading report template: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dsChiTiet);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);

            System.out.println("Exporting report to: " + pdfFile.getAbsolutePath());
            exporter.exportReport();
            System.out.println("Report exported successfully");

            if (chartFile != null && chartFile.exists()) {
                chartFile.delete();
            }

            JOptionPane.showMessageDialog(this, "Báo cáo đã được xuất thành công tại " + pdfFile.getAbsolutePath() + "!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất báo cáo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JFreeChart createBarChart(DefaultCategoryDataset dataset, String loai) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Thống Kê Doanh Thu Theo " + (loai.equals("ngay") ? "Ngày" : loai.equals("sanpham") ? "Sản Phẩm" : "Tài Khoản"),
                loai.equals("ngay") ? "Ngày" : loai.equals("sanpham") ? "Sản Phẩm" : "Tài Khoản",
                "Doanh Thu (VND)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(245, 245, 245));
        plot.setDomainGridlinePaint(new Color(200, 200, 200));
        plot.setRangeGridlinePaint(new Color(200, 200, 200));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new GradientPaint(0f, 0f, new Color(33, 150, 243), 0f, 0f, new Color(3, 169, 244)));
        renderer.setDrawBarOutline(false);
        renderer.setMaximumBarWidth(0.1);
        renderer.setItemMargin(0.02);

        chart.getTitle().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        plot.getDomainAxis().setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        plot.getRangeAxis().setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        return chart;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 715, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
