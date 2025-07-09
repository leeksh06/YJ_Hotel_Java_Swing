package yjhotel.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import yjhotel.Page;
import yjhotel.YJHotel;
import yjhotel.model.UserVO;

public class MyPage extends Page {
	static class Param {
		protected String u_id = null;
	}

	private static final long serialVersionUID = -4814805383787415047L;
	private static final int PANEL_WIDTH = 550;

	private UserVO user = new UserVO();
	private String u_id = null;

	private CenterPanel center = new CenterPanel();
	private BottomPanel bottom = new BottomPanel();

	public MyPage() {
		super("마이페이지", "My Page");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(center);
		add(Box.createVerticalStrut(10));
		add(bottom);
	}

	@Override
	public void onMount(Object param) {
		Param params = (Param) param;
		this.u_id = params.u_id;

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
			} else {
				YJHotel.showAlert("회원이 존재하지 않습니다.");
			}
		} catch (SQLException err) {
			YJHotel.showAlert("데이터베이스 처리에 실패했습니다.");
		}
	}

	private class CenterPanel extends JPanel {
		private static final long serialVersionUID = 8169886658779607735L;

		public CenterPanel() {
			setLayout(new FlowLayout(FlowLayout.LEFT));
			setPreferredSize(new Dimension(PANEL_WIDTH, 234));
		}

		public void load() throws Exception {
			removeAll();
			JPanel centerFrame = new JPanel(new BorderLayout(0, 10));
			final ImagePanel imagePanel = new ImagePanel();
			{
				JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				{
					JLabel label = new JLabel(user.name);
					label.setFont(new Font("굴림", Font.BOLD, 20));
					panel.add(label);
				}
				{
					JLabel label = new JLabel("님 반갑습니다!");
					label.setFont(new Font("굴림", Font.PLAIN, 20));
					panel.add(label);
				}
				centerFrame.add(panel, BorderLayout.NORTH);
			}
			{
				JPanel panels[] = { new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)),
						new JPanel(new BorderLayout(20, 0)) };
				{
					imagePanel.setPreferredSize(new Dimension(150, 145));
					Connection conn = YJHotel.getConnection();
					Statement stmt = conn.createStatement();
					String sql = null;
					sql  = "SELECT u_profile_image FROM 회원 WHERE u_id='" + u_id + "'";
					ResultSet rs = stmt.executeQuery(sql);
					if (rs.next())
						imagePanel.image = ImageIO.read(new URL(user.profile_image)).getScaledInstance(150, 145, BufferedImage.SCALE_DEFAULT);
					else
						imagePanel.image = ImageIO.read(new URL("https://cdn.pixabay.com/photo/2015/04/08/07/25/fat-712246_1280.png")).getScaledInstance(150, 145, BufferedImage.SCALE_DEFAULT);
					imagePanel.repaint();
					
					if (stmt != null)
						stmt.close();
					panels[1].add(imagePanel, BorderLayout.WEST);
				}
				{
					JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
					{
						JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
						panel.add(new TopicLabel("이메일 주소"));
						panel.add(new TopicLabel("휴대폰 번호"));
						panel.add(new TopicLabel("아이디"));
						panel.add(new TopicLabel("보유 금액"));
						contentPanel.add(panel, BorderLayout.WEST);
					}
					{
						JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
						panel.add(new ContentLabel(user.email));
						panel.add(new ContentLabel(user.phone_number));
						panel.add(new ContentLabel(user.id));

						JPanel cost_panel = new JPanel(new BorderLayout(20, 0));
						cost_panel.add(new ContentLabel(String.valueOf(user.cash)), BorderLayout.CENTER);
						JButton chargeCost = new JButton("충전하기");
						chargeCost.addActionListener(e -> {
							try {
								JDialog dialog = new JDialog();
								dialog.setLayout(new BorderLayout());
								
								JPanel outPanel = new JPanel(new BorderLayout(0, 30));
								outPanel.setPreferredSize(new Dimension(300, 200));
								outPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								
								JLabel label = new JLabel("얼마를 충전하시겠습니까?");
								label.setFont(new Font("Monospaced", Font.BOLD, 20));
								label.setHorizontalAlignment(JLabel.CENTER);
								outPanel.add(label, BorderLayout.NORTH);
								
								JPanel inPanel = new JPanel();
								JTextField field = new JTextField();
								field.setPreferredSize(new Dimension(140, 50));
								field.setHorizontalAlignment(JTextField.CENTER);
								field.setFont(new Font("Monospaced", Font.BOLD, 20));
								inPanel.add(field);
								outPanel.add(inPanel, BorderLayout.CENTER);
								
								JPanel inPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
								inPanel2.setPreferredSize(new Dimension(300, 50));
								JButton confirmButton = new JButton("충전");
								confirmButton.addActionListener(e2 -> {
									if (!field.getText().equals("")) {
										try {
											Connection conn = YJHotel.getConnection();
											Statement stmt = conn.createStatement();
											String sql = null;
											sql  = "UPDATE 회원 SET u_cash = u_cash + " + Integer.parseInt(field.getText());
											sql += " WHERE u_number = " + user.number;
											stmt.executeUpdate(sql);
											
											dialog.dispose();
											
											if (stmt != null)
												stmt.close();
										} catch (SQLException err) {
											err.printStackTrace();
										}
									}
								});
								JButton cancelButton = new JButton("취소");
								cancelButton.addActionListener(e2 -> {
									dialog.dispose();
								});
								inPanel2.add(confirmButton);
								inPanel2.add(cancelButton);
								outPanel.add(inPanel2, BorderLayout.SOUTH);
								
								dialog.add(outPanel);
								dialog.pack();
								dialog.setLocationRelativeTo(null);
								dialog.setTitle("보유 포인트 충전");
								dialog.setResizable(false);
								dialog.setVisible(true);
								
								Connection conn = YJHotel.getConnection();
								Statement stmt = conn.createStatement();
								
								if (stmt != null)
									stmt.close();
							} catch (SQLException err) {
								err.printStackTrace();
							}
						});
						cost_panel.add(chargeCost, BorderLayout.EAST);

						panel.add(cost_panel);
						contentPanel.add(panel, BorderLayout.CENTER);
					}
					panels[1].add(contentPanel, BorderLayout.CENTER);
					panels[0].add(panels[1]);
				}
				centerFrame.add(panels[0], BorderLayout.CENTER);
			}
			{
				JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 28, 0));
				JButton button = new JButton("프로필 이미지 변경");
				button.setFont(new Font("굴림", Font.BOLD, 8));
				button.addActionListener(e -> {
				    JDialog dialog = new JDialog();
				    dialog.setLayout(new BorderLayout());

				    String[] imageUrls = {
				        "https://cdn.pixabay.com/photo/2015/04/08/07/25/fat-712246_1280.png",
				        "https://cdn.pixabay.com/photo/2013/07/13/10/00/face-156456_1280.png",
				        "https://cdn.pixabay.com/photo/2017/01/31/13/05/cameo-2023867_1280.png"
				    };

				    JPanel outsideImagePanel = new JPanel(new GridLayout(1, imageUrls.length, 10, 0));
				    final int SIZE = 120;
				    for (int i = 0; i < imageUrls.length; i++) {
				        ImagePanel insidePanel = new ImagePanel();
				        insidePanel.setPreferredSize(new Dimension(SIZE, SIZE));
				        try {
				            insidePanel.image = ImageIO.read(new URL(imageUrls[i]))
				                    .getScaledInstance(SIZE, SIZE, BufferedImage.SCALE_DEFAULT);
				            insidePanel.repaint();
				        } catch (Exception err) {
				            err.printStackTrace();
				        }
				        outsideImagePanel.add(insidePanel);
				    }

				    JPanel outsideButtonPanel = new JPanel(new GridLayout(1, imageUrls.length, 10, 0));
				    for (int i = 0; i < imageUrls.length; i++) {
				        JButton choiceButton = new JButton("선택하기");
					choiceButton.setName(String.valueOf(i));
					choiceButton.addActionListener(e2 -> {
					    JButton tmpButton = (JButton)e2.getSource();
					    try {
					        imagePanel.image =
					            ImageIO.read(
					                new URL(imageUrls[Integer.parseInt(tmpButton.getName())])
					            ).getScaledInstance(150,
					                                145,
					                                BufferedImage.SCALE_DEFAULT);
					        imagePanel.repaint();

					        Connection conn =
					            YJHotel.getConnection();
					        Statement stmt =
					            conn.createStatement();

					        String sql = "UPDATE 회원 SET u_profile_image ='"
					                     + imageUrls[Integer.parseInt(tmpButton.getName())]
					                     + "' WHERE u_id ='"
					                     + u_id
					                     + "'";
					        stmt.executeUpdate(sql);

					        if(stmt != null)
					            stmt.close();
					    } catch(Exception err) {
					         err.printStackTrace();    
					    }

				       });
				       outsideButtonPanel.add(choiceButton);
				   }
					dialog.add(outsideImagePanel, BorderLayout.CENTER);
					dialog.add(outsideButtonPanel, BorderLayout.SOUTH);
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("프로필 이미지 변경");
					dialog.setResizable(false);
					dialog.setVisible(true);
				});
				panel.add(button);
				centerFrame.add(panel, BorderLayout.SOUTH);
			}
			add(centerFrame);
		}

		private class TopicLabel extends JLabel {
			private static final long serialVersionUID = 1L;

			public TopicLabel(String text) {
				super(text);
				setFont(new Font("굴림", Font.BOLD, 15));
				setHorizontalAlignment(JLabel.CENTER);
			}
		}

		private class ContentLabel extends JLabel {
			private static final long serialVersionUID = 1L;

			public ContentLabel(String text) {
				super(text);
				setFont(new Font("굴림", Font.PLAIN, 15));
			}
		}
	}

	private class BottomPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public BottomPanel() {
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(PANEL_WIDTH, 410));
		}

		public void load() throws Exception {
			removeAll();
			JTabbedPane tab = new JTabbedPane();
			tab.setPreferredSize(new Dimension(PANEL_WIDTH, 350));
			Connection conn = YJHotel.getConnection();
			{
				JPanel bottomFrame = new JPanel(new BorderLayout());
				tab.addTab("투숙 예정 정보", bottomFrame);
				Statement stmt = conn.createStatement();
				ResultSet exist = stmt.executeQuery("SELECT * FROM 예약 WHERE u_number=" + user.number + " AND b_status='예약중'");
				if (exist.next()) {
					{
						JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
						{
							ImagePanel imagePanel = new ImagePanel();
							imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							imagePanel.setPreferredSize(new Dimension(120, 120));
	
							stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(
									"SELECT r_image FROM 방_이미지 WHERE r_number=(SELECT r_number FROM 예약 WHERE u_number="
											+ user.number + " AND b_status='예약중')");
							if (rs.next()) {
								imagePanel.image = ImageIO.read(new URL(rs.getString("r_image")))
										.getScaledInstance(120, 120, BufferedImage.SCALE_DEFAULT);
								imagePanel.repaint();
							}
							rs.close();
	
							panel.add(imagePanel);
						}
						{
							JPanel content_panel = new JPanel(new GridLayout(3, 1, 0, 20));
	
							stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(
									"SELECT r_max_personnel, r_total_cost FROM 방 WHERE r_number=(SELECT r_number FROM 예약 WHERE u_number="
											+ user.number + " AND b_status='예약중')");
							if (rs.next()) {
								ResultSet rs2 = conn.createStatement().executeQuery(
										"SELECT h_name FROM 숙소 WHERE h_number=(SELECT h_number FROM 예약 WHERE u_number="
												+ user.number + " AND b_status='예약중')");
								if (rs2.next())
									content_panel.add(new MyLabel(rs2.getString("h_name")));
								content_panel.add(new MyLabel(rs.getString("r_max_personnel") + "인실"));
								content_panel.add(new MyLabel(rs.getInt("r_total_cost") + "원"));
							}
							panel.add(content_panel);
						}
						{
							JPanel buttonPanel = new JPanel(new BorderLayout());
							buttonPanel.setPreferredSize(new Dimension(80, 25));
							JButton cancelButton = new JButton("예약 취소");
							cancelButton.setMargin(new Insets(0, 0, 0, 0));
							cancelButton.addActionListener(e -> {
								if (YJHotel.showConfirm("정말로 예약을 취소하시겠습니까?")) {
									try {
										Statement stmt2 = conn.createStatement();
										String sql = "";
										sql += "UPDATE 회원 SET u_cash = u_cash + ";
										sql += "(SELECT b_payment_cost FROM 예약 ";
										sql += "WHERE u_number = " + user.number + " AND b_status='예약중') ";
										sql += "WHERE u_number = " + user.number;
										stmt2.executeUpdate(sql);
										
										sql = "";
										sql += "UPDATE 예약 SET b_status='취소'";
										sql += "WHERE u_number = " + user.number;
										sql += " AND b_status = '예약중'";
										stmt2.executeUpdate(sql);
										
										if (stmt2 != null)
											stmt2.close();
									} catch (Exception err) {
										err.printStackTrace();
									}
								}
							});
							buttonPanel.add(cancelButton, BorderLayout.CENTER);
							panel.add(buttonPanel);
						}
						bottomFrame.add(panel, BorderLayout.NORTH);
					}
					{
						JPanel panels[] = { new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)),
								new JPanel(new GridLayout(1, 2)) };
						panels[1].setPreferredSize(new Dimension(515, 80));
						{
							JPanel leftPanel = new JPanel(new BorderLayout(30, 0));
							{
								JPanel topicPanel = new JPanel(new GridLayout(3, 1));
								topicPanel.add(new MyLabel("예약 번호", true));
								topicPanel.add(new MyLabel("대표 예약인", true));
								topicPanel.add(new MyLabel("예약 인원", true));
	
								JPanel contentPanel = new JPanel(new GridLayout(3, 1));
	
								stmt = conn.createStatement();
								ResultSet rs = stmt.executeQuery(
										"SELECT b_number, b_guest_quantity FROM 예약 WHERE u_number=" + user.number + " AND b_status='예약중'");
								if (rs.next()) {
									contentPanel.add(new MyLabel(String.valueOf(rs.getInt("b_number"))));
									contentPanel.add(new MyLabel(user.name));
									contentPanel.add(new MyLabel(rs.getInt("b_guest_quantity") + "명"));
								}
	
								leftPanel.add(topicPanel, BorderLayout.WEST);
								leftPanel.add(contentPanel, BorderLayout.CENTER);
							}
							JPanel rightPanel = new JPanel(new BorderLayout(30, 0));
							{
								JPanel topicPanel = new JPanel(new GridLayout(3, 1));
								topicPanel.add(new MyLabel("투숙 기간", true));
								topicPanel.add(new MyLabel("객실명", true));
								topicPanel.add(new MyLabel("객실", true));
	
								JPanel contentPanel = new JPanel(new GridLayout(3, 1));
	
								stmt = conn.createStatement();
								ResultSet rs = stmt.executeQuery(
										"SELECT count(*) FROM 예약_일자 WHERE b_number=(SELECT b_number FROM 예약 WHERE u_number="
												+ user.number + " AND b_status='예약중')");
								if (rs.next()) {
									int size = rs.getInt(1);
									rs = stmt.executeQuery(
											"SELECT b_date FROM 예약_일자 WHERE b_number=(SELECT b_number FROM 예약 WHERE u_number="
													+ user.number + " AND b_status='예약중') order by b_date");
	
									String min_date = null, max_date = null;
									for (int count = 1; rs.next(); count++) {
										if (count == 1)
											min_date = rs.getString("b_date");
										if (count == size)
											max_date = rs.getString("b_date");
									}
	
									rs = stmt.executeQuery(
											"SELECT r_name, r_room_number FROM 방 WHERE r_number=(SELECT r_number FROM 예약 WHERE u_number="
													+ user.number + " AND b_status='예약중')");
									if (rs.next()) {
										contentPanel.add(new MyLabel(min_date + " ~ " + max_date, 12));
										contentPanel.add(new MyLabel(rs.getString("r_name")));
										contentPanel.add(new MyLabel(rs.getInt("r_room_number") + "호"));
									}
								}
	
								rightPanel.add(topicPanel, BorderLayout.WEST);
								rightPanel.add(contentPanel, BorderLayout.CENTER);
							}
							panels[1].add(leftPanel);
							panels[1].add(rightPanel);
						}
						panels[0].add(panels[1]);
						bottomFrame.add(panels[0], BorderLayout.CENTER);
					}
					{
						JPanel panels[] = { new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)),
								new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)),
								new JPanel(new GridLayout(1, 2, 20, 0)) };
						panels[1].setBorder(
								BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "결제 정보"));
						panels[1].setPreferredSize(new Dimension(PANEL_WIDTH - 30, 75));
						panels[2].setPreferredSize(new Dimension(PANEL_WIDTH - 50, 40));
						{
							JPanel leftPanel = new JPanel(new BorderLayout(20, 0));
							{
								JPanel topicPanel = new JPanel(new GridLayout(2, 1, 0, 10));
								topicPanel.add(new MyLabel("결제 방식", 12, true));
								topicPanel.add(new MyLabel("결제 일자", 12, true));
	
								JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 10));
	
								stmt = conn.createStatement();
								ResultSet rs = stmt
										.executeQuery("SELECT b_payment_method, b_payment_date FROM 예약 WHERE u_number=" + user.number
												+ " AND b_status='예약중'");
								if (rs.next()) {
									contentPanel.add(new MyLabel(rs.getString("b_payment_method"), 12));
									contentPanel.add(new MyLabel(rs.getString("b_payment_date"), 12));
								}
	
								leftPanel.add(topicPanel, BorderLayout.WEST);
								leftPanel.add(contentPanel, BorderLayout.CENTER);
							}
							JPanel rightPanel = new JPanel(new BorderLayout(20, 0));
							{
								JPanel topicPanel = new JPanel(new GridLayout(2, 1, 0, 10));
								topicPanel.add(new MyLabel("비용", 12, true));
	
								JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 10));
	
								stmt = conn.createStatement();
								ResultSet rs = stmt.executeQuery("SELECT b_payment_cost FROM 예약 WHERE u_number="
										+ user.number + " AND b_status='예약중'");
								if (rs.next())
									contentPanel.add(new MyLabel(String.valueOf(rs.getInt("b_payment_cost")), 12));
	
								rightPanel.add(topicPanel, BorderLayout.WEST);
								rightPanel.add(contentPanel, BorderLayout.CENTER);
							}
							panels[2].add(leftPanel);
							panels[2].add(rightPanel);
							panels[1].add(panels[2]);
						}
						panels[0].add(panels[1]);
						bottomFrame.add(panels[0], BorderLayout.SOUTH);
					}
				}
				else {
					JLabel label = new JLabel("예악중인 숙소가 없습니다.");
					label.setFont(new Font("Monospaced", Font.BOLD, 30));
					label.setHorizontalAlignment(JLabel.CENTER);
					bottomFrame.add(label, BorderLayout.CENTER);
				}
			}
			{
				JPanel bottomFrame = new JPanel();
				bottomFrame.setLayout(new BoxLayout(bottomFrame, BoxLayout.Y_AXIS));
				JScrollPane scroll = new JScrollPane(bottomFrame);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				tab.addTab("투숙 기록", scroll);
				{
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM 예약 WHERE u_number=" + user.number + " AND b_status='기록'");
					while (rs.next()) {
						JPanel panels[] = { new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)),
								new JPanel(new GridLayout(1, 2)) };
						panels[0].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						{
							JPanel panel = new JPanel(new BorderLayout(20, 0));
							{
								ImagePanel imagePanel = new ImagePanel();
								imagePanel.setBackground(Color.WHITE);
								imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								ResultSet rs2 = conn.createStatement().executeQuery(
										"SELECT r_image FROM 방_이미지 WHERE r_number=" + rs.getInt("r_number"));
								if (rs2.next()) {
									imagePanel.image = ImageIO.read(new URL(rs2.getString("r_image")))
											.getScaledInstance(100, 100, BufferedImage.SCALE_DEFAULT);
									imagePanel.repaint();
								}

								imagePanel.setPreferredSize(new Dimension(100, 100));
								panel.add(imagePanel, BorderLayout.WEST);
							}
							{
								JPanel inPanel = new JPanel(new BorderLayout(20, 0));
								{
									JPanel topicPanel = new JPanel(new GridLayout(3, 1, 0, 5));
									topicPanel.add(new MyLabel("예약 번호", true));
									topicPanel.add(new MyLabel("대표 예약인", true));
									topicPanel.add(new MyLabel("예약 인원", true));

									JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 5));
									contentPanel.add(new MyLabel(String.valueOf(rs.getInt("b_number"))));
									contentPanel.add(new MyLabel(user.name));
									contentPanel.add(new MyLabel(String.valueOf(rs.getInt("b_guest_quantity"))));

									inPanel.add(topicPanel, BorderLayout.WEST);
									inPanel.add(contentPanel, BorderLayout.CENTER);
								}
								panel.add(inPanel, BorderLayout.CENTER);
							}
							{
								JPanel inPanel = new JPanel(new BorderLayout(20, 0));
								{
									JPanel topicPanel = new JPanel(new GridLayout(3, 1, 0, 8));
									topicPanel.add(new MyLabel("투숙 기간", true));
									topicPanel.add(new MyLabel("객실명", true));
									topicPanel.add(new MyLabel("객실", true));

									Statement stmt2 = conn.createStatement();
									ResultSet rs2 = stmt2.executeQuery(
											"SELECT count(*) FROM 예약_일자 WHERE b_number=" + rs.getInt("b_number"));
									rs2.next();
									int size = rs2.getInt(1);
									rs2 = stmt2.executeQuery("SELECT b_date FROM 예약_일자 WHERE b_number="
											+ rs.getInt("b_number") + " order by b_date");

									String min_date = null, max_date = null;
									for (int count = 1; rs2.next(); count++) {
										if (count == 1)
											min_date = rs2.getString("b_date");
										if (count == size)
											max_date = rs2.getString("b_date");
									}

									rs2 = stmt2.executeQuery("SELECT * FROM 방 WHERE r_number=" + rs.getInt("r_number"));
									rs2.next();

									JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 8));
									contentPanel.add(new MyLabel(min_date + " ~ " + max_date, 10));
									contentPanel.add(new MyLabel(rs2.getString("r_name")));
									contentPanel.add(new MyLabel(rs2.getInt("r_room_number") + "호"));

									inPanel.add(topicPanel, BorderLayout.WEST);
									inPanel.add(contentPanel, BorderLayout.CENTER);
								}
								panel.add(inPanel, BorderLayout.EAST);
							}
							panels[1].add(panel);
						}
						panels[0].add(panels[1]);
						bottomFrame.add(panels[0]);
					}
				}
			}
			{
				JPanel bottomFrame = new JPanel();
				bottomFrame.setLayout(new BoxLayout(bottomFrame, BoxLayout.Y_AXIS));
				JScrollPane scroll = new JScrollPane(bottomFrame);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				tab.addTab("취소된 예약", scroll);
				{
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM 예약 WHERE u_number=" + user.number + " AND b_status='취소'");
					while (rs.next()) {
						JPanel panels[] = { new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)),
								new JPanel(new GridLayout(1, 2)) };
						panels[0].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						{
							JPanel panel = new JPanel(new BorderLayout(20, 0));
							{
								ImagePanel imagePanel = new ImagePanel();
								imagePanel.setBackground(Color.WHITE);
								imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								imagePanel.setPreferredSize(new Dimension(100, 100));
								ResultSet rs2 = conn.createStatement().executeQuery(
										"SELECT r_image FROM 방_이미지 WHERE r_number=" + rs.getInt("r_number"));
								if (rs2.next()) {
									imagePanel.image = ImageIO.read(new URL(rs2.getString("r_image")))
											.getScaledInstance(100, 100, BufferedImage.SCALE_DEFAULT);
									imagePanel.repaint();
								}
								panel.add(imagePanel, BorderLayout.WEST);
							}
							{
								JPanel inPanel = new JPanel(new BorderLayout(20, 0));
								{
									JPanel topicPanel = new JPanel(new GridLayout(3, 1, 0, 5));
									topicPanel.add(new MyLabel("예약 번호", true));
									topicPanel.add(new MyLabel("대표 예약인", true));
									topicPanel.add(new MyLabel("예약 인원", true));

									JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 5));
									contentPanel.add(new MyLabel(String.valueOf(rs.getInt("b_number"))));
									contentPanel.add(new MyLabel(user.name));
									contentPanel.add(new MyLabel(String.valueOf(rs.getInt("b_guest_quantity"))));

									inPanel.add(topicPanel, BorderLayout.WEST);
									inPanel.add(contentPanel, BorderLayout.CENTER);
								}
								panel.add(inPanel, BorderLayout.CENTER);
							}
							{
								JPanel inPanel = new JPanel(new BorderLayout(20, 0));
								{
									JPanel topicPanel = new JPanel(new GridLayout(3, 1, 0, 8));
									topicPanel.add(new MyLabel("투숙 기간", true));
									topicPanel.add(new MyLabel("객실명", true));
									topicPanel.add(new MyLabel("객실", true));

									Statement stmt2 = conn.createStatement();
									ResultSet rs2 = stmt2.executeQuery(
											"SELECT count(*) FROM 예약_일자 WHERE b_number=" + rs.getInt("b_number"));
									rs2.next();
									int size = rs2.getInt(1);
									rs2 = stmt2.executeQuery("SELECT b_date FROM 예약_일자 WHERE b_number="
											+ rs.getInt("b_number") + " order by b_date");

									String min_date = null, max_date = null;
									for (int count = 1; rs2.next(); count++) {
										if (count == 1)
											min_date = rs2.getString("b_date");
										if (count == size)
											max_date = rs2.getString("b_date");
									}

									rs2 = stmt2.executeQuery("SELECT * FROM 방 WHERE r_number=" + rs.getInt("r_number"));
									rs2.next();

									JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 8));
									contentPanel.add(new MyLabel(min_date + " ~ " + max_date, 10));
									contentPanel.add(new MyLabel(rs2.getString("r_name")));
									contentPanel.add(new MyLabel(rs2.getInt("r_room_number") + "호"));

									inPanel.add(topicPanel, BorderLayout.WEST);
									inPanel.add(contentPanel, BorderLayout.CENTER);
								}
								panel.add(inPanel, BorderLayout.EAST);
							}
							panels[1].add(panel);
						}
						panels[0].add(panels[1]);
						bottomFrame.add(panels[0]);
					}
				}
			}
			{
				JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
				{
					JButton button = new JButton("로그아웃");
					button.setPreferredSize(new Dimension(140, 45));
					button.setFont(new Font("굴림", Font.BOLD, 20));
					panel.add(button);
					button.addActionListener(e -> {
						YJHotel.logout();
					});
				}
				add(panel, BorderLayout.CENTER);
			}
			add(tab, BorderLayout.NORTH);
		}
	}
	private class MyLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		public MyLabel(String text) {
			this(text, 15, false);
		}

		public MyLabel(String text, boolean mode) {
			this(text, 15, mode);
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
