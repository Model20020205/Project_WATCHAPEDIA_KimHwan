package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.MakingMovieDto;
import dto.MakingPersonDto;

public class WatchaPediaMakingAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인물 상세 페이지 Action
		
		// 한글 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		// 인물_IDX 가져오기
		int person_idx = Integer.parseInt(request.getParameter("person_idx"));
		
		// 출연, 제작 상세 페이지 (인물 사진, 인물 이름, 역할 , 좋아요 개수)
		ArrayList<MakingPersonDto> showPerson = null;
		
		// 출련, 제작 상세 페이지 (개봉 연도, 포스터, 영화 제목, 역할 , 별점)
		ArrayList<MakingMovieDto> showMovieInfo = null;
		
		// Dao 객체 생성
		DetailPageDao dDao = new DetailPageDao();
		try {
			showPerson = dDao.showPersonInfo(person_idx);
			showMovieInfo = dDao.showMovieInfo(person_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("person_idx", person_idx);
		request.setAttribute("showPerson", showPerson);
		request.setAttribute("showMovieInfo", showMovieInfo);
		request.getRequestDispatcher("WatchaPedia_Making_Detail.jsp").forward(request, response);
		
		// 테스트
		System.out.println("인물 상세 페이지 출력 성공");
	}

}
