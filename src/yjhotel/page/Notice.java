package yjhotel.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import yjhotel.Page;
import yjhotel.YJHotel;
import yjhotel.frame.NoticeContent;

public class Notice extends Page implements ActionListener {
	private static final long serialVersionUID = -8208433180212650340L;

	JButton n_Back, n_Home;
	JPanel n_Center, n_Start;
	public static String[] n_Date, n_Name, n_Content;
	public static int n_Cnt = 0; // 버튼 수
	JButton b_Notice[]; // 공지 제목 버튼

	Font f1 = new Font("HYPMokGak", Font.BOLD, 22); // 버튼
	Font f2 = new Font("HYPMokGak", Font.BOLD, 25); // 레이블
	Font f3 = new Font("HYPMokGak", Font.BOLD, 20); // 공지 버튼
	Font f4 = new Font("HYPMokGak", Font.BOLD, 18); // 공지 제목
	Font f5 = new Font("HYPMokGak", Font.BOLD, 14); // 공지 날짜

	LineBorder l1 = new LineBorder(Color.BLACK, 2, true);

	ImageIcon img = new ImageIcon("./src/j_HomeWork/left.png");
	ImageIcon img2 = new ImageIcon("./src/j_HomeWork/home.png");

	// 메인 프레임 생성, 각종 메소드 호출
	public Notice() {
		super("NOTICE", "NOTICE");

		setPreferredSize(new Dimension(550, 660));
		setLayout(null);

		setData(); // DB 값 가져옴
		drawNotice_Bottom(); // 공지사항 스크롤 붙이는 패널 메소드

	}

	// 공지사항 본체
	public void drawNotice_Bottom() {

		// 중단 패널
		n_Center = new JPanel();
		// n_Center.setBackground(Color.BLACK);
		n_Center.setBounds(2, 0, 585, 700);
		n_Center.setLayout(null);
		this.add(n_Center);

		// 중단 스크롤에 붙일 패널
		JPanel sc_Back = new JPanel();

		// 중단 실제 공지사항 패널, 크기는 밑에서 설정
		JPanel sc_Front = new JPanel();
		sc_Front.setLayout(null);
		sc_Back.add(sc_Front);
		// sc_Front.setBackground(Color.BLACK);

		sc_Front.setPreferredSize(new Dimension(520, n_Cnt * 60 - 10)); // 공지 길이 설정

		// sc_Front에 공지 1줄마다 패널 생성
		JPanel n_Line[] = new JPanel[n_Cnt];
		int lineX = 0;
		int lineY = 0;
		for (int i = 0; i < n_Cnt; i++) {
			n_Line[i] = new JPanel();
			n_Line[i].setBounds(lineX, lineY, 475, 50);
			n_Line[i].setBorder(l1);
			n_Line[i].setLayout(null);
			sc_Front.add(n_Line[i]);
			lineY += 60;
		}

		// 버튼 설정
		JLabel b_Number[] = new JLabel[n_Cnt]; // 넘버 버튼
		b_Notice = new JButton[n_Cnt]; // 공지 버튼
		JLabel b_Date[] = new JLabel[n_Cnt];

		int b_NumberX = 15, b_NumberY = 5; // 공지사항 번호 좌표
		int b_NoticeX = 75, b_NoticeY = 5; // 공지사항 제목 좌표
		int b_DateX = 390, b_DateY = 5; // 공지사항 날짜 좌표

		for (int i = 0; i < n_Cnt; i++) {
			b_Number[i] = new JLabel(i + 1 + "");
			b_Number[i].setBounds(b_NumberX, b_NumberY, 50, 40);
			b_Number[i].setOpaque(true);
			b_Number[i].setHorizontalAlignment(JLabel.CENTER); // 텍스트 중앙 정렬
			b_Number[i].setFont(f3);
			n_Line[i].add(b_Number[i]);
		}

		for (int i = 0; i < n_Cnt; i++) {
			b_Notice[i] = new JButton(n_Name[i]);
			b_Notice[i].setBounds(b_NoticeX, b_NoticeY, 305, 40);
			b_Notice[i].addActionListener(this);
			b_Notice[i].setHorizontalAlignment(JLabel.CENTER);
			b_Notice[i].setFont(f4);
			b_Notice[i].setContentAreaFilled(false);
			b_Notice[i].setBorderPainted(false);
			n_Line[i].add(b_Notice[i]);
		}

		for (int i = 0; i < n_Cnt; i++) {
			b_Date[i] = new JLabel();
			b_Date[i].setText(n_Date[i]);
			b_Date[i].setBounds(b_DateX, b_DateY, 80, 40);
			b_Date[i].setOpaque(true);
			b_Date[i].setHorizontalAlignment(JLabel.CENTER);
			b_Date[i].setFont(f5);
			n_Line[i].add(b_Date[i]);
		}

		JScrollPane n_Sc = new JScrollPane(sc_Back);
		n_Sc.setBounds(20, 20, 505, 620);
		n_Sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 수평 스크롤 X
		n_Sc.getVerticalScrollBar().setUnitIncrement(7);
		n_Sc.setBorder(l1);
		n_Center.add(n_Sc);

	}

	// 버튼 액션 이벤트 메소드(구현X)
	// for문은 스크롤 안에 공지사항 제목 클릭하면 Notice_Con.java 생성하면서 매칭되는 내용 담고있는 창 새로 띄움
	// Notice_Con은 스스로 실행하는게 아니라 Notice_Form에서 공지 클릭하면 생성되는 일종의 팝업창 비슷한 느낌
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		for (int i = 0; i < n_Cnt; i++) {
			if (e.getSource() == b_Notice[i])
				new NoticeContent(i);
		}

		if (e.getSource() == n_Back) {

		} else if (e.getSource() == n_Home) {

		}
	}

	// DB에서 데이터 가져옴
	public void setData() {

		Connection con = null;

		try {
			// 드라이버 적재
			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("드라이버 적재 성공");

			// 데이터베이스 연결
			con = YJHotel.getConnection();
//			System.out.println("데이터베이스 연결 성공");

			// 1개의 statement 객체는 1개만 사용되어야 함
			Statement stmt = con.createStatement();
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from 공지사항"); // 갯수 세는 놈
			ResultSet rs2 = stmt2.executeQuery("select * from 공지사항 order by n_date desc"); // 테이블 n_date 가져오는 놈

			// rs.next()를 통해 다음행을 내려갈 수 있으면 true를 반환하고, 커서를 한칸 내린다. 다음행이 없으면 false를 반환한다.
			while (rs.next()) {
				// getInt(1)은 컬럼의 1번째 값을 정수형으로 가져온다. / getString(2)는 컬럼의 2번째 값을 String형으로 가져온다.
				n_Cnt += 1;
			}

			int x = 0;
			n_Date = new String[n_Cnt];
			n_Name = new String[n_Cnt];
			n_Content = new String[n_Cnt];

			while (rs2.next()) {
				n_Date[x] = rs2.getString(4);
				n_Name[x] = rs2.getString(2);
				n_Content[x] = rs2.getString(3);
				x += 1;
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
			e.printStackTrace();
		}

	}

}