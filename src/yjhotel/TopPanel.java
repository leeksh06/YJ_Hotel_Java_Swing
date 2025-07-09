package yjhotel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

// 공용으로 사용할 TopPanel 패널
public class TopPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 4458376419632840721L;
	private JTextField field;

	public TopPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		Font font = new Font("Monospaced", Font.BOLD, 16);

		// 뒤로가기 버튼
		JButton backButton = new JButton("<");
		backButton.setFont(font);
		backButton.setName("back");
		backButton.setPreferredSize(new Dimension(40, 40));
		backButton.addActionListener(this);
		panel.add(backButton);
		backButton.setMargin(new Insets(0, 0, 0, 0));

		// 수직 여백 20 추가
		panel.add(Box.createHorizontalStrut(20));

		// 홈 버튼 추가
		JButton homeButton = new JButton("H");
		homeButton.setFont(font);
		homeButton.setName("home");
		homeButton.setPreferredSize(new Dimension(40, 40));
		homeButton.addActionListener(this);
		homeButton.setMargin(new Insets(0, 0, 0, 0));
		panel.add(homeButton);

		panel.add(Box.createHorizontalStrut(20));

		// 페이지 레이블 추가
		field = new JTextField("폼 이름", 15);
		field.setFont(new Font("굴림", Font.BOLD, 15));
		field.setEditable(false);
		field.setFocusable(false);
		field.setPreferredSize(new Dimension(200, 40));
		field.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
				BorderFactory.createLineBorder(Color.WHITE, 10)));
		field.setBackground(Color.WHITE);
		panel.add(field);

		panel.setPreferredSize(new Dimension(YJHotel.FRAME_WIDTH, 40));
		add(panel);
	}

	public void setTitle(String title) {
		field.setText(title);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = ((JButton) e.getSource()).getName();
		if (name.equals("home")) {
			// route to home
			YJHotel.route("mainpage");
		}
		if (name.equals("back")) {
			// route to back
			YJHotel.goBack();
		}
	}
}