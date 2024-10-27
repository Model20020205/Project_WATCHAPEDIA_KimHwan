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

@WebServlet("/RegisterDuplicationCheck")
public class RegisterDuplicationCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원가입 아이디 중복 체크
		
		System.out.println("POST 요청 들어옴.");
		
		// 회원가입 아이디 가져오기
		String id = request.getParameter("user_id");
		
		System.out.println("id = " + id);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// 임의 설정
		int result = 0;
		
		try {
			result = dDao.registerDuplication(id);
		} catch (Exception e) { e.printStackTrace(); }
		
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		System.out.println(result);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(obj);
	}

}
