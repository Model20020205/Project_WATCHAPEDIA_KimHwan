package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.DetailPageDao;
import dto.WriteByMemberCommentDto;

@WebServlet("/ScrollServlet")
public class ScrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 무한 스크롤 적용 서블릿
		System.out.println("Post 방식 요청 들어옴");
		
		// pageNum 임의 설정
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		System.out.println("pageNum = " + pageNum);
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		System.out.println("mm_idx = " + mm_idx);
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		
		// 회원이 작성한 코멘트 전체 출력
		ArrayList<WriteByMemberCommentDto> showWriteByMember = new ArrayList<WriteByMemberCommentDto>();
		try {
			showWriteByMember = dDao.showWriteByMember(mm_idx, pageNum);
		} catch(Exception e) { e.printStackTrace(); }
		
		System.out.println("pageNum = " + pageNum);
		
		// JSONArray 객체 생성
		JSONArray array = new JSONArray();
		for(WriteByMemberCommentDto dto : showWriteByMember) {
			JSONObject obj = new JSONObject();
			obj.put("profile_img", dto.getProfile_img());
			obj.put("nickname", dto.getNickname());
			obj.put("score", dto.getScore());
			obj.put("poster", dto.getPoster());
			obj.put("movie_title", dto.getMovie_title());
			obj.put("release_year", dto.getRelease_year());
			obj.put("content", dto.getContent());
			obj.put("love_eva_idx", dto.getLove_eva_idx());
			obj.put("reply_idx", dto.getReply_idx());
			obj.put("eva_idx", dto.getEva_idx());
			obj.put("movie_idx", dto.getMovie_idx());
			
			array.add(obj); 
		}
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(array);
	}
}
