package yjhotel.frame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;

import yjhotel.YJHotel;

public class Register extends JPanel implements ActionListener {
	private static final long serialVersionUID = 6129959965622135405L;
	String[] u_id, u_phone; // DB에서 가져오는 데이터를 담은 배열
	String[] u_email;

	int regi_Cnt = 0; // DB에 사용자 수 담는거
	int get_Client = 0;
	int pass_id = 0; // 사용자가 입력한 아이디가 중복인지 아닌지 여부: 0이면 중복 X, 1이면 중복 O

	int do_search = 0; // 아이디 중복체크 여부: 0이면 중복체크 X, 1이면 중복체크 O
	int pass_ph = 0; // 전화번호 중복 검사
	int e_mail = 0;
	int pass_check = 0;

	int sum_check = 0;

	String eq_phone1 = "";
	String eq_phone2 = "";
	String eq_phone3 = "";

	// 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
	LocalDate now = LocalDate.now();
	int year = now.getYear();
	int month = now.getMonth().getValue();
	int day = now.getDayOfMonth();

	int regi_num = 0; // 고유번호
	String regi_name = ""; // 성명
	String eq_id = ""; // 아이디
	String regi_ps = ""; // 비밀번호
	String regi_ps_2 = "";// 비번확인
	String eq_phone = ""; // 폰넘버
	String regi_birth = ""; // 생년월일
	String regi_mail = ""; // 메일
	String mail_check = "";
	String regi_enroll = year + "-" + month + "-" + day;

	JTextField f, f2, f3, f5, f6;
	JTextField f1; // 추가
	JComboBox<String> mailBox;

	String null_name = "";
	String null_pass = "";
	String null_passs = "";
	String null_num = "";
	String null_birth = "";
	String null_mail = "";
	String blank = "";
	String blank2 = "";

