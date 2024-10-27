package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.CommentDetailDto;
import dto.ReplyDto;

public class WatchaPediaEvaUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 코멘트(=평가글) 수정 Action
		System.out.println("왓챠피디아 코멘트(=평가글) 수정 Action 도착 성공");
		
		// 코멘트(=평가글)_IDX 가져오기
		int eva_idx = Integer.parseInt(request.getParameter("eva_idx"));
		System.out.println("평가글_IDX = " + eva_idx);
		
		// 수정할 내용 가져오기
		String content = request.getParameter("eva_upd_content");
		System.out.println(content);
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		System.out.println("회원_IDX = " + mm_idx);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// 코멘트(=평가글) 상세 페이지 (프로필 사진, 닉네임, 영화 제목, 개봉 연도, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
		ArrayList<CommentDetailDto> showComInfo = new ArrayList<CommentDetailDto>();
		
		// 코멘트(프로필 사진, 닉네임, 댓글 내용, 댓글 작성일자, 댓글 좋아요 개수)
		ArrayList<ReplyDto> showReply = new ArrayList<ReplyDto>();
		
		try {
			dDao.updateComment(content, eva_idx, mm_idx);
			showComInfo = dDao.showComment(eva_idx);
			showReply = dDao.showReply(eva_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		request.setAttribute("showComInfo", showComInfo);
		request.setAttribute("showReply", showReply);
		request.getRequestDispatcher("Controller?watcha=watcha_comment_detail_action").forward(request, response);
	}

}
