


-- ------------------------------------------------------
-- 2.	Hiển thị thông tin của tất cả nhân viên có trong hệ thống 
-- có tên bắt đầu là một trong các ký tự “H”, “T” hoặc “K” và có tối đa 15 ký tự.

select * from nhanvien where ( nhanvien.HoTen like "H%" or nhanvien.HoTen like "T%" or  nhanvien.HoTen like "K%" )
and char_length(nhanvien.hoten) < 15 ;

-- ----------------------------------------------------
-- 3.	Hiển thị thông tin của tất cả khách hàng có độ tuổi từ 18 đến 50 tuổi và có địa chỉ ở “Đà Nẵng” hoặc “Quảng Trị”.
select *,  ROUND(DATEDIFF(CURDATE(), khachhang.NgaySinh) / 365, 0) AS  Tuổi from khachhang 
where (khachhang.DiaChi like '%Đà Nẵng%' or khachhang.DiaChi like '%Quảng Trị%') 
and ROUND(DATEDIFF(CURDATE(), khachhang.NgaySinh) / 365, 0) > 18 
and ROUND(DATEDIFF(CURDATE(), khachhang.NgaySinh) / 365, 0) < 50  ;

-- -----------------------------------------------------
-- 4.	Đếm xem tương ứng với mỗi khách hàng đã từng đặt phòng bao nhiêu lần.
-- Kết quả hiển thị được sắp xếp tăng dần theo số lần đặt phòng của khách hàng. Chỉ đếm những khách hàng nào có Tên loại khách hàng là “Diamond”.

select b.idKhachHang as `Id khách hàng` , b.hoten as `Họ tên` ,c.tenloaikhachhang as `Loại khách hàng`  , count(a.idKhachHang) as `Số lần đặt`
from hopdong a  join khachhang b on a.idKhachHang = b.idKhachHang 
join loaikhachhang c on c.idLoaiKhachHang = b.idLoaiKhachHang 
where c.TenLoaiKhachHang like "Diamond"
group by b.idKhachHang
order by `Số lần đặt` asc
;

-- --------------------------------------------------------
-- 5.	Hiển thị IDKhachHang, HoTen, TenLoaiKhach, IDHopDong, TenDichVu, NgayLamHopDong, NgayKetThuc, TongTien
-- (Với TongTien được tính theo công thức như sau: ChiPhiThue + SoLuong*Gia, với SoLuong và Giá là từ bảng DichVuDiKem) cho tất cả các Khách hàng đã từng đặt phỏng.
--  (Những Khách hàng nào chưa từng đặt phòng cũng phải hiển thị ra).

select a.idKhachHang, a.hoten, c.TenLoaiKhachHang, b.idHopDong , d.TenDichVu, b.NgayBatDau , b.NgayKetThuc, (d.ChiPhiThue + e.SoLuong * f.gia) as `Tổng tiền` 
from khachhang a left join hopdong b on a.idKhachHang = b.idKhachHang 
left join loaikhachhang c on a.idLoaiKhachHang = c.idLoaiKhachHang
left join dichvu d on d.idDichvu = b.idDichVu 
left join hopdongchitiet e on e.idHopDong = b.idHopDong
left join dichvudikem f on f.idDichVuDiKem = e.idDichVuDiKem
ORDER BY `Tổng tiền`
;


-- --------------------------------------------------------
-- 6.	Hiển thị IDDichVu, TenDichVu, DienTich, ChiPhiThue, TenLoaiDichVu
-- của tất cả các loại Dịch vụ chưa từng được Khách hàng thực hiện đặt từ quý 1 của năm 2019 (Quý 1 là tháng 1, 2, 3)
--

select a.idDichVu , a.TenDichVu, a.DienTich, a.ChiPhiThue , c.TenLoaiDichVu
from dichvu a left join hopdong b on a.idDichVu = b.idDichVu
left join loaidichvu c on a.idLoaiDichVu = c.idLoaiDichVu 
where  a.idDichVu not in (select distinct a.idDichVu from dichvu a join hopdong b on a.idDichVu = b.idDichVu
							where year(b.NgayBatDau) = 2019 and month(b.NgayBatDau) between 1 and 3 )
group by a.idDichVu
;


 -- ---------------------------------------------------------------------------------------------
