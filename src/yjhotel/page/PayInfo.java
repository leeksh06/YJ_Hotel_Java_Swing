package yjhotel.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import yjhotel.Page;
import yjhotel.YJHotel;
import yjhotel.model.CouponSubVO;
import yjhotel.model.RoomInfoDAO;

// FrameFormat 클래스 이름은 개인적으로 변경하여 사용하시면 됩니다.
public class PayInfo extends Page {
	// @ 실제로 변수를 받아오는 클래스 선언입니다.
	// @ onMount에서 param 가져올 때 아래 내용 참고해주세요.
	static class Param {
		public int h_number;
		public int r_number;
		public int b_cost;
		public int b_guest_quantity;
		public int b_days;
		public String b_date;
	}

	private static final long serialVersionUID = 1265625818849460396L;
	private static final int FRAME_WIDTH = 550;
	// @ Database 클래스를 RoomInfoDao로 변경하였습니다.
	// @ 이름 맞춰서 수정해주세요.
	RoomInfoDAO RoomInfoDao = new RoomInfoDAO();
	int hotelnum;
	int roomnum;
	int usernum;
	int totalcost;
	int guestquant;
	int bdays;
	String bdate;
	int couponnum;
	int rate;
	int lasttotalcost;

	// 전체 프로그램의 너비는 600이지만 내부에서 사용하게 될 너비는
	// 이보다 더 작아야 하기 때문에 600에서 50을 낮춘 550을 사용하였습니다.

	private CenterPanel center = new CenterPanel();
	private BottomPanel bottom = new BottomPanel();
	// @ 아래 세 클래스를 하나로 합치시면 됩니다.
	// private BottomPanel bottom = new BottomPanel();

	public PayInfo() throws IOException {
		super("결제 정보창", "영진숙소 예약 프로그램");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(center);
		add(Box.createVerticalStrut(20));
		add(bottom);

		// @ add(bottom); 으로 변경해주세요

	}

