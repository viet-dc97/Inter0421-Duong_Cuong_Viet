-- Cơ sở dữ liệu: `furama`
--
CREATE DATABASE `furama`;
USE `furama`;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bophan`
--

CREATE TABLE `bophan` (
  `idBoPhan` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `BoPhan` varchar(45) NOT NULL
);

-- Cấu trúc bảng cho bảng `dichvu`
--

CREATE TABLE `dichvu` (
  `idDichVu` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `TenDichVu` varchar(45)NOT NULL,
  `DienTich` int NOT NULL,
  `SoTang` int DEFAULT NULL,
  `ChiPhiThue` double NOT NULL,
  `SoNguoiToiDa` int NOT NULL,
  `idKieuThue` int NOT NULL,
  `idLoaiDichVu` int NOT NULL,
  `TieuChuanPhong` varchar(45) DEFAULT NULL,
  `MoTa` varchar(45) NULL,
  `DienTichHoBoi` int DEFAULT NULL,
  `DichVuMienPhiDiKem` varchar(45) DEFAULT NULL
);

--
-- Cấu trúc bảng cho bảng `dichvudikem`
--

CREATE TABLE `dichvudikem` (
  `idDichVuDiKem` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `tenDichVuDiKem` varchar(45) NOT NULL,
  `DonVi` int NOT NULL,
  `Gia` double NOT NULL
);


-- Cấu trúc bảng cho bảng `hopdong`
--

CREATE TABLE `hopdong` (
  `idHopDong` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `idNhanVien` int NOT NULL,
  `idKhachHang` int NOT NULL,
  `idDichVu` int NOT NULL,
  `NgayBatDau` date NOT NULL,
  `NgayKetThuc` date NOT NULL,
  `TienDatCoc` double NOT NULL,
  `TongTien` double NOT NULL
) ;


-- Cấu trúc bảng cho bảng `hopdongchitiet`
--

CREATE TABLE `hopdongchitiet` (
  `idHopDongChiTiet` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `idHopDong` int NOT NULL,
  `idDichVuDiKem` int NOT NULL,
  `SoLuong` int NOT NULL
);


-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `idKhachHang` int NOT NULL PRIMARY KEY,
  `HoTen` varchar(50) NOT NULL,
  `GioiTinh` bit(1) NOT NULL,
  `CMND` varchar(9) NOT NULL,
  `SoDT` varchar(10) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `idLoaiKhachHang` int NOT NULL,
  `DiaChi` varchar(100) NOT NULL
) ;


-- Cấu trúc bảng cho bảng `kieuthue`
--

CREATE TABLE `kieuthue` (
  `idKieuThue` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `TenKieuThue` varchar(45) NOT NULL,
  `Gia` double NOT NULL
);

-- Cấu trúc bảng cho bảng `loaidichvu`
--

CREATE TABLE `loaidichvu` (
  `idLoaiDichVu` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `TenLoaiDichVu` varchar(45) NOT NULL
);

-- Cấu trúc bảng cho bảng `loaikhachhang`
--

CREATE TABLE `loaikhachhang` (
  `idLoaiKhachHang` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `TenLoaiKhachHang` varchar(45) NOT NULL
);


-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `idNhanVien` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `HoTen` varchar(50)  NOT NULL,
  `NgaySinh` date NOT NULL,
  `CMND` varchar(9) NOT NULL,
  `SoDT` varchar(10) NOT NULL,
  `email` varchar(100)  NOT NULL,
  `idTrinhDo` int NOT NULL,
  `idViTri` int NOT NULL,
  `Luong` double NOT NULL,
  `idBoPhan` int NOT NULL
) ;


-- Cấu trúc bảng cho bảng `trinhdo`
--

CREATE TABLE `trinhdo` (
  `idTrinhDo` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `TrinhDo` varchar(45) NOT NULL
);


-- Cấu trúc bảng cho bảng `vitri`
--

CREATE TABLE `vitri` (
  `idViTri` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ViTri` varchar(45) NOT NULL
);



--
-- Chỉ mục cho bảng `dichvu`
--
ALTER TABLE `dichvu`
  ADD KEY `fk_dichvu_kieuthue_idx` (`idKieuThue`),
  ADD KEY `fk_dichvu_loaidichvu_idx` (`idLoaiDichVu`);


--
-- Chỉ mục cho bảng `hopdong`
--
ALTER TABLE `hopdong`
  ADD KEY `fk_hopdong_nhanvien_idx` (`idNhanVien`),
  ADD KEY `fk_hopdong_khachhang_idx` (`idKhachHang`),
  ADD KEY `fk_hopdong_dichvu_idx` (`idDichVu`);

--
-- Chỉ mục cho bảng `hopdongchitiet`
--
ALTER TABLE `hopdongchitiet`
  ADD KEY `fk_hopdongchitiet_hopdong_idx` (`idHopDong`),
  ADD KEY `fk_hopdongchitiet_dchvudikem_idx` (`idDichVuDiKem`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD KEY `fk_khachhang_loaiKhachhang_idx` (`idLoaiKhachHang`);


--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD KEY `fk_vitri_employee_idx` (`idViTri`),
  ADD KEY `fk_bophan_employee_idx` (`idBoPhan`),
  ADD KEY `fk_trinhdo_employee_idx` (`idTrinhDo`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `dichvu`
--
ALTER TABLE `dichvu`
  ADD CONSTRAINT `fk_dichvu_kieuthue` FOREIGN KEY (`idKieuThue`) REFERENCES `kieuthue` (`idKieuThue`),
  ADD CONSTRAINT `fk_dichvu_loaidichvu` FOREIGN KEY (`idLoaiDichVu`) REFERENCES `loaidichvu` (`idLoaiDichVu`);

--
-- Các ràng buộc cho bảng `hopdong`
--
ALTER TABLE `hopdong`
  ADD CONSTRAINT `fk_hopdong_dichvu` FOREIGN KEY (`idDichVu`) REFERENCES `dichvu` (`idDichVu`),
  ADD CONSTRAINT `fk_hopdong_khachhang` FOREIGN KEY (`idKhachHang`) REFERENCES `khachhang` (`idKhachHang`),
  ADD CONSTRAINT `fk_hopdong_nhanvien` FOREIGN KEY (`idNhanVien`) REFERENCES `nhanvien` (`idNhanVien`);

--
-- Các ràng buộc cho bảng `hopdongchitiet`
--
ALTER TABLE `hopdongchitiet`
  ADD CONSTRAINT `fk_hopdongchitiet_dchvudikem` FOREIGN KEY (`idDichVuDiKem`) REFERENCES `dichvudikem` (`idDichVuDiKem`),
  ADD CONSTRAINT `fk_hopdongchitiet_hopdong` FOREIGN KEY (`idHopDong`) REFERENCES `hopdong` (`idHopDong`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD CONSTRAINT `fk_khachhang_loaiKhachhang` FOREIGN KEY (`idLoaiKhachHang`) REFERENCES `loaikhachhang` (`idLoaiKhachHang`);

--
-- Các ràng buộc cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD CONSTRAINT `fk_bophan_employee` FOREIGN KEY (`idBoPhan`) REFERENCES `bophan` (`idBoPhan`),
  ADD CONSTRAINT `fk_trinhdo_employee` FOREIGN KEY (`idTrinhDo`) REFERENCES `trinhdo` (`idTrinhDo`),
  ADD CONSTRAINT `fk_vitri_employee` FOREIGN KEY (`idViTri`) REFERENCES `vitri` (`idViTri`);
