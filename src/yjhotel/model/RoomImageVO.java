package yjhotel.model;

public class RoomImageVO {
	int r_number;
	String r_image;

	RoomImageVO(String r_image) {
		this.r_image = r_image;
	}

	public int getR_number() {
		return r_number;
	}

	public void setR_number(int r_number) {
		this.r_number = r_number;
	}

	public String getR_image() {
		return r_image;
	}

	public void setR_image(String r_image) {
		this.r_image = r_image;
	}
}
