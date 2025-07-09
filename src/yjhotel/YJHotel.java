package yjhotel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import yjhotel.frame.Login;
import yjhotel.model.UserVO;

// 페이지 라우트 히스토리 기록용 클래스
class RouteHistory {
	private String path;
	private Object param;

	public RouteHistory(String path, Object param) {
		this.path = path;
		this.param = param;
	}

	public String getPath() {
		return path;
	}

	public Object getParam() {
		return param;
	}

}

public class YJHotel extends JFrame {
	/* 
	 * =========================================
	 *  YJHotel의 기능을 담당하는 static 필드/메서드 영역
	 * =========================================
	 * */
	private static final long serialVersionUID = -3311882568429453065L;
	// 싱글턴 인스턴스 방법을 사용하였습니다.
	public static YJHotel instance = null;
	
	// 공용 JFrame 실제 영역 너비
	public static final int FRAME_WIDTH = 550;
	
	// DB 연결 정보
	public static final String FRAME_TITLE = "영진숙소 예약 프로그램";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/yjhotell";
	private static final String DB_ID = "root";
	private static final String DB_PASSWD = "1234";

	// DB 관련 데이터
	private static Connection conn = null;
	private static UserVO user = null;

	// 라우팅 관련 데이터
	private static boolean isFullScreen = false;
	private static String defaultPath = null;
	private static Map<String, Page> routes = new HashMap<>();
	private static Thread mountThread;

	private static ArrayList<RouteHistory> routeHistory = new ArrayList<>();

	// 싱글턴으로 인스턴스가 생성되었는지 체크 후 없으면 생성
	private static void checkInstance() {
		if (instance == null) {
			launch();
		}
	}

	// 현재 JFrame을 닫고, 로그인 창 띄우기
	public static void logout() {
		exit();
		launchLogin();
	}

	// 로그인 Frame 열기
	public static void launchLogin() {
		new Login();
	}

	// 현재 JFrame이 있으면 닫고, 초기화
	public static void exit() {
		if (instance != null) {
			instance.dispose();
			instance = null;
			isFullScreen = false;
			user = null;
		}
	}

	// 쓰레드를 통해 비동기 적으로 DB 연결
	public static void connectAsync() {
		new Thread(() -> connect()).start();
	}

