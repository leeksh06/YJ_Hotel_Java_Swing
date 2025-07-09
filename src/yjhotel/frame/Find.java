package yjhotel.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import yjhotel.YJHotel;

public class Find extends JPanel {
	private static final long serialVersionUID = 8885570339154654782L;
	String[] u_name, u_id, u_email, u_phone, u_password;
	int regi_Cnt = 0;
	int get_Client = 0;
	int pass_id = 0;

	int do_search = 0;
	int pass_ph = 0;
	int e_mail = 0;

	String eq_phone1 = "";
	String eq_phone2 = "";
	String eq_phone3 = "";

	String eq_mail1 = "";
	String eq_mail2 = "";
	String eq_name = "";

	String eq_phone1P = "";
	String eq_phone2P = "";
	String eq_phone3P = "";

	String eq_mail1P = "";
	String eq_mail2P = "";
	String eq_nameP = "";

	String regi_mail = ""; // 메일
	String mail_check = "";
	String mail_checkP = "";
	// 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
	LocalDate now = LocalDate.now();
	int year = now.getYear();
	int month = now.getMonth().getValue();
	int day = now.getDayOfMonth();

	int regi_num = 0; // 고유번호
	String regi_name = ""; // 성명
	String eq_id = ""; // 아이디
	String regi_ps = ""; // 비밀번호
	String eq_phone = ""; // 폰넘버
	String eq_phoneP = ""; // 폰넘버2
	String check_name = "";
	String check_idP = "";

	JComboBox<String> mailBox;
	JComboBox<String> telBox;

	String null_name = "";
	String null_email = "";
	String null_num = "";
	String null_num1 = "";
	String null_num2 = "";
	String blank = "";
	String blank2 = "";
	String null_id = "";

	String null_nameP = "";
	String null_emailP = "";
	String null_numP = "";
	String null_num1P = "";
	String null_num2P = "";
	String null_idP = "";

	int sum_check_Find = 0;
	int sum_check_Find1 = 0;

	public Find(JDialog dialog) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 280));
		createTabbedPane(dialog);
		set_RegiData(0);
	}

	void createTabbedPane(JDialog dialog) {

		JTabbedPane tPane = new JTabbedPane(); // 탭
		add(tPane);// 탭을 프레임에 붙히기

		// 아이디 찾기
		JPanel id_Search = new JPanel(); // ID찾기 패널
		id_Search.setLayout(null);
		tPane.add("아이디 찾기", id_Search);

		//
		JLabel label = new JLabel("성명");
		label.setBounds(60, 30, 40, 10);
		id_Search.add(label);

		JTextField f = new JTextField();
		f.setBounds(115, 25, 100, 22);
		id_Search.add(f);
		//
		mailBox = new JComboBox<String>(); // 010~박스 내리기추가하는법
		mailBox.addItem("naver.com");
		mailBox.addItem("daum.com");
		mailBox.addItem("google.com");
		JLabel label1 = new JLabel("이메일 주소");
		label1.setBounds(30, 70, 70, 10);
		id_Search.add(label1);

		JTextField f1 = new JTextField();
		f1.setBounds(115, 65, 80, 22);
		JLabel f11 = new JLabel("@");
		f11.setBounds(200, 65, 40, 22);

		mailBox.setBounds(220, 65, 100, 22);
		id_Search.add(f1);
		id_Search.add(f11);
		id_Search.add(mailBox);
		//
		JLabel label_ = new JLabel("-");
		JLabel label_R = new JLabel("-");
		telBox = new JComboBox<String>(); // 010~박스 내리기추가하는법
		telBox.addItem("010");
		telBox.addItem("011");
		telBox.addItem("017");
		JLabel label4 = new JLabel("휴대폰 번호");
		label4.setBounds(30, 90, 80, 50);
		id_Search.add(label4);

		telBox.setBounds(115, 103, 50, 25);
		id_Search.add(telBox); // 추가한ㄷ 010박스 나열

		label_.setBounds(175, 103, 80, 25);
		id_Search.add(label_);

		JTextField f4_1 = new JTextField();
		f4_1.setBounds(190, 103, 50, 25);
		id_Search.add(f4_1);
		//
		f4_1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (f4_1.getText().length() >= 4)
					e.consume();
			}
		});
		// 여기부분이 필드에 KeyListener로 필드의 글자수 제한

		label_R.setBounds(250, 103, 80, 25);
		id_Search.add(label_R);

		JTextField f4_2 = new JTextField();
		f4_2.setBounds(265, 103, 50, 25);
		id_Search.add(f4_2);
		//
		f4_2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (f4_2.getText().length() >= 4)
					e.consume();
			}
		});
		// 여기부분이 필드에 KeyListener로 필드의 글자수 제한
