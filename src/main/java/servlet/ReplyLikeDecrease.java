package servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.DetailPageDao;
import dto.ReplyDto;

@WebServlet("/ReplyLikeDecrease")
public class ReplyLikeDecrease extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 댓글 좋아요 Ajax 감소 서블릿
		System.out.println("Ajax 요청 들어옴");
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		System.out.println("좋아요 취소한 회원_IDX = " + mm_idx);
		
		// 댓글_IDX 가져오기
		int reply_idx = Integer.parseInt(request.getParameter("reply_idx"));
		System.out.println("댓글_IDX = " + reply_idx);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// 코멘트(프로필 사진, 닉네임, 댓글 내용, 댓글 작성일자, 댓글 좋아요 개수)
		ArrayList<ReplyDto> showReply = new ArrayList<ReplyDto>();
		
		// 좋아요 취소 기능 메소드 실행
		try {
			dDao.replyLikeDecrease(reply_idx, mm_idx);
			showReply = dDao.showReply(reply_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		// 업데이트된 좋아요 개수 가져오기
		int reply_like_idx = 1;
		try {
			reply_like_idx = dDao.replyCount(reply_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		// 좋아요 클릭 시 업데이트가 됐는지 체크하는 메소드
		boolean checkLike = false;
		try {
			checkLike = dDao.checkLike(reply_idx, mm_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		System.out.println("checkLike = " + checkLike);
		
		JSONObject obj = new JSONObject();
		obj.put("reply_like_idx", reply_like_idx);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(obj);
		
		System.out.println("Ajax 요청 성공");
	}

}
