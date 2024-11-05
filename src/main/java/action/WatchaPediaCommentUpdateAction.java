package action;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;

public class WatchaPediaCommentUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 코멘트 댓글 수정 Action
		System.out.println("코멘트 댓글 수정 Action 도착 성공");
		
		// 한글 인코딩 : Post방식은 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		// 댓글 내용 가져오기
		String reply_content = request.getParameter("reply_upd_content");
		
		// 댓글_IDX 가져오기
		int reply_idx = Integer.parseInt(request.getParameter("reply_idx"));
		System.out.println("reply_idx" + reply_idx);
		
		// 평가글_IDX 가져오기
		int eva_idx = Integer.parseInt(request.getParameter("eva_idx"));
		
		// Dao 객체 생성
		try {
			DetailPageDao dDao = new DetailPageDao();
			dDao.updateReply(reply_content, reply_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// 테스트
		System.out.println("댓글 내용 수정 = " + reply_content);
		System.out.println("댓글_IDX = " + reply_idx);
		
		request.setAttribute("reply_content", reply_content);
		request.setAttribute("reply_idx", reply_idx);
		request.setAttribute("eva_idx", eva_idx);
		
		request.getRequestDispatcher("Controller?watcha=watcha_comment_detail_action").forward(request, response);
				
	}
}