-- 7.	Hiển thị thông tin IDDichVu, TenDichVu, DienTich, SoNguoiToiDa, ChiPhiThue, TenLoaiDichVu 
-- của tất cả các loại dịch vụ đã từng được Khách hàng đặt phòng trong năm 2018 nhưng chưa từng được Khách hàng đặt phòng  trong năm 2019.

select b.idKhachHang, a.idDichVu, a.TenDichVu, a.DienTich, a.SoNguoiToiDa, a.ChiPhiThue, c.TenLoaiDichVu , b.NgayBatDau 
from dichvu a join hopdong b on a.idDichVu = b.idDichVu
join loaidichvu c on a.idLoaiDichVu = c.idLoaiDichVu
where a.idDichVu in (select b.idDichVu from hopdong a join dichvu b on a.idDichVu = b.idDichVu
where year(a.NgayBatDau) = 2018) 
and a.idDichVu not in (select b.idDichVu from hopdong a join dichvu b on a.idDichVu = b.idDichVu
where year(a.NgayBatDau) = 2019)
order by b.NgayBatDau
;

 -- ---------------------------------------------------------------------------------------------
-- 8.	Hiển thị thông tin HoTenKhachHang có trong hệ thống, với yêu cầu HoTenKhachHang không trùng nhau.
-- Học viên sử dụng theo 3 cách khác nhau để thực hiện yêu cầu trên

-- c1
select HoTen from khachhang group by HoTen;

-- c2 
select distinct Hoten from khachhang order by HoTen;
-- c3


 -- ---------------------------------------------------------------------------------------------
-- 9.	Thực hiện thống kê doanh thu theo tháng, nghĩa là tương ứng với mỗi tháng trong năm 2019 thì sẽ có bao nhiêu khách hàng thực hiện đặt phòng

select month(NgayBatDau), count(idHopDong) from hopdong
where year(NgayBatDau) = 2019
group by month(NgayBatDau);

  -- ---------------------------------------------------------------------------------------------
 -- 10.	Hiển thị thông tin tương ứng với từng Hợp đồng thì đã sử dụng bao nhiêu Dịch vụ đi kèm.
 -- Kết quả hiển thị bao gồm IDHopDong, NgayLamHopDong, NgayKetthuc, TienDatCoc, SoLuongDichVuDiKem (được tính dựa trên việc count các IDHopDongChiTiet).
 
 select a.idHopDong, a.NgayBatDau, a.NgayKetThuc, a.TienDatCoc, count(b.idHopDongChiTiet) as `Số lượng dịch vụ đi kèm` 
 from hopdong a join hopdongchitiet b on a.idHopDong = b.idHopDong
 group by a.idHopDong;
 
  -- ---------------------------------------------------------------------------------------------
 -- 11.	Hiển thị thông tin các Dịch vụ đi kèm đã được sử dụng bởi những Khách hàng có TenLoaiKhachHang là “Diamond” và có địa chỉ là “Vinh” hoặc “Quảng Ngãi”.
 
 select e.idDichVuDiKem, e.tenDichVuDiKem, e.Gia , b.HoTen, c.TenLoaiKhachHang, b.DiaChi from hopdong a join khachhang b on a.idKhachHang = b.idKhachHang
 join loaikhachhang c on c.idLoaiKhachHang = b.idLoaiKhachHang
 join hopdongchitiet d on d.idHopDong = a.idHopDong
 join dichvudikem e on e.idDichVuDiKem = d.idDichVuDiKem
 where c.TenLoaiKhachHang like 'Diamond' and (b.DiaChi like'%Quảng Ngãi%' or b.DiaChi like '%Vinh%')
 group by e.tenDichVuDiKem
 order by e.idDichVuDiKem
 ;
 
  -- ---------------------------------------------------------------------------------------------
 -- 12.	Hiển thị thông tin IDHopDong, TenNhanVien, TenKhachHang, SoDienThoaiKhachHang, TenDichVu, SoLuongDichVuDikem (được tính dựa trên tổng Hợp đồng chi tiết), 
 -- TienDatCoc của tất cả các dịch vụ đã từng được khách hàng đặt vào 3 tháng cuối năm 2019 nhưng chưa từng được khách hàng đặt vào 6 tháng đầu năm 2019.
 
