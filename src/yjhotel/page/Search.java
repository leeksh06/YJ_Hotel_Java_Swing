package yjhotel.page;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import yjhotel.Page;
import yjhotel.YJHotel;
import yjhotel.model.SearchDAO;
import yjhotel.model.SearchVO;

// FrameFormat 클래스 이름은 개인적으로 변경하여 사용하시면 됩니다.
public class Search extends Page {
	private static final long serialVersionUID = -3944285343434235020L;
	private static final int FRAME_WIDTH = 550;
	// 전체 프로그램의 너비는 600이지만 내부에서 사용하게 될 너비는
	// 이보다 더 작아야 하기 때문에 600에서 50을 낮춘 550을 사용하였습니다.
	private Display display = new Display();

	public Search() {
		super("숙소 리스트", "영진숙소 예약 프로그램");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(display);
	}

	@Override
	public void onMount(Object param) {
		try {
			display.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Display extends JPanel {
		private static final long serialVersionUID = 1L;

		BufferedImage image;
		URL url;

		int h_num;
		int cnt3 = 0; // 검색 버튼 클릭시 몇 개의 행을 가져오는지 확인하기 위한 변수.
		int x = 15; // 스크롤 패널에 15개의 패널을 넣기 위한 변수.
		int a; // 스크롤 패널에 패널을 넣기 위한 변수.

		public Display() {
			setPreferredSize(new Dimension(FRAME_WIDTH, 660));
			setLayout(null);
		}

		public void load() throws IOException {
			removeAll();
			JTextField people1, cost1, cost2, house_name; // 각 검색창의 텍스트 필드.
			JLabel Lb_people1, Lb_people2, Lb_cost, Lb_house_name, cost3; // cost3는 (원)을 표기하기 위함.
			JButton Bt_search, Bt_reset;
			JPanel panel2; // 검색 패널과 스크롤 패널을 담은 패널
			panel2 = new JPanel();
			panel2.setBounds(0, 0, 550, 250);
			panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panel2.setLayout(null);

			Lb_house_name = new JLabel("숙소명");
			Lb_house_name.setBounds(64, 35, 50, 20);
			Lb_house_name.setFont(new Font("굴림", Font.BOLD, 15));
			panel2.add(Lb_house_name);
			house_name = new JTextField();
			house_name.setBounds(140, 28, 250, 33);
			panel2.add(house_name);

			Lb_people1 = new JLabel("방크기");
			Lb_people1.setBounds(64, 91, 50, 20);
			Lb_people1.setFont(new Font("굴림", Font.BOLD, 15));
			Lb_people2 = new JLabel("(2/4/6 인실)");
			Lb_people2.setBounds(220, 91, 90, 20);
			Lb_people2.setFont(new Font("굴림", Font.BOLD, 12));
			panel2.add(Lb_people1);
			panel2.add(Lb_people2);
			people1 = new JTextField();
			people1.setBounds(140, 85, 70, 33);
			panel2.add(people1);

			Lb_cost = new JLabel("비용");
			Lb_cost.setBounds(70, 149, 50, 20);
			Lb_cost.setFont(new Font("굴림", Font.BOLD, 15));
			panel2.add(Lb_cost);
			cost1 = new JTextField();
			cost1.setBounds(140, 142, 100, 33);
			panel2.add(cost1);
			cost2 = new JTextField();
			cost2.setBounds(290, 142, 100, 33);
			panel2.add(cost2);
			cost3 = new JLabel("(원)");
			cost3.setBounds(400, 147, 30, 20);
			cost3.setFont(new Font("굴림", Font.BOLD, 12));
			panel2.add(cost3);

			Bt_search = new JButton("검색");
			Bt_search.setBounds(380, 200, 70, 40);
			Bt_search.setFont(new Font("Monospaced", Font.BOLD, 10));
			panel2.add(Bt_search);

			Bt_reset = new JButton("초기화");
			Bt_reset.setBounds(467, 200, 70, 40);
			Bt_reset.setFont(new Font("Monospaced", Font.BOLD, 10));
			panel2.add(Bt_reset);

			add(panel2);

			JPanel botpanel1 = new JPanel(new GridLayout(x, 1, 0, 10));

			JScrollPane scrollPane = new JScrollPane(botpanel1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(0, 270, 550, 390);
			scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			scrollPane.getVerticalScrollBar().setUnitIncrement(9);

			JPanel RoomPanel[] = new JPanel[x];
			JPanel RoomLeftPanel[] = new JPanel[x];
			JLabel Roomimg[] = new JLabel[x];

			JPanel RoomMidPanel[] = new JPanel[x];
			JLabel Roomname[] = new JLabel[x];
			JLabel Roomarea[] = new JLabel[x];
			JLabel Roomfurniture[] = new JLabel[x];

			JPanel RoomRightPanel[] = new JPanel[x];
			JButton Roombut[] = new JButton[x];

			for (a = 0; a < x; a++) {
				RoomPanel[a] = new JPanel(new BorderLayout(10, 0));
				RoomPanel[a].setPreferredSize(new Dimension(550, 120));
				botpanel1.add(RoomPanel[a]);
				// 패널 왼쪽
				RoomLeftPanel[a] = new JPanel();
				RoomPanel[a].add(RoomLeftPanel[a], BorderLayout.WEST);
				Roomimg[a] = new JLabel();
				Roomimg[a].setPreferredSize(new Dimension(120, 120));
				RoomLeftPanel[a].add(Roomimg[a]);

				// 패널 중앙
				RoomMidPanel[a] = new JPanel(new GridLayout(4, 1));
				RoomPanel[a].add(RoomMidPanel[a], BorderLayout.CENTER);
				Roomname[a] = new JLabel("숙소 이름");
				RoomMidPanel[a].add(Roomname[a]);
				Roomarea[a] = new JLabel("몇 인실");
				RoomMidPanel[a].add(Roomarea[a]);
				Roomfurniture[a] = new JLabel("min ~ max");
				RoomMidPanel[a].add(Roomfurniture[a]);

				// 패널 오른쪽
				RoomRightPanel[a] = new JPanel(new BorderLayout());
				RoomPanel[a].add(RoomRightPanel[a], BorderLayout.EAST);
				Roombut[a] = new JButton("상세보기");
				Roombut[a].setPreferredSize(new Dimension(100, 40));
				RoomRightPanel[a].add(Roombut[a], BorderLayout.SOUTH);

			}

			SearchDAO dao = new SearchDAO(); // DB 쿼리문을 가져오기 위해 SearchDAO의 클래스를 dao에 담음.
			int cnt = 0; // ArrayList의 하나의 행을 받아올 때마다 1 증가(몇 개의 행을 가져왔는지 확인하기 위함.)
			int cnt1 = 0; // ArrayList의 하나의 행을 받아올 때마다 1 증가(몇 개의 행을 가져왔는지 확인하기 위함.)
			int cnt2 = 0; // ArrayList의 하나의 행을 받아올 때마다 1 증가(몇 개의 행을 가져왔는지 확인하기 위함.)
			int r_people = 0; // 스크롤 패널의 최소 인원을 표기하기 위함.
			int r_people1 = 0; // 스크롤 패널의 최대 인원을 표기하기 위함.
			int r_cost = 0; // 스크롤 패널의 최소 가격을 표기하기 위함.
			int r_cost1 = 0; // 스크롤 패널의 최소 가격을 표기하기 위함.
			String h_name = new String();

			ArrayList<SearchVO> people = dao.people(r_people, r_people1);
			for (SearchVO vo : people) {
				int data = vo.getR_people();
				int data1 = vo.getR_people1();
				if (data == data1) {
					Roomarea[cnt].setText(data + " 인실");
				} else {
					Roomarea[cnt].setText(data1 + "인실 / " + data + "인실");
					cnt++;
				}
			}
			ArrayList<SearchVO> cost = dao.cost(r_cost, r_cost1);
			for (SearchVO vo : cost) {
				int max = vo.getR_cost();
				int min = vo.getR_cost1();
				Roomfurniture[cnt1].setText(min + "원 ~ " + max + "원");
				cnt1++;
			}
			ArrayList<SearchVO> name = dao.name(h_name);
			for (SearchVO vo : name) {
				String data = vo.getH_name();
				String img = vo.getH_image();
				url = new URL(img); // 숙소 이미지 DB에서 가져오기
				image = ImageIO.read(url); // 숙소 이미지
				ImageIcon imageIcon = new ImageIcon(image);
				Image i_img = imageIcon.getImage();
				Image updateImg = i_img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
				ImageIcon updateIcon = new ImageIcon(updateImg);

				Roomimg[cnt2].setIcon(updateIcon);
				Roomname[cnt2].setText(data + "");
				Roombut[cnt2].setName(vo.getH_number() + "");
				cnt2++;
			}

			add(scrollPane);

			Bt_search.addActionListener(new ActionListener() {
				BufferedImage image;
				URL url;
				ImageIcon imageIcon, updateIcon;

				public void actionPerformed(ActionEvent e) {
					cnt3 = 0;
					botpanel1.removeAll();
					for (int c = 0; c < x; c++) {
						RoomRightPanel[c].removeAll();
					}
					if (e.getSource() == Bt_search) {
						String h_image = new String();
						String h_name = house_name.getText();
						String m_r_people = people1.getText();
						String min = cost1.getText();
						String max = cost2.getText();
						if (m_r_people.length() < 1 || min.length() < 1 || max.length() < 1)// 입력이 안된 경우
						{
							JOptionPane.showMessageDialog(null, "조건을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
							return;
							// 처리
						}

						int r_people = Integer.parseInt(m_r_people);
						int r_cost = Integer.parseInt(min);
						int r_cost1 = Integer.parseInt(max);
						int h_number = 0;
						SearchDAO dao = new SearchDAO();
						ArrayList<SearchVO> s_name = dao.s_name(h_name, h_image, r_people, r_cost, h_number);
						for (SearchVO vo : s_name) {
							String data = vo.getH_name();
							String img = vo.getH_image();
							h_num = vo.getH_number();

							try {
								url = new URL(img);
								image = ImageIO.read(url);
								imageIcon = new ImageIcon(image);
								Image i_img = imageIcon.getImage();
								Image updateImg = i_img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
								updateIcon = new ImageIcon(updateImg);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							int data1 = vo.getR_people();
							int data2 = vo.getR_cost();
							int data3 = vo.getR_cost1();
							RoomPanel[cnt3].add(RoomRightPanel[cnt3], BorderLayout.EAST);
							RoomRightPanel[cnt3].add(Roombut[h_num - 1], BorderLayout.SOUTH);

							botpanel1.add(RoomPanel[cnt3]);

							Roomimg[cnt3].setIcon(updateIcon);

							Roomname[cnt3].setText(data + "");
							if (data2 < r_cost && r_cost <= data3) {
								Roomfurniture[cnt3].setText(data2 + "원");
							} else if (r_cost1 < data3 && data2 <= r_cost1) {
								Roomfurniture[cnt3].setText(data2 + "원");
							} else if (r_cost <= data2 && data3 <= r_cost1) {
								Roomfurniture[cnt3].setText(data2 + "원");
							} else {
								botpanel1.removeAll();
							}

							Roomarea[cnt3].setText(data1 + " 인실");
							Roombut[cnt3].setName(vo.getH_number() + "");
							cnt3++;
						}
						scrollPane.revalidate();
						scrollPane.repaint();
					}
					cnt3 = 0;
				}
			});

			Bt_reset.addActionListener(new ActionListener() {
				BufferedImage image;
				URL url;
				int cnt = 0; // 검색시 ArrayList의 하나의 행을 받아올 때마다 1 증가(몇 개의 행을 가져왔는지 확인하기 위함.)
				int cnt1 = 0; // 검색시 ArrayList의 하나의 행을 받아올 때마다 1 증가(몇 개의 행을 가져왔는지 확인하기 위함.)
				int cnt2 = 0; // 검색시 ArrayList의 하나의 행을 받아올 때마다 1 증가(몇 개의 행을 가져왔는지 확인하기 위함.)
				int cnt3 = 0; // 검색시 ArrayList의 하나의 행을 받아올 때마다 1 증가(몇 개의 행을 가져왔는지 확인하기 위함.)
				int r_people = 0; // 검색시 스크롤 패널의 최소 인원을 표기하기 위함.
				int r_people1 = 0; // 검색시 스크롤 패널의 최대 인원을 표기하기 위함.
				int r_cost = 0; // 검색시 스크롤 패널의 최소 가격을 표기하기 위함.
				int r_cost1 = 0; // 검색시 스크롤 패널의 최대 가격을 표기하기 위함.
				String h_name = new String();

				public void actionPerformed(ActionEvent e) {
					botpanel1.removeAll();
					people1.setText("");
					cost1.setText("");
					cost2.setText("");
					house_name.setText("");
					for (int d = 0; d < 15; d++) {
						RoomPanel[d].add(RoomRightPanel[d], BorderLayout.EAST);
						RoomRightPanel[d].add(Roombut[d], BorderLayout.SOUTH);
					}

					SearchDAO dao = new SearchDAO();

					ArrayList<SearchVO> people = dao.people(r_people, r_people1);
					for (SearchVO vo : people) {
						int data = vo.getR_people();
						int data1 = vo.getR_people1();
						if (data == data1) {
							Roomarea[cnt].setText(data + " 인실");
						} else {
							Roomarea[cnt].setText(data1 + "인실 / " + data + "인실");
							cnt++;
						}
					}
					ArrayList<SearchVO> cost = dao.cost(r_cost, r_cost1);
					for (SearchVO vo : cost) {
						int max = vo.getR_cost();
						int min = vo.getR_cost1();
						Roomfurniture[cnt1].setText(min + "원 ~ " + max + "원");
						cnt1++;
					}
					ArrayList<SearchVO> name = dao.name(h_name);
					for (SearchVO vo : name) {
						String data = vo.getH_name();
						String img = vo.getH_image();
						try {
							url = new URL(img);
							image = ImageIO.read(url);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						ImageIcon imageIcon = new ImageIcon(image);
						Image i_img = imageIcon.getImage();
						Image updateImg = i_img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
						ImageIcon updateIcon = new ImageIcon(updateImg);

						Roomimg[cnt2].setIcon(updateIcon);
						Roomname[cnt2].setText(data + "");
						Roombut[cnt2].setName(vo.getH_number() + "");
						cnt2++;
					}
					for (cnt3 = 0; cnt3 < 15; cnt3++) {
						botpanel1.add(RoomPanel[cnt3]);
						scrollPane.revalidate();
						scrollPane.repaint();
					}
					cnt = 0;
					cnt1 = 0;
					cnt2 = 0;
					cnt3 = 0;

				}

			});
			for (int i = 0; i < 15; i++) { // 상세보기 버튼을 눌렀을때 몇 번 버튼이 입력되었는지 방 상세보기 폼에 값을 보내는 것
				final int buttonIndex = i;
				Roombut[i].addActionListener(e -> {
					int h_number = Integer.parseInt(Roombut[buttonIndex].getName());
					RoomInfo.Param param = new RoomInfo.Param();
					param.h_number = h_number;
					YJHotel.route("roominfo", param);

				});
			}

		}

		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, 0));
			g.drawLine(255, 157, 275, 157);

		}

	}
}
