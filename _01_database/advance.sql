-- 21.	Tạo khung nhìn có tên là V_NHANVIEN để lấy được thông tin của tất cả các nhân viên có địa chỉ là “Hải Châu” 
-- và đã từng lập hợp đồng cho 1 hoặc nhiều Khách hàng bất kỳ  với ngày lập hợp đồng là “12/12/2019”
drop view if exists V_NHANVIEN;
create view V_NHANVIEN as
select nv.idNhanVien, nv.HoTen, nv.NgaySinh, nv.SoDT, nv.diachi from nhanvien nv join hopdong hd on nv.idNhanVien = hd.idNhanVien
where hd.NgayBatDau = '2019-12-12' and nv.DiaChi like '%Hải Châu%';




-- -------------------------------------------------------------------------------------
-- 22.	Thông qua khung nhìn V_NHANVIEN thực hiện cập nhật địa chỉ thành “Liên Chiểu”
-- đối với tất cả các Nhân viên được nhìn thấy bởi khung nhìn này.
start transaction;
update V_NHANVIEN 
set diachi = "Liên Chiểu" ;
rollback;



-- -------------------------------------------------------------------------------------
-- 23.	Tạo Clustered Index có tên là IX_KHACHHANG trên bảng Khách hàng.
-- Giải thích lý do và thực hiện kiểm tra tính hiệu quả của việc sử dụng INDEX

create index IX_KHACHHANG on khachhang(idkhachhang);
show index from khachhang;
explain select * from khachhang;

-- Clustered index định nghĩa thứ tự mà dữ liệu được lưu trữ vật lý trong một bảng.
-- Khi một table có một Clusted Index thì khi đó table được gọi là Clustered Table. 
-- Giống như bạn có 1 mục lục, bạn tìm kiếm đến 1 mục và chỉ việc click vào expand thông tin ra là xong, không cần phải đi đâu khác nữa.
-- vì mỗi table chỉ nên có 1 clustered index nên inniDB sẽ mặc định chọn Primary Key làm "index column"
-- Nếu table không có khai báo Primary key, InnoDB sẽ tìm kiếm cột nào thỏa mãn điều kiện Unique và Not null để thay thế
-- Nếu trong table đó vẫn không có cột nào Unique và Not null, InnoDB sẽ dùng cách cuối cùng là tự define một hidden primary key và cluster data trên cái cột này.


-- -------------------------------------------------------------------------------------
-- 24.	Tạo Non-Clustered Index có tên là IX_SoDT_DiaChi trên các cột SODIENTHOAI và DIACHI trên bảng KHACH HANG 
-- và kiểm tra tính hiệu quả tìm kiếm sau khi tạo Index.

create index IX_SoDT_DiaChi on khachhang(sodt,diachi);
show indexes from khachhang; 
EXPLAIN SELECT idKhachHang, HoTen , DiaChi
FROM
    khachhang
WHERE
    diachi like '%Đà Nẵng%';




-- -------------------------------------------------------------------------------------
-- 25.	Tạo Store procedure Sp_1 Dùng để xóa thông tin của một Khách hàng nào đó với Id Khách hàng được truyền vào như là 1 tham số của Sp_1
drop procedure if exists sp_1;
DELIMITER $$ 
create procedure sp_1(in id int)
begin    
	delete from hopdong
    where idkhachhang =id;
    
	delete from khachhang 
	where idKhachHang = id;
end $$;
delimiter ;

start transaction;
call sp_1(3);
rollback;

-- -------------------------------------------------------------------------------------
-- 26.	Tạo Store procedure Sp_2 Dùng để thêm mới vào bảng HopDong với yêu cầu Sp_2 phải thực hiện kiểm tra tính hợp lệ của dữ liệu bổ sung,
-- với nguyên tắc không được trùng khóa chính và đảm bảo toàn vẹn tham chiếu đến các bảng liên quan.
drop procedure if exists sp_2;
DELIMITER $$ 
create procedure sp_2(
	in idhd int,
    in idnv int,
    in idkh int,
    in iddv int,
    in ngayKT date,
    in tiencoc double,
    in tt double,
    out result varchar(250)
)
begin
	if exists (select * from hopdong where idhopdong = idhd) then
		set result = 'Id Hợp đồng đã tồn tại';
	elseif not exists (select * from nhanvien where idNhanVien = idnv) then
		set result = 'Id nhân viên không tồn tại' ;
	elseif not exists (select * from khachhang where idKhachHang = idkh) then
		set result = 'Id khách hàng không tồn tại' ;
	elseif not exists (select * from dichvu where idDichVu = iddv) then
		set result = 'Id dịch vụ không tồn tại' ;
	elseif (ngayKT <= curdate()) then
		set result = 'Ngày kết thúc phải sau hôm nay';
	else 
		insert into hopdong value (idhd , idnv, idkh, iddv , curdate(), ngaykt, tiencoc , tt );
		set result = 'Thêm thành công';
        
	end if;
end $$;
DELIMITER ;

start transaction;
set @result = '0';
call furama.sp_2(11, 1, 1, 1, '2021-04-22', 200000, 2000000, @result);
select @result;
rollback;



