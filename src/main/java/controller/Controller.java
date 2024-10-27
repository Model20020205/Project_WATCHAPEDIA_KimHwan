package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.LogOutAction;
import action.LoginAction;
import action.RegisterAction;
import action.WatchaPediaCommentDeleteAction;
import action.WatchaPediaCommentDetailAction;
import action.WatchaPediaCommentInsertAction;
import action.WatchaPediaCommentLikeAction;
import action.WatchaPediaCommentUpdateAction;
import action.WatchaPediaCommentWriteDateAction;
import action.WatchaPediaDetailAction;
import action.WatchaPediaEvaByMemberAction;
import action.WatchaPediaEvaDeleteAction;
import action.WatchaPediaEvaInsertAction;
import action.WatchaPediaEvaUpdateAction;
import action.WatchaPediaMainAction;
import action.WatchaPediaMakingAction;
import action.WatchaPediaProfileAction;
import action.WatchaPediaStarEvaAction;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 인코딩 설정 : Post방식은 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String watcha = request.getParameter("watcha");
		System.out.println("watcha = " + watcha);
		
		Action action = null;
		
		switch(watcha) {
		case "login_action" : action = new LoginAction(); break;	// 로그인 Aciton 기능 명세 (O)
		case "logout_action" : action = new LogOutAction(); break;	// 로그아웃 Aciton 기능 명세 (O)
		case "register_action" : action = new RegisterAction(); break;	// 회원가입 Aciton 기능 명세 (O)
		case "watcha_main_action" : action = new WatchaPediaMainAction(); break;	// 왓챠피디아 메인 페이지 Action 기능 명세 (O)
		case "watcha_detail_action" : action = new WatchaPediaDetailAction(); break;	// 왓챠피디아 영화 상세 페이지 Action 기능 명세 (O)
		case "watcha_making_action" : action = new WatchaPediaMakingAction(); break;	// 왓챠피디아 출연, 제작 상세 페이지 Action 기능 명세 (O)
		case "watcha_comment_detail_action" : action = new WatchaPediaCommentDetailAction(); break;	// 왓챠피디아 코멘트 상세 페이지 Action 기능 명세 (O)
		case "watcha_comment_insert_action" : action = new WatchaPediaCommentInsertAction(); break;	// 왓챠피디아 댓글 작성 Action
		case "watcha_comment_update_action" : action = new WatchaPediaCommentUpdateAction(); break;	// 왓챠피디아 댓글 수정 Action
		case "watcha_comment_delete_action" : action = new WatchaPediaCommentDeleteAction(); break; // 왓챠피디아 댓글 삭제 Action
		case "watcha_comment_like_action" : action = new WatchaPediaCommentLikeAction(); break;	// 왓챠피디아 좋아요 내림차순 코멘트 더보기 페이지 Action 기능 명세 (O)
		case "watcha_comment_write_date_action" : action = new WatchaPediaCommentWriteDateAction(); break;	// 왓챠피디아 작성일자 내림차순 코멘트 더보기 페이지 Action 기능 명세 (O)
		case "watcha_eva_insert_action" : action = new WatchaPediaEvaInsertAction(); break;	// 왓챠피디아 코멘트(=평가글) 작성 Action 기능 명세 (O)
		case "watcha_eva_update_action" : action = new WatchaPediaEvaUpdateAction(); break;	// 왓챠피디아 코멘트(=평가글) 수정 Action 기능 명세 (O)
		case "watcha_eva_delete_action" : action = new WatchaPediaEvaDeleteAction(); break;	// 왓챠피디아 코멘트(=평가글) 삭제 Action 기능 명세 (O)
		case "watcha_profile_action" : action = new WatchaPediaProfileAction(); break;	// 왓챠피디아 프로필 설정 이동 기능 명세 (O)
		case "watcha_star_eva_action" : action = new WatchaPediaStarEvaAction(); break;	// 왓챠피디아 회원이 별점 평가한 영화 페이지 기능 명세 (O)
		case "watcha_eva_write_action" : action = new WatchaPediaEvaByMemberAction(); break;	// 왓챠피디아 회원이 코멘트 작성한 전체 페이지 기능 명세 (O)
		}
		
		action.execute(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
