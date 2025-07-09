package yjhotel.model;

public class RoomSubVO {
	int r_number;
	String r_name;
	int r_room_number;
	int r_area_size;
	int r_max_personnel;
	int r_demand_rate;
	int r_cost;
	int r_totalcost;
	int h_number;

	RoomSubVO(String r_name, int r_room_number, int r_max_personnel, int r_area_size, int r_cost, int r_number) {
		this.r_name = r_name;
		this.r_room_number = r_room_number;
		this.r_max_personnel = r_max_personnel;
		this.r_area_size = r_area_size;
		this.r_cost = r_cost;
		this.r_number = r_number;
	}

	public int getR_number() {
		return r_number;
	}

	public void setR_number(int r_number) {
		this.r_number = r_number;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public int getR_room_number() {
		return r_room_number;
	}

	public void setR_room_number(int r_room_number) {
		this.r_room_number = r_room_number;
	}

	public int getR_area_size() {
		return r_area_size;
	}

	public void setR_area_size(int r_area_size) {
		this.r_area_size = r_area_size;
	}

	public int getR_max_personnel() {
		return r_max_personnel;
	}

	public void setR_max_personnel(int r_max_personnel) {
		this.r_max_personnel = r_max_personnel;
	}

	public int getR_demand_rate() {
		return r_demand_rate;
	}

	public void setR_demand_rate(int r_demand_rate) {
		this.r_demand_rate = r_demand_rate;
	}

	public int getR_cost() {
		return r_cost;
	}

	public void setR_cost(int r_cost) {
		this.r_cost = r_cost;
	}

	public int getR_totalcost() {
		return r_totalcost;
	}

	public void setR_totalcost(int r_totalcost) {
		this.r_totalcost = r_totalcost;
	}

	public int getH_number() {
		return h_number;
	}

	public void setH_number(int h_number) {
		this.h_number = h_number;
	}

}
