package yjhotel.model;

import java.sql.Date;

public class CouponVO {
	// 가공되지않은 DB 필드값
	public int coupon_id;
	public String serial_number; // char16
	public String name; // varchar30
	public String display;
	public double rate;
	public Date min_date;
	public Date max_date;
	public int min_age;
	public int max_age;
	public int min_cost;
	public int min_enroll_date;
	public int max_enroll_date;

	// DB 필드값을 가공해서 리턴하는 부분
	// 기간
	public String getPeriod() {
		if (min_date == null || max_date == null)
			return "날짜를 불러올 수 없습니다.";
		return min_date.toString() + " ~ " + max_date.toString();
	}

	// 툴팁
	public String getTooltip() {
		String tooltip = "";
		if (min_age != 0) {
			tooltip = "," + min_age + "세 이상";
		}
		if (max_age != 0) {
			tooltip += "," + max_age + "세 이하";
		}
		if (min_cost != 0) {
			tooltip += "," + min_cost + "원 이상";
		}
		if (min_enroll_date != 0) {
			tooltip += ",가입일 " + min_enroll_date + "일 이상";
		}
		if (max_enroll_date != 0) {
			tooltip += ",가입일 " + max_enroll_date + "일 이하";
		}
		if (tooltip.length() != 0) {
			tooltip += " 사용가능";
			return tooltip.substring(1);
		}
		return "조건없음";
	}
}