//
		JButton button1 = new JButton("찾기");/////////////////////////////////////////////////////// 찾기
		button1.setBounds(100, 150, 70, 40);
		id_Search.add(button1);
		button1.addActionListener(e -> {// 아이디 찾기 버튼 액션

			eq_phone1 = (String) telBox.getSelectedItem();
			eq_phone2 = f4_1.getText();
			eq_phone3 = f4_2.getText();

			eq_phone = eq_phone1 + eq_phone2 + eq_phone3;

			eq_mail1 = f1.getText();
			eq_mail2 = (String) mailBox.getSelectedItem();
			mail_check = eq_mail1 + "@" + eq_mail2;

			null_name = f.getText();
			null_email = f1.getText();
			check_name = f.getText();
			null_num1 = f4_1.getText();
			null_num2 = f4_2.getText();

			check_Find();

//			for (int i = 0; i < regi_Cnt; i++) {
//				if(null_name.equals(u_name[i])&& mail_check.equals(u_email[i]) && eq_phone.equals(u_phone[i]))
//					JOptionPane.showMessageDialog(null, "아이디는 ["+u_id[i]+"]입니다.", "아이디 찾기", JOptionPane.DEFAULT_OPTION);
//				
//			}

//			for (int i = 0; i < regi_Cnt; i++) {
//			
//				
//				if(check_name.equals(u_name[i])) {
//					if(mail_check.equals(u_email[i])) {
//						if(eq_phone.equals(u_phone[i])) 	{
//							JOptionPane.showMessageDialog(null, "아이디는 ["+u_id[i]+"]입니다.", "아이디 찾기", JOptionPane.DEFAULT_OPTION);
//								f.setText(""); f1.setText(""); f4_1.setText(""); f4_2.setText("");
//								
//						}else JOptionPane.showMessageDialog(null, "정보를 다시 확인해 주세요.", "아이디 찾기", JOptionPane.ERROR_MESSAGE);
//						break;
//						
//						
//					}
//					
//				}
//				
//			}

			for (int i = 0; i < regi_Cnt; i++) {

				if (check_name.equals(u_name[i])) {
					if (mail_check.equals(u_email[i])) {
						if (eq_phone.equals(u_phone[i])) {
							JOptionPane.showMessageDialog(null, "아이디는 [" + u_id[i] + "]입니다.", "아이디 찾기",
									JOptionPane.DEFAULT_OPTION);
							// f.setText(""); f1.setText(""); f4_1.setText(""); f4_2.setText("");

						} else if (check_name.equals(u_name[i]) && (eq_phone.equals(u_phone[i]))
								|| (!mail_check.equals(u_email[i])))
							JOptionPane.showMessageDialog(null, "정보를 다시 확인해 주세요1.", "아이디 찾기",
									JOptionPane.ERROR_MESSAGE);

						else if (check_name.equals(u_name[i]) && (mail_check.equals(u_email[i]))
								|| (!eq_phone.equals(u_phone[i])))
							JOptionPane.showMessageDialog(null, "정보를 다시 확인해 주세요.2", "아이디 찾기",
									JOptionPane.ERROR_MESSAGE);

					}

				}

			}

		}

		);

//
		JButton button2 = new JButton("취소");
		button2.setBounds(200, 150, 70, 40);
		id_Search.add(button2);
		button2.addActionListener(e -> {
			// 아이디 찾기 취소 버튼 액션
			dialog.dispose();
		});

