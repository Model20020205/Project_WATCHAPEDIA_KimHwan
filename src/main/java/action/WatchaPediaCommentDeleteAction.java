package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.CommentDetailDto;
import dto.ReplyDto;

public class WatchaPediaCommentDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 댓글 삭제 Action
		System.out.println("댓글 삭제 Action 도착 성공");
		
		// 한글 인코딩 : Post방식은 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		// 평가글_IDX 가져오기
		int eva_idx = Integer.parseInt(request.getParameter("eva_idx"));
		
		// 테스트
		System.out.println("평가글_IDX = " + eva_idx);
		
		// 댓글_IDX 가져오기
		int reply_idx = Integer.parseInt(request.getParameter("reply_idx"));
		
		// 테스트
		System.out.println("댓글_IDX = " + reply_idx);
		
		// 코멘트(=평가글) 상세 페이지 (프로필 사진, 닉네임, 영화 제목, 개봉 연도, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
		ArrayList<CommentDetailDto> showComInfo = null;
		
		// 코멘트(프로필 사진, 닉네임, 댓글 내용, 댓글 작성일자)
		ArrayList<ReplyDto> showReply = null;
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		try {
			dDao.deleteReply(reply_idx);
			showComInfo = dDao.showComment(eva_idx);
			showReply = dDao.showReply(eva_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("showComInfo", showComInfo);
		request.setAttribute("showReply", showReply);
		request.getRequestDispatcher("Controller?watcha=watcha_comment_detail_action").forward(request, response);
		
		// 테스트
		System.out.println("댓글 삭제 성공");
	}

}
