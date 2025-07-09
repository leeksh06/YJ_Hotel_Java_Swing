package yjhotel.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import yjhotel.Page;
import yjhotel.YJHotel;
import yjhotel.model.BookingVO;
import yjhotel.model.RoomVO;
import yjhotel.model.UserVO;

public class DateReservation extends Page {
	static class Param {
		public int r_number;
		public int h_number;
	}

	private static final long serialVersionUID = -1182873622661729850L;

	private static final int PANEL_WIDTH = 550;

	private ArrayList<BookingVO> bookings = new ArrayList<>();
	private UserVO user = new UserVO();
	private RoomVO room = new RoomVO();

	private String u_id = null;
	private int r_number = 0;
	private int h_number = 0;

	private String min_date = null;
	private String max_date = null;

	private MyLabel date_label = new MyLabel("", 12);
	private MyLabel days_label = new MyLabel("", 14);
	private MyLabel cost_label = new MyLabel("", 14);

	private CenterPanel center = new CenterPanel();
	private BottomPanel bottom = new BottomPanel();

	public DateReservation() {
		super("예약 날짜 지정", "Date Reservation");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(center);
		add(Box.createVerticalStrut(10));
		add(bottom);
	}

	@Override
	public void onMount(Object param) {
		Param params = castParam(param);
		String u_id = YJHotel.getUser().id;
		if (u_id == null || params.r_number == 0) {
			System.out.println("입력 정보가 올바르지 않습니다.");
			return;
		}
		this.u_id = u_id;
		r_number = params.r_number;
		h_number = params.h_number;

		makeConnection();
		try {
			center.load();
			bottom.load();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeConnection() {
		try {
			Connection conn = YJHotel.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM 회원 WHERE u_id='" + u_id + "'");
			if (rs.next()) {
				user.number = rs.getInt("u_number");
				user.name = rs.getString("u_name");
				user.id = rs.getString("u_id");
				user.password = rs.getString("u_password");
				user.phone_number = rs.getString("u_phone_number");
				user.birthday = rs.getDate("u_birthday");
				user.email = rs.getString("u_email");
				user.cash = rs.getInt("u_cash");
				user.enroll_date = rs.getDate("u_enroll_date");
				user.profile_image = rs.getString("u_profile_image");
				System.out.println("회원 연결 성공");
			} else
				System.out.println("회원이 존재하지 않습니다.");

			rs = stmt.executeQuery("SELECT * FROM 예약 WHERE b_status='예약중' AND r_number=" + r_number);
			for (int i = 0; rs.next(); i++) {
				bookings.add(new BookingVO());
				bookings.get(i).number = rs.getInt("b_number");
				bookings.get(i).guest_quantity = rs.getInt("b_guest_quantity");
				bookings.get(i).payment_method = rs.getString("b_payment_method");
				bookings.get(i).payment_cost = rs.getInt("b_payment_cost");
				bookings.get(i).payment_date = rs.getString("b_payment_date");
				bookings.get(i).status = rs.getString("b_status");
				bookings.get(i).u_number = rs.getInt("u_number");
				bookings.get(i).h_number = rs.getInt("h_number");
				bookings.get(i).r_number = rs.getInt("r_number");
				System.out.println(bookings.get(i).number + "번 예약 연결 성공");
			}

			rs = stmt.executeQuery("SELECT * FROM 방 WHERE r_number=" + r_number);
			if (rs.next()) {
				room.number = rs.getInt("r_number");
				room.name = rs.getString("r_name");
				//room.description = rs.getString("r_description");
				room.room_number = rs.getInt("r_room_number");
				room.area_size = rs.getInt("r_area_size");
				room.max_personnel = rs.getInt("r_max_personnel");
				room.demand_rate = rs.getDouble("r_demand_rate");
				room.cost = rs.getInt("r_cost");
				room.total_cost = rs.getInt("r_total_cost");
				room.h_number = rs.getInt("h_number");
				System.out.println("방 연결 성공");
			} else
				System.out.println("방이 존재하지 않습니다.");
		} catch (SQLException err) {
//			System.out.println("연결에 실패하였습니다.");
			YJHotel.showAlert("데이터베이스 처리에 실패했습니다.");
		}
	}

	private class CenterPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		Calendar cal = Calendar.getInstance();

		public CenterPanel() {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setPreferredSize(new Dimension(PANEL_WIDTH, 400));
			setLayout(new BorderLayout());
		}

		private int getMaxDay(int month) {
			switch (month) {
			case 2:
				return 28;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			default:
				return 31;
			}
		}

		private void load() throws SQLException {
			removeAll();
			Connection conn = YJHotel.getConnection();
			{
				JPanel container = new JPanel(new GridLayout(1, 3));
				{
					JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
					JButton button = new JButton(cal.get(Calendar.YEAR) + "년");
					button.setPreferredSize(new Dimension(75, 30));
					panel.add(button);
					container.add(panel);
				}
				{
					JPanel panel = new JPanel();
					{
						JButton button = new JButton("<");
						button.setPreferredSize(new Dimension(40, 30));
						button.setFont(new Font("Monospaced", Font.BOLD, 10));
						button.addActionListener(e -> {
							cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
							removeAll();
							try {
								load();
							} catch (Exception err) {

							}
							repaint();
							revalidate();
						});
						panel.add(button);
					}
					{
						JButton button = new JButton(cal.get(Calendar.MONTH) + 1 + "월");
						button.setPreferredSize(new Dimension(80, 50));
						button.setFont(new Font("굴림", Font.BOLD, 20));
						panel.add(button);
					}
					{
						JButton button = new JButton(">");
						button.setPreferredSize(new Dimension(40, 30));
						button.setFont(new Font("Monospaced", Font.BOLD, 10));
						button.addActionListener(e -> {
							cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
							removeAll();
							try {
								load();
							} catch (Exception err) {

							}
							repaint();
							revalidate();
						});
						panel.add(button);
					}
					container.add(panel);
				}
				{
					JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
					String titles[] = { "월", "화", "수", "목", "금", "토", "일" };
					JButton button = new JButton(String.format("%d월 %d일 %s요일", cal.get(Calendar.MONTH) + 1,
							cal.get(Calendar.DATE), titles[cal.get(Calendar.DAY_OF_WEEK) - 1]));
					button.setPreferredSize(new Dimension(130, 30));
					panel.add(button);
					container.add(panel);
				}
				add(container, BorderLayout.NORTH);
			}
			{
				JPanel container = new JPanel(new BorderLayout());
				{
					JPanel panel = new JPanel(new GridLayout(1, 7));
					JLabel labels[] = new JLabel[7];
					String titles[] = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
					for (int i = 0; i < 7; i++) {
						labels[i] = new JLabel(titles[i]);
						labels[i].setHorizontalAlignment(JLabel.CENTER);
						labels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
						panel.add(labels[i]);
					}
					labels[0].setForeground(Color.RED);
					labels[6].setForeground(Color.RED);
					container.add(panel, BorderLayout.NORTH);
				}
				{
					cal.set(Calendar.DATE, 1);

					Statement stmt = conn.createStatement();

					JPanel panel = new JPanel(new GridLayout(6, 7));
					JButton buttons[][] = new JButton[6][7];
					for (int day = 1, r = 0; r < 6; r++) {
						for (int c = 0; c < 7; c++) {
							if ((r == 0 && c < cal.get(Calendar.DAY_OF_WEEK) - 1)
									|| day > getMaxDay(cal.get(Calendar.MONTH) + 1)) {
								buttons[r][c] = new JButton("");
								buttons[r][c].setEnabled(false);
								buttons[r][c].setBackground(new Color(160, 160, 160));
							} else {
								buttons[r][c] = new JButton(String.valueOf(day));
								buttons[r][c].setBackground(Color.WHITE);
								buttons[r][c].addActionListener(e -> {
									JButton tmp = (JButton) e.getSource();
									if (min_date == null) {
										min_date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
												+ tmp.getText();
										Calendar c1 = Calendar.getInstance();
										Calendar c2 = Calendar.getInstance();
										c2.set(Integer.parseInt(min_date.substring(0, 4)),
												Integer.parseInt(min_date.substring(5, min_date.indexOf('-', 5))) - 1,
												Integer.parseInt(min_date.substring(min_date.indexOf('-', 5) + 1,
														min_date.length())));
										if (c1.after(c2)) {
											YJHotel.showAlert("오늘 이전은 예약이 불가능합니다!");
											min_date = null;
										}
										else
											date_label.setText(min_date);
									} else if (max_date == null) {
										int year = cal.get(Calendar.YEAR);
										int month = cal.get(Calendar.MONTH) + 1;
										max_date = year + "-" + month + "-" + tmp.getText();

										Calendar c1 = Calendar.getInstance();
										c1.set(Integer.parseInt(min_date.substring(0, 4)),
												Integer.parseInt(min_date.substring(5, min_date.indexOf('-', 5))) - 1,
												Integer.parseInt(min_date.substring(min_date.indexOf('-', 5) + 1,
														min_date.length())));
										Calendar c2 = Calendar.getInstance();
										c2.set(year, month - 1, Integer.parseInt(tmp.getText()));

										if (c1.after(c2)) {
											min_date = max_date;
											max_date = null;
											date_label.setText(min_date);
										} else {
											date_label.setText(min_date + " ~ " + max_date);
											int days = (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000
													/ (24 * 60 * 60));
											days_label.setText(String.valueOf(days));
											cost_label.setText(String.valueOf(room.total_cost * days));
										}
									} else {
										min_date = max_date = null;
										date_label.setText("");
										days_label.setText("");
										cost_label.setText("");
									}
								});
								ResultSet rs = stmt.executeQuery(
										"SELECT b_number FROM 예약_일자 WHERE b_date='" + cal.get(Calendar.YEAR) + "-"
												+ (cal.get(Calendar.MONTH) + 1) + "-" + day++ + "'");
								while (rs.next()) {
									if (conn.createStatement().executeQuery("SELECT * FROM 예약 WHERE b_number="
											+ rs.getInt("b_number") + " AND r_number=" + r_number).next()) {
										buttons[r][c].setEnabled(false);
										buttons[r][c].setBackground(new Color(210, 210, 210));
									}
								}
							}
							panel.add(buttons[r][c]);
						}
						buttons[r][0].setForeground(Color.RED);
						buttons[r][6].setForeground(Color.RED);
					}
					container.add(panel, BorderLayout.CENTER);
				}
				add(container, BorderLayout.CENTER);
			}
		}
	}

	private class BottomPanel extends JPanel {
		private static final long serialVersionUID = -6378189534685653400L;

		public BottomPanel() {
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "선택 정보"));
			setPreferredSize(new Dimension(PANEL_WIDTH, 255));
			setLayout(new BorderLayout());
		}

