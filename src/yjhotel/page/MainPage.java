package yjhotel.page;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import yjhotel.Page;
import yjhotel.YJHotel;
import yjhotel.frame.NoticeContent;


public class MainPage extends Page {

	private static final long serialVersionUID = -1693126929666438711L;
	private static final int FRAME_WIDTH = 550;
	
	JLabel user_name;
	
	public MainPage() {
		super("", "영진숙소 예약 프로그램", true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		add(new Display());
	}

	public void onMount(Object param) {
		String u_name = YJHotel.getUser().name;
		user_name.setText(u_name);
		user_name.setFont(new Font("Aharoni 굵게", Font.BOLD, 23));
		user_name.setBounds(400, 10, 200, 50);
	}
	
	
	class Display extends JPanel { // 메인 패널
		private static final long serialVersionUID = 1L;
		int i;
		Font f1;
		ImageIcon img_slider1[], img_slider2[], sign[]; // sign[]은 이미지 슬라이더의 왼쪽, 오른쪽 버튼의 이미지
		JPanel panel1, panel2, panel3; // panel1은 로고, 마이페이지, 로그아웃이 담긴 패널, panel2는 이미지 슬라이더가 담긴 패널, panel3는 바로가기 버튼이 담긴
										// 패널.
		JLabel logo_img, go_label[], user_name1, user_name2; // user_name2은 환영합니다, go_label[]는 바로가기 버튼의 명칭을 표기하기 위함.
		JButton button_mypage, button_logout, left_slider, right_slider, button_slider1, button_slider2, go_button[]; // button_slider1
																														// 는
																														// 이미지
																														// 슬라이더(공지
																														// 배너)의
																														// 첫
																														// 번째
																														// 이미지
																														// button_slider2는
																														// 두
																														// 번째
																														// 이미지,
		// left_slider은 이미지 슬라이더 왼쪽 버튼, right_slider는 이미지 슬라이더 오른쪽 버튼, go_button[]은 바로가기
		// 버튼
		JRadioButton img_JRadio[]; // 이미지 슬라이더 하단에 몇 번째인지 확인하기 위함.
		Thread th; // 일정 시간 뒤에 이미지 슬라이더의 이미지가 넘어가도록 하는 쓰레드.

		public Display() {
			setPreferredSize(new Dimension(FRAME_WIDTH, 660));
			setLayout(null);

			panel1 = new JPanel();

			logo_img = new JLabel(new ImageIcon("yjhotel_1.png"));
			logo_img.setBounds(15, 5, 330, 124);
			panel1.add(logo_img);

			f1 = new Font("Aharoni", Font.BOLD, 15);
				
			user_name = new JLabel();
			panel1.add(user_name);
			user_name1 = new JLabel();
			user_name1.setText("님");
			user_name1.setFont(new Font("Aharoni 굵게", Font.BOLD, 20));
			user_name1.setBounds(480, 10, 200, 50);
			panel1.add(user_name1);
			user_name2 = new JLabel("환영합니다.");
			user_name2.setBounds(405, 50, 200, 50);
			user_name2.setFont(new Font("Aharoni 굵게", Font.BOLD, 17));
			panel1.add(user_name2);

			button_mypage = new JButton("마이페이지");
			button_mypage.setFont(f1);
			button_mypage.setForeground(Color.blue);
			button_mypage.setBounds(340, 95, 130, 50);
			button_mypage.setBorderPainted(false);
			button_mypage.setContentAreaFilled(false);
			button_mypage.setFocusPainted(false);
			button_mypage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MyPage.Param param = new MyPage.Param();
					param.u_id = YJHotel.getUser().id;
					YJHotel.route("mypage", param);
				}
			});
			panel1.add(button_mypage);

