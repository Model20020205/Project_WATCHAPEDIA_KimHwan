<%@page import="dto.RightUplDto"%>
<%@page import="dto.RatedRDto"%>
<%@page import="dto.ScoreDescDto"%>
<%@page import="dao.DetailPageDao"%>
<%@page import="dto.RecentListDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 메인 페이지 -->
<%
	// Dao 객체 생성
	DetailPageDao dDao = new DetailPageDao();

	// 지금 뜨는 코멘트 
	ArrayList<RightUplDto> showRight = (ArrayList<RightUplDto>) request.getAttribute("showRight");

	// 영화 메인 페이지 개봉연도 내림차순
	ArrayList<RecentListDto> showRecent = (ArrayList<RecentListDto>) request.getAttribute("showRecent");
	
	// 영화 메인 페이지 별점 순위 내림차순
	ArrayList<ScoreDescDto> showDesc = (ArrayList<ScoreDescDto>) request.getAttribute("showDesc");
	
	// 연령 설정
	int avg_idx = 1;
	
	// 영화 메인 페이지 연령별 순위
	ArrayList<RatedRDto> showAvg = (ArrayList<RatedRDto>) request.getAttribute("showAvg");
	
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
	<title>왓챠피디아 메인 페이지</title>
	<link rel= "stylesheet" href="WatchaPedia_Main.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<!-- jQuery 사용 -->
