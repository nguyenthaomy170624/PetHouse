CREATE DATABASE PETHOUSE_NTM;
GO

USE PETHOUSE_NTM;
GO

-- Bảng LoaiSanPham
CREATE TABLE LoaiSanPham (
    MaLoai NVARCHAR(10) PRIMARY KEY,
    TenLoai NVARCHAR(50)
);
-- Bảng NhanVien
CREATE TABLE NhanVien (
    MaNV NVARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100),
    GioiTinh NVARCHAR(10),
    NgaySinh DATE,
    DiaChi NVARCHAR(255),
    SoDienThoai NVARCHAR(11),
    VaiTro NVARCHAR(50),
    Email NVARCHAR(100),
    CCCD NVARCHAR(12)
);
-- Bảng TaiKhoan
CREATE TABLE TaiKhoan (
    TenDangNhap NVARCHAR(50) PRIMARY KEY,
    MatKhau NVARCHAR(100),
	TrangThai Int,
    MaNV NVARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV)
);
select *from TaiKhoan

-- Bảng KhachHang
CREATE TABLE KhachHang (
    MaKH NVARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100),
    SoDienThoai NVARCHAR(11),
    DiaChi NVARCHAR(255),
    Email NVARCHAR(100),
    CCCD NVARCHAR(12)
);


-- Bảng SanPham
CREATE TABLE SanPham (
    MaSP NVARCHAR(10) PRIMARY KEY,
    TenSP NVARCHAR(100),
    MaLoai NVARCHAR(10) FOREIGN KEY REFERENCES LoaiSanPham(MaLoai),
    GiaBan DECIMAL(18, 0),
    SoLuong INT,
    MoTa NVARCHAR(MAX),
    Anh NVARCHAR(255)
);

-- Bảng HoaDon
CREATE TABLE HoaDon (
    MaHD NVARCHAR(10) PRIMARY KEY,
    MaKH NVARCHAR(10) FOREIGN KEY REFERENCES KhachHang(MaKH),
    MaNV NVARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
    NgayLap DATE,
    TongTien DECIMAL(18, 0)
);
select*from HoaDon
-- Bảng CTHoaDon
CREATE TABLE CTHoaDon (
	MaCTHD (10) PRIMARY KEY,
    MaHD NVARCHAR(10),
    MaSP NVARCHAR(10),
    SoLuong INT,
    DonGia DECIMAL(18, 0),
    PRIMARY KEY (MaHD, MaSP),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP),
	GhiChu NVARCHAR(255)
);
ALTER TABLE CTHoaDon ADD GhiChu NVARCHAR(255);
select *from LoaiSanPham
---- Bảng PhieuNhap
--CREATE TABLE PhieuNhap (
--    MaPN NVARCHAR(10) PRIMARY KEY,
--    MaNV NVARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
--    MaNCC NVARCHAR(10) FOREIGN KEY REFERENCES NhaCungCap(MaNCC),
--    NgayNhap DATE
--);

---- Bảng CTPhieuNhap
--CREATE TABLE CTPhieuNhap (
--    MaPN NVARCHAR(10),
--    MaSP NVARCHAR(10),
--    SoLuong INT,
--    DonGia DECIMAL(18, 0),
--    PRIMARY KEY (MaPN, MaSP),
--    FOREIGN KEY (MaPN) REFERENCES PhieuNhap(MaPN),
--    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
--);

