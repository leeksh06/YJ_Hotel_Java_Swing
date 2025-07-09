package yjhotel;

import javax.swing.JPanel;

// JFrame을 대체할 JPanel을 상속받는 Page 클래스
public class Page extends JPanel {
	private static final long serialVersionUID = -2501202385287063475L;
	// 공유 JFrame의 타이틀
	private String title = "";
	// TopPanel 에서 보여줄 레이블
	private String label = "";
	// TopPanel 숨김 여부
	private boolean isFullScreen = false;

	public Page(String label, String title) {
		this.title = title;
		this.label = label;
	}
	
	public Page(String label, String title, boolean isFullScreen) {
		this.title = title;
		this.label = label;
		this.isFullScreen = isFullScreen;
	}

	public String getTitle() {
		return title;
	}

	public String getLabel() {
		return label;
	}
	
	public boolean isFullScreen() {
		return isFullScreen;
	}

	// 생성자 대신 페이지가 열렸을 때를 가정하여 호출됨.
	// route("패스명", 파라미터) 형태로 호출될 때 Object param에 파라미터가 할당됨.
	public void onMount(Object param) {
		/* 
		 * 해당 param은 적절하게 cast 하여 사용
		 * 
		 * ex)
		 * 
		 * // DataReservation 에 넘겨줄 파라미터 생성
		 * DataReservation.Param params = castParam(param);
		 * params.u_id = u_id;
		 * params.r_number = r_number;
		 * // 파라미터와 함께 reservation 호출
		 * YJHotel.route("reservation", params);
		 */
	}

	// 파라미터 캐스팅 함수
	public <T> T castParam(Object o) {
		try {
			// warning을 지우기 위해 사용
			@SuppressWarnings("unchecked")
			
			// 상속받는 페이지가 가지고 있는 static Param 클래스를 기준으로 캐스팅
			T t = (T) o;
			return t;
		} catch (ClassCastException e) {
			// 캐스팅에 실패한 경우 오류 출력
			e.printStackTrace();
		}
		return null;
	}
}
