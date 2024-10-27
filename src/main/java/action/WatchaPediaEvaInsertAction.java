package action;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.CommentDto;

public class WatchaPediaEvaInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 코멘트(=평가글) 작성 Action
		System.out.println("평가글 작성 Action 도착 성공");
		
		// 영화_IDX 가져오기
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		System.out.println("영화_IDX = " + movie_idx);
		
		// 평가글 내용 가져오기
		String content = request.getParameter("content");
		System.out.println("평가글 내용 = " + content);
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		System.out.println("회원_IDX = " + mm_idx);
		
		// 영화 코멘트 부분
		ArrayList<CommentDto> showAll = new ArrayList<CommentDto>();
		
		
		DetailPageDao dDao = new DetailPageDao();
		try {
			dDao.insertComment(movie_idx, content, mm_idx);
			showAll = dDao.showAll(movie_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("showAll", showAll);
		
		request.getRequestDispatcher("Controller?watcha=watcha_detail_action").forward(request, response);
		
		// 테스트
		System.out.println("영화_IDX = " + movie_idx);
		System.out.println("내용 = " + content);
		System.out.println("회원_IDX = " + mm_idx);
	}

}