//

		////////////////////////////////////////////////////////////////////// 비밀번호 찾기
		JPanel ps_Search = new JPanel(); // PS 찾기 패널
		ps_Search.setLayout(null);
		tPane.add("비밀번호 찾기", ps_Search);

		//
		JLabel label_p1 = new JLabel("아이디");
		label_p1.setBounds(60, 30, 40, 10);
		ps_Search.add(label_p1);

		JTextField f_p1 = new JTextField();
		f_p1.setBounds(115, 25, 100, 22);
		ps_Search.add(f_p1);

		//
		JComboBox<String> mailBox_p1 = new JComboBox<String>(); // 010~박스 내리기추가하는법
		mailBox_p1.addItem("naver.com");
		mailBox_p1.addItem("daum.com");
		mailBox_p1.addItem("google.com");
		JLabel label1_p1 = new JLabel("이메일 주소");
		label1_p1.setBounds(30, 70, 70, 10);
		ps_Search.add(label1_p1);

		JTextField f1_p1 = new JTextField();
		f1_p1.setBounds(115, 65, 80, 22);

		JLabel f12 = new JLabel("@");
		f12.setBounds(200, 65, 40, 22);

		ps_Search.add(f12);
		mailBox_p1.setBounds(220, 65, 100, 22);
		ps_Search.add(f1_p1);
		ps_Search.add(mailBox_p1);
		//
		JLabel label__p1 = new JLabel("-");
		JLabel label_R_p1 = new JLabel("-");
		JComboBox<String> telBox_p1 = new JComboBox<String>(); // 010~박스 내리기추가하는법
		telBox_p1.addItem("010");
		telBox_p1.addItem("011");
		telBox_p1.addItem("017");
		JLabel label4_p1 = new JLabel("휴대폰 번호");
		label4_p1.setBounds(30, 90, 80, 50);
		ps_Search.add(label4_p1);

		telBox_p1.setBounds(115, 103, 50, 25);
		ps_Search.add(telBox_p1); // 추가한ㄷ 010박스 나열

		label__p1.setBounds(175, 103, 80, 25);
		ps_Search.add(label__p1);

		JTextField f4_1_p1 = new JTextField();
		f4_1_p1.setBounds(190, 103, 50, 25);
		ps_Search.add(f4_1_p1);
		//
		f4_1_p1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (f4_1_p1.getText().length() >= 4)
					e.consume();
			}
		});
		// 여기부분이 필드에 KeyListener로 필드의 글자수 제한
		label_R_p1.setBounds(250, 103, 80, 25);
		ps_Search.add(label_R_p1);

		JTextField f4_2_p1 = new JTextField();
		f4_2_p1.setBounds(265, 103, 50, 25);
		ps_Search.add(f4_2_p1);
//
		f4_2_p1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (f4_2_p1.getText().length() >= 4)
					e.consume();
			}
		});
		// 여기부분이 필드에 KeyListener로 필드의 글자수 제한
		JButton button1_p1 = new JButton("찾기");
		button1_p1.setBounds(100, 150, 70, 40);
		ps_Search.add(button1_p1);

		button1_p1.addActionListener(e -> {// 비밀번호 찾기액션

			// check_Find_P();
			eq_phone1P = (String) telBox_p1.getSelectedItem();
			eq_phone2P = f4_1_p1.getText();
			eq_phone3P = f4_2_p1.getText();

			eq_phoneP = eq_phone1P + eq_phone2P + eq_phone3P;

			eq_mail1P = f1_p1.getText();
			eq_mail2P = (String) mailBox_p1.getSelectedItem();

			mail_checkP = eq_mail1P + "@" + eq_mail2P;
			check_idP = f_p1.getText();
			null_idP = f_p1.getText();
			null_num1P = f4_1_p1.getText();
			null_num2P = f4_2_p1.getText();
			null_emailP = f1_p1.getText();

			check_Find_P();

			for (int i = 0; i < regi_Cnt; i++) {
				if (check_idP.equals(u_id[i])) {
				}
				if (mail_checkP.equals(u_email[i]))
					if (eq_phoneP.equals(u_phone[i])) {
						JOptionPane.showMessageDialog(null, "비밀번호는 [" + u_password[i] + "]입니다.", "비밀번호 찾기",
								JOptionPane.DEFAULT_OPTION);
						f_p1.setText("");
						f1_p1.setText("");
						f4_1_p1.setText("");
						f4_2_p1.setText("");

					} else if (!eq_phoneP.equals(u_phone[i]) || (mail_checkP.equals(u_email[i])))
						JOptionPane.showMessageDialog(null, "정보를 다시 확인해 주세요.", "비밀번호 찾기", JOptionPane.ERROR_MESSAGE);

			}

		});

