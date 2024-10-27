package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WatchaPediaMainFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 왓챠피디아 메인 페이지 이동 Action
		request.getRequestDispatcher("WatchaPedia_Main.jsp").forward(request, response);
	}

}
