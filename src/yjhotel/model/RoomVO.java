package yjhotel.model;

import java.util.ArrayList;

public class RoomVO {
	public int					number;
	public String				name;
	public String				description;
	public int					room_number;
	public int					area_size;
	public int					max_personnel;
	public double				demand_rate;
	public int					cost;
	public int					total_cost;
	public int					h_number;
	public ArrayList<String>	images = new ArrayList<>();
}