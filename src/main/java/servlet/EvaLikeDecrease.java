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
import dto.CommentDetailDto;

@WebServlet("/EvaLikeDecrease")
public class EvaLikeDecrease extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 평가글 좋아요 감소 Ajax
		System.out.println("평가글 Ajax 요청 들어옴");
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		
		System.out.println("좋아요를 누른 회원_IDX = " + mm_idx);
		
		// 평가글_IDX 가져오기
		int eva_idx = Integer.parseInt(request.getParameter("eva_idx"));
		
		System.out.println("평가글_idx = " + eva_idx);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// 코멘트(=평가글) 상세 페이지 (프로필 사진, 닉네임, 영화 제목, 개봉 연도, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
		ArrayList<CommentDetailDto> showComInfo = new ArrayList<CommentDetailDto>();
		
		// 평가글 좋아요 클릭 시 감소 DELETE문
		try {
			dDao.evaDecrease(eva_idx, mm_idx);
			showComInfo = dDao.showComment(eva_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		System.out.println(mm_idx + ", " + eva_idx);
		
		// 업데이트된 좋아요 개수 가져오기
		int love_eva_idx = 1;
		try {
			love_eva_idx = dDao.evaCount(eva_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		// 업데이트 됐는지 체크하는 메소드
		boolean checkEva = false;
		try {
			checkEva = dDao.checkEva(eva_idx, mm_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		System.out.println(checkEva);
		
		JSONObject obj = new JSONObject();
		obj.put("love_eva_idx", love_eva_idx);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(obj);
	}

}
