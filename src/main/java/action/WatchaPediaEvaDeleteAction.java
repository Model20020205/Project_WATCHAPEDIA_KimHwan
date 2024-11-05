package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;

public class WatchaPediaEvaDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 코멘트(=평가글) 삭제 Action
		System.out.println("코멘트(=평가글) 삭제 Action 도착 성공");
		
		// 평가글_IDX 가져오기
		int eva_idx = Integer.parseInt(request.getParameter("eva_idx"));
		System.out.println("평가글_IDX = " + eva_idx);
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		System.out.println("회원_IDX = " + mm_idx);
		
		
		try {
			// Dao 객체 생성
			DetailPageDao dDao = new DetailPageDao();
			dDao.deleteComment(eva_idx, mm_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		request.getRequestDispatcher("Controller?watcha=watcha_main_action").forward(request, response);
	}

}
