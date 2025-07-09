package yjhotel.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

import yjhotel.page.Notice;

public class NoticeContent {

	int c_Cnt;

	Font f1 = new Font("견고딕 보통", Font.BOLD, 24); // NOTICE
	Font f2 = new Font("견고딕 보통", Font.BOLD, 18); // 공지 제목
	Font f3 = new Font("견고딕 보통", Font.BOLD, 15); // 공지 날짜

	// Notice_Form에서 생성자를 받아 호출 됨
	public NoticeContent(int select) {

		for (int i = 0; i < Notice.n_Cnt; i++)
			if (select == i)
				draw_Content(i);
	}

	// 생성자와 매칭되는 공지 제목, 날짜, 내용 불러서 레이블에 저장
	// 중간에 paint_line n_POP = new paint_line();에서 생성된 n_POP이 창 본체
	public void draw_Content(int num) {

		JLabel c_Top = new JLabel("NOTICE");
		c_Top.setBounds(550 - 120, 0, 110, 40);
		c_Top.setHorizontalAlignment(JLabel.CENTER);
		c_Top.setOpaque(true);
		c_Top.setFont(f1);

		JLabel c_Name = new JLabel(Notice.n_Name[num]);
		c_Name.setFont(f2);
		c_Name.setBounds(10, 42, 510, 40);

		JLabel c_Date = new JLabel(Notice.n_Date[num]);
		c_Date.setFont(f3);
		c_Date.setBounds(10, 42, 100, 100);

		String str1 = Notice.n_Content[num].replace("다. ", "다.");
		str1 = str1.replace("다.  ", "다.");
		str1 = str1.replace("다.", "다.`!`");
		str1 = str1.replace("요. ", "요.");
		str1 = str1.replace("요.", "요.`!`");
		str1 = str1.replace("오. ", "오.");
		str1 = str1.replace("오.", "오.`!`");

		String[] str = str1.split("`!`");
//		System.out.println(Arrays.toString(str));

		c_Cnt = str.length;

		for (int i = 0; i < str.length; i++) {
			if (str[i].length() > 44) {
				c_Cnt += 1;
//				System.out.println(str[i].length());
			}
		}

		// 창 본체(Frame) 생성
		// 여기다 각종 레이블 등등 다 붙여 넣음
		paint_line n_POP = new paint_line();

		n_POP.setLayout(null);
		n_POP.setResizable(false);
		n_POP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // EXIT = 모든 창 종료 DISPOSE = 해당 창만 종료

		n_POP.add(c_Top);

		n_POP.add(c_Name);
		n_POP.setTitle(Notice.n_Name[num]);
		n_POP.add(c_Date);

		String sup = "";

		String[] s = new String[c_Cnt];

		int[] ascii = new int[c_Cnt];

		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < str[i].length(); j++) {
				if (48 <= str[i].charAt(j) && str[i].charAt(j) <= 57)
					ascii[i] += 1;
			}
		}

		for (int i = 0; i < str.length; i++) {
			if (str[i].length() > 44) {
				if (ascii[i] >= 10)
					sup += str[i].substring(0, 44) + '`' + str[i].substring(44) + '`';
				else if (5 <= ascii[i] && ascii[i] <= 10)
					sup += str[i].substring(0, 45) + '`' + str[i].substring(45) + '`';
				else
					sup += str[i].substring(0, 40) + '`' + str[i].substring(40) + '`';
			} else
				sup += str[i] + '`';
//			System.out.println(sup); //문자열 수정할 때 어떻게 바뀌나 하나씩 출력
		}

		s = sup.split("`");

		JLabel[] c_Con = new JLabel[c_Cnt];
//		System.out.println("CNT = " + c_Cnt);

		int c_ConY = 170;
		for (int i = 0; i < c_Cnt; i++) {
			c_Con[i] = new JLabel(s[i]);
			c_Con[i].setFont(f3);
			c_Con[i].setBounds(10, c_ConY, 520, 30);
			c_Con[i].setHorizontalAlignment(JLabel.LEFT);
			n_POP.add(c_Con[i]);
			c_ConY += 30;
		}

		n_POP.setSize(550, 215 + c_Cnt * 30 + 60);
		n_POP.setLocationRelativeTo(null);
		n_POP.setVisible(true);

	}

	// 제목 날짜 밑에 공지 내용과 분리하는 선 + 하단에 공지 내용 끝났다 표시하는 선 생성
	// 창 본체(프레임)
	class paint_line extends JFrame {
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(20, 160, 530, 160);
			g.drawLine(20, 215 + c_Cnt * 30 + 20, 530, 215 + c_Cnt * 30 + 20);
		}

	}

}
