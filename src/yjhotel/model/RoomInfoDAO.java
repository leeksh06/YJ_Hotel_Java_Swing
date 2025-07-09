package yjhotel.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import yjhotel.YJHotel;

public class RoomInfoDAO {

	Connection con = null;
	ResultSet rs = null;
	// ResultSet : 실행한 쿼리문의 값을 받는 객체
	Statement st = null;
	// Statement : 그냥 가져오는것
	PreparedStatement ps = null;
	// PreparedStatement : ?넣어서 집어넣는것

	public RoomInfoDAO() {
		con = YJHotel.getConnection();
	}

	public String readu_name(int u_number) {

		String u_name = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 회원 where u_number = " + u_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				u_name = rs.getString("u_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return u_name;
	}

	public String readu_cash(int u_number) {

		String u_cash = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 회원 where u_number = " + u_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				u_cash = rs.getString("u_cash");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return u_cash;
	}

	public String readh_name(int h_number) {

		String h_name = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 숙소 where h_number = " + h_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				h_name = rs.getString("h_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return h_name;
	}

	public String readh_location(int h_number) {

		String h_location = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 숙소 where h_number = " + h_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				h_location = rs.getString("h_location");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return h_location;
	}

	public String readh_check_in(int h_number) {

		String h_check_in = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 숙소 where h_number = " + h_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				h_check_in = rs.getString("h_check_in");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return h_check_in;
	}

	public String readh_check_out(int h_number) {

		String h_check_out = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 숙소 where h_number = " + h_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				h_check_out = rs.getString("h_check_out");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return h_check_out;
	}

	public int readr_cost(int h_number) {

		String r_cost = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 방 where h_number = " + h_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				r_cost = rs.getString("r_cost");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return Integer.parseInt(r_cost);
	}

	public String readr_name(int r_number) {

		String r_name = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 방 where r_number = " + r_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				r_name = rs.getString("r_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return r_name;
	}

	public String readr_room_number(int r_number) {

		String r_room_number = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 방 where r_number = " + r_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				r_room_number = rs.getString("r_room_number");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return r_room_number;
	}

	public String readc_name(int h_number) {

		String c_name = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 방 where h_number = " + h_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				c_name = rs.getString("c_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return c_name;
	}

