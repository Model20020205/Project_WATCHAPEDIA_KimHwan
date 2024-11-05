package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.RatedRDto;
import dto.RecentListDto;
import dto.RightUplDto;
import dto.ScoreDescDto;

public class WatchaPediaMainAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 메인 페이지 Action
		System.out.println("메인 페이지 이동 Action 성공");
		
		
		// 지금 뜨는 코멘트 
		ArrayList<RightUplDto> showRight = null;

		// 영화 메인 페이지 개봉연도 내림차순
		ArrayList<RecentListDto> showRecent = null;
		
		// 영화 메인 페이지 별점 순위 내림차순
		ArrayList<ScoreDescDto> showDesc = null;
		
		// 연령 설정
		int avg_idx = 1;
		
		// 영화 메인 페이지 연령별 순위
		ArrayList<RatedRDto> showAvg = null;
		
		try {
			// Dao 객체 생성
			DetailPageDao dDao = new DetailPageDao();
			showRight = dDao.showRight();
			showRecent = dDao.showRecent();
			showDesc = dDao.showScoDesc();
			showAvg = dDao.showRate(avg_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		request.setAttribute("showRight", showRight);
		request.setAttribute("showRecent", showRecent);
		request.setAttribute("showDesc", showDesc);
		request.setAttribute("showAvg", showAvg);
		
		request.getRequestDispatcher("WatchaPedia_Main.jsp").forward(request, response);
	}

}