	public Register(JDialog dialog) {

		set_RegiData(0);
		setPreferredSize(new Dimension(410, 480));

		setLayout(null);
		//
	   

		JLabel label = new JLabel("성  명");
		label.setBounds(50, 17, 50, 50);
		add(label);

		f = new JTextField();
		f.setBounds(90, 30, 200, 25);
		add(f);
		f.addActionListener(e -> {
			// 성명 필드 액션
		});

		// +43
		JLabel label1 = new JLabel("아이디");
		label1.setBounds(43, 60, 50, 50);
		add(label1);

		//JTextField f1 = new JTextField();
		 this.f1 = new JTextField();
		f1.setBounds(90, 73, 200, 25);
		add(f1);
		f1.addActionListener(e -> {
			// 아이디 필드 액션
		});
		//

		JLabel label2 = new JLabel("비밀번호");
		label2.setBounds(35, 103, 50, 50);
		add(label2);

		f2 = new JTextField();
		f2.setBounds(90, 116, 200, 25);
		add(f2);
		f2.addActionListener(e -> {
			// 비밀번호 필드 액션
		});
		//
		JLabel label3 = new JLabel("비밀번호 확인");
		label3.setBounds(10, 143, 80, 50);
		add(label3);

		f3 = new JTextField();
		// JPasswordField f3 = new JPasswordField(); //비밀번호 암호화
		// f3.setEchoChar('*');
		f3.setBounds(90, 156, 200, 25);
		add(f3);
		f3.addActionListener(e -> {
			// 비밀번호 확인필드 액션
		});
		//
		JLabel label_ = new JLabel("-");
		JLabel label_R = new JLabel("-");
		JComboBox<String> telBox = new JComboBox<String>(); // 010~박스 내리기추가하는법
		telBox.addItem("010");
		telBox.addItem("011");
		telBox.addItem("017");
		JLabel label4 = new JLabel("휴대폰 번호");
		label4.setBounds(20, 186, 80, 50);
		add(label4);

		telBox.setBounds(90, 199, 50, 25);
		add(telBox); // 추가한ㄷ 010박스 나열

		label_.setBounds(147, 199, 80, 25);
		add(label_);

		JTextField f4_1 = new JTextField();
		f4_1.setBounds(160, 199, 50, 25);
		add(f4_1);
		//
		f4_1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (f4_1.getText().length() >= 4)
					e.consume();
			}
		});
		// 여기부분이 필드에 KeyListener로 필드의 글자수 제한

		label_R.setBounds(220, 199, 80, 25);
		add(label_R);

		JTextField f4_2 = new JTextField();
		f4_2.setBounds(235, 199, 50, 25);
		add(f4_2);
		//
		f4_2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (f4_2.getText().length() >= 4)
					e.consume();
			}
		});
		// 여기부분이 필드에 KeyListener로 필드의 글자수 제한

		f4_2.addActionListener(e -> {
			// 휴대폰번호 두번째박스 액션
		});

		//
		JLabel label5 = new JLabel("생년월일");
		label5.setBounds(35, 229, 50, 50);
		add(label5);

		f5 = new JTextField();
		//
		f5.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (f5.getText().length() >= 6)
					e.consume();
			}
		});
		// 여기부분이 필드에 KeyListener로 필드의 글자수 제한
		f5.setBounds(90, 242, 80, 25);
		add(f5);

		f5.addActionListener(e -> {
			// 생년월일 액션 필드
		});

		JLabel R5_1 = new JLabel("ex)981103");
		R5_1.setBounds(195, 242, 200, 25);
		add(R5_1);

		/////////////////////////////////////////////////////////////////////////////
		mailBox = new JComboBox<String>(); // 메일박스 내리기추가하는법
		mailBox.addItem("naver.com");
		mailBox.addItem("daum.com");
		mailBox.addItem("google.com");

		JLabel label_RRR = new JLabel("@");
		JLabel label6 = new JLabel("이메일");
		label6.setBounds(43, 275, 50, 50);
		add(label6);

		f6 = new JTextField();
		f6.setBounds(90, 288, 80, 25);
		add(f6);
		f6.addActionListener(e -> {
			// 이메일 액션 필드
		});

		label_RRR.setBounds(176, 288, 80, 25);
		add(label_RRR);

		mailBox.setBounds(195, 288, 90, 25);
		add(mailBox);
		//
		JButton button_A = new JButton("등록"); // 회원가입 버튼////////////////////////////등록ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ
		button_A.setBounds(90, 340, 80, 45);
		add(button_A);
		button_A.addActionListener(e -> {
			eq_phone1 = (String) telBox.getSelectedItem();
			eq_phone2 = f4_1.getText();
			eq_phone3 = f4_2.getText();

			eq_phone = eq_phone1 + eq_phone2 + eq_phone3;

			mail_check = f6.getText() + "@" + (String) mailBox.getSelectedItem();

			eq_id = f1.getText();
			null_name = f.getText();
			null_pass = f2.getText();
			null_passs = f3.getText();
			null_birth = f5.getText();

			null_mail = f6.getText();
			// mail_check = f2.getText();
			regi_ps = f2.getText();
			regi_ps_2 = f3.getText();

			sum_check = 0;
			check_data();
			for (int i = 0; i < regi_Cnt; i++) {
				if (eq_phone.equals(u_phone[i])) {
					JOptionPane.showMessageDialog(null, "중복되는 휴대폰 번호입니다.다시 입력해주십시오.", "중복확인",
							JOptionPane.DEFAULT_OPTION);
					pass_ph += 1;
					break;
				} else
					pass_ph = 0;
			}

			for (int i = 0; i < regi_Cnt; i++) {
				if (do_search == 0 && sum_check == 0) {
					JOptionPane.showMessageDialog(null, "아이디 중복 체크를 해주세요.", "", JOptionPane.DEFAULT_OPTION);
					break;
				} else {
					if (pass_ph == 0 && sum_check == 0) {
						set_RegiData(1);
						JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.", "회원가입", JOptionPane.DEFAULT_OPTION);
						dialog.dispose();
						break;
					}
				}

			}

		});

		JButton button_B = new JButton("취소");
		button_B.setBounds(220, 340, 80, 45);
		add(button_B);
		button_B.addActionListener(e -> {
			dialog.dispose();
		});

		JButton button1 = new JButton("중복 확인");

		button1.addActionListener(e -> {
			// 중복체크
			 System.out.println("버튼 클릭 이벤트 발생"); // 로그 추가
			 eq_id = f1.getText();
			    String blank = "";

			    for (int i = 0; i < regi_Cnt; i++) {

			        if (eq_id.equals(blank)) {
			            JOptionPane.showMessageDialog(null, "아이디를 입력해 주십시오", "", JOptionPane.DEFAULT_OPTION);
			            break;
			        }

			        if (eq_id.equals(u_id[i])) {
			            JOptionPane.showMessageDialog(null, "중복되는 아이디입니다.다시 입력해주십시오.", "중복확인", JOptionPane.DEFAULT_OPTION);
			            f1.setText("");
			            pass_id = 1;
			            do_search = 0;
			            break;
			        }

			        pass_id = 0;

			        if (pass_id == 0 && i == regi_Cnt - 1) {
			           do_search = 1;
			           JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다.", "중복확인 ", JOptionPane.DEFAULT_OPTION);
			       }
			   }
			});

		button1.setBounds(298, 71, 88, 28);
		add(button1);
		//

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void check_data() {

		if (null_name.equals(blank)) {
			JOptionPane.showMessageDialog(null, "성명을 입력해주세요.", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;
		} else if (eq_id.equals(blank)) {
			JOptionPane.showMessageDialog(null, "아이디를 입력해 주십시오", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;
		}

		else if (do_search == 0) {
			JOptionPane.showMessageDialog(null, "아이디 중복 체크를 해주세요.", "", JOptionPane.WARNING_MESSAGE);
			sum_check += 1;
		}

		else if (null_pass.equals(blank)) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;
		}

		else if (null_passs.equals(blank)) {
			JOptionPane.showMessageDialog(null, "비밀번호를 한번더 입력해주세요.", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;
		}

		else if (!null_passs.equals(null_pass)) {
			JOptionPane.showMessageDialog(null, "비밀번호를 다시확인해 주세요..", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;
		}

		else if (eq_phone2.equals(blank)) {
			JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요.", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;

		} else if (eq_phone3.equals(blank)) {
			JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요.", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;

		}

		else if (null_birth.equals(blank)) {
			JOptionPane.showMessageDialog(null, "생년월일을 입력해주세요.", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;

		}

		else if (null_mail.equals(blank)) {
			JOptionPane.showMessageDialog(null, "메일 입력해주세요.", "", JOptionPane.DEFAULT_OPTION);
			sum_check += 1;

		}

	}

	public void set_RegiData(int num) {

		// set_RegiData(0) = DB에서 사용자 값 가져옴
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
				u_email = new String[regi_Cnt];
				int x = 0;

				while (rs2.next()) {
					u_id[x] = rs2.getString(3);
					u_phone[x] = rs2.getString(5);
					u_email[x] = rs2.getString(7);

					x += 1;
				}

			} catch (SQLException e) {
				System.out.println("연결에 실패하였습니다.");
				e.printStackTrace();
			}
		}

		// set_RegiData(1) = DB에 신규 사용자 정보를 집어넣음
		if (num == 1) {

			Connection con = null;

			try {
				// 데이터베이스 연결
				con = YJHotel.getConnection();

				Statement stmt = con.createStatement();

				regi_name = f.getText(); // 성명
				String rr = f5.getText(); // 생년월일
				// 112233
				String regi_birth = rr.substring(0, 2) + "-" + rr.substring(2, 4) + "-" + rr.substring(4, 6);
				String regi_mail = f6.getText() + "@" + (String) mailBox.getSelectedItem(); // 메일

				String s = String.format(
					    "INSERT INTO 회원 (u_number, u_name, u_id, u_password, u_phone_number, u_birthday, u_email, u_enroll_date, u_profile_image)"
					    + " VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', 'https://cdn.pixabay.com/photo/2015/04/08/07/25/fat-712246_1280.png')",
					    regi_Cnt + 1,
					    regi_name,
					    eq_id,
					    regi_ps,
					    eq_phone,
					    regi_birth,
					    regi_mail,
					    regi_enroll
					);
					stmt.executeUpdate(s);


			} catch (SQLException e) {
				System.out.println("연결에 실패하였습니다.");
				e.printStackTrace();
			}
		}

	}
}