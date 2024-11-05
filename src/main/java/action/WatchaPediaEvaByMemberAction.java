package action;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.WriteByMemberCommentDto;

public class WatchaPediaEvaByMemberAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 회원이 작성한 코멘트(=평가글) 전체 출력 Action
		System.out.println("코멘트(=평가글) 전체 출력 Aciton 도착 성공");
		
		// pageNum 임의 설정
		int pageNum = 1;
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		
		
		// 임의 설정
		int lastPageNum = 0;
		
		// 회원이 작성한 코멘트 전체 출력
		ArrayList<WriteByMemberCommentDto> showWriteByMember = null;
		try {
			// Dao 객체 생성
			DetailPageDao dDao = new DetailPageDao();
			showWriteByMember = dDao.showWriteByMember(mm_idx, pageNum);
			lastPageNum = dDao.getLastScrollNumber(mm_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		System.out.println("lastPageNum = " + lastPageNum);
		
		request.setAttribute("showWriteByMember", showWriteByMember);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("lastPageNum", lastPageNum);
		request.getRequestDispatcher("WatchaPedia_Eva_Comment_Mine.jsp").forward(request, response);
	}

}
