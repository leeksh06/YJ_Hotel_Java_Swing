package yjhotel.model;

public class SearchVO {

	private String h_name; // 숙소 이름
	private String h_image; // 숙소 이미지

	private int r_cost; // 최소 가격
	private int r_cost1; // 최대 가격
	private int r_people; // 최소 인원
	private int r_people1; // 최대 인원
	private int h_number; // 숙소 넘버(검색 폼에서 몇 번 숙소를 클릭했는지 방 상세보기 폼에 버튼 번호를 넘기기 위함.)

	public int getH_number() {
		return h_number;
	}

	public void setH_number(int h_number) {
		this.h_number = h_number;
	}

	public String getH_image() {
		return h_image;
	}

	public void setH_image(String h_image) {
		this.h_image = h_image;
	}

	public int getR_people1() {
		return r_people1;
	}

	public void setR_people1(int r_people1) {
		this.r_people1 = r_people1;
	}

	public int getR_people() {
		return r_people;
	}

	public void setR_people(int r_people) {
		this.r_people = r_people;
	}

	public int getR_cost1() {
		return r_cost1;
	}

	public void setR_cost1(int r_cost1) {
		this.r_cost1 = r_cost1;
	}

	public int getR_cost() {
		return r_cost;
	}

	public void setR_cost(int r_cost) {
		this.r_cost = r_cost;
	}

	public String getH_name() {
		return h_name;
	}

	public void setH_name(String h_name) {
		this.h_name = h_name;
	}
}