package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;

public class RegisterAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 회원가입 Action
		System.out.println("회원가입 Action 도착 성공");
		
		// 한글 인코딩 : Post방식은 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String nickname = request.getParameter("nickname");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		DetailPageDao dDao = new DetailPageDao();
		try {
			dDao.registerCheck(nickname, id, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 저장된 id, nickname, password 테스트
		System.out.println(nickname);
		System.out.println(id);
		System.out.println(password);
		
		request.setAttribute("nickname", nickname);
		request.setAttribute("id", id);
		request.setAttribute("password", password);

		request.getRequestDispatcher("Controller?watcha=watcha_main_form").forward(request, response);
		
		// 테스트
		System.out.println("회원가입 IDX 전달 성공");
	}

}