			button_logout = new JButton("로그아웃");
			button_logout.setFont(f1);
			button_logout.setForeground(Color.blue);
			button_logout.setBounds(440, 95, 120, 50);
			button_logout.setBorderPainted(false);
			button_logout.setContentAreaFilled(false);
			button_logout.setFocusPainted(false);
			button_logout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					YJHotel.logout();
				}
			});
			panel1.add(button_logout);

			panel1.setPreferredSize(new Dimension(510, 120));
			panel1.setLayout(null);
			panel1.setBounds(0, 0, 550, 140);
			add(panel1);

			panel2 = new JPanel();

			th = new Thread(rr);
			th.start();

			img_JRadio = new JRadioButton[4];
			for (int t = 0; t < 4; t++) {
				img_JRadio[t] = new JRadioButton();
				img_JRadio[t].setBorderPainted(false);
				img_JRadio[t].setContentAreaFilled(false);
				img_JRadio[t].setFocusPainted(false);
				img_JRadio[t].setEnabled(false);
				img_JRadio[t].setBounds(225 + 30 * t, 220, 32, 32);
				panel2.add(img_JRadio[t]);
			}
			img_JRadio[0].setSelected(true);

			img_slider1 = new ImageIcon[4]; // 공지 배너의 이미지
			img_slider1[0] = new ImageIcon("1.png");
			img_slider1[1] = new ImageIcon("2.png");
			img_slider1[2] = new ImageIcon("3.png");
			img_slider1[3] = new ImageIcon("4.png");
			img_slider2 = new ImageIcon[4];
			img_slider2[0] = new ImageIcon("2.png");
			img_slider2[1] = new ImageIcon("3.png");
			img_slider2[2] = new ImageIcon("4.png");
			img_slider2[3] = new ImageIcon("1.png");
			sign = new ImageIcon[2];
			sign[0] = new ImageIcon("left.png");
			sign[1] = new ImageIcon("right.png");

			left_slider = new JButton(sign[0]);
			left_slider.setBorderPainted(false);
			left_slider.setContentAreaFilled(false);
			left_slider.setFocusPainted(false);

			left_slider.setBounds(10, 100, 40, 40);
			left_slider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (img_JRadio[0].isSelected() == true) {
						img_JRadio[0].setSelected(false);
						img_JRadio[3].setSelected(true);
						button_slider1.setIcon(img_slider1[3]);
						button_slider2.setIcon(img_slider2[3]);
						button_slider1.setName("4");
						button_slider2.setName("0");
					} else if (img_JRadio[1].isSelected() == true) {
						img_JRadio[1].setSelected(false);
						img_JRadio[0].setSelected(true);
						button_slider1.setIcon(img_slider1[0]);
						button_slider2.setIcon(img_slider2[0]);
						button_slider1.setName("0");
						button_slider2.setName("1");
					} else if (img_JRadio[2].isSelected() == true) {
						img_JRadio[2].setSelected(false);
						img_JRadio[1].setSelected(true);
						button_slider1.setIcon(img_slider1[1]);
						button_slider2.setIcon(img_slider2[1]);
						button_slider1.setName("1");
						button_slider2.setName("3");
					} else if (img_JRadio[3].isSelected() == true) {
						img_JRadio[3].setSelected(false);
						img_JRadio[2].setSelected(true);
						button_slider1.setIcon(img_slider1[2]);
						button_slider2.setIcon(img_slider2[2]);
						button_slider1.setName("3");
						button_slider2.setName("4");
					}
				}
			});
			panel2.add(left_slider);

			right_slider = new JButton(sign[1]);
			right_slider.setBounds(500, 100, 40, 40);
			right_slider.setBorderPainted(false);
			right_slider.setContentAreaFilled(false);
			right_slider.setFocusPainted(false);

			right_slider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (img_JRadio[3].isSelected() == true) {
						img_JRadio[3].setSelected(false);
						img_JRadio[0].setSelected(true);
						button_slider1.setIcon(img_slider1[0]);
						button_slider2.setIcon(img_slider2[0]);
						button_slider1.setName("0");
						button_slider2.setName("1");
					} else if (img_JRadio[1].isSelected() == true) {
						img_JRadio[1].setSelected(false);
						img_JRadio[2].setSelected(true);
						button_slider1.setIcon(img_slider1[2]);
						button_slider2.setIcon(img_slider2[2]);
						button_slider1.setName("3");
						button_slider2.setName("4");
					} else if (img_JRadio[2].isSelected() == true) {
						img_JRadio[2].setSelected(false);
						img_JRadio[3].setSelected(true);
						button_slider1.setIcon(img_slider1[3]);
						button_slider2.setIcon(img_slider2[3]);
						button_slider1.setName("4");
						button_slider2.setName("0");
					} else if (img_JRadio[0].isSelected() == true) {
						img_JRadio[0].setSelected(false);
						img_JRadio[1].setSelected(true);
						button_slider1.setIcon(img_slider1[1]);
						button_slider2.setIcon(img_slider2[1]);
						button_slider1.setName("1");
						button_slider2.setName("3");
					}

				}
			});
			panel2.add(right_slider);

			button_slider1 = new JButton();
			button_slider1.setBounds(60, 35, 200, 180);
			button_slider1.setBorderPainted(false);
			button_slider1.setContentAreaFilled(false);
			button_slider1.setFocusPainted(false);
			button_slider1.setIcon(img_slider1[0]);
			button_slider1.setName("0");
			button_slider1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = Integer.parseInt(((JButton)e.getSource()).getName());
					new NoticeContent(index);
				}
			});
			panel2.add(button_slider1);

			button_slider2 = new JButton();
			button_slider2.setBounds(290, 35, 200, 180);
			button_slider2.setBorderPainted(false);
			button_slider2.setContentAreaFilled(false);
			button_slider2.setFocusPainted(false);
			button_slider2.setIcon(img_slider2[0]);
			button_slider2.setName("1");
			button_slider2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = Integer.parseInt(((JButton)e.getSource()).getName());
					new NoticeContent(index);
				}
			});
			panel2.add(button_slider2);

			panel2.setLayout(null);
			panel2.setBounds(0, 150, 550, 270);
			add(panel2);

			panel3 = new JPanel();

			go_button = new JButton[4];
			go_label = new JLabel[4];

			go_label[0] = new JLabel("숙소");
			go_label[0].setFont(new Font("Aharoni 굵게", Font.BOLD, 18));
			go_label[0].setBounds(45, 140, 70, 70);
			panel3.add(go_label[0]);
			go_button[0] = new JButton(new ImageIcon("hotel.png"));
			go_button[0].setBorderPainted(false);
			go_button[0].setContentAreaFilled(false);
			go_button[0].setFocusPainted(false);
			go_button[0].setBounds(15, 50, 100, 100);
			go_button[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					YJHotel.route("search");
				}
			});
			panel3.add(go_button[0]);

			go_label[1] = new JLabel("공지사항");
			go_label[1].setFont(new Font("Aharoni 굵게", Font.BOLD, 18));
			go_label[1].setBounds(165, 140, 100, 70);
			panel3.add(go_label[1]);
			go_button[1] = new JButton(new ImageIcon("notice.png"));
			go_button[1].setBorderPainted(false);
			go_button[1].setContentAreaFilled(false);
			go_button[1].setFocusPainted(false);
			go_button[1].setBounds(150, 50, 100, 100);
			go_button[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					YJHotel.route("notice");
				}
			});
			panel3.add(go_button[1]);

			go_label[2] = new JLabel("쿠폰");
			go_label[2].setFont(new Font("Aharoni 굵게", Font.BOLD, 18));
			go_label[2].setBounds(315, 140, 70, 70);
			panel3.add(go_label[2]);
			go_button[2] = new JButton(new ImageIcon("coupon.png"));
			go_button[2].setBorderPainted(false);
			go_button[2].setContentAreaFilled(false);
			go_button[2].setFocusPainted(false);
			go_button[2].setBounds(285, 50, 100, 100);
			go_button[2].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					YJHotel.route("coupon");
				}
			});
			panel3.add(go_button[2]);

			go_label[3] = new JLabel("차트");
			go_label[3].setFont(new Font("Aharoni 굵게", Font.BOLD, 18));
			go_label[3].setBounds(455, 140, 70, 70);
			panel3.add(go_label[3]);
			go_button[3] = new JButton(new ImageIcon("chart.png"));
			go_button[3].setBorderPainted(false);
			go_button[3].setContentAreaFilled(false);
			go_button[3].setFocusPainted(false);
			go_button[3].setBounds(425, 50, 100, 100);
			go_button[3].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//				dispose();
					YJHotel.route("rank");
				}
			});
			panel3.add(go_button[3]);

			panel3.setLayout(null);

			panel3.setBounds(0, 410, 550, 400);
			add(panel3);

		}
		

		public void paint(Graphics g) { // 폼의 회색 선을 그리기 위함
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, 0));
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(0, 160, 900, 160);
			g.drawLine(0, 405, 900, 405);
		}

		Runnable rr = () -> { // 5초 뒤에 이미지 슬라이더의 이미지가 넘어가도록 설정한 람다식
			try {
				while (true) {
					Thread.sleep(5000);
					if (img_JRadio[0].isSelected() == true) {
						img_JRadio[0].setSelected(false);
						img_JRadio[1].setSelected(true);
						img_JRadio[2].setSelected(false);
						img_JRadio[3].setSelected(false);
						button_slider1.setIcon(img_slider1[1]);
						button_slider2.setIcon(img_slider2[1]);
						button_slider1.setName("1");
						button_slider2.setName("3");
					} else if (img_JRadio[1].isSelected() == true) {
						img_JRadio[0].setSelected(false);
						img_JRadio[1].setSelected(false);
						img_JRadio[2].setSelected(true);
						img_JRadio[3].setSelected(false);
						button_slider1.setIcon(img_slider1[2]);
						button_slider2.setIcon(img_slider2[2]);
						button_slider1.setName("3");
						button_slider2.setName("4");
					} else if (img_JRadio[2].isSelected() == true) {
						img_JRadio[0].setSelected(false);
						img_JRadio[1].setSelected(false);
						img_JRadio[2].setSelected(false);
						img_JRadio[3].setSelected(true);
						button_slider1.setIcon(img_slider1[3]);
						button_slider2.setIcon(img_slider2[3]);
						button_slider1.setName("4");
						button_slider2.setName("0");
					} else if (img_JRadio[3].isSelected() == true) {
						img_JRadio[0].setSelected(true);
						img_JRadio[1].setSelected(false);
						img_JRadio[2].setSelected(false);
						img_JRadio[3].setSelected(false);
						button_slider1.setIcon(img_slider1[0]);
						button_slider2.setIcon(img_slider2[0]);
						button_slider1.setName("0");
						button_slider2.setName("1");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}
}