		private void load() throws Exception {
			removeAll();
			Connection conn = YJHotel.getConnection();
			{
				JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
				{
					ImagePanel panel = new ImagePanel();
					panel.setBackground(Color.WHITE);
					panel.setPreferredSize(new Dimension(100, 100));
					panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

					ResultSet rs = conn.createStatement()
							.executeQuery("SELECT r_image FROM 방_이미지 WHERE r_number=" + r_number);
					if (rs.next()) {
						panel.image = ImageIO.read(new URL(rs.getString("r_image"))).getScaledInstance(100, 100,
								BufferedImage.SCALE_DEFAULT);
						panel.repaint();
					}

					container.add(panel);
				}
				{
					JPanel panel = new JPanel(new GridLayout(3, 1, 0, 12));
					panel.add(new MyLabel(room.name));
					panel.add(new MyLabel(room.area_size + "평"));
					panel.add(new MyLabel("최대 인원 " + room.max_personnel + "명"));
					container.add(panel);
				}
				add(container, BorderLayout.NORTH);
			}
			JTextField capField = new JTextField(4);
			{
				JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 28, 0));
				{
					JPanel innerContainer = new JPanel(new BorderLayout(28, 0));
					innerContainer.setPreferredSize(new Dimension(370, 100));
					final int SIZE = 14;
					{
						JPanel panel = new JPanel(new GridLayout(4, 1, 0, 8));
						panel.add(new MyLabel("예약 인원", SIZE, true));
						panel.add(new MyLabel("예약 기간", SIZE, true));
						panel.add(new MyLabel("총 예약일 수", SIZE, true));
						panel.add(new MyLabel("비용", SIZE, true));
						innerContainer.add(panel, BorderLayout.WEST);
					}
					{
						JPanel panel = new JPanel(new GridLayout(4, 1, 0, 8));
						JPanel textPanels[] = { new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)),
								new JPanel(new BorderLayout(5, 0)) };
						capField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						capField.setHorizontalAlignment(JTextField.CENTER);
						MyLabel alert = new MyLabel("", SIZE);
						alert.setForeground(Color.RED);
						capField.addActionListener(e -> {
							JTextField tmp = (JTextField) e.getSource();
							int max_personnel = Integer.parseInt(tmp.getText());
							if (max_personnel > room.max_personnel || max_personnel <= 0) {
								tmp.setText("");
								alert.setText("올바른 값을 입력하세요.");
							} else if (!alert.getText().equals(""))
								alert.setText("");
						});
						textPanels[1].add(capField, BorderLayout.WEST);
						textPanels[1].add(new MyLabel("명", SIZE), BorderLayout.CENTER);
						textPanels[1].add(alert, BorderLayout.EAST);
						textPanels[0].add(textPanels[1]);
						panel.add(textPanels[0]);
						panel.add(date_label);
						panel.add(days_label);
						panel.add(cost_label);
						innerContainer.add(panel, BorderLayout.CENTER);
					}
					JPanel outerContainer = new JPanel(new BorderLayout());
					outerContainer.add(innerContainer, BorderLayout.CENTER);
					{
						JPanel panel = new JPanel(new BorderLayout());
						JButton button = new JButton("결제하기");
						button.setFont(new Font("굴림", Font.BOLD, 20));
						button.setPreferredSize(new Dimension(130, 50));
						panel.add(button, BorderLayout.SOUTH);
						outerContainer.add(panel, BorderLayout.EAST);
						button.addActionListener((e) -> {
			 				String cost = cost_label.getText();
							String days = days_label.getText();
							String caps = capField.getText();
							if (days.trim().equals("")) {
								YJHotel.showAlert("날짜를 선택해주세요");
								return;
							}
							if (caps.trim().equals("")) {
								YJHotel.showAlert("인원 수를 입력해주세요.");
								return;
							}
							if (Integer.valueOf(caps.trim()) > room.max_personnel || Integer.valueOf(caps.trim()) <= 0) {
								YJHotel.showAlert("올바른 인원 수를 입력해주세요.");
								return;
							}
							try {
								int b_guest_quantity = Integer.parseInt(caps);
								int b_cost = Integer.parseInt(cost);
								int b_days = Integer.parseInt(days);
								String b_date = date_label.getText();
								PayInfo.Param param = new PayInfo.Param();
								param.h_number = h_number;
								param.r_number = r_number;
								param.b_cost = b_cost;
								param.b_days = b_days;
								param.b_date = b_date;
								param.b_guest_quantity = b_guest_quantity;

								YJHotel.route("payinfo", param);
							} catch (Exception ex) {
								ex.printStackTrace();
								YJHotel.showAlert("유효하지 않은 값이 있습니다.");
							}
						});
					}
					container.add(outerContainer);
				}
				add(container, BorderLayout.CENTER);
			}
		}
	}

	private class MyLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		public MyLabel(String text) {
			this(text, 15, false);
		}

		public MyLabel(String text, int size) {
			this(text, size, false);
		}

		public MyLabel(String text, int size, boolean mode) {
			super(text);
			setFont(new Font("굴림", mode ? Font.BOLD : Font.PLAIN, size));
			if (mode)
				setHorizontalAlignment(JLabel.CENTER);
		}
	}

	private class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		Image image;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(image, 0, 0, null);
		}
	}
}