select hd.idHopDong, nv.HoTen as `ten nv`, kh.HoTen as`ten kh` ,kh.SoDT, dv.TenDichVu,count(hdct.SoLuong) as `So luong dich vu di kem`, hd.TienDatCoc from hopdong hd 
join nhanvien nv on hd.idNhanVien = nv.idNhanVien
join dichvu dv on dv.idDichVu = hd.idDichVu
join hopdongchitiet hdct on hdct.idHopDong = hd.idHopDong
join khachhang kh on kh.idKhachHang = hd.idKhachHang
 where hd.idDichVu not in (
		select b.idDichVu from khachhang a join hopdong b on a.idKhachHang = b.idKhachHang
		where year(b.NgayBatDau) = 2019 and month(b.NgayBatDau) between 1 and 6 ) 
    and hd.idDichVu in (select b.idDichVu from khachhang a join hopdong b on a.idKhachHang = b.idKhachHang
		where year(b.NgayBatDau) = 2019 and month(b.NgayBatDau) between 10 and 12)
;
 
 
  -- ---------------------------------------------------------------------------------------------
 -- 13.	Hiển thị thông tin các Dịch vụ đi kèm được sử dụng nhiều nhất bởi các Khách hàng đã đặt phòng. 
 -- (Lưu ý là có thể có nhiều dịch vụ có số lần sử dụng nhiều như nhau).
 

 select c.idDichVuDiKem, c.tenDichVuDiKem,c.gia, sum(b.SoLuong) as `Số lần sử dụng` 
 from hopdong a join hopdongchitiet b on a.idHopDong = b.idHopDong
				join dichvudikem c on c.idDichVuDiKem = b.idDichVuDiKem

group by c.idDichVuDiKem
having `Số lần sử dụng` in (select max(`Số lần sử dụng`) from (select sum(b.SoLuong) as `Số lần sử dụng` from hopdong a join hopdongchitiet b on a.idHopDong = b.idHopDong
													join dichvudikem c on c.idDichVuDiKem = b.idDichVuDiKem
													group by c.idDichVuDiKem
                                                    order by `Số lần sử dụng` desc
                                                    ) as max)
 ;
 
 

 
  -- ---------------------------------------------------------------------------------------------
 -- 14.	Hiển thị thông tin tất cả các Dịch vụ đi kèm chỉ mới được sử dụng một lần duy nhất. 
 -- Thông tin hiển thị bao gồm IDHopDong, TenLoaiDichVu, TenDichVuDiKem, SoLanSuDung.
 
 select a.idHopDong, c.tenDichVuDiKem, sum(b.SoLuong) as `Số lần sử dụng` from hopdong a join hopdongchitiet b on a.idHopDong = b.idHopDong
 join dichvudikem c on c.idDichVuDiKem = b.idDichVuDiKem
 join dichvu d on d.idDichVu = a.idDichVu
 group by c.tenDichVuDiKem
 having sum(b.SoLuong)  = 1
 ;
 
  -- ---------------------------------------------------------------------------------------------
 -- 15.	Hiển thi thông tin của tất cả nhân viên bao gồm IDNhanVien, HoTen, TrinhDo, TenBoPhan, SoDienThoai, DiaChi mới chỉ lập được tối đa 3 hợp đồng từ năm 2018 đến 2019.
 
 select a.idNhanVien, a.HoTen , a.SoDT, a.NgaySinh, c.TrinhDo, d.BoPhan, count(b.idHopDong) as `số lần lập hợp đồng`, b.NgayBatDau
 from nhanvien a join hopdong b on b.idNhanVien = a.idNhanVien
 join trinhdo c on c.idTrinhDo = a.idTrinhDo
 join bophan d on d.idBoPhan = a.idBoPhan
 where NgayBatDau between '2018-01-01' and '2019-12-31'
 group by a.idNhanVien
 having count(b.idHopDong) <=3
 ;
 
 -- ---------------------------------------------------------------------------------------------
 -- 16.	Xóa những Nhân viên chưa từng lập được hợp đồng nào từ năm 2017 đến năm 2019.
 
