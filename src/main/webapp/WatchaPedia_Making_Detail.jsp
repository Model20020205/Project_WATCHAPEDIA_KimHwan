<%@page import="dto.MakingMovieDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.CommentDto"%>
<%@page import="dto.MakingPersonDto"%>
<%@page import="dao.DetailPageDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// Dao 객체 생성
	DetailPageDao dDao = new DetailPageDao();

	int person_idx = (Integer) request.getAttribute("person_idx");
	
	// 출연, 제작 상세 페이지 (인물 사진, 인물 이름, 역할, 좋아요 개수)
	ArrayList<MakingPersonDto> showPerson = (ArrayList<MakingPersonDto>) request.getAttribute("showPerson");
	
	// 출연, 제작 상세 페이지 (개봉 연도, 포스터, 영화 제목, 역할, 별점)
	ArrayList<MakingMovieDto> showMovieInfo = (ArrayList<MakingMovieDto>) request.getAttribute("showMovieInfo");
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
	<title>출연/제작 상세페이지</title>
	<link rel="stylesheet" href="WatchaPedia_Making_Detail.css">
	
	<!-- jQuery 사용 -->
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		$(function(){
			$(".director_movie_img").click(function(){	// 영화_IDX Action으로 넘김
				let moive_idx = $(this).find("form").submit();
			});
		});
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
		<!-- header 부분 -->
		<div class="making_middle_screen">
			<div class="middle_content_box">
				<div class="middle_top_profile_box">
					<% for(MakingPersonDto dto : showPerson) { %>
						<div class="middle_top_profile">
							<div class="middle_profile_img">
								<img class="dir_profile_img" src="<%=dto.getPerson_img() %>">
							</div>
							<div class="middle_profile_info_box">
								<div class="middle_profile_name">
									<!-- 인물 이름 -->
									<%=dto.getPerson_name() %>
								</div>
								<div class="middle_profile_genre">
									<!-- 역할 -->
									<%=dto.getJob() %>
								</div>
							</div>
						</div>
					<% } %>	
				</div>
				<div class="middle_movie_info">
					<div class="director_regarding">
						<h2 class="movie_name">영화</h2>
						<div class="director_intro">감독</div>
						<div class="type_box">
							<div class="none1" ></div>
							<div class="none2" ></div>
							<div class="director_title">제목</div>
							<div class="director_role">역할</div>
							<div class="director_evaluation">평가</div>
						</div>
						
						<!-- 영화 목록 div 시작 -->
					<% for(MakingMovieDto dto : showMovieInfo) { %>	
						<div class="director_movie_info_div">
							<div class="director_movie_year"><%=dto.getRelease_year() %></div>
							<div class="director_movie_img">
								<form action="<%=request.getContextPath() %>/Controller" method="post">
									<input type="hidden" name="watcha" value="watcha_detail_action">
									<input type="hidden" name="movie_idx" value="<%=dto.getMovie_idx() %>">
									<img class="dir_movie_poster" src="<%=dto.getPoster() %>">
								</form>
							</div>
							<div class="director_movie_name"><%=dto.getMovie_title() %></div>
							<div class="director_movie_director"><%=dto.getJob() %></div>
							<div class="director_movie_ave">
								평균
								<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="none" viewBox="0 0 20 19"><path fill="#FFC633" d="m10 0 3.233 5.55 6.278 1.36-4.28 4.79.647 6.39L10 15.5l-5.878 2.59.647-6.39L.49 6.91l6.278-1.36L10 0Z"></path></svg>
								<%=dto.getScore() %>
							</div>
						</div>
					<% } %>	
						<!-- 영화 목록 div 끝 -->
						
					</div>
				</div>
			</div>
		</div>
		<div class="footer">	<!-- footer -->
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
		</div>					<!-- footer 여기까지 -->
	</div>
</body>
</html>