INSERT INTO LoaiSanPham VALUES
('L01', N'Chó'),
('L02', N'Mèo'),
('L03', N'Phụ kiện'),
('L04', N'Thức ăn'),
('L05', N'Chuồng trại'),
('L06', N'Vật dụng y tế'),
('L07', N'Đồ chơi');
GO
INSERT INTO NhanVien VALUES
('NV01', N'Nguyễn Văn An', N'Nam', '1995-01-01', N'Q1, TP.HCM', '0909000001', 'admin', 'nguyen.van.an@gmail.com', '012345678901'),
('NV02', N'Lê Thị Bình', N'Nữ', '1998-03-21', N'Q2, TP.HCM', '0909000002', 'nvbh', 'le.thi.binh@gmail.com', '012345678902'),
('NV03', N'Phạm Văn Cường', N'Nam', '1994-05-10', N'Q3, TP.HCM', '0909000003', 'nvbh', 'pham.van.cuong@gmail.com', '012345678903'),
('NV04', N'Trần Thị Dung', N'Nữ', '1997-07-15', N'Q4, TP.HCM', '0909000004', 'nvbh', 'tran.thi.dung@gmail.com', '012345678904'),
('NV05', N'Hoàng Văn Em', N'Nam', '1996-08-08', N'Q5, TP.HCM', '0909000005', 'nvbh', 'hoang.van.em@gmail.com', '012345678905'),
('NV06', N'Ngô Thị Gấm', N'Nữ', '1993-10-30', N'Q6, TP.HCM', '0909000006', 'nvbh', 'ngo.thi.gam@gmail.com', '012345678906'),
('NV07', N'Vũ Văn Hiếu', N'Nam', '1992-12-12', N'Q7, TP.HCM', '0909000007', 'nvbh', 'vu.van.hieu@gmail.com', '012345678907'),
('NV08', N'Lý Thị Hòa', N'Nữ', '1990-04-18', N'Q8, TP.HCM', '0909000008', 'nvbh', 'ly.thi.hoa@gmail.com', '012345678908'),
('NV09', N'Đỗ Văn Ích', N'Nam', '1991-11-11', N'Q9, TP.HCM', '0909000009', 'nvbh', 'do.van.ich@gmail.com', '012345678909');
GO

INSERT INTO TaiKhoan VALUES
('admin', 'admin123',1 ,'NV01'),
('nvban1', '123456', 1,'NV02'),
('nvban2', '123456',1, 'NV03'),
('nvban3', '123456', 1,'NV04'),
('nvban4', '123456', 1,'NV05'),
('nvkho1', '123456', 1,'NV06'),
('nvkho2', '123456', 1,'NV07'),
('nvkho3', '123456',1, 'NV08'),
('nvkho4', '123456', 1,'NV09');
GO

INSERT INTO KhachHang VALUES
('KH001', N'Trần Văn Như', '0901000001', N'Quận 1, TP.HCM', 'tran.van.nhu@gmail.com', '012345678910'),
('KH002', N'Lê Thị Kim Ngân', '0901000002', N'Quận 3, TP.HCM', 'le.kim.ngan@gmail.com', '012345678911'),
('KH003', N'Nguyễn Minh Tuấn', '0901000003', N'Quận 5, TP.HCM', 'nguyen.minh.tuan@gmail.com', '012345678912'),
('KH004', N'Phạm Thị Lan Hương', '0901000004', N'Quận 7, TP.HCM', 'pham.lan.huong@gmail.com', '012345678913'),
('KH005', N'Đỗ Trung Hiếu', '0901000005', N'Quận 10, TP.HCM', 'do.trung.hieu@gmail.com', '012345678914'),
('KH006', N'Võ Thị Bích Phượng', '0901000006', N'Thủ Đức, TP.HCM', 'vo.bich.phuong@gmail.com', '012345678915'),
('KH007', N'Huỳnh Nhật Nam', '0901000007', N'Bình Thạnh, TP.HCM', 'huynh.nhat.nam@gmail.com', '012345678916'),
('KH008', N'Bùi Thị Mai Trang', '0901000008', N'Tân Bình, TP.HCM', 'bui.mai.trang@gmail.com', '012345678917'),
('KH009', N'Ngô Văn Kiệt', '0901000009', N'Phú Nhuận, TP.HCM', 'ngo.van.kiet@gmail.com', '012345678918'),
('KH010', N'Tống Thị Diễm My', '0901000010', N'Quận 12, TP.HCM', 'tong.diem.my@gmail.com', '012345678919'),
('KH011', N'Hoàng Anh Duy', '0901000011', N'Gò Vấp, TP.HCM', 'hoang.anh.duy@gmail.com', '012345678920'),
('KH012', N'Đinh Thị Bảo Ngọc', '0901000012', N'Tân Phú, TP.HCM', 'dinh.bao.ngoc@gmail.com', '012345678921'),
('KH013', N'Trương Quốc Thắng', '0901000013', N'Quận 9, TP.HCM', 'truong.quoc.thang@gmail.com', '012345678922'),
('KH014', N'Cao Thị Hồng Nhung', '0901000014', N'Bình Tân, TP.HCM', 'cao.hong.nhung@gmail.com', '012345678923'),
('KH015', N'Lý Minh Châu', '0901000015', N'Nhà Bè, TP.HCM', 'ly.minh.chau@gmail.com', '012345678924');
GO

