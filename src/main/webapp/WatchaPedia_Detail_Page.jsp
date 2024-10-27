<%@page import="dao.DetailPageDao"%>
<%@page import="dto.MoreCommentDto"%>
<%@page import="dto.LikeCountDto"%>
<%@page import="dto.CommentDto"%>
<%@page import="dto.ComProDto"%>
<%@page import="dto.StaffDto"%>
<%@page import="dto.DetailPageDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 영화_IDX 가져오기
	int movie_idx = (Integer) request.getAttribute("movie_idx");
	
	// 영화 상세 정보 부분
	ArrayList<DetailPageDto> showList = (ArrayList<DetailPageDto>) request.getAttribute("showList");
	
	// 출연/제작 부분
	ArrayList<StaffDto> showStaff = (ArrayList<StaffDto>) request.getAttribute("showStaff");
	
	// 영화 코멘트 부분(프로필, 닉네임, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
	ArrayList<CommentDto> showAll = (ArrayList<CommentDto>) request.getAttribute("showAll");
	
	// 전체 코멘트 출력
	ArrayList<MoreCommentDto> showScroll = (ArrayList<MoreCommentDto>) request.getAttribute("showScroll");
	
	// 전체 코멘트 개수 출력
	int allCountComment = (Integer) request.getAttribute("allCountComment");
%>

<%	
	// 로그인한 회원_IDX 가져오기
	Integer mm_idx = (Integer) session.getAttribute("mm_idx");
	
	// 로그인하지 않았을 때 회원_IDX 0으로 설정
	if(mm_idx==null) {
		mm_idx = 0;
	}
%>

<%
	DetailPageDao dDao = new DetailPageDao();
%>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>상세 페이지</title>
	<link rel="stylesheet" href="WatchaPedia_Detail_Page.css">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<!-- jQuery 시작 -->
	<script>
		$(function(){
			$(".button_text").click(function(){	// 평가 작성란
				$(".ins_main_screen").css("display", "flex");
				$(".main_screen").css("display", "block");
			});
			$(".insert_out").click(function(){	// 평가 작성란
				$(".ins_main_screen").css("display", "none");
				$(".main_screen").css("display", "flex");
			});
			$(".list_review_box").click(function(){	// 평가글_IDX Action으로 넘김
				$(this).find("form").submit();
			});
			$(".actor_name_box").click(function(){	// 인물_IDX Action으로 넘김
				$(this).find("form").submit();
			});
			$(".save_button").click(function(event){
				let mm_idx = <%=mm_idx %>;	// 현재 로그인한 회원_IDX 가져오기
				if(mm_idx==0) {
					alert("로그인 후 이용해주세요.");
					event.preventDefault();	// form 제출 막기
					return false;	// 추가로 false로 반환
				}
			});
			
			// 별점 클릭 시
			$(".star_img").click(function(){
				let starValue = $(this).val();	// 별점 개수 및 값
				let mm_idx = $(this).parents().find("input[name='mm_idx']").val();	// 별점을 클릭한 회원_IDX
				
				// 로그인하지 않았을 때의 별점 클릭 시
				if(mm_idx==0) {
					alert("로그인 후 이용해주세요.");
					return;
				}
				
				let movie_idx = $(this).parents().find("input[name='movie_idx']").val();	// 별점이 평가된 영화_IDX
				$.ajax({
					type: 'POST',
					url: 'StarEvaluation',
					dataType: 'JSON',
					data: {
						starValue: starValue,
						mm_idx : mm_idx,
						movie_idx : movie_idx,
					},
					success: function(res) {
						// 취소하기 버튼 생성
						$("#star_revoke_button").css("display", "flex");
					}.bind(this),
					error: function(a, b, c) {
						alert("실패");
					}
				});
			});
			
			// 별점 취소하기 클릭 시
			$("#star_revoke_button").click(function(){
				let mm_idx = $(this).parents().find("input[name='mm_idx']").val();	// 별점을 클릭한 회원_IDX
				let movie_idx = $(this).parents().find("input[name='movie_idx']").val();	// 별점이 평가된 영화_IDX
				let starAvg = $(this).parents().find("input[name='starAvg']").val();	// 별점 개수 및 값
				$.ajax({
					type: 'POST',
					url: 'StarDeleteEvaluation',					
					dataType: 'JSON',
					data: {
						mm_idx : mm_idx,
						movie_idx : movie_idx,
						starAvg : starAvg
					},
					success: function(res) {
						$(".star_img").prop("checked", false);  // 모든 라디오버튼 선택 해제
						$("#star_revoke_button").css("display", "none");
					},
					error: function(a, b, c) {
						alert("실패");
					}
				});
			});
		});
	</script>
