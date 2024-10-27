package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DetailPageDao;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 로그인 Action
		System.out.println("로그인 Action 도착 성공");
		
		// 한글 인코딩 : Post방식은 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");	// 아이디 받아오기
		String password = request.getParameter("password");	// 비밀번호 받아오기
		
		DetailPageDao dDao = new DetailPageDao();
		int mm_idx = 0;	// 임시 설정
		try {
			mm_idx = dDao.loginCheck(id, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(mm_idx!=0) {	// mm_idx가 Dao에서 쿼리문을 실행시켰을 때 0이 아니라면 로그인 성공 및 Session에 저장
			HttpSession session = request.getSession();
			session.setAttribute("mm_idx", mm_idx);
			request.setAttribute("logintru", true);
			request.getRequestDispatcher("Controller?watcha=watcha_main_action").forward(request, response);
			System.out.println("로그인한 회원_IDX = " + mm_idx);	// mm_idx가 잘 넘어오는지 테스트
			System.out.println("로그인 성공");
		} else {
			mm_idx = 0;
			request.setAttribute("loginfal", false);
			request.getRequestDispatcher("WatchaPedia_Login.jsp").forward(request, response);
			System.out.println("로그인 실패");
		}
	}

}