	@Override
	public void onMount(Object param) {
		Param params = castParam(param);

		// @ 이부분에서 실제로 사용할 변수 가져오시면 됩니다.
		usernum = YJHotel.getUser().number;
		hotelnum = params.h_number;
		roomnum = params.r_number;
		totalcost = params.b_cost;
		guestquant = params.b_guest_quantity;
		bdays = params.b_days;
		bdate = params.b_date;
		// @ 위 세 변수 이외에는 params. 에서 가져오시면 돼요.

		try {
			center.load();
			bottom.load();
			// @ bottom.load();
			// @ bottom 이 합쳐지면 아래 두줄은 지워주세요.

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 다만, 개인적으로 클래스를 만드는 경우 아래 코드처럼 setPreferredSize(new Dimension(FRAME_WIDTH,
	// 높이));
	// 함수를 통해 너비를 FRAME_WIDTH로 맞춰줘야 합니다.
	private class CenterPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Font font18 = new Font("굴림", Font.BOLD, 18);
		private Font font15 = new Font("굴림", Font.BOLD, 15);

		public CenterPanel() {
			setPreferredSize(new Dimension(FRAME_WIDTH, 260));
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.black));
		}

		public void load() throws IOException {
			removeAll();
			JPanel LPanel = new JPanel();
			LPanel.setPreferredSize(new Dimension(150, 130));

			URL url = new URL(RoomInfoDao.readr_image(roomnum));
			BufferedImage image = ImageIO.read(url);
			ImageIcon imageIcon = new ImageIcon(image);
			Image img = imageIcon.getImage();
			Image updateImg = img.getScaledInstance(140, 130, Image.SCALE_SMOOTH);
			ImageIcon updateIcon = new ImageIcon(updateImg);

			JLabel imagelabel = new JLabel();
			imagelabel.setIcon(updateIcon);
			LPanel.add(imagelabel);

			add(LPanel, BorderLayout.WEST);

			JPanel RPanel = new JPanel();
			RPanel.setPreferredSize(new Dimension(350, 130));

			add(RPanel, BorderLayout.CENTER);
			// 라벨들
			JLabel Label1 = new JLabel(RoomInfoDao.readh_name(hotelnum));
			Label1.setPreferredSize(new Dimension(350, 30));
			Label1.setFont(font18);
			RPanel.add(Label1);
			JLabel Label2 = new JLabel(RoomInfoDao.readh_location(hotelnum));
			Label2.setPreferredSize(new Dimension(350, 30));
			Label2.setFont(font18);
			RPanel.add(Label2);
			JLabel Label3 = new JLabel(
					RoomInfoDao.readr_name(roomnum) + " " + RoomInfoDao.readr_room_number(roomnum) + "호");
			Label3.setPreferredSize(new Dimension(350, 30));
			Label3.setFont(font18);
			RPanel.add(Label3);
			JLabel Label4 = new JLabel(bdate);
			Label4.setPreferredSize(new Dimension(350, 30));
			Label4.setFont(font18);
			RPanel.add(Label4);

			JPanel flowLP = new JPanel(new FlowLayout(FlowLayout.CENTER));

			// 체크인아웃
			JPanel LP = new JPanel(new GridLayout(2, 2));
			LP.setPreferredSize(new Dimension(250, 90));
			flowLP.add(LP);

			add(flowLP, BorderLayout.SOUTH);
			LP.setBorder(BorderFactory.createLineBorder(Color.black));

			JLabel c1 = new JLabel("체크인");
			JLabel c2 = new JLabel("체크아웃");
			JLabel c3 = new JLabel(RoomInfoDao.readh_check_in(hotelnum));
			JLabel c4 = new JLabel(RoomInfoDao.readh_check_out(hotelnum));

			c1.setFont(font15);
			c2.setFont(font15);
			c3.setFont(font15);
			c4.setFont(font15);

			c1.setHorizontalAlignment(JLabel.CENTER);
			c2.setHorizontalAlignment(JLabel.CENTER);
			c3.setHorizontalAlignment(JLabel.CENTER);
			c4.setHorizontalAlignment(JLabel.CENTER);

			LP.add(c1);
			LP.add(c2);
			LP.add(c3);
			LP.add(c4);
		}
	}

	// @ 이하 BottomPanel1, 2도 동일합니다.
	// @ JPanel에 적용될 부분만 통합될 생성자에 넣어주시고,
	// @ 이외 부분은 public void load(){} 안에 넣어주세요.

	private class BottomPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public BottomPanel() {
			setPreferredSize(new Dimension(FRAME_WIDTH, 380));
			setBorder(BorderFactory.createLineBorder(Color.black));
		}

		public void load() throws IOException {
			lasttotalcost = totalcost;
			removeAll();
			JLabel Label1 = new JLabel(RoomInfoDao.readu_name(usernum) + "님");
			Label1.setPreferredSize(new Dimension(520, 30));
			add(Label1);
			JLabel Label2 = new JLabel("보유포인트 : " + RoomInfoDao.readu_cash(usernum));
			Label2.setPreferredSize(new Dimension(520, 30));
			add(Label2);
			JLabel Label3 = new JLabel("비용 : " + totalcost + "원");
			Label3.setPreferredSize(new Dimension(520, 30));
			add(Label3);
			JLabel Label4 = new JLabel("할인 비율 : " + rate + "%");

			Label4.setPreferredSize(new Dimension(520, 30));
			add(Label4);
			JLabel Label5 = new JLabel("총 결제 비용 : " + lasttotalcost + "원");
			Label5.setPreferredSize(new Dimension(520, 30));
			add(Label5);

			int x = RoomInfoDao.readc_count(usernum);
			int r;

			JPanel coponP1 = new JPanel();
			coponP1.setPreferredSize(new Dimension(360, 180));
			coponP1.setBorder(new TitledBorder(new LineBorder(Color.black), "사용 가능 쿠폰"));

			JLabel coponlabel = new JLabel("현재 선택한 쿠폰");
			coponlabel.setPreferredSize(new Dimension(100, 30));
			coponP1.add(coponlabel);

			JTextField coponText = new JTextField("쿠폰");
			coponText.setPreferredSize(new Dimension(230, 30));
			coponText.setEditable(false);
			coponP1.add(coponText);

			// 스크롤
			JPanel scroll = new JPanel(new GridLayout(0, 1));
			JScrollPane scrollPane = new JScrollPane(scroll, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			scrollPane.setPreferredSize(new Dimension(340, 100));
			coponP1.add(scrollPane);

			add(coponP1);

			JButton roomBut[] = new JButton[x];
			JPanel coponPanel[] = new JPanel[x];

			ArrayList<CouponSubVO> arr1 = new ArrayList<CouponSubVO>();
			arr1 = RoomInfoDao.readC_Data(usernum);

			for (r = 0; r < x; r++) {
				coponPanel[r] = new JPanel();
				scroll.add(coponPanel[r]);

				roomBut[r] = new JButton(arr1.get(r).getC_name());
				roomBut[r].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String value = e.getActionCommand();
						coponText.setText(value);

						ArrayList<CouponSubVO> coupon = new ArrayList<CouponSubVO>();
						coupon = RoomInfoDao.readC_Data(usernum);

						for (int s = 0; s < x; s++) {
							roomBut[s].setEnabled(true);
							if (e.getSource() == roomBut[s]) {

								couponnum = coupon.get(s).getC_number();
								rate = RoomInfoDao.readc_rate(couponnum);
								lasttotalcost = (int) (totalcost * ((100 - rate) / 100.0));
								Label4.setText("할인 비율 : " + rate + "%");
								Label5.setText("총 결제 비용 : " + lasttotalcost + "원");
								roomBut[s].setEnabled(false);
							}
						}

					}
				});
				coponPanel[r].add(roomBut[r]);
				roomBut[r].setPreferredSize(new Dimension(330, 30));
			}

			JPanel coponP2 = new JPanel();
			coponP2.setPreferredSize(new Dimension(160, 180));

			JButton butbut1 = new JButton("결제하기");
			butbut1.setPreferredSize(new Dimension(140, 85));
			butbut1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (e.getSource() == butbut1) {
						boolean result = YJHotel.showConfirm("결제하시겠습니까?");
						if (result) {
							if (Integer.parseInt(RoomInfoDao.readu_cash(usernum)) >= lasttotalcost) {
								String firstdate = bdate.substring(0, 10).trim();
								
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d", Locale.US);
								LocalDate date1 = LocalDate.parse(firstdate, formatter);
								RoomInfoDao.insert_reservedb(guestquant,lasttotalcost, usernum, hotelnum, roomnum);				
								for (int i = 0; i < bdays; i ++) {
									LocalDate date2 = date1.plusDays(i);
									
									Date date = java.sql.Date.valueOf(date2);
									RoomInfoDao.insert_reservedb2(date);
								}
								RoomInfoDao.update_u_cash(lasttotalcost, usernum);
								RoomInfoDao.update_c_status(couponnum, usernum);
								YJHotel.showAlert("결제되었습니다.", JOptionPane.INFORMATION_MESSAGE);
								YJHotel.route("mainpage");
							} else {
								YJHotel.showAlert("보유 포인트가 부족합니다.");
							}
						}
					}
				}
			});
			coponP2.add(butbut1);

			JButton butbut2 = new JButton("취소하기");
			butbut2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					YJHotel.route("mainpage");
				}
			});
			butbut2.setPreferredSize(new Dimension(140, 85));
			coponP2.add(butbut2);

			add(coponP2);

		}
	}

}