drop database yjhotel;
create database yjhotel;
use yjhotel;

CREATE TABLE 회원 (
u_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
u_name VARCHAR(12) NOT NULL,
u_id VARCHAR(16) NOT NULL UNIQUE,
u_password VARCHAR(30) NOT NULL,
u_phone_number VARCHAR(11) NOT NULL UNIQUE,
u_birthday DATE,
u_email VARCHAR(25) NOT NULL UNIQUE,
u_cash INT NOT NULL DEFAULT 0,
u_enroll_date DATE NOT NULL,
u_profile_image VARCHAR(100)
);

CREATE TABLE 숙소 (
h_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
h_name VARCHAR(20) NOT NULL UNIQUE,
h_description VARCHAR(100),
h_check_in TIME NOT NULL DEFAULT '13:00:00',
h_check_out TIME NOT NULL DEFAULT '11:00:00',
h_demand VARCHAR(10) NOT NULL DEFAULT '비성수기',
h_address VARCHAR(20) NOT NULL,
h_location VARCHAR(80) NOT NULL,
h_image VARCHAR(100),
CHECK (h_demand = '성수기' OR h_demand = '비성수기')
);

CREATE TABLE 방 (
r_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
r_name VARCHAR(20) NOT NULL,
r_room_number INT NOT NULL,
r_area_size INT NOT NULL DEFAULT 0,
r_max_personnel INT NOT NULL DEFAULT 1,
r_demand_rate DOUBLE NOT NULL DEFAULT 1.0,
r_cost INT NOT NULL DEFAULT 0,
r_total_cost INT NOT NULL DEFAULT 0,
h_number INT NOT NULL,
FOREIGN KEY (h_number) REFERENCES 숙소(h_number)
ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE 방_이미지 (
r_image VARCHAR(100),
r_number INT NOT NULL,
FOREIGN KEY (r_number) REFERENCES 방(r_number)
ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE 예약 (
b_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
b_guest_quantity INT NOT NULL DEFAULT 1,
b_payment_method VARCHAR(16) NOT NULL DEFAULT '보유 포인트',
b_payment_cost INT NOT NULL DEFAULT 0,
b_status VARCHAR(10) NOT NULL DEFAULT '기록',
b_payment_date DATE NOT NULL,
u_number INT NOT NULL,
h_number INT NOT NULL,
r_number INT NOT NULL,
FOREIGN KEY (u_number) REFERENCES 회원(u_number)
ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (h_number) REFERENCES 숙소(h_number)
ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (r_number) REFERENCES 방(r_number)
ON DELETE NO ACTION ON UPDATE CASCADE,
CHECK (b_status = '예약중' OR b_status = '기록' OR b_status = '취소'),
CHECK (b_payment_method = '보유 포인트')
);

CREATE TABLE 예약_일자 (
b_date DATE NOT NULL,
b_number INT NOT NULL,
FOREIGN KEY (b_number) REFERENCES 예약(b_number)
ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE 쿠폰 (
c_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
c_serial_number CHAR(16) NOT NULL UNIQUE,
c_name VARCHAR(30) NOT NULL,
c_display VARCHAR(10) NOT NULL DEFAULT '출력',
c_rate DOUBLE NOT NULL,
c_min_date DATE NOT NULL,
c_max_date DATE NOT NULL,
c_min_age INT,
c_max_age INT,
c_min_cost INT,
c_max_enroll_date DATE,
c_min_enroll_date DATE,
CHECK (LENGTH(c_serial_number) = 16),
CHECK (c_display = '출력' OR c_display = '숨김'),
CHECK (c_min_date <= c_max_date)
);

CREATE TABLE 쿠폰_보유상태 (
c_status VARCHAR(16) NOT NULL DEFAULT '보유중',
c_number INT NOT NULL,
u_number INT NOT NULL,
FOREIGN KEY (c_number) REFERENCES 쿠폰(c_number)
ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (u_number) REFERENCES 회원(u_number)
ON DELETE CASCADE ON UPDATE CASCADE,
CHECK (c_status = '보유중' OR c_status='사용완료')
);

CREATE TABLE 공지사항 (
n_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
n_title VARCHAR(50) NOT NULL,
n_content VARCHAR(500) NOT NULL,
n_date DATE NOT NULL
);
CREATE TABLE 공지사항_이미지 (
n_image VARCHAR(100) NOT NULL,
n_number INT NOT NULL,
FOREIGN KEY (n_number) REFERENCES 공지사항(n_number)
ON DELETE CASCADE ON UPDATE CASCADE
);
