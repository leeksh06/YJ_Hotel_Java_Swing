package yjhotel;

import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// 로딩용 패널
public class Loading extends JPanel {
	private static final long serialVersionUID = 5764854221087854574L;

	public Loading() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// gif 로딩 이미지를 가져와 JLabel로 띄우기
		ImageIcon icon = new ImageIcon("./loading.gif");
		Image image = icon.getImage().getScaledInstance(80, 80, Image.SCALE_REPLICATE);
		JLabel loading = new JLabel(new ImageIcon(image));

		// 중간 쯤 오게 만들기 위해 280의 수직 여백 추가
		add(Box.createVerticalStrut(280));
		add(loading); 
	}

}
