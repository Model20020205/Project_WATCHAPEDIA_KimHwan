package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetailPageDao;
import dto.ProfileDto;

public class WatchaPediaProfileAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 프로필 설정 Action
		System.out.println("프로필 설정 Action 도착 성공");
		
		// 회원_IDX 가져오기
		int mm_idx = Integer.parseInt(request.getParameter("mm_idx"));
		
		
		// 헤더 프로필 경로 설정
		String profile = "";
		
		// 프로필 설정 페이지
		ArrayList<ProfileDto> showProfile = null;
		
		try {
			// Dao 객체 생성
			DetailPageDao dDao = new DetailPageDao();
			profile = dDao.getProfileImg(mm_idx);
			showProfile = dDao.showProfile(mm_idx);
		} catch(Exception e) { e.printStackTrace(); }
		
		System.out.println(profile);
		
		request.setAttribute("profile_img", profile);
		request.setAttribute("showProfile", showProfile);
		request.getRequestDispatcher("WatchaPedia_Profile.jsp").forward(request, response);
	}

}