</head>
<body>
	<div id= "root">
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
		<div class="main_content">
		<!-- 지금 뜨는 코멘트 -->
			<div class="comment_full_box">
				<div class="comment_title_box">
					<div class="comment_title">
						지금 뜨는 코멘트
					</div>
				</div>
				<div class="comment_content_box">
				<% for(RightUplDto dto : showRight) { %>	
					<div class="comment_radius_box">
						<div class="radius_inner_box">
							<div class="user_info_box">
								<img src="<%=dto.getProfile_img() %>" class="user_img">
								<div class="user_name">
									<%=dto.getNickname() %>
								</div>
							</div>
							<div class="score_box">
								<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="none" viewBox="0 0 20 19"><path fill="#FFC633" d="m10 0 3.233 5.55 6.278 1.36-4.28 4.79.647 6.39L10 15.5l-5.878 2.59.647-6.39L.49 6.91l6.278-1.36L10 0Z"></path></svg>
								<%=dto.getScore() %>
							</div>
						</div>
						<div class="article_box">
						<form action="<%=request.getContextPath() %>/Controller" method="post">
							<input type="hidden" name="watcha" value="watcha_detail_action">
							<input type="hidden" name="movie_idx" value="<%=dto.getMovie_idx() %>">
							<button class="article_img_box">
								<img src="<%=dto.getPoster() %>" class="article_img">
							</button>
						</form>
							<div class="article_content_box">
								<div class="article_title">
									<%=dto.getMovie_title() %>
								</div>
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_comment_detail_action">
								<input type="hidden" name="eva_idx" value="<%=dto.getEva_idx() %>">
								<button class="article_content">
									<%=dto.getContent() %>
								</button>
							</form>	
							</div>
						</div>
						<div class="comment_count_box">
							<div class="comment_like_count">
								<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M5.70833 10.15H3.70312C3.03866 10.15 2.5 10.6887 2.5 11.3531V16.8221C2.5 17.4865 3.03866 18.0252 3.70312 18.0252H5.70833" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
								<path fill-rule="evenodd" clip-rule="evenodd" d="M16.6553 15.7475C16.4815 17.0515 15.3692 18.0252 14.0537 18.0252H5.30566V9.61157L7.45753 2.07233C7.56975 1.67916 7.9397 1.41677 8.33929 1.5034C9.30034 1.71174 10.9255 2.32596 10.9255 4.09743V6.62225C10.9255 6.84374 11.105 7.02329 11.3265 7.02329H15.1911C16.5836 7.02329 17.6568 8.25105 17.4717 9.63127C17.1984 11.6699 16.927 13.7087 16.6553 15.7475Z" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
								</svg>
								<%=dto.getLove_eva_idx() %>
							</div>
							<div class="reply_count">
								<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M11.5878 2H7.40656C3.84184 2 0.950684 4.89112 0.950684 8.46098C0.950684 12.0308 3.84184 14.922 7.41174 14.922H8.84177C8.90394 14.922 8.95058 14.9738 8.94539 15.0359L8.66561 17.3623C8.60861 17.8545 9.16301 18.181 9.56715 17.8908L15.0386 13.9168C17.0023 12.4609 18.0489 10.7614 18.0489 8.46098C18.0489 4.89112 15.1577 2 11.5878 2Z" stroke="#666666" stroke-width="1.5"></path>
								</svg>
								<%=dto.getReply_idx() %>
							</div>
						</div>
					</div>
				<% } %>	
				</div>
			</div>
			<!-- 지금 뜨는 코멘트 여기까지 -->
			<!-- 최근 개봉 순위 -->	
			<div class= "centercontent">
				<div class= "centercontent-title centercontent-padding">
					<p class= "titlesize">
						최근 개봉한 영화
					</p>
				</div>
				<section class="centercontent-title">
					<div class="centercontent-moviesize">
						<ul class="movieul">
						<% for(int i=0; i<=showRecent.size()-1; i++) { %>
							<li class="movieli">
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_detail_action">
								<input type="hidden" name="movie_idx" value="<%=showRecent.get(i).getMovie_idx() %>"/>
								<input type="hidden" name="mm_idx" value="<%=mm_idx%>">
								<button type="submit" class="movie_poster_button">
									<div class="movie-picture">
										<img class= "movie-img" src="<%=showRecent.get(i).getPoster() %>"/>
									</div>
								</button>
							</form>
								<div class="movie-rank"><%=i+1 %></div>
								<div class="movie-content">
									<div class="movie-content-title"><%=showRecent.get(i).getMovie_title() %></div>
									<div class="movie-content-year"><%=showRecent.get(i).getRelease_year() %></div>
									<div class="movie-content-star">
										<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="none" viewBox="0 0 20 19"><path fill="#FFC633" d="m10 0 3.233 5.55 6.278 1.36-4.28 4.79.647 6.39L10 15.5l-5.878 2.59.647-6.39L.49 6.91l6.278-1.36L10 0Z"></path></svg>
										<%=showRecent.get(i).getScore() %>
									</div>
								</div>
							</li>
						<% } %>
						</ul>
					</div>
				</section>
			</div>
			<!-- 최근 개봉 순위 여기까지 -->
			<!-- 별점 높은 순위 -->	
			<div class= "centercontent">
				<div class= "centercontent-title centercontent-padding">
					<p class= "titlesize">
						별점 높은 순위 영화
					</p>
				</div>
				<section class="centercontent-title">
					<div class="centercontent-moviesize">
						<ul class="movieul">
						<% for(int i=0; i<=showDesc.size()-1; i++) { %>	
							<li class="movieli">
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_detail_action">
								<input type="hidden" name="movie_idx" value="<%=showDesc.get(i).getMovie_idx() %>"/>
								<input type="hidden" name="mm_idx" value="<%=mm_idx%>">
								<button type="submit" class="movie_poster_button">
									<div class="movie-picture">
										<img class= "movie-img" src="<%=showDesc.get(i).getPoster() %>"/>
									</div>
								</button>
							</form>
								<div class="movie-rank"><%=i+1 %></div>
								<div class="movie-content">
									<div class="movie-content-title"><%=showDesc.get(i).getMovie_title() %></div>
									<div class="movie-content-year"><%=showDesc.get(i).getRelease_year() %></div>
									<div class="movie-content-star">
										<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="none" viewBox="0 0 20 19"><path fill="#FFC633" d="m10 0 3.233 5.55 6.278 1.36-4.28 4.79.647 6.39L10 15.5l-5.878 2.59.647-6.39L.49 6.91l6.278-1.36L10 0Z"></path></svg>
										<%=showDesc.get(i).getScore() %>
									</div>
								</div>
							</li>
						<% } %>
						</ul>
					</div>
				</section>
			</div>
			<!-- 별점 높은 순위 여기까지 -->
			<!-- 연령별 인기 순위 -->	
			<div class= "centercontent">
				<div class= "centercontent-title centercontent-padding">
					<p class= "titlesize">
						연령별 인기 순위 영화
					</p>
				</div>
				<section class="centercontent-title">
					<div class="centercontent-moviesize">
						<ul class="movieul">
						<% for(int i=0; i<=showAvg.size()-1; i++) { %>	
							<li class="movieli">
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_detail_action">
								<input type="hidden" name="movie_idx" value="<%=showAvg.get(i).getMovie_idx() %>"/>
								<input type="hidden" name="mm_idx" value="<%=mm_idx%>">
								<button type="submit" class="movie_poster_button">
									<div class="movie-picture">
										<img class= "movie-img" src="<%=showAvg.get(i).getPoster() %>"/>
									</div>
								</button>
							</form>
								<div class="movie-rank"><%=i+1 %></div>
								<div class="movie-content">
									<div class="movie-content-title"><%=showAvg.get(i).getMovie_title() %></div>
									<div class="movie-content-year"><%=showAvg.get(i).getRelease_year() %></div>
									<div class="movie-content-star">
										<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="none" viewBox="0 0 20 19"><path fill="#FFC633" d="m10 0 3.233 5.55 6.278 1.36-4.28 4.79.647 6.39L10 15.5l-5.878 2.59.647-6.39L.49 6.91l6.278-1.36L10 0Z"></path></svg>
										<%=showAvg.get(i).getScore() %>
									</div>
								</div>
							</li>
						<% } %>
						</ul>
					</div>
				</section>
			</div>
			<!-- 연령별 인기 순위 여기까지 -->	
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
	</div>
</body>
</html>