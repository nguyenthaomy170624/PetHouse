package report;

import dao.HoaDonDao;
import dao.CTHoaDonDAO;
import dao.KhachHangDAO;
import dao.SanPhamDAO;
import dao.NhanVienDAO; 
import model.HoaDon;
import model.CTHoaDon;
import model.KhachHang;
import model.DuLieuHoaDon;
import model.SanPham;
import model.NhanVien; 
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;

public class XuatHD {
    private HoaDonDao hoaDonDao = new HoaDonDao();
    private CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO();
    private KhachHangDAO khachHangDAO = new KhachHangDAO();
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO(); // Added

    public void generateInvoice(String maHD, String outputPath) throws Exception {
        HoaDon hoaDon = hoaDonDao.findByMaHD(maHD);
        if (hoaDon == null) {
            throw new Exception("Hóa đơn không tồn tại!");
        }

        KhachHang khachHang = khachHangDAO.findByMaKH(hoaDon.getMaKH());
        NhanVien nhanVien = nhanVienDAO.findByMaNV(hoaDon.getMaNV()); // Fetch employee
        List<CTHoaDon> ctHoaDons = ctHoaDonDAO.getByMaHD(maHD);

        List<DuLieuHoaDon> invoiceDataList = new ArrayList<>();
        for (CTHoaDon ct : ctHoaDons) {
            SanPham sp = sanPhamDAO.findByMaSP(ct.getMaSP());
            DuLieuHoaDon data = new DuLieuHoaDon(
                hoaDon.getMaHD(),
                hoaDon.getNgayLap(),
                hoaDon.getMaKH(),
                khachHang != null ? khachHang.getHoTen() : "",
                nhanVien != null ? nhanVien.getHoTen() : "", 
                ct.getMaSP(),
                sp != null ? sp.getTenSP() : "",
                ct.getSoLuong(),
                ct.getDonGia(),
                ct.getGhiChu(),
                hoaDon.getTongTien(),
                khachHang != null ? khachHang.getDiaChi() : "",
                khachHang != null ? khachHang.getSoDienThoai() : "",
                ct.getSoLuong() * ct.getDonGia()
            );
            invoiceDataList.add(data);
        }

        InputStream inputStream = getClass().getResourceAsStream("/report/XuatHoaDon.jasper");
        JasperReport jasperReport;
        if (inputStream != null) {
            try {
                jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            inputStream = getClass().getResourceAsStream("/report/XuatHoaDon.jrxml");
            if (inputStream == null) {
                throw new Exception("Không tìm thấy file XuatHoaDon.jrxml hoặc XuatHoaDon.jasper trong src/report!");
            }
            try {
                jasperReport = JasperCompileManager.compileReport(inputStream);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoiceDataList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "HÓA ĐƠN BÁN HÀNG");
        parameters.put("REPORT_CLASSPATH", getClass().getResource("/").getPath()); // For logo

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        System.out.println("Hóa đơn đã được xuất thành công tại: " + outputPath);

        try {
            File pdfFile = new File(outputPath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Không thể mở file PDF: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}