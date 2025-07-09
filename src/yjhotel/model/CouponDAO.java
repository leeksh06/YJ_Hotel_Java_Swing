package yjhotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yjhotel.YJHotel;

public class CouponDAO {

	// ResultSet과 Statement PreparedStatement는 사용 후 close 해줘야 메모리에 남지 않는다.
	private static void closeRs(ResultSet rs) {
		try {
			if (!rs.isClosed()) {
				rs.close();
			}
		} catch (Exception e) {

		}
	}

	// ResultSet 결과에서 쿠폰을 실제로 가져오는 부분이 중복되는 코드가 되어
	// 해당부분을 하나의 메소드로 합침
	private static CouponVO parseCoupon(ResultSet rs) {
		CouponVO coupon = new CouponVO();
		try {

			coupon.coupon_id = rs.getInt(1);
			coupon.serial_number = rs.getString(2);
			coupon.name = rs.getString(3);
			coupon.display = rs.getString(4);
			coupon.rate = rs.getDouble(5);
			coupon.min_date = rs.getDate(6);
			coupon.max_date = rs.getDate(7);
			coupon.min_age = rs.getInt(8);
			coupon.max_age = rs.getInt(9);
			coupon.min_cost = rs.getInt(10);
			coupon.min_enroll_date = rs.getInt(11);
			coupon.max_enroll_date = rs.getInt(12);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupon;
	}

	public static ArrayList<CouponVO> getCoupons() {
		ResultSet rs = null;
		try {
			ArrayList<CouponVO> coupons = new ArrayList<CouponVO>();

			// DB 연결 가져오기
			Connection conn = YJHotel.getConnection();
			// Statement 생성하기
			Statement stm = conn.createStatement();
			// Statement에서 쿼리 실행해서 ResultSet 결과 받기
			rs = stm.executeQuery("select * from 쿠폰");

			while (rs.next()) {
				CouponVO coupon = parseCoupon(rs);
				coupons.add(coupon);
			}

			return coupons;
		} catch (Exception e) {
			// DB 과정에서 문제 발생시 오류 캐치
			e.printStackTrace();
			YJHotel.showAlert("쿠폰을 불러올 수 없습니다.");
		} finally {
			closeRs(rs);
		}

		return null;
	}

	public static ArrayList<CouponVO> getRemainCoupons(int u_number) {
		ResultSet rs = null;
		try {
			Connection conn = YJHotel.getConnection();
			ArrayList<CouponVO> coupons = new ArrayList<CouponVO>();
			Statement stm = conn.createStatement();
			rs = stm.executeQuery("select * from 쿠폰 where c_number in" + "(select c_number from 쿠폰_보유상태 where u_number="
					+ u_number + " and c_status='보유중');");

			while (rs.next()) {
				CouponVO coupon = parseCoupon(rs);
				coupons.add(coupon);
			}

			return coupons;
		} catch (Exception e) {
			e.printStackTrace();
			YJHotel.showAlert("쿠폰을 불러올 수 없습니다.");
		} finally {
			closeRs(rs);
		}

		return null;
	}

	public static ArrayList<CouponVO> getAvailCoupons(int u_number) {
		ResultSet rs = null;
		try {
			Connection conn = YJHotel.getConnection();
			ArrayList<CouponVO> coupons = new ArrayList<CouponVO>();
			Statement stm = conn.createStatement();
			rs = stm.executeQuery("select * from 쿠폰 where c_display=\"출력\" and c_number not in"
					+ "(select c_number from 쿠폰_보유상태 where u_number=" + u_number + " and (c_status='보유중' or c_status='사용완료'));");

			while (rs.next()) {
				CouponVO coupon = parseCoupon(rs);
				coupons.add(coupon);
			}

			return coupons;
		} catch (Exception e) {
			e.printStackTrace();
			YJHotel.showAlert("쿠폰을 불러올 수 없습니다.");
		} finally {
			closeRs(rs);
		}

		return null;
	}

	public static CouponVO getCouponWithSN(int u_number, String serial_number) {
		ResultSet rs = null;
		try {
			Connection conn = YJHotel.getConnection();
			Statement stm = conn.createStatement();
			rs = stm.executeQuery("select * from 쿠폰 where c_serial_number='" + serial_number + "'");

			CouponVO coupon = null;
			if (rs.next()) {
				coupon = parseCoupon(rs);
			} else {
				return null;
			}

			rs.close();

			PreparedStatement pstm = conn.prepareStatement("select * from 쿠폰_보유상태 where u_number=? and c_number=?");
			pstm.setInt(1, u_number);
			pstm.setInt(2, coupon.coupon_id);
			rs = pstm.executeQuery();
			if (rs.next()) {
				coupon.serial_number = null;
				return coupon;
			}

			return coupon;
		} catch (Exception e) {
			e.printStackTrace();
			YJHotel.showAlert("시리얼 번호를 찾을 수 없습니다.");
		} finally {
			closeRs(rs);
		}
		return null;
	}

	public static boolean insertCouponStatus(int u_number, int c_number) {
		ResultSet rs = null;
		try {
			Connection conn = YJHotel.getConnection();
			// SQL 쿼리의 기능
			// 테이블 이름 뒤에 ()를 생략해도, 테이블 필드와 동일한 순서로 전부 넣으면 동작함.
			String sql = "insert into 쿠폰_보유상태 values ('보유중', ?, ?)";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, c_number);
			pstm.setInt(2, u_number);
			pstm.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			YJHotel.showAlert("쿠폰을 등록할 수 없습니다.");
		} finally {
			closeRs(rs);
		}
		return false;
	}
}
