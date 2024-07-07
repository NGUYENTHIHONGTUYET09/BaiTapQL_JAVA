﻿
CREATE DATABSE QuanLyBenhNhan

CREATE TABLE TINH(
 MATTP INT PRIMARY KEY ,
 TENTPP NVARCHAR(50)
 );

 INSERT INTO TINHTP (MATTP, TENTPP) VALUES
(1, N'An Giang'),
(2, N'Bà Rịa - Vũng Tàu'),
(3, N'Bạc Liêu'),
(4, N'Bắc Kạn'),
(5, N'Bắc Giang'),
(6, N'Bắc Ninh'),
(7, N'Bến Tre'),
(8, N'Bình Dương'),
(9, N'Bình Định'),
(10, N'Bình Phước'),
(11, N'Bình Thuận'),
(12, N'Cà Mau'),
(13, N'Cao Bằng'),
(14, N'Cần Thơ'),
(15, N'Đà Nẵng'),
(16, N'Đắk Lắk'),
(17, N'Đắk Nông'),
(18, N'Điện Biên'),
(19, N'Đồng Nai'),
(20, N'Đồng Tháp'),
(21, N'Gia Lai'),
(22, N'Hà Giang'),
(23, N'Hà Nam'),
(24, N'Hà Nội'),
(25, N'Hà Tĩnh'),
(26, N'Hải Dương'),
(27, N'Hải Phòng'),
(28, N'Hậu Giang'),
(29, N'Hòa Bình'),
(30, N'Hưng Yên'),
(31, N'Khánh Hòa'),
(32, N'Kiên Giang'),
(33, N'Kon Tum'),
(34, N'Lai Châu'),
(35, N'Lâm Đồng'),
(36, N'Lạng Sơn'),
(37, N'Lào Cai'),
(38, N'Long An'),
(39, N'Nam Định'),
(40, N'Nghệ An'),
(41, N'Ninh Bình'),
(42, N'Ninh Thuận'),
(43, N'Phú Thọ'),
(44, N'Phú Yên'),
(45, N'Quảng Bình'),
(46, N'Quảng Nam'),
(47, N'Quảng Ngãi'),
(48, N'Quảng Ninh'),
(49, N'Quảng Trị'),
(50, N'Sóc Trăng'),
(51, N'Sơn La'),
(52, N'Tây Ninh'),
(53, N'Thái Bình'),
(54, N'Thái Nguyên'),
(55, N'Thanh Hóa'),
(56, N'Thừa Thiên Huế'),
(57, N'Tiền Giang'),
(58, N'TP Hồ Chí Minh'),
(59, N'Trà Vinh'),
(60, N'Tuyên Quang'),
(61, N'Vĩnh Long'),
(62, N'Vĩnh Phúc'),
(63, N'Yên Bái');

go

CREATE TABLE BenhNhan (
    id INT IDENTITY(1,1) PRIMARY KEY , -- Trường tự động tăng
    maBN AS 'BN' + RIGHT('00000' + CAST(id AS VARCHAR(10)), 5), -- Trường computed column cho mã định dạng
    tenBN NVARCHAR(50),
    sdt VARCHAR(10),
    ngaySinh SMALLDATETIME,
    diaChi NVARCHAR(50),
    gioiTinh NVARCHAR(5),
    queQuan int,
);
