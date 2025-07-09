package yjhotel.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import yjhotel.Page;
import yjhotel.YJHotel;
import yjhotel.model.CouponVO;
import yjhotel.model.CouponDAO;

public class CouponPage extends Page implements ActionListener {
	private static final long serialVersionUID = 1L;
	public JPanel remainPanel;
	public JPanel availPanel;
	private JScrollPane remainScroll;
	private JScrollPane availScroll;
	private JTextField serialField;
	private ArrayList<CouponVO> remainCoupons = new ArrayList<>();
	private ArrayList<CouponVO> availCoupons = new ArrayList<>();

	public CouponPage() {
		super("쿠폰 페이지", "쿠폰");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel serialPanel = new JPanel();
		serialPanel.setLayout(null);
		serialPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		serialPanel.setPreferredSize(new Dimension(YJHotel.FRAME_WIDTH, 130));

		JLabel serialLabel = new JLabel("시리얼 번호 등록", SwingConstants.CENTER);
		serialLabel.setFont(new Font("Dialog", Font.BOLD, 32));
		serialLabel.setSize(new Dimension(YJHotel.FRAME_WIDTH, 60));
		serialLabel.setLocation(0, 0);

		serialField = new JTextField("");
		serialField.setSize(new Dimension(320, 44));
		serialField.setLocation(70, 74);
		serialField.setBackground(Color.white);
		serialField.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.gray),
				BorderFactory.createEmptyBorder(0, 12, 0, 12)));

		JButton serialButton = new JButton("등록");
		serialButton.addActionListener(this);
		serialButton.setName("registerSerial");
		serialButton.setSize(new Dimension(80, 44));
		serialButton.setLocation(410, 74);

		serialPanel.add(serialLabel);
		serialPanel.add(serialField);
		serialPanel.add(serialButton);

		add(serialPanel);

		// 20 만큼의 여백을 추가
		add(Box.createVerticalStrut(20));

		JPanel couponPanel = new JPanel();
		couponPanel.setLayout(null);
		couponPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		couponPanel.setPreferredSize(new Dimension(YJHotel.FRAME_WIDTH, 510));
		couponPanel.setLocation(0, 0);
		JLabel remainLabel = new JLabel("보유중인 쿠폰", SwingConstants.CENTER);
		remainLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		remainLabel.setSize(160, 66);
		remainLabel.setLocation(0, 0);
		JLabel availLabel = new JLabel("등록 가능 쿠폰", SwingConstants.CENTER);
		availLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		availLabel.setSize(160, 66);
		availLabel.setLocation(0, 180);
		couponPanel.add(remainLabel);
		couponPanel.add(availLabel);

		remainPanel = new JPanel();
		availPanel = new JPanel();
		remainPanel.setLayout(null);
		availPanel.setLayout(null);
		remainScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		remainScroll.setViewportView(remainPanel);
		remainScroll.setSize(374, 160);
		remainScroll.setLocation(166, 20);
		availScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		availScroll.setViewportView(availPanel);
		availScroll.setSize(374, 300);
		availScroll.setLocation(166, 200);

		couponPanel.add(remainScroll);
		couponPanel.add(availScroll);

		add(couponPanel);

		setPreferredSize(new Dimension(YJHotel.FRAME_WIDTH, 660));
	}

	@Override
	public void onMount(Object param) {
		// 스크롤 위치를 0으로 초기화 => 최상단으로 스크롤 이동
		remainScroll.getVerticalScrollBar().setValue(0);
		availScroll.getVerticalScrollBar().setValue(0);
		updateCoupons();
	}

	// CouponDAO에서 DB를 가져와서 실제로 업데이트하도록 호출하는 메서드
	private void updateCoupons() {
		int u_number = YJHotel.getUser().number;
		ArrayList<CouponVO> coupons = CouponDAO.getRemainCoupons(u_number);
		if (coupons != null) {
			this.remainCoupons = coupons;
		}
		ArrayList<CouponVO> availCoupons = CouponDAO.getAvailCoupons(u_number);
		if (coupons != null) {
			this.availCoupons = availCoupons;
		}
		setItems(remainScroll, this.remainCoupons, false);
		setItems(availScroll, this.availCoupons, true);
	}

	// ScrollPane에 coupons를 반영하는 메서드
	public void setItems(JScrollPane targetScroll, ArrayList<CouponVO> coupons, boolean needButton) {
		// 쿠폰간의 간격이 8이다.
		final int PAD = 8;
		final int HEIGHT = 60;
		final int PAD_HEIGHT = PAD + HEIGHT;
		JComponent targetPanel = (JComponent) targetScroll.getViewport().getView();
		targetPanel.removeAll();
		for (int i = 0; i < coupons.size(); i++) {
			CouponVO coupon = coupons.get(i);
			int ACCUM_HEIGHT = PAD_HEIGHT * i;
			JComponent itemPanel = needButton ? new JButton() : new JPanel();
			// 버튼으로 쿠폰을 만들겠다.
			if (needButton) {
				// 몇번 버튼인지 확인하기위해 버튼에 이름을 지정
				itemPanel.setName("coupon-avail-" + i);
				// 클릭 이벤트를 받기위해 리스너 지정
				((JButton) itemPanel).addActionListener(this);
			}
			// 툴팁을 보여주기 위해 설정
			itemPanel.setToolTipText(coupon.getTooltip());
			itemPanel.setLayout(null);
			itemPanel.setBackground(new Color(250, 250, 250));
			itemPanel.setSize(340, 60);
			itemPanel.setLocation(PAD, ACCUM_HEIGHT + PAD);
 
			JLabel couponTitle = new JLabel(coupon.name);
			couponTitle.setSize(314, 20);
			couponTitle.setLocation(PAD, PAD);
			
			JLabel couponDate = new JLabel(coupon.getPeriod());
			couponDate.setSize(314, 20);
			couponDate.setLocation(PAD, 32);
			itemPanel.add(couponTitle);
			itemPanel.add(couponDate);
			targetPanel.add(itemPanel);
		}
		// 변경된 쿠폰을 전부 그리기 위해 필요한 높이를 계산하여 적용
		targetPanel.setPreferredSize(new Dimension(340, (PAD_HEIGHT * coupons.size()) + PAD));
		// 스크롤이 자식이 변경된걸 체크하고 다시 그리도록 실행
		targetScroll.repaint();
		targetScroll.revalidate();
	}

	private void registerCoupon(String serial) {
		CouponVO coupon = CouponDAO.getCouponWithSN(YJHotel.getUser().number, serial);
		if (coupon == null) {
			YJHotel.showAlert("시리얼 번호를 찾을 수 없습니다.");
			return;
		}

		if (coupon.serial_number == null) {
			YJHotel.showAlert("이미 보유중인 쿠폰입니다.");
			return;
		}

		boolean result = CouponDAO.insertCouponStatus(YJHotel.getUser().number, coupon.coupon_id);
		if (result) {
			String message = String.format("'%s' 쿠폰을 등록하였습니다.", coupon.name);
			YJHotel.showAlert(message, JOptionPane.INFORMATION_MESSAGE);
			updateCoupons();
		} else {
			YJHotel.showAlert("쿠폰을 등록할 수 없습니다.");
		}
	}

	private void registerAvailCoupon(int couponIndex) {
		CouponVO clickedCoupon = availCoupons.get(couponIndex);

		boolean result = YJHotel.showConfirm(clickedCoupon.name + "을 등록하시겠습니까?");
		if (result) {
			registerCoupon(clickedCoupon.serial_number);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 
		String name = ((JButton) e.getSource()).getName();
		if (name.equals("registerSerial")) {
			// 등록을 누른 순간 텍스트
			
			// 현재 입력된 값 가져오기
			String serial = serialField.getText();
			// 입력창 공백으로 초기화하기
			serialField.setText("");

			// 입력받은 시리얼 코드로 쿠폰 추가하기 호출
			registerCoupon(serial);
		}
		if (name.startsWith("coupon-avail")) {
			// 등록 가능한 쿠폰이 클릭됨.
			// 자른 후 숫자만 남기 때문에
			// "coupon-avail-숫자"에서 "coupon-avail-" 문자열의 길이만큼 자르기 위해 사용
			int couponIndex = Integer.parseInt(name.substring(13));

			registerAvailCoupon(couponIndex);
		}
	}
}