-- -------------------------------------------------------------------------------------
-- 27.	Tạo triggers có tên Tr_1 Xóa bản ghi trong bảng HopDong 
-- thì hiển thị tổng số lượng bản ghi còn lại có trong bảng HopDong ra giao diện console của database

 DROP trigger if exists tr_1;
DELIMITER $$
create trigger tr_1 
after delete
on hopdong 
for each row
begin
declare message varchar(100);
select count(idHopDong) as `Số hợp đồng còn lại` from hopdong into @p;
set message = concat('số hợp đồng còn lại là ', @p);
signal sqlstate '45000' set message_text =  message ;
end $$;
DELIMITER ;



start transaction;
delete from hopdong where idHopDong =1;
select * from hopdong;
rollback;



 
-- -------------------------------------------------------------------------------------
-- 28.	Tạo triggers có tên Tr_2 Khi cập nhật Ngày kết thúc hợp đồng, cần kiểm tra xem thời gian cập nhật có phù hợp hay không,
-- với quy tắc sau: Ngày kết thúc hợp đồng phải lớn hơn ngày làm hợp đồng ít nhất là 2 ngày.
-- Nếu dữ liệu hợp lệ thì cho phép cập nhật, nếu dữ liệu không hợp lệ thì
-- in ra thông báo “Ngày kết thúc hợp đồng phải lớn hơn ngày làm hợp đồng ít nhất là 2 ngày” trên console của database
DROP trigger if exists tr_2;
DELIMITER $$
create trigger tr_2 
before update
on hopdong 
for each row
begin
if((new.NgayKetThuc - new.NgayBatDau) < 2 )then
signal sqlstate '45000' set message_text =  'Ngày kết thúc hợp đồng phải lớn hơn ngày làm hợp đồng ít nhất là 2 ngày' ;
end if;
end $$;
DELIMITER ;

start transaction;
update hopdong
set ngayketthuc = '2015-03-21'
where idhopdong =1;
rollback;




-- -------------------------------------------------------------------------------------
-- 29.	Tạo user function thực hiện yêu cầu sau:
-- a.	Tạo user function func_1: Đếm các dịch vụ đã được sử dụng với Tổng tiền là > 2.000.000 VNĐ.
drop function if exists func_1;

DELIMITER $$ 
CREATE FUNCTION `func_1` (
	tongtien double
)
RETURNS INTEGER
DETERMINISTIC
BEGIN
declare a integer ;
set a = (select count(distinct dv.idDichVu) as `số lần` from dichvu dv join hopdong hd on dv.idDichVu = hd.idDichVu
where hd.TongTien > tongtien) ;
RETURN a;
END $$
DELIMITER 
;

-- b.	Tạo user function Func_2: Tính khoảng thời gian dài nhất tính từ lúc bắt đầu làm hợp đồng đến lúc kết thúc hợp đồng mà Khách hàng đã thực hiện thuê dịch vụ 
-- (lưu ý chỉ xét các khoảng thời gian dựa vào từng lần làm hợp đồng thuê dịch vụ, không xét trên toàn bộ các lần làm hợp đồng).
-- Mã của Khách hàng được truyền vào như là 1 tham số của function này.

drop function if exists func_2;

DELIMITER $$ 
CREATE FUNCTION `func_2` (
	idkh int
)
RETURNS INTEGER
DETERMINISTIC
BEGIN
declare a integer ;
set a = (select max(NgayKetThuc-NgayBatDau) as `Thời gian(ngày)`
				from hopdong hd join khachhang kh on hd.idKhachHang = kh.idKhachHang
				where kh.idKhachHang = idkh) ;
RETURN a;
END $$
DELIMITER 
;




-- -------------------------------------------------------------------------------------
-- 30.	Tạo Stored procedure Sp_3 để tìm các dịch vụ được thuê bởi khách hàng với loại dịch vụ là “Room” từ đầu năm 2015 đến hết năm 2019 
-- để xóa thông tin của các dịch vụ đó (tức là xóa các bảng ghi trong bảng DichVu) và xóa những HopDong sử dụng dịch vụ liên quan
-- (tức là phải xóa những bản gi trong bảng HopDong) và những bản liên quan khác.
drop procedure if exists sp_3;
delimiter $$
create procedure sp_3(
		in tendv varchar(50),
        in tuNam year,
        in denNam year
        )
begin
	delete from hopdong where hopdong.idDichVu in 
		(select * from (select dv.idDichVu from hopdong hd join dichvu dv on dv.idDichVu = hd.idDichVu
						join loaidichvu ldv on ldv.idLoaiDichVu = dv.idLoaiDichVu
						where year(hd.NgayBatDau) between tuNam and denNam and ldv.TenLoaiDichVu like  tendv  ) as x);
	delete from dichvu where dichvu.iddichvu in
		(select * from (select dv.idDichVu from hopdong hd join dichvu dv on dv.idDichVu = hd.idDichVu
						join loaidichvu ldv on ldv.idLoaiDichVu = dv.idLoaiDichVu
						where year(hd.NgayBatDau) between tuNam and denNam and ldv.TenLoaiDichVu like  tendv  ) as x);

end ;$$
delimiter ;

