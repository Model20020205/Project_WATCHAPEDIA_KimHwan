<%@page import="dao.DetailPageDao"%>
<%@page import="dto.MovieEvaStarDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	
	// 로그인한 회원_IDX 가져오기
	Integer mm_idx = (Integer) session.getAttribute("mm_idx");
	
	// 로그인하지 않았을 때 회원_IDX 0으로 설정
	if(mm_idx==null) {
		mm_idx = 0;
	}
%>

<%
	// Dao 객체 생성
	DetailPageDao dDao = new DetailPageDao();
	
	// 별점 평가한 영화 목록 가져오기
	ArrayList<MovieEvaStarDto> showMovieStar = (ArrayList<MovieEvaStarDto>) request.getAttribute("showMovieStar");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>평가한 작품들 페이지</title>
	<link rel="stylesheet" href="WatchaPedia_Eva_Page.css">
</head>
<body>
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
	<div class="more_content">
		<header class="more_header">
			<div class="more_comment_box">
				평가한 작품들
			</div>
		</header>
		<div class="more_like_box">
			<div class="more_like_small_box">
				<div class="more_like_select_box">
					<span class="_0RJ_8 j5Hqr yudZz" style="background-image: url(&quot;https://an2-ast.amz.wtchn.net/ayg/images/icDropDownGray.eb179356f4205da40ec7.svg?as-resource&quot;); width: 24px; height: 24px;"></span>
					<div class="more_like">
						담은 순
					</div>
				</div>
			</div>
		</div>
		<div class="movie_poster_box">
			<div class="movie_inner_box">
				<div class="movie_inner_line_box">
				<% for(MovieEvaStarDto dto : showMovieStar) { %>	
					<div class="movie_poster_padding">	<!-- 여기서부터 영화 포스터 div 반복 시작 -->
						<form action="<%=request.getContextPath() %>/Controller">
							<input type="hidden" name="watcha" value="watcha_detail_action">
							<input type="hidden" name="movie_idx" value="<%=dto.getMovie_idx() %>">
							<button class="movie_frame">
								<img src="<%=dto.getPoster() %>" class="poster_img">
								<div class="poster_content">
									<div class="poster_title">
										<%=dto.getMovie_title() %>
									</div>
									<div class="poster_star">
										평가함 ★ <%=dto.getScore() %>
									</div>
								</div>
							</button>
						</form>
					</div>	<!-- 여기서부터 영화 포스터 div 반복 시작 여기까지 -->
				<% } %>	
				</div>
			</div>				
		</div>
	</div>
</body>
</html>