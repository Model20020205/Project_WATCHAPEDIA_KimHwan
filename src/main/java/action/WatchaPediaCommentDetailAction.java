package action;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.CommentDetailDto;
import dto.ReplyDto;

public class WatchaPediaCommentDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 코멘트 상세 페이지 Action
		System.out.println("코멘트 상세 페이지 Action 도착 성공");
		
		// 한글 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		// 평가글_IDX 가져오기
		int eva_idx = Integer.parseInt(request.getParameter("eva_idx"));
		
		// 코멘트(=평가글) 상세 페이지 (프로필 사진, 닉네임, 영화 제목, 개봉 연도, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
		ArrayList<CommentDetailDto> showComInfo = new ArrayList<CommentDetailDto>();

		// 코멘트(프로필 사진, 닉네임, 댓글 내용, 댓글 작성일자)
		ArrayList<ReplyDto> showReply = new ArrayList<ReplyDto>();
		
		// Dao객체 생성
		DetailPageDao dDao = new DetailPageDao();
		try {
			showComInfo = dDao.showComment(eva_idx);
			showReply = dDao.showReply(eva_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("eva_idx", eva_idx);
		request.setAttribute("showComInfo", showComInfo);
		request.setAttribute("showReply", showReply);
		request.getRequestDispatcher("WatchaPedia_Comment_Detail.jsp").forward(request, response);

		// 테스트
		System.out.println("평가글_IDX = " + eva_idx);
		System.out.println("코멘트 상세 페이지 출력 성공");
	}

}
