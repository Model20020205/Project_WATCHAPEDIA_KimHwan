package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.CommentDetailDto;

public class WatchaPediaCommentInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 코멘트 댓글 작성 Action
		System.out.println("코멘트 댓글 작성 Aciton 도착 성공");
		
		// 한글 인코딩 설정 : Post방식은 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		
		// 평가글_IDX 가져오기
		int eva_idx = Integer.parseInt(request.getParameter("eva_idx"));
		
		// 댓글 내용 가져오기
		String reply_content = request.getParameter("reply_content");
		
		// 코멘트(=평가글) 상세 페이지 (프로필 사진, 닉네임, 영화 제목, 개봉 연도, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
		ArrayList<CommentDetailDto> showComInfo = null;
		
		// Dao 객체 생성
		try {
			DetailPageDao dDao = new DetailPageDao();
			dDao.writeReply(mm_idx, reply_content, eva_idx);
			showComInfo = dDao.showComment(eva_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("mm_idx", mm_idx);
		request.setAttribute("eva_idx", eva_idx);
		request.setAttribute("reply_content", reply_content);
		request.getRequestDispatcher("Controller?watcha=watcha_comment_detail_action").forward(request, response);
		
		// 테스트
		System.out.println("회원_IDX = " + mm_idx);
		System.out.println("평가글 내용 = " + reply_content);
		System.out.println("평가글_IDX = " + eva_idx);
		System.out.println("댓글 작성 Action 성공");
	}

}