	// DB 연결하는 메소드
	private static boolean connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PASSWD);
			return true;
		} catch (ClassNotFoundException e) {
			showAlert("데이터 베이스를 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			showAlert("데이터 베이스에 연결할 수 없습니다.");
			e.printStackTrace();
		}
		return false;
	}

	// 공용 DB 커넥션 getter
	public static Connection getConnection() {
		if (conn == null) {
			connect();
		}
		return conn;
	}

	public static boolean isConnected() {
		return conn != null;
	}

	// 유저 로그인 정보 getter/setter
	public static void setUser(UserVO user) {
		YJHotel.user = user;
	}

	public static UserVO getUser() {
		return YJHotel.user;
	}

	// 라우트 추가 함수 path가 들어오면 page가 JFrame에 추가되도록
	public static void addRoute(String path, Page page) {
		routes.put(path, page);
	}

	// 파라미터가 없는 라우트 메서드, 빈 새 객체를 파라미터로 사용
	public static void route(String path) {
		route(path, new Object());
	}

	// 파라미터를 받는 라우트 메서드
	public static void route(String path, Object object) {
		checkInstance();

		// 라우팅 목록에 path가 없으면 문자열 출력, 만약 없으면 오류 발생
		if (!routes.containsKey(path)) {
			System.out.println("Route path not found");
		}

		// 라우팅 목록에서 페이지를 가져오고 공용 JFrame에 업데이트
		Page page = routes.get(path);
		instance.updateContent(path, page, object);
	}

	// 기본 시작 path 설정
	public static void setDefaultPath(String path) {
		YJHotel.defaultPath = path;
	}

	// 페이지가 이동할 때 이동한 라우트 히스토리 추가
	public static void addHistory(RouteHistory history) {
		routeHistory.add(history);
		// 최대 10개까지만 저장
		if (routeHistory.size() > 10) {
			routeHistory.remove(0);
		}
	}

	// 뒤로가기 메서드
	public static void goBack() {
		// 뒤로가기 히스토리가 있는 경우
		if (routeHistory.size() > 1) {
			// 현재 라우트과 이전 라우트을 지우고
			RouteHistory history = routeHistory.get(routeHistory.size() - 2);
			routeHistory.remove(routeHistory.size() - 2);
			routeHistory.remove(routeHistory.size() - 1);
			// 이전 라우트 기준으로 다시 라우트함
			route(history.getPath(), history.getParam());
		}
	}

	// 공용 JFrame에 종속되는 Yes/No 컨펌 띄우기
	public static boolean showConfirm(String message) {
		checkInstance();
		int result = JOptionPane.showConfirmDialog(instance, message, FRAME_TITLE, JOptionPane.YES_NO_OPTION);
		return result == JOptionPane.YES_OPTION;
	}

	// 공용 JFrame에 종속되는 오류 앨럿 띄우기
	public static void showAlert(String message) {
		checkInstance();
		JOptionPane.showMessageDialog(instance, message, FRAME_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	// 공용 JFrame에 종속되는 사용자 지정 메세지 앨럿 띄우기
	public static void showAlert(String message, int messageType) {
		checkInstance();
		JOptionPane.showMessageDialog(instance, message, FRAME_TITLE, messageType);
	}

	// 지정한 JFrame에 종속되는 사용자 지정 메세지 앨럿 띄우기
	public static void showAlert(JFrame frame, String message, int messageType) {
		JOptionPane.showMessageDialog(frame, message, FRAME_TITLE, messageType);
	}

	// JFrame 인스턴스 생성하기
	public static void launch() {
		// 이미 인스턴스가 있으면 없애기
		if(instance != null) {
			exit();
		}
		// 인스턴스 생성 후 기본 path로 이동
		instance = new YJHotel();
		YJHotel.route(defaultPath);
	}

	/* 
	 * ======================================
	 *  공용 JFrame 사용될 instance 필드/메서드 영역 
	 * ======================================
	 * */
	
	// TopPanel과 실제 content 영역 패널
	public JPanel contentPane = new JPanel();
	private TopPanel top = new TopPanel();

	private YJHotel() {
		setLayout(new BorderLayout());
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		add(top, BorderLayout.NORTH);
		add(contentPane, BorderLayout.CENTER);

		// 600,800의 크기로 프레임 크기 지정
		setTitle(FRAME_TITLE);
		
		// 기본 닫기 옵션을 종료로 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 800);
		setResizable(false);
		// 관계형 위치 지정자를 없애 화면 중앙에 오도록 설정
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// 실제 contentPane에 Page를 업데이트 하는 메서드
	public void updateContent(String path, Page page, Object param) {
		// Loading 애니메이션을 위해 컨텐츠를 임시로 Loading 패널로 변경
		Loading loading = new Loading();
		contentPane.removeAll();
		contentPane.add(loading);
		contentPane.repaint();
		contentPane.revalidate();
		
		// Page가 그려지는 onMount/repaint/revalidate의 과정을 비동기로 실행하기 위한 쓰레드
		mountThread = new Thread(() -> {
			// 탑 패널과 JFrame의 타이틀 변경
			top.setTitle(page.getLabel());
			setTitle(page.getTitle());
			
			// 라우트 히스토리에 현재 이동하는 페이지 추가
			RouteHistory history = new RouteHistory(path, param);
			addHistory(history);

			// 페이지의 onMount/repaint/revalidate 실행
			// onMount의 경우 DB를 긁어오고 새로 JComponent들을 생성하는 과정에서 다소 시간이 걸리기 때문에 쓰레드로 처리
			page.onMount(param);
			page.revalidate();
			page.repaint();

			// 만약 현재 로드된 페이지과 마지막 history가 다르면 로드 중 페이지가 이동했으므로 쓰레드는 처럼 interrupt 처리를 위해 return
			if (history != routeHistory.get(routeHistory.size() - 1)) {
				System.out.println("Mount Thread interrupted");
				return;
			}

			// onMount/repaint/revalidate 과정이 끝나면 Loading 패널을 지우고 Page에 그려진 패널 추가
			contentPane.removeAll();
			contentPane.add(page);
			contentPane.repaint();
			contentPane.revalidate();
			mountThread = null;
		});
		mountThread.start();

		// 페이지의 풀스크린 여부를 체크하여 TopPanel의 그려짐 여부 처리
		toggleFullScreen(page.isFullScreen());
	}

	// 플래그에 따라 풀스크린으로 토글하는 메서드
	public void toggleFullScreen(boolean isFullScreen) {
		// 새로운 플래그와 현재 풀스크린 여부가 다르면
		if (isFullScreen != YJHotel.isFullScreen) {
			if (isFullScreen) {
				// 풀스크린으로 변경
				remove(top);
			} else {
				// TopPanel 추가
				add(top, BorderLayout.NORTH);
			}
			// 현재 풀스크린 여부를 변경하고, 재검증 처리
			YJHotel.isFullScreen = isFullScreen;
			revalidate();
		}
	}

}
