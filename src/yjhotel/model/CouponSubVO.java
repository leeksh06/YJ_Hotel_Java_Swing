package yjhotel.model;

public class CouponSubVO {
	int c_number;
	int c_serial_number;
	String c_name;
	int c_rate;

	CouponSubVO(int c_number,String c_name, int c_rate) {
		this.c_number = c_number;
		this.c_name = c_name;
		this.c_rate = c_rate;
	}

	public int getC_number() {
		return c_number;
	}

	public void setC_number(int c_number) {
		this.c_number = c_number;
	}

	public int getC_serial_number() {
		return c_serial_number;
	}

	public void setC_serial_number(int c_serial_number) {
		this.c_serial_number = c_serial_number;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public int getC_rate() {
		return c_rate;
	}

	public void setC_rate(int c_rate) {
		this.c_rate = c_rate;
	}
}
