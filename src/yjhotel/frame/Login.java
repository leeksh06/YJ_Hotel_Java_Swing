package yjhotel.frame;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import yjhotel.YJHotel;
import yjhotel.model.UserVO;

public class Login extends JFrame {
	private static final long serialVersionUID = -2384463602642349236L;

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 200);
		JPanel panel = new JPanel();
		add(panel);

		panel.setLayout(null);
		//
		JLabel label = new JLabel("아이디");
		label.setBounds(30, 5, 50, 50);
		panel.add(label);

		JTextField f = new JTextField();
		f.setBounds(80, 21, 200, 20);
		panel.add(f);
		//
		JLabel label2 = new JLabel("비밀번호");
		label2.setBounds(20, 35, 50, 50);
		panel.add(label2);

		JPasswordField f2 = new JPasswordField(); // 비밀번호 암호화
		f2.setEchoChar('*');
		f2.setBounds(80, 51, 200, 20);
		panel.add(f2);

		//
		Font f1 = new Font("돋움", Font.BOLD, 9);
		JButton button1 = new JButton("아이디 찾기/비밀번호찾기");
		button1.setBorderPainted(false);// 버튼 테두리 삭제
		button1.setContentAreaFilled(false);// 버튼배경 삭제
		button1.setBounds(135, 75, 180, 15);
		button1.setFont(f1);
		panel.add(button1);

		button1.addActionListener(e -> {
			JDialog dialog = new JDialog(this, "아이디/비밀번호 찾기", true);
			dialog.add(new Find(dialog));
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		});

		//
		JButton button2 = new JButton("로그인");
		JButton button3 = new JButton("회원가입");

		button2.addActionListener(e -> {
			String id = f.getText().trim();
			String pw = new String(f2.getPassword()).trim();

			if (!isValidInput(id, pw)) {
				return;
			}

			login(id, pw);

		});

		button3.addActionListener(e -> {
			JDialog dialog = new JDialog(this, "회원가입", true);
			dialog.add(new Register(dialog));
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		});

		button2.setBounds(60, 100, 80, 40);
		panel.add(button2);

		button3.setBounds(160, 100, 90, 40);

		panel.add(button3);

		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public boolean isValidInput(String id, String pw) {
		if (id.equals("")) {
			YJHotel.showAlert(this, "아이디를 입력해주세요.", JOptionPane.DEFAULT_OPTION);
			return false;
		}

		if (pw.equals("")) {
			YJHotel.showAlert(this, "비밀번호를 입력해주세요.", JOptionPane.DEFAULT_OPTION);
			return false;
		}

		return true;
	}

	public void login(String id, String pw) {
		if (!YJHotel.isConnected()) {
			YJHotel.showAlert(this, "데이터베이스에 연결중 입니다. 잠시 후에 다시 시도해주세요.", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Connection conn = YJHotel.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select * from 회원 where u_id=? and u_password=?");
			pstm.setString(1, id);
			pstm.setString(2, pw);

			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				// user exists
				UserVO user = new UserVO();
				user.number = rs.getInt(1);
				user.name = rs.getString(2);
				user.id = rs.getString(3);
				user.password = rs.getString(4);
				user.phone_number = rs.getString(5);
				user.birthday = rs.getDate(6);
				user.email = rs.getString(7);
				user.cash = rs.getInt(8);
				user.enroll_date = rs.getDate(9);
				user.profile_image = rs.getString(10); 

				// login success
				YJHotel.setUser(user);
				YJHotel.launch();
				dispose();
			} else {
				// login failed
				YJHotel.showAlert(this, "아이디 또는 비밀번호가 일치하지 않습니다.", JOptionPane.DEFAULT_OPTION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}