//
		JButton button2_p1 = new JButton("취소");
		button2_p1.setBounds(200, 150, 70, 40);
		ps_Search.add(button2_p1);

		button2_p1.addActionListener(e -> {
			dialog.dispose();
		});

//

	}

	public void check_Find() {
		if (null_name.equals(blank)) {
			JOptionPane.showMessageDialog(null, "정보를 입력해주세요1.", "정보 입력", JOptionPane.DEFAULT_OPTION);
		} else if (null_email.equals(blank)) {
			JOptionPane.showMessageDialog(null, "이메일 주소를 입력해주세요1.", "정보 입력", JOptionPane.DEFAULT_OPTION);

		} else if (null_num1.equals(blank)) {
			JOptionPane.showMessageDialog(null, "휴대폰 번호를 입력해주세요1.", "정보 입력", JOptionPane.DEFAULT_OPTION);

		} else if (null_num2.equals(blank)) {
			JOptionPane.showMessageDialog(null, "휴대폰 번호를 입력해주세요2.", "정보 입력", JOptionPane.DEFAULT_OPTION);
			sum_check_Find += 1;
		}

	}

	public void check_Find_P() {

		if (null_idP.equals(blank2)) {
			JOptionPane.showMessageDialog(null, "정보를 입력해주세요2.", "정보 입력", JOptionPane.DEFAULT_OPTION);

		} else if (null_emailP.equals(blank2)) {
			JOptionPane.showMessageDialog(null, "이메일 주소를 입력해주세요2.", "정보 입력", JOptionPane.DEFAULT_OPTION);

		} else if (null_num1P.equals(blank2)) {
			JOptionPane.showMessageDialog(null, "휴대폰 번호를 입력해주세요2a.", "정보 입력", JOptionPane.DEFAULT_OPTION);

		} else if (null_num2P.equals(blank2)) {
			JOptionPane.showMessageDialog(null, "휴대폰 번호를 입력해주세요2b.", "정보 입력", JOptionPane.DEFAULT_OPTION);
			sum_check_Find1 += 1;
		}

	}

	public void set_RegiData(int num) {

		if (num == 0) {

			Connection con = null;

			try {
				// 데이터베이스 연결
				con = YJHotel.getConnection();

				// 1개의 statement 객체는 1개만 사용되어야 함
				Statement stmt = con.createStatement();
				Statement stmt2 = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from 회원"); // 갯수 세는 놈
				ResultSet rs2 = stmt2.executeQuery("select * from 회원"); // 테이블 n_date 가져오는 놈

				// rs.next()를 통해 다음행을 내려갈 수 있으면 true를 반환하고, 커서를 한칸 내린다. 다음행이 없으면 false를 반환한다.
				while (rs.next()) {
					regi_Cnt += 1;

				}

				u_id = new String[regi_Cnt];
				u_phone = new String[regi_Cnt];
				u_password = new String[regi_Cnt];
				u_email = new String[regi_Cnt];
				u_name = new String[regi_Cnt];
				int x = 0;

				while (rs2.next()) {
					u_name[x] = rs2.getString(2);
					u_id[x] = rs2.getString(3);
					u_password[x] = rs2.getString(4);
					u_phone[x] = rs2.getString(5);
					u_email[x] = rs2.getString(7);
					x += 1;
				}

			} catch (SQLException e) {
				System.out.println("연결에 실패하였습니다.");
				e.printStackTrace();
			}
		}

	}
}
