package yjhotel;

import yjhotel.model.UserVO;
import yjhotel.page.CouponPage;
import yjhotel.page.DateReservation;
import yjhotel.page.MainPage;
import yjhotel.page.MyPage;
import yjhotel.page.Notice;
import yjhotel.page.PayInfo;
import yjhotel.page.Rank;
import yjhotel.page.RoomInfo;
import yjhotel.page.Search;

public class Main {
	// 디버깅 과정에서 로그인을 스킵할지 여부
	// jar로 내보낼 땐 false 필수
	private static final boolean DEBUG_PASS_LOGIN = false;

	public static void main(String[] args) {
		// DB connection 이 오래걸리는 PC를 위해 쓰레드로 미리 실행
		YJHotel.connectAsync();
		try {
			// 사용할 페이지 인스턴스 생성
			MainPage mainpage = new MainPage();
			DateReservation reservation = new DateReservation();
			MyPage mypage = new MyPage();
			CouponPage couponPage = new CouponPage();
			Search search = new Search();
			Rank rank = new Rank();
			Notice notice = new Notice();
			RoomInfo roomInfo = new RoomInfo();
			PayInfo payInfo = new PayInfo();

			/*
			 * YJHotel.addRoute("home", home); 으로 등록 시 YJHotel.route("home"); 으로 이동 가능.
			 * 
			 * 이동에 파라미터가 필요한 경우 YJHotel.route("home", 파라미터) 형태로 사용가능.
			 */
			YJHotel.addRoute("mainpage", mainpage);
			YJHotel.addRoute("reservation", reservation);
			YJHotel.addRoute("mypage", mypage);
			YJHotel.addRoute("coupon", couponPage);
			YJHotel.addRoute("search", search);
			YJHotel.addRoute("rank", rank);
			YJHotel.addRoute("notice", notice);
			YJHotel.addRoute("roominfo", roomInfo);
			YJHotel.addRoute("payinfo", payInfo);

			// 기본 화면 home으로 라우팅
			YJHotel.setDefaultPath("mainpage");

			if (DEBUG_PASS_LOGIN) {
				UserVO user = new UserVO();
				user.id = "gildong";
				user.number = 1;
				YJHotel.setUser(user);
				// 실제 프레임 실행 부, 싱글턴 패턴이 적용되어 있음.
				YJHotel.launch();
			} else {
				// 로그인 창 실행
				YJHotel.launchLogin();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}