INSERT INTO NhaCungCap VALUES
('NCC01', N'Trại nhân giống Miền Nam', N'Huỳnh Văn Lũy, Thủ Dầu Một, Bình Dương', '0908001122'),
('NCC02', N'Trại nhân giống Miền Bắc', N'Thôn Đức Hậu, Xã Đức Hòa, Sóc Sơn, Hà Nội', '0912334455');
GO

INSERT INTO SanPham VALUES
('SP01', N'Chó Poodle', 'L01', 5000000, 5, N'Chó Poodle thuần chủng, khỏe mạnh', '/HinhAnh/poodle.jpg'),
('SP02', N'Mèo Anh lông ngắn', 'L02', 3000000, 4, N'Mèo Anh lông ngắn thuần chủng', 'meoanh.jpg'),
('SP03', N'Dây dắt chó', 'L03', 100000, 20, N'Dây dắt bền, dài 1.5m', 'daydatcho.jpg'),
('SP04', N'Chuồng inox', 'L05', 1500000, 10, N'Chuồng inox cao cấp', 'chuonginox.jpg'),
('SP05', N'Thức ăn hạt', 'L04', 200000, 30, N'Thức ăn hạt dinh dưỡng', 'thuccan.jpg'),
('SP06', N'Vaccine 5 trong 1', 'L06', 800000, 15, N'Vaccine cho thú cưng', 'vaccine5in1.jpg'),
('SP07', N'Xương gặm', 'L07', 50000, 50, N'Xương gặm an toàn', 'xuonggam.jpg'),
('SP08', N'Bánh thưởng cho mèo', 'L04', 120000, 25, N'Bánh thưởng vị cá', 'banhthuong.jpg'),
('SP09', N'Bát ăn đôi', 'L03', 75000, 40, N'Bát ăn đôi tiện lợi', 'batandoi.jpg'),
('SP10', N'Chổi lông chó mèo', 'L03', 45000, 35, N'Chổi lông chuyên dụng', 'choilon.jpg');
GO

--INSERT INTO PhieuNhap VALUES
--('PN01', 'NV06', 'NCC01', '2025-03-25'),
--('PN02', 'NV07', 'NCC02', '2025-03-26'),
--('PN03', 'NV08', 'NCC01', '2025-03-27'),
--('PN04', 'NV09', 'NCC02', '2025-03-28'),
--('PN05', 'NV06', 'NCC01', '2025-03-29'),
--('PN06', 'NV07', 'NCC02', '2025-03-30'),
--('PN07', 'NV08', 'NCC01', '2025-03-31'),
--('PN08', 'NV09', 'NCC02', '2025-04-01'),
--('PN09', 'NV06', 'NCC01', '2025-04-02'),
--('PN10', 'NV07', 'NCC02', '2025-04-03'),
--('PN11', 'NV08', 'NCC01', '2025-04-04'),
--('PN12', 'NV09', 'NCC02', '2025-04-05'),
--('PN13', 'NV06', 'NCC01', '2025-04-06'),
--('PN14', 'NV07', 'NCC02', '2025-04-07'),
--('PN15', 'NV08', 'NCC01', '2025-04-08');
--GO

