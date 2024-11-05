package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.CommentDto;
import dto.DetailPageDto;
import dto.MoreCommentDto;
import dto.StaffDto;

public class WatchaPediaDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// [왓챠피디아 상세 페이지 이동 Action]
		System.out.println("왓챠피디아 상세 페이지 이동 Action 도착 성공");
		
		// 영화_IDX 가져오기
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		System.out.println("영화_IDX = " + movie_idx);
		
		// 영화 상세 정보 부분
		ArrayList<DetailPageDto> showList = null;
		
		// 출연, 제작 리스트 전체 출력
		ArrayList<StaffDto> showStaff = null;
		
		// 영화 코멘트 부분(프로필, 닉네임, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
		ArrayList<CommentDto> showAll = null;
		
		// ArrayList의 참조변수에 null 값을 부여한 이유는 어떤 영화_IDX에서 가져오는지를 알기 위해서
		ArrayList<MoreCommentDto> showScroll = null;

		// 전체 코멘트 개수 출력
		int allCountComment = 1;
		
		// 임의 설정
		int pageNum = 1;
		
		try {
			// Dao 객체 생성
			DetailPageDao dDao = new DetailPageDao();
			showList = dDao.showList(movie_idx);
			showStaff = dDao.showStaff(movie_idx);
			showAll = dDao.showAll(movie_idx);
			showScroll = dDao.showScroll(movie_idx, pageNum);
			allCountComment = dDao.allCommentCount(movie_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("movie_idx", movie_idx);
		request.setAttribute("showList", showList);
		request.setAttribute("showStaff", showStaff);
		request.setAttribute("showAll", showAll);
		request.setAttribute("showScroll", showScroll);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("allCountComment", allCountComment);
		request.getRequestDispatcher("WatchaPedia_Detail_Page.jsp").forward(request, response);
	}

}
