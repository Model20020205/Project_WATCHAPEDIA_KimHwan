package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.MovieEvaStarDto;

public class WatchaPediaStarEvaAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 별점 평가한 영화 페이지
		System.out.println("별점 평가 Action 도착 성공");
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		System.out.println("회원_IDX = " + mm_idx);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// 영화 회원이 별점 평가한 영화 페이지 (포스터, 영화 제목, 별점)
		ArrayList<MovieEvaStarDto> showMovieStar = null;
		
		try {
			showMovieStar = dDao.showMovieStar(mm_idx);
		} catch (Exception e) { e.printStackTrace(); }
		
		request.setAttribute("showMovieStar", showMovieStar);
		request.getRequestDispatcher("WatchaPedia_Eva_Page.jsp").forward(request, response);
	}

}