</head>
<body>
	<div class="main_screen">
		<!-- 평가글 작성란 창 생성 -->
		<% for(DetailPageDto dto : showList) { %>	
		<div class="ins_main_screen">
			<div class="insert_comment">
				<header class="insert_header_box">
					<div class="insert_title_box"><%=dto.getMovie_title() %></div>
					<button class="insert_out">
						<svg focusable="false" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"></path></svg>
					</button>
				</header>
				<!-- 평가글 작성 Action -->	
				<form action="<%=request.getContextPath() %>/Controller" method="post">
					<div class="insert_content_box">
						<input type="hidden" name="watcha" value="watcha_eva_insert_action">
						<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
						<input type="hidden" name="movie_idx" value="<%=movie_idx %>">
						<textarea class="insert_box" name="content" placeholder="이 작품에 대한 생각을 자유롭게 표현해주세요."></textarea>
						<div class="down_box">
							<div class="korean_count">0/10000</div>
							<button class="save_button" type="submit">저장</button>
						</div>
					</div>
				</form>
				<!-- 평가글 작성 Action 여기까지 -->	
			</div>
		</div>
		<!-- 평가글 작성란 창 생성 여기까지 -->
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
			<div class="main_photo">
				<img class="photo_size" src="<%=dto.getMovie_background() %>">
				<div class="movie_info_box">
					<div class="movie_info_inner_box">
						<div class="movie_title"><%=dto.getMovie_title() %></div>
						<div class="movie_release_year_genre"><%=dto.getRelease_year() %> <%=dto.getGenre_name() %></div>
						<div class="movie_time_age"><%=dto.getMovie_time() %> <%=dto.getAge_name() %></div>
					</div>
				</div>	
			</div>
			<div class="detail_box">
				<div class="detail_info_box">
					<div class="detail_poster_box">
						<img class="detail_poster" src="<%=dto.getPoster() %>">
					</div>
					<div class="detail_info">
						<div class="detail_evaluation">
							<div class="detail_star">
								<div class="detail_star_select">
									<div class="star_rating">
										<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
										<input type="hidden" name="movie_idx" value="<%=movie_idx %>">
										<% int starAvg = dDao.checkStarExists(mm_idx, movie_idx); %>
									    <input type="radio" class="star_img" value="1" <%= starAvg == 1 ? "checked" : "" %> >
									    <input type="radio" class="star_img" value="2" <%= starAvg == 2 ? "checked" : "" %> >
									    <input type="radio" class="star_img" value="3" <%= starAvg == 3 ? "checked" : "" %> >
									    <input type="radio" class="star_img" value="4" <%= starAvg == 4 ? "checked" : "" %> >
									    <input type="radio" class="star_img" value="5" <%= starAvg == 5 ? "checked" : "" %> >
								  	</div>
									<div id="star_revoke_button" style="display: <%=starAvg!=0 ? "flex" : "none" %>">취소하기</div>
									<input type="hidden" name="starAvg" value="<%=starAvg %>">
								</div>
								<div class="detail_star_text">평가하기</div>
							</div>
							<div class="detail_score_box">
								<div class="detail_score_number_box">
									<div class="detail_score_number"><%=dto.getScore() %>점</div>
								</div>
							</div>
							<div class="detail_comment_box">
								<div class="button_text">
									<svg aria-hidden="true" viewBox="0 0 24 24">
								    	<path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04a1.004 1.004 0 0 0 0-1.41L18.37 3.29c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path>
								    </svg>
								    <div class="text">코멘트</div>
								</div>
							</div>
						</div>
						<div class="detail_post"><%=dto.getMovie_description() %></div>
					</div>
				</div>
			</div>
		</div>
		<% } %>	
		<div class="main_content2">
			<!-- 출연/제작 시작 -->
			<div class="appear">
				<div class="apper_title">
					출연/제작
				</div>
				<div class="appear_info_box">
					<div class="appear_info">
					<!-- 출연/제작 반복 -->
					<% for(StaffDto dto : showStaff) { %>	
						<div class="actor_box">
							<div class="actor_info_box">
								<div class="actor_name_box">
									<!-- 인물_IDX 저장 -->
									<form action="<%=request.getContextPath() %>/Controller" method="post">
										<input type="hidden" name="watcha" value="watcha_making_action">
										<input name="person_idx" type="hidden" value="<%=dto.getPerson_idx() %>">
									</form>
									<div class="actor_img">
										<img class="actor_photo" src="<%=dto.getPerson_img() %>">
									</div>
									<div class="actor_desc">
										<div class="actor_name_desc"><%=dto.getPerson_name() %></div>
										<div class="actor_role"><%=dto.getJob() %></div>
									</div>
								</div>
							</div>
						</div>
					<% } %>	
					<!-- 출연/제작 반복 여기까지 -->
					</div>
				</div>
			</div>
			<!-- 출연/제작 끝 -->
			<!-- 코멘트 전체 화면 시작 -->
			<div class="comment">
				<div class="comment_text">
					<div class="comment_font">코멘트</div>
					<div class="require_font"><%=allCountComment %></div>
					<div class="more_box">
					<!-- (핑크색) 더보기 클릭 시 페이지네이션 적용된 코멘트로 이동 -->	
					<form action="<%=request.getContextPath() %>/Controller">
						<input type="hidden" name="watcha" value="watcha_comment_like_action">
						<input type="hidden" name="movie_idx" value="<%=movie_idx %>">
						<button type="submit" class="more_button">더보기</button>
					</form>
					<!-- (핑크색) 더보기 클릭 시 페이지네이션 적용된 코멘트로 이동 여기까지 -->	
					</div>
				</div>
				<div class="comment_list_box">
					<!-- 코멘트 div 시작 -->
					<% for(CommentDto dto : showAll) { %>
						<div class="comment_list_link">
							<div class="comment_list">
								<div class="list_comment_box">
									<div class="list_comment">
										<div class="list_comment_img_box">
											<img class="user_img" src="<%=dto.getProfile_img() %>">
										</div>
										<%=dto.getNickname() %>
									</div>
									<div class="star_cur">
										<div class="star_cur_box">
											<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="none" viewBox="0 0 20 19"><path fill="#FFC633" d="m10 0 3.233 5.55 6.278 1.36-4.28 4.79.647 6.39L10 15.5l-5.878 2.59.647-6.39L.49 6.91l6.278-1.36L10 0Z"></path></svg>
											<%=dto.getScore() %>
										</div>
									</div>
								</div>
								<div class="list_review_box">
									<form action="<%=request.getContextPath() %>/Controller" method="post">
										<input type="hidden" name="watcha" value="watcha_comment_detail_action">
										<input type="hidden" name="eva_idx" value="<%=dto.getEva_idx()  %>">
									</form>
									<%=dto.getContent() %>
								</div>
								<!-- 좋아요 개수 JSP 설정 -->	
								<div class="like_count">
									<div class="count_box">
										<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
										<path d="M5.70833 10.15H3.70312C3.03866 10.15 2.5 10.6887 2.5 11.3531V16.8221C2.5 17.4865 3.03866 18.0252 3.70312 18.0252H5.70833" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
										<path fill-rule="evenodd" clip-rule="evenodd" d="M16.6553 15.7475C16.4815 17.0515 15.3692 18.0252 14.0537 18.0252H5.30566V9.61157L7.45753 2.07233C7.56975 1.67916 7.9397 1.41677 8.33929 1.5034C9.30034 1.71174 10.9255 2.32596 10.9255 4.09743V6.62225C10.9255 6.84374 11.105 7.02329 11.3265 7.02329H15.1911C16.5836 7.02329 17.6568 8.25105 17.4717 9.63127C17.1984 11.6699 16.927 13.7087 16.6553 15.7475Z" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
										</svg>
										<%=dto.getLove_eva_idx() %>
										<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
										<path d="M11.5878 2H7.40656C3.84184 2 0.950684 4.89112 0.950684 8.46098C0.950684 12.0308 3.84184 14.922 7.41174 14.922H8.84177C8.90394 14.922 8.95058 14.9738 8.94539 15.0359L8.66561 17.3623C8.60861 17.8545 9.16301 18.181 9.56715 17.8908L15.0386 13.9168C17.0023 12.4609 18.0489 10.7614 18.0489 8.46098C18.0489 4.89112 15.1577 2 11.5878 2Z" stroke="#666666" stroke-width="1.5"></path>
										</svg>
										<%=dto.getReply_idx() %>							
									</div>
								</div>
								<!-- 좋아요 개수 JSP 설정 -->	
							</div>
						</div>
					<% } %>
					<!-- 코멘트 div 끝 -->
				</div>
			</div>
			<!-- 코멘트 전체 화면 끝 -->
			<!-- 동영상 시작 -->
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