--INSERT INTO CTPhieuNhap VALUES
--('PN01', 'SP01', 2, 4500000),
--('PN02', 'SP02', 1, 2700000),
--('PN03', 'SP03', 10, 90000),
--('PN04', 'SP04', 5, 1400000),
--('PN05', 'SP05', 15, 180000),
--('PN06', 'SP06', 5, 750000),
--('PN07', 'SP07', 20, 45000),
--('PN08', 'SP08', 10, 110000),
--('PN09', 'SP09', 15, 70000),
--('PN10', 'SP10', 20, 40000),
--('PN11', 'SP01', 1, 4600000),
--('PN12', 'SP02', 2, 2800000),
--('PN13', 'SP03', 5, 95000),
--('PN14', 'SP05', 10, 190000),
--('PN15', 'SP07', 25, 48000);
GO

INSERT INTO HoaDon VALUES
('HD01', 'KH001', 'NV02', '2025-04-07', 150000),
('HD02', 'KH002', 'NV03', '2025-04-08', 200000),
('HD03', 'KH003', 'NV04', '2025-04-09', 350000),
('HD04', 'KH004', 'NV05', '2025-04-10', 450000),
('HD05', 'KH005', 'NV02', '2025-04-11', 600000),
('HD06', 'KH006', 'NV03', '2025-04-12', 250000),
('HD07', 'KH007', 'NV04', '2025-04-13', 320000),
('HD08', 'KH008', 'NV05', '2025-04-14', 280000),
('HD09', 'KH009', 'NV03', '2025-04-15', 400000),
('HD10', 'KH010', 'NV04', '2025-04-16', 520000);
GO

INSERT INTO CTHoaDon VALUES
('HD01', 'SP07', 2, 50000),
('HD02', 'SP03', 1, 100000),
('HD03', 'SP04', 1, 150000),
('HD04', 'SP05', 2, 200000),
('HD05', 'SP06', 1, 800000),
('HD06', 'SP01', 1, 5000000),
('HD07', 'SP08', 3, 120000),
('HD08', 'SP09', 2, 75000),
('HD09', 'SP10', 1, 45000),
('HD10', 'SP02', 1, 3000000),
('HD10', 'SP03', 1, 100000),
('HD09', 'SP05', 1, 200000),
('HD08', 'SP04', 1, 1500000),
('HD07', 'SP07', 2, 50000),
('HD06', 'SP08', 1, 120000);
GO
--- Xóa nhân viên
CREATE TRIGGER trg_XoaNhanVien
ON NhanVien
INSTEAD OF DELETE
AS
BEGIN
    DELETE FROM TaiKhoan
    WHERE MaNV IN (SELECT MaNV FROM deleted);
    DELETE FROM NhanVien
    WHERE MaNV IN (SELECT MaNV FROM deleted);
END;
--Tính tổng tiền cho hóa đơn
CREATE TRIGGER trg_CapNhatTongTien
ON CTHoaDon
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE HoaDon
    SET TongTien = ISNULL((
        SELECT SUM(SoLuong * DonGia)
        FROM CTHoaDon
        WHERE CTHoaDon.MaHD = HoaDon.MaHD
    ), 0)
    WHERE MaHD IN (
        SELECT MaHD FROM inserted
        UNION
        SELECT MaHD FROM deleted
    );
END
select manv from NhanVien
select * from HoaDon
select * from CTHoaDon
SELECT nv.VaiTro 
FROM TaiKhoan tk 
JOIN NhanVien nv ON tk.MaNV = nv.MaNV 
WHERE tk.TenDangNhap = 'admin'