	public int readc_rate(int c_number) {
		
		 int c_rate = 0;
		try {

			st = con.createStatement();
			
			String sql = "select * from 쿠폰 where c_number = " + c_number;
			
			rs = st.executeQuery(sql);
			while(rs.next()) {
				c_rate = rs.getInt("c_rate");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("쿼리 적용 실패");
		} finally {
			dbClose();
		}
		return c_rate;
	}

	public String readc_name(String value) {

		String c_name = new String();
		try {

			st = con.createStatement();

			String sql = "select * from 쿠폰 where c_name = " + value;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				c_name = rs.getString("c_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return c_name;
	}

	public ArrayList<RoomSubVO> readR_Data(int h_number) {
		ArrayList<RoomSubVO> arr = new ArrayList<RoomSubVO>();

		try {

			st = con.createStatement();

			String sql = "select r_name , r_room_number, r_room_number, r_max_personnel,r_area_size, r_cost, r_number from 방 where h_number = "
					+ h_number;

			rs = st.executeQuery(sql);

			while (rs.next()) {
				arr.add(new RoomSubVO(rs.getString("r_name"), rs.getInt("r_room_number"), rs.getInt("r_max_personnel"),
						rs.getInt("r_area_size"), rs.getInt("r_cost"), rs.getInt("r_number")));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return arr;
	}

	public ArrayList<CouponSubVO> readC_Data(int u_number) {
	    ArrayList<CouponSubVO> arr = new ArrayList<CouponSubVO>();

	    try {
	        st = con.createStatement();

	        String sql = "SELECT c.c_number, c.c_name, c.c_rate " +
	                     "FROM 쿠폰 c " +
	                     "JOIN 쿠폰_보유상태 cs ON c.c_number = cs.c_number " +
	                     "WHERE c.c_min_date <= CURDATE() " +
	                     "AND c.c_max_date >= CURDATE() " +
	                     "AND cs.c_status = '보유중' " +
	                     "AND cs.u_number = " + u_number;

	        rs = st.executeQuery(sql);

	        while (rs.next()) {
	            arr.add(new CouponSubVO(rs.getInt("c_number"), rs.getString("c_name"), rs.getInt("c_rate")));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        dbClose();
	    }
	    return arr;
	}

	public ArrayList<RoomImageVO> readr_imagelist(int h_number) {
		ArrayList<RoomImageVO> arr = new ArrayList<RoomImageVO>();

		try {

			st = con.createStatement();

			String sql = "select 방_이미지.r_image from 방 join 방_이미지 on 방.r_number = 방_이미지.r_number and h_number = "
					+ h_number;

			rs = st.executeQuery(sql);

			while (rs.next()) {
				arr.add(new RoomImageVO(rs.getString("r_image")));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return arr;
	}

	public String readr_image(int r_number) {
		String r_image = new String();

		try {

			st = con.createStatement();

			String sql = "select * from 방_이미지 where r_number = " + r_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				r_image = rs.getString("r_image");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return r_image;
	}

	public String readr_count(int h_number) {

		String r_count = new String();
		try {

			st = con.createStatement();

			String sql = "select count(*) from 방 where h_number = " + h_number;

			rs = st.executeQuery(sql);
			while (rs.next()) {
				r_count = rs.getString("count(*)");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return r_count;
	}
	
	public int readc_count(int u_number) {
	    int r_count = 0;
	    try {
	        st = con.createStatement();

	        String sql = "SELECT COUNT(*) AS count " +
	                     "FROM 쿠폰 c " +
	                     "JOIN 쿠폰_보유상태 cs ON c.c_number = cs.c_number " +
	                     "WHERE c.c_min_date <= CURDATE() " +
	                     "AND c.c_max_date >= CURDATE() " +
	                     "AND cs.c_status = '보유중' " +
	                     "AND cs.u_number = " + u_number;

	        rs = st.executeQuery(sql);

	        if (rs.next()) {
	            r_count = rs.getInt("count");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("쿼리 적용 실패");
	    } finally {
	        dbClose();
	    }
	    return r_count;
	}

	
	public boolean insert_reservedb(int b_guest_quantity ,int lasttotalcost , int u_number, int h_number , int r_number) {

		
		
		String sql = "insert into 예약 (b_guest_quantity, b_payment_method, b_payment_cost, b_payment_date , b_status, u_number, h_number, r_number) values ( " + b_guest_quantity  + " , \"보유 포인트\" , "+ lasttotalcost + " , NOW() , \"예약중\" , " + u_number + " , " + h_number + " , " + r_number + ")";
		int count = 0;
		try {

			st = con.createStatement();

			count = st.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return count > 0 ? true : false ;
	}
	
	public boolean insert_reservedb2(Date date) {

		
		
		String sql = "insert into 예약_일자 (b_date , b_number) values (\""+ date + "\" , (select MAX(b_number) from 예약))";
		int count = 0;
		try {

			st = con.createStatement();

			count = st.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		return count > 0 ? true : false ;
	}
	
	public boolean update_u_cash(int totalcost , int u_number) {

		String sql = " UPDATE 회원 SET u_cash = u_cash - " + totalcost + " WHERE u_number = " + u_number;
		int count = 0;
		System.out.println("sql = " + sql);
		try {

			ps = con.prepareStatement(sql);

			count = ps.executeUpdate(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		
		return  count > 0 ? true : false ;
	}
	
	public boolean update_c_status(int couponnum , int u_number) {

		String sql = " UPDATE 쿠폰_보유상태 SET c_status = \"사용완료\" WHERE u_number = " + u_number + " and c_number = " + couponnum;
		int count = 0;
		try {

			ps = con.prepareStatement(sql);

			count = ps.executeUpdate(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbClose();
		}
		
		return  count > 0 ? true : false ;
	}

	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