start transaction;
-- xóa những bảng hợp đông được lập trước 2017 loại trừ những hợp đồng được lập bởi các nhân viên có lập hợp đồng trong 2017-2019
delete hopdong.* from hopdong
where hopdong.idHopDong in ( select* from (
 select idHopDong from hopdong 
 where idNhanVien in( 
	select a.idNhanVien from nhanvien a left join hopdong b on a.idNhanVien = b.idNhanVien
	where a.idNhanVien not in ( 
		select a.idNhanVien 
		from nhanvien a join hopdong b on a.idNhanVien = b.idNhanVien 
		where  b.NgayBatDau between '2017-01-01' and '2019-12-31')
	group by a.idNhanVien
  )) as X
  )  ;

delete from nhanvien 
where nhanvien.idNhanVien in (select * from (
 select a.idNhanVien from nhanvien a left join hopdong b on a.idNhanVien = b.idNhanVien
 where a.idNhanVien not in 
  ( select a.idNhanVien 
  from nhanvien a join hopdong b on a.idNhanVien = b.idNhanVien 
  where  b.NgayBatDau between '2017-01-01' and '2019-12-31')
  group by a.idNhanVien
  ) as X
  )
 ;
rollback;
 
 
  -- ---------------------------------------------------------------------------------------------
 -- 17.	Cập nhật thông tin những khách hàng có TenLoaiKhachHang từ  Platinium lên Diamond,
 -- chỉ cập nhật những khách hàng đã từng đặt phòng với tổng Tiền thanh toán trong năm 2019 là lớn hơn 10.000.000 VNĐ.
 update khachhang a
join loaikhachhang b on a.idLoaiKhachHang = b.idLoaiKhachHang
join hopdong c on c.idKhachHang = a.idKhachHang
 Set a.idLoaiKhachHang = 1
where kh.idKhachHang in (select a.idKhachHang
						from hopdong a
                        where  year(a.NgayBatDau) = 2019 
						group by a.idKhachHang
						having sum(TongTien) > 10000000);
                        
                        
                        
  -- ---------------------------------------------------------------------------------------------
 -- 18.	Xóa những khách hàng có hợp đồng trước năm 2016 (chú ý ràng buộc giữa các bảng).
 
start transaction;
-- xóa những bản hợp đồng mà chỉ có khách đặt trc năm 2016 và khách không có phát sinh hợp đồng sau 2016
delete hopdong.* from hopdong
where hopdong.idHopDong in ( select* from (
 select idHopDong from hopdong 
 where idKhachHang in( 
	select idKhachHang from khachhang 
	where idKhachHang not in (
	select a.idKhachHang from khachhang a join hopdong b on a.idKhachHang = b.idKhachHang
    where b.NgayBatDau > '2015-12-31'
)
  )) as X
  )  ;

delete khachhang.* from khachhang 
where khachhang.idKhachHang in (select * from (
 select idKhachHang from khachhang
 where idKhachHang not in 
  ( select a.idKhachHang from khachhang a join hopdong b on a.idKhachHang = b.idKhachHang
    where b.NgayBatDau > '2015-12-31')
  ) as X
)
 ;
rollback;
 
 
 
  -- ---------------------------------------------------------------------------------------------
 -- 19.	Cập nhật giá cho các Dịch vụ đi kèm được sử dụng trên 10 lần trong năm 2019 lên gấp đôi.
 -- chưa xong

start transaction;

update dichvudikem
set gia = gia * 2
where idDichVuDiKem in (select a.idDichVuDiKem  
						from hopdongchitiet a join dichvudikem b  on a.idDichVuDiKem = b.idDichVuDiKem
                        join hopdong c on c.idHopDong = a.idHopDong
                        where  year(c.NgayBatDau) = 2019
						group by a.idDichVuDiKem
						having sum(a.SoLuong) > 10 );
                        
rollback ;



 
  -- ---------------------------------------------------------------------------------------------
 -- 20.	Hiển thị thông tin của tất cả các Nhân viên và Khách hàng có trong hệ thống,
 -- thông tin hiển thị bao gồm ID (IDNhanVien, IDKhachHang), HoTen, Email, SoDienThoai, NgaySinh, DiaChi.
 
SELECT a.idKhachHang, a.HoTen , a.email, a.SoDT, a.DiaChi FROM khachhang a
UNION 
SELECT b.idNhanVien , b.HoTen, b.email, b.SoDT, b.NgaySinh FROM nhanvien b;
 


 