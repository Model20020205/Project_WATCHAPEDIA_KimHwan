package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.MoreComWriteDto;

public class WatchaPediaCommentWriteDateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 작성일자 내림차순 코멘트 더보기 Action
		System.out.println("코멘트 더보기 작성일자 내림차순 도착");
		
		// 한글 인코딩 설정 : Post방식은 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		// 영화_IDX 가져오기
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		System.out.println("영화_IDX = " + movie_idx);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// pageNum 설정
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		} catch(Exception e) { e.printStackTrace(); }
		System.out.println("pageNum = " + pageNum);
		
		// lastPageNum 설정
		int lastPageNum = 3;
		try {
			lastPageNum = dDao.getLastPageNum(movie_idx);
			System.out.println("lastPageNum = " + lastPageNum);
		} catch(Exception e) { e.printStackTrace(); }
		
		ArrayList<MoreComWriteDto> showWrite = new ArrayList<MoreComWriteDto>();
		try {
			showWrite = dDao.showWrite(movie_idx, pageNum);
		} catch(Exception e) { e.printStackTrace(); }
		
		// 좋아요 순인지 작성 순인지 구분할 수 있게 하는 변수 저장
		// 0이면 좋아요 순서 1이면 작성 순서
		int sunseo = 1;
		
		request.setAttribute("movie_idx", movie_idx);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("lastPageNum", lastPageNum);
		request.setAttribute("showWrite", showWrite);
		request.setAttribute("sunseo", sunseo);
		
		request.getRequestDispatcher("WatchaPedia_More_List_Comment_Desc.jsp").forward(request, response);
	}

}
