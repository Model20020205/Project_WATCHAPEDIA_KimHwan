package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 로그아웃 Action
		System.out.println("로그아웃 Action 도착 성공");
		
		// 한글 인코딩 : Post방식은 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);	// "세션이 없으면 그냥 넘어가" 라는 의미
		Integer mm_idx = (Integer) session.getAttribute("mm_idx");
		System.out.println("로그아웃 하려는 회원_IDX = " + mm_idx);
		
		if(session!=null) {
			if(mm_idx==null) {
				System.out.println("로그아웃하는 회원_IDX = " + mm_idx);
			}
			session.invalidate();	// 로그아웃하는 메소드
		}
		
		request.setAttribute("logOut", true);	// 로그아웃 시 alert를 띄워주기 위해서 설정
		request.getRequestDispatcher("Controller?watcha=watcha_main_action").forward(request, response);
		
		// 테스트
		System.out.println("로그아웃 성공");
	}

}
