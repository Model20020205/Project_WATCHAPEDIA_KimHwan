<%@page import="dao.DetailPageDao"%>
<%@page import="dto.ProfileDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//Dao 객체 생성
	DetailPageDao dDao = new DetailPageDao();
	ArrayList<ProfileDto> showProfile = (ArrayList<ProfileDto>) request.getAttribute("showProfile");
%>

<%	
	// 로그인한 회원_IDX 가져오기
	Integer mm_idx = (Integer) session.getAttribute("mm_idx");
	
	// 로그인하지 않았을 때 회원_IDX 0으로 설정
	if(mm_idx==null) {
		mm_idx = 0;
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>왓챠피디아 프로필 설정</title>
	<link rel="stylesheet" href="WatchaPedia_Profile.css">
	<!-- jQuery 사용 -->
	<script>
		
	</script>
</head>
<body>
	<div class="main_screen">
		<!-- 로그인 헤더 -->
		<header class="header_box">
			<div class="header">
				<div class="inner_header">
					<div class="watcha_logo">
						<!-- 왓챠피디아 로고 클릭 시 메인페이지 이동 -->
						<a class="watcha_logo_box" href="Controller?watcha=watcha_main_action">
							<img src="img/watcha.png">
						</a>
						<!-- 왓챠피디아 로고 클릭 시 메인페이지 이동 여기까지 -->
					</div>
					<div class="right_box">
						<div class="search_box">
							<input type="text" placeholder="영화, 인물 유저를 검색해보세요.">
						</div>	
						<% if(mm_idx==0) { %>
							<!-- 로그인 페이지로 이동 -->
							<a class="login_button" href="WatchaPedia_Login.jsp">로그인</a>
							<script>
								$(function() {
						            // 로그아웃 상태 체크
						            <% if (request.getAttribute("logOut") != null) { %>
						                localStorage.setItem('logOutCheck', 'true'); // 로그아웃 상태를 localStorage에 저장
						            <% } %>
	
						            // localStorage에 loggedOut 값이 있는지 체크
						            if (localStorage.getItem('logOutCheck')) {
						                alert("로그아웃 되었습니다."); // 알림 띄우기
						                localStorage.removeItem('logOutCheck'); // 알림 후 값 삭제
						            }
						        });
						    </script>
							<!-- 로그인 페이지로 이동 여기까지 -->
						<% } else { %>
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="logout_action">
								<button type="submit" class="login_button">로그아웃</button>
							</form>
						<% } %>
						<!-- 회원가입 페이지 이동 -->
						<a class="register_button" href="WatchaPedia_Register.jsp">회원가입</a>
						<!-- 회원가입 페이지 이동 여기까지 -->
					<!-- 프로필 사진 -->
					<% if(mm_idx!=0) { %>
						<form action="<%=request.getContextPath() %>/Controller" method="post">
							<input type="hidden" name="watcha" value="watcha_profile_action">
							<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
							<button class="profile_move">
								<% if(dDao.getProfileImg(mm_idx)==null) { %>
									<img src="data:image/png;base64,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" class="profile_img">
								<% } else {%>
									<img src="<%=dDao.getProfileImg(mm_idx) %>" class="profile_img">
								<% } %>	
							</button>
						</form>
					<% } %>	
					</div>
				</div>
			</div>
		</header>
		<!-- 로그인 헤더 여기까지 -->
		<div class="middle_screen">
			<div class="middle_content">
				<div class="profile_info_box">
					<div class="profile_info_inner_box">
						<div class="edit_button_box">
							<div class="edit_button_div">
								<svg class="edit_button" width="28" height="28" viewBox="0 0 28 28" fill="none"><path d="M12.4777 4.19995H15.523C15.5674 4.19995 15.6065 4.22921 15.619 4.2718L16.4305 7.0375C16.4394 7.06784 16.4622 7.09214 16.4918 7.10331C17.2634 7.39482 17.9728 7.81784 18.5938 8.34578C18.6184 8.36666 18.6514 8.37469 18.6827 8.36712L21.4294 7.70323C21.4729 7.69273 21.518 7.71234 21.54 7.75126L23.0646 10.4511C23.0859 10.4889 23.0805 10.5361 23.051 10.568L21.112 12.6717C21.0909 12.6946 21.0817 12.726 21.0869 12.7566C21.1542 13.1608 21.1893 13.5762 21.1893 14C21.1893 14.4237 21.1542 14.8391 21.0869 15.2433C21.0817 15.2739 21.0909 15.3053 21.112 15.3282L23.051 17.4319C23.0805 17.4638 23.0859 17.511 23.0646 17.5488L21.54 20.2486C21.518 20.2876 21.4729 20.3072 21.4294 20.2967L18.6827 19.6328C18.6514 19.6252 18.6184 19.6332 18.5938 19.6541C17.9728 20.1821 17.2634 20.6051 16.4918 20.8966C16.4622 20.9078 16.4394 20.9321 16.4305 20.9624L15.619 23.7281C15.6065 23.7707 15.5674 23.7999 15.523 23.7999H12.4777C12.4333 23.7999 12.3943 23.7707 12.3818 23.7281L11.5703 20.9624C11.5614 20.9321 11.5386 20.9078 11.509 20.8966C10.7373 20.6051 10.028 20.1821 9.40699 19.6541C9.38243 19.6332 9.3494 19.6252 9.31807 19.6328L6.57135 20.2967C6.5279 20.3072 6.48276 20.2876 6.46078 20.2486L4.93619 17.5488C4.91485 17.511 4.92033 17.4638 4.94974 17.4319L6.88877 15.3282C6.90984 15.3053 6.91904 15.2739 6.91393 15.2433C6.84658 14.8391 6.8115 14.4237 6.8115 14C6.8115 13.5762 6.84658 13.1608 6.91393 12.7566C6.91904 12.726 6.90984 12.6946 6.88877 12.6717L4.94974 10.568C4.92033 10.5361 4.91485 10.4889 4.93619 10.4511L6.46078 7.75126C6.48276 7.71234 6.5279 7.69273 6.57135 7.70323L9.31807 8.36712C9.3494 8.37469 9.38243 8.36666 9.40699 8.34578C10.028 7.81784 10.7373 7.39482 11.509 7.10331C11.5386 7.09214 11.5614 7.06784 11.5703 7.0375L12.3818 4.2718C12.3943 4.22921 12.4333 4.19995 12.4777 4.19995Z" stroke="currentColor" stroke-width="1.4"></path><circle cx="13.9992" cy="14" r="2.8" stroke="currentColor" stroke-width="1.4"></circle></svg>
							</div>
						</div>
						<% for(ProfileDto dto : showProfile) { %>
						<div class="profile_info">
							<div class="profile_box">
								<div class="profile_outline">
									<div class="profile_img_box">
										<img class="profile_img_edit" src="<%=dto.getProfile_img() %>">
									</div>
								</div>
								<div class="profile_write_box">
									<div class="profile_name">
										<%=dto.getNickname() %>										
									</div>
									<div class="profile_email">
										<%=dto.getId() %>
									</div>
								</div>
							</div>
						</div>
						<div class="profile_info2">
							<!-- 회원이 별점 평가 영화 목록 -->
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_star_eva_action">
								<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
								<button class="evaluation_count">
									<div class="eva_number"><%=dto.getLove_eva_idx() %></div>
									<div class="eva_text">평가</div>
								</button>
							</form>
							<!-- 회원이 작성한 평가글 목록 -->
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_eva_write_action">
								<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
								<button class="comment_count">
									<div class="comment_number"><%=dto.getStar_idx() %></div>
									<div class="comment_text">코멘트</div>
								</button>
							</form>
						</div>
						<% } %>	
					</div>
				</div>
			</div>
		</div>
		<!-- footer -->	
		<div class="footer">
	       <div class="real_time_box">
	          <div class="real_time_text">
	             	지금까지&nbsp;<span class="real_time_count">&#9733; <%=dDao.allEvaCount() %> 개의 평가가</span>&nbsp;쌓였어요.
	          </div>
	       </div>
	       <div class="preference">
	          <div class="preference_desc">
	             <div class="service">서비스 이용약관 | <span style="font-weight: bold;">개인정보 처리방침</span> | 회사 안내</div>
	              <div class="center">고객센터 | cs@watchapedia.co.kr, 02-515-9985</div>
	              <div class="cooperation">제휴 및 대외 협력 | https://watcha.team/contact</div>
	              <div class="address">주식회사 왓챠 | 대표 박태훈 | 서울특별시 서초구 강남대로 343 신덕빌딩 3층</div>
	              <div class="number">사업자 등록 번호 211-88-66013</div>
	              <div class="watcha">WATCHAPEDIA 2024 by WATCHA, Inc. All rights reserved.</div>
	           </div>
	        </div>
	     </div>
	     <!-- footer 여기까지 -->
	</div>
</body>
</html>