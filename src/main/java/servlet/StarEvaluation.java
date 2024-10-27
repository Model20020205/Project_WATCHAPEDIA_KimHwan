package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.DetailPageDao;

@WebServlet("/StarEvaluation")
public class StarEvaluation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 별점 구현 (INSERT)
		System.out.println("INSERT POST 요청 들어옴.");
		
		// 별점 가져오기
		int score = Integer.parseInt(request.getParameter("starValue"));
		System.out.println("별점 1개 클릭 시 = " + score);
		
		// 영화_IDX 가져오기
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		System.out.println("영화_IDX = " + movie_idx);
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		System.out.println("회원_IDX = " + mm_idx);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// 별점 클릭 시 값 INSERT
		try {
			dDao.starInsert(mm_idx, movie_idx, score);
		} catch(Exception e) { e.printStackTrace(); }
		
		// 별점 클릭 시 저장 메소드
		int starAvg = 0;
		try {
			starAvg = dDao.checkStarExists(mm_idx, movie_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		System.out.println("starAvg = " + starAvg);
		
		JSONObject obj = new JSONObject();
		obj.put("score", score);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(obj);
	}

}
