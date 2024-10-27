<%@page import="dao.DetailPageDao"%>
<%@page import="dto.ReplyDto"%>
<%@page import="dto.CommentDto"%>
<%@page import="dto.CommentDetailDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 평가글_IDX 가져오기
	int eva_idx = (Integer) request.getAttribute("eva_idx");
	
	// 코멘트(=평가글) 상세 페이지 (프로필 사진, 닉네임, 영화 제목, 개봉 연도, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
	ArrayList<CommentDetailDto> showComInfo = (ArrayList<CommentDetailDto>) request.getAttribute("showComInfo");
	
	// 코멘트(프로필 사진, 닉네임, 댓글 내용, 댓글 작성일자, 댓글 좋아요 개수)
	ArrayList<ReplyDto> showReply = (ArrayList<ReplyDto>) request.getAttribute("showReply");
	
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
	// Dao 객체 생성
	DetailPageDao dDao = new DetailPageDao();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>코멘트 상세페이지</title>
	<link rel="stylesheet" href="WatchaPedia_Comment_Detail.css">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<!-- jQuery 사용 -->
	<script>
		$(function(){
			$(".likpos_button_box button:nth-of-type(2)").click(function(){
				$(".ins_main_screen").css("display", "flex");	// (댓글 새로 작성하려고 할 때) 댓글 클릭 시 창 열림
			});
			$(".insert_out").click(function(){	// (댓글 새로 작성하려고 할 때) 댓글 작성 창 닫힘
				$(".ins_main_screen").css("display", "none");
			})
			$(".insert_out").click(function(){	// (자기가 쓴 댓글 수정 시) 댓글 클릭 시 창 열림
				$(".post_main_screen").css("display", "none");
			});
			$(".modify_text").click(function(){	// (자기가 쓴 댓글 수정 시) 댓글 수정 클릭 시 댓글 수정 창 열림
				$(".post_main_screen").css("display", "flex");
			});
			$(".modify_box").click(function(event){	// (자기가 쓴 댓글 수정 시) [댓글 수정 + 삭제 박스] 열림
				$(this).closest(".list_comment_likcou").siblings(".modify_select").css("display", "flex");
				event.stopPropagation();
			});
			$(".close_text").click(function(event){	// 나가기 클릭 시 [댓글 수정 + 삭제 박스] 닫힘
				$(".modify_select").css("display", "none");
			});
			$(".modify_text").click(function(event) {	// 댓글 수정 박스 안에 있는 텍스트 클릭 시 전파 방지
			    event.stopPropagation();
			});
			$("#eva_update").click(function(){	// 코멘트(=평가글) 수정 클릭 시 창 열림
				$(".eva_main_screen").css("display", "flex");
			});
			$(".eva_insert_out").click(function(){	// 코멘트(=평가글) 수정 창 닫힘
				$(".eva_main_screen").css("display", "none");				
			});
			$(".save_button").click(function(event){
				let mm_idx = $(this).parents().parents().parents().find("input[name='mm_idx']").val();
				
				// 로그인하지 않았을 때 댓글 작성 막기
				if(mm_idx==0) {
					alert("로그인 후 이용해주세요.");
					event.preventDefault();	// form 제출 막기
					return false;	// false로 반환
				}
			});
		});
		<!-- Ajax 사용 (댓글 좋아요) -->	
		$(function(){
		    // 좋아요 버튼 클릭 시
		    $(".likcou_text").click(function() {
		        let reply_idx = $(this).find("input[name='reply_idx']").val();	// 좋아요 버튼 클릭 시 해당 댓글_IDX 가져오기
		        let mm_idx = $(this).find("input[name='mm_idx']").val();	// 좋아요 버튼 클릭한 회원_IDX 가져오기
		        
		    	// 로그인하지 않았을 때의 별점 클릭 시 막기
				if(<%=mm_idx %>==0) {
					alert("로그인 후 이용해주세요.");
					return false;
				}
		        
		        $.ajax({
		            type: 'post',
		            url: 'ReplyLikeIncrease', // 좋아요 증가 서블릿
		            dataType: 'JSON',
		            data: { reply_idx: reply_idx, mm_idx: mm_idx },
		            success: function(res) {
		                // 좋아요 수 업데이트
		                $(this).closest(".comment_list").find(".reply_like_count").text(res.reply_like_idx);
		                
		                // 버튼 상태 전환
		                $(this).hide(); // 좋아요 버튼 숨김
		                $(this).closest(".comment_list").find(".like_revoke_button").show(); // 좋아요 취소 버튼 보이기
		            }.bind(this),
		            error: function() {
		                alert("좋아요 처리에 실패했습니다."); 
		            }
		        });
		    });
	
		    // 좋아요 취소 버튼 클릭 시
		    $(".like_revoke_button").click(function() {
		        let reply_idx = $(this).find("input[name='reply_idx']").val();
		        let mm_idx = $(this).find("input[name='mm_idx']").val();
		        
		        $.ajax({
		            type: 'post',
		            url: 'ReplyLikeDecrease', // 좋아요 취소 서블릿
		            dataType: 'JSON',
		            data: { reply_idx: reply_idx, mm_idx: mm_idx },
		            success: function(res) {
		                // 좋아요 수 업데이트
		                $(this).closest(".comment_list").find(".reply_like_count").text(res.reply_like_idx);
		                
		                // 버튼 상태 전환
		                $(this).hide(); // 좋아요 취소 버튼 숨김
		                $(this).closest(".comment_list").find(".likcou_text").show(); // 좋아요 버튼 보이기
		            }.bind(this),
		            error: function() {
		                alert("좋아요 취소 처리에 실패했습니다.");
		            }
		        });
		    });
		});
		<!-- Ajax 사용 (댓글 좋아요) 여기까지 -->	
		
		<!-- Ajax 사용 (평가글 좋아요) -->
		$(function(){
			// 평가글 좋아요 버튼 클릭 시 증가
			$("#eva_like_button").click(function(){
				let mm_idx = $(this).parent().find("input[name='mm_idx']").val();	// 좋아요 클릭한 회원_IDX 가져오기
				
				// 로그인하지 않았을 때의 별점 클릭 시
				if(mm_idx==0) {
					alert("로그인 후 이용해주세요.");
					return;
				} 
				
				let eva_idx = $(this).parent().find("input[name='eva_idx']").val();	// 좋아요 클릭한 코멘트(=평가글)_IDX 가져오기
				
				$.ajax ({
					type: 'post',
					url: 'EvaLikeIncrease',
					dataType: 'JSON',
					data: {
						mm_idx : mm_idx,					
						eva_idx : eva_idx
					},
					success: function(res) {
						// 좋아요 수 업데이트
						$("#comment_like_count").text(res.love_eva_idx);
						
						// 버튼 상태 전환
						$(this).hide();	// 좋아요 버튼 숨김
						$("#eva_revoke_button").show();
					}.bind(this),
					error: function() {
						alert("실패;");
					}
				});			
			});
			
			// 평가글 좋아요 취소 버튼 클릭 시 감소
			$("#eva_revoke_button").click(function(){
				let mm_idx = $(this).parent().find("input[name='mm_idx']").val();
				let eva_idx = $(this).parent().find("input[name='eva_idx']").val();
				$.ajax({
					type: 'post',
					url: 'EvaLikeDecrease',
					dataType: 'JSON',
					data: {
						mm_idx : mm_idx,
						eva_idx : eva_idx
					},
					success: function(res) {
						// 좋아요 수 업데이트
						$("#comment_like_count").text(res.love_eva_idx);
						
						// 버튼 상태 전환
						$(this).hide();	// 좋아요 취소 버튼 숨김
						$("#eva_like_button").show();
					}.bind(this),
					error: function() {
						alert("실패;;;");
					}
				});
			});
		});
		<!-- Ajax 사용 (평가글 좋아요) 여기까지 -->
	</script>
</head>
<body>
	<div class="comment_main_screen">
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
		<div class="comment_main_content">
			<div class="comment_inner_content">
				<div class="comment_top_inner">
				<% for(CommentDetailDto dto : showComInfo) { %>
					<!-- 댓글창 열기 -->
					<div class="ins_main_screen">
						<div class="insert_comment">
							<header class="insert_header_box">
								<div class="insert_title_box"><%=dto.getMovie_title() %></div>
								<button class="insert_out">
									<svg focusable="false" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"></path></svg>
								</button>
							</header>
							<!-- 댓글 작성 Action -->
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_comment_insert_action">
								<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
								<input type="hidden" name="eva_idx" value="<%=eva_idx %>">
								<div class="insert_content_box">
									<textarea name="reply_content" class="insert_box" placeholder="이 작품에 대한 생각을 자유롭게 표현해주세요."></textarea>
									<div class="down_box">
										<div class="korean_count">0/10000</div>
										<button class="save_button" type="submit">저장</button>
									</div>
								</div>
							</form>
							<!-- 댓글 작성 Action 여기까지 -->
						</div>
					</div>
					<!-- 댓글창 열기 여기까지 -->
					<div class="top_inner_title">
						<div class="comment_title_box">
							<div class="comment_user_info">
								<div class="comment_user_img_box">
									<img class="user_profile_img" src="<%=dto.getProfile_img() %>">
								</div>
								<div class="comment_user_content">
									<div class="user_top_content">
										<!-- 닉네임 -->
										<%=dto.getNickname() %>
									</div>
									<div class="user_down_content">
										<!-- 작성일자 -->
										<%=dto.getWrite_date() %>
									</div>
								</div>
							</div>
							<div class="comment_movie_title">
								<div class="comment_movie_title_box">
									<div class="comment_movie_topic">
										<!-- 영화 제목 -->
										<%=dto.getMovie_title() %>
									</div>
									<div class="comment_movie_genre">
										<!-- 개봉 연도 -->
										<%=dto.getRelease_year() %>
									</div>
								</div>
							</div>
							<div class="comment_movie_star">
								<div class="comment_star_box">
									<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="none" viewBox="0 0 20 19"><path fill="#FFC633" d="m10 0 3.233 5.55 6.278 1.36-4.28 4.79.647 6.39L10 15.5l-5.878 2.59.647-6.39L.49 6.91l6.278-1.36L10 0Z"></path></svg>
									<div class="comment_star_text">
										<%=dto.getScore() %>	<!-- 별점 -->
									</div>
								</div>
							</div>
						</div>
						<div class="comment_poster_box">
							<img class="comment_poster_img_edit" src="<%=dto.getPoster() %>">
						</div>
					</div>
					<div class="top_inner_content">
						<%=dto.getContent() %>	<!-- 평가글 내용 -->
					</div>
					<div class="top_inner_count">
						<div class="comment_likpost_box">
							<div class="comment_like_text">
								좋아요
							</div>
							<!-- 좋아요 개수 -->
							<div id="comment_like_count">
								<%=dto.getLove_eva_idx() %>
							</div>
							<!-- 좋아요 개수 -->	
							<div class="comment_post_count">댓글 <%=dto.getReply_eva_idx() %></div> <!-- 댓글 개수 -->
						</div>
						<% if(mm_idx!=0) { %>
						<div class="eva_crud_box">
							<!-- 평가글 수정 클릭 시 뜨는 창 -->
							<div class="eva_main_screen">
								<div class="insert_comment">
									<header class="insert_header_box">
										<div class="insert_title_box">평가글 수정하기</div>
										<button class="eva_insert_out">
											<svg focusable="false" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"></path></svg>
										</button>
									</header>
									<form action="<%=request.getContextPath() %>/Controller" method="post">
										<!-- 평가글 수정 -->
										<div class="insert_content_box">
											<input type="hidden" name="watcha" value="watcha_eva_update_action">
											<input type="hidden" name="eva_idx" value="<%=eva_idx %>">
											<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
											<textarea name="eva_upd_content" class="insert_box"></textarea>
											<div class="down_box">
												<div class="korean_count">0/10000</div>
												<button class="update_button" type="submit">수정</button>
											</div>
										</div>
										<!-- 평가글 수정 여기까지 -->
									</form>
								</div>
							</div>
							<!-- 평가글 수정 클릭 시 뜨는 창 여기까지 -->	
							<!-- 평가글 삭제 -->
							<div id="eva_update">수정</div>
							<form action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="watcha_eva_delete_action">
								<input type="hidden" name="eva_idx" value="<%=eva_idx %>">
								<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
								<button class="eva_delete">삭제</button>
							</form>
							<!-- 평가글 삭제 여기까지 -->
						</div>
						<% } %>
					</div>
					<div class="like_comment">
						<div class="likpos_button_box">
							<input type="hidden" name="mm_idx" value="<%=mm_idx %>">
							<input type="hidden" name="eva_idx" value="<%=eva_idx %>">
						<%
							boolean checkEva = dDao.checkEva(eva_idx, mm_idx);
						%>
							<!-- 평가글 좋아요 버튼 -->
							<button id="eva_like_button" style="display: <%= checkEva ? "none" : "flex"%>">
								<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M5.70833 10.15H3.70312C3.03866 10.15 2.5 10.6887 2.5 11.3531V16.8221C2.5 17.4865 3.03866 18.0252 3.70312 18.0252H5.70833" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
								<path fill-rule="evenodd" clip-rule="evenodd" d="M16.6553 15.7475C16.4815 17.0515 15.3692 18.0252 14.0537 18.0252H5.30566V9.61157L7.45753 2.07233C7.56975 1.67916 7.9397 1.41677 8.33929 1.5034C9.30034 1.71174 10.9255 2.32596 10.9255 4.09743V6.62225C10.9255 6.84374 11.105 7.02329 11.3265 7.02329H15.1911C16.5836 7.02329 17.6568 8.25105 17.4717 9.63127C17.1984 11.6699 16.927 13.7087 16.6553 15.7475Z" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
								</svg>
								좋아요
							</button>
							<!-- 평가글 좋아요 버튼 클릭 시 좋아요 취소로 변경 -->
							<div id="eva_revoke_button" style="display: <%= checkEva ? "flex" : "none"%>">
								<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M5.70833 10.15H3.70312C3.03866 10.15 2.5 10.6887 2.5 11.3531V16.8221C2.5 17.4865 3.03866 18.0252 3.70312 18.0252H5.70833" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
								<path fill-rule="evenodd" clip-rule="evenodd" d="M16.6553 15.7475C16.4815 17.0515 15.3692 18.0252 14.0537 18.0252H5.30566V9.61157L7.45753 2.07233C7.56975 1.67916 7.9397 1.41677 8.33929 1.5034C9.30034 1.71174 10.9255 2.32596 10.9255 4.09743V6.62225C10.9255 6.84374 11.105 7.02329 11.3265 7.02329H15.1911C16.5836 7.02329 17.6568 8.25105 17.4717 9.63127C17.1984 11.6699 16.927 13.7087 16.6553 15.7475Z" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
								</svg>
								좋아요 취소
							</div>
							<!-- 평가글 좋아요 취소 버튼 -->	
							<button class="likpos_button">
								<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M11.5878 2H7.40656C3.84184 2 0.950684 4.89112 0.950684 8.46098C0.950684 12.0308 3.84184 14.922 7.41174 14.922H8.84177C8.90394 14.922 8.95058 14.9738 8.94539 15.0359L8.66561 17.3623C8.60861 17.8545 9.16301 18.181 9.56715 17.8908L15.0386 13.9168C17.0023 12.4609 18.0489 10.7614 18.0489 8.46098C18.0489 4.89112 15.1577 2 11.5878 2Z" stroke="#666666" stroke-width="1.5"></path>
								</svg>
								댓글
							</button>
						</div>
					</div>
					<% } %>
				</div>
				<div class="comment_down_inner">
				<% for(ReplyDto dto : showReply) { %>
					<!-- 여기서부터 코멘트 div 시작 -->
					<div class="comment_list">
						<div class="comment_list_box">
							<div class="list_img_box">
								<img class="comment_user_img" src="<%=dto.getProfile_img() %>">
							</div>
							<div class="list_comment_content_box">
								<div class="list_comment_user_name">
									<div class="list_comment_user_name_box">
										<!-- 닉네임 -->
										<%=dto.getNickname() %>
									</div>
									<div class="comment_writedate">
										<!-- 댓글 작성일자 -->
										<%=dto.getReply_date() %>
									</div>
								</div>
								<div class="list_comment_content">
									<!-- 댓글 내용 -->
									<%=dto.getReply_content() %>
									<%=dto.getReply_idx() %>
								</div>
								<div class="list_comment_likcou">
									<div class="likcou_box">
									<% 
										boolean checkLike = dDao.checkLike(dto.getReply_idx(), mm_idx); 
									%>
								        <!-- 댓글 좋아요 버튼 -->
								        <div class="likcou_text" style="display: <%=checkLike ? "none" : "flex" %>">
								            <input type="hidden" name="reply_idx" value="<%=dto.getReply_idx() %>">
								            <input type="hidden" name="mm_idx" value="<%=mm_idx %>">
								            좋아요
								        </div>
								        <div class="like_revoke_button" style="display: <%=checkLike ? "flex" : "none" %>">
								            <input type="hidden" name="reply_idx" value="<%=dto.getReply_idx() %>">
								            <input type="hidden" name="mm_idx" value="<%=mm_idx %>">
								            좋아요 취소
								        </div>
								        <!-- 댓글 좋아요 취소 버튼 -->
										<div class="likcou_img">
											<svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
											<path d="M5.70833 10.15H3.70312C3.03866 10.15 2.5 10.6887 2.5 11.3531V16.8221C2.5 17.4865 3.03866 18.0252 3.70312 18.0252H5.70833" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
											<path fill-rule="evenodd" clip-rule="evenodd" d="M16.6553 15.7475C16.4815 17.0515 15.3692 18.0252 14.0537 18.0252H5.30566V9.61157L7.45753 2.07233C7.56975 1.67916 7.9397 1.41677 8.33929 1.5034C9.30034 1.71174 10.9255 2.32596 10.9255 4.09743V6.62225C10.9255 6.84374 11.105 7.02329 11.3265 7.02329H15.1911C16.5836 7.02329 17.6568 8.25105 17.4717 9.63127C17.1984 11.6699 16.927 13.7087 16.6553 15.7475Z" stroke="#666666" stroke-width="1.5" stroke-linejoin="round"></path>
											</svg>
																					
											<div class="reply_like_count">
												<%=dto.getReply_like_idx() %>
											</div>
										</div>
									</div>
								<% if(mm_idx.equals(dto.getMm_idx())) { %>	
									<div class="modify_box">
										<svg class="modify_button" viewBox="0 0 24 24" focusable="false" height="24" width="24"><path d="M0 0h24v24H0z" fill="none"></path><path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path></svg>
									</div>
								<% } else { %>
										<div class="modify_box" style="display: none"></div>
								<% }%>
								</div>
								<div class="modify_select">
									<div class="modify_text">
										댓글 수정<%=dto.getReply_idx() %>
										<input type="hidden" id="reply_upd_idx" name="reply_upd_idx" value="<%=dto.getReply_idx() %>">
									</div>
									<!-- 댓글 수정 클릭 시 뜨는 창 -->
									<div class="post_main_screen">
										<div class="insert_comment">
											<header class="insert_header_box">
												<div class="insert_title_box">댓글</div>
												<button class="insert_out">
													<svg focusable="false" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"></path></svg>
												</button>
											</header>
											<!-- 댓글 수정 -->
											<form action="<%=request.getContextPath() %>/Controller" method="post">
												<div class="insert_content_box">
													<input type="hidden" name="watcha" value="watcha_comment_update_action">
													<textarea name="reply_upd_content" class="insert_box"></textarea>
													<input type="hidden" name="reply_idx" value="<%=dto.getReply_idx() %>">
													<%=dto.getReply_idx() %>
													<input type="hidden" name="eva_idx" value="<%=eva_idx %>">
													<div class="down_box">
														<div class="korean_count">0/10000</div>
														<button class="update_button" type="submit">수정</button>
													</div>
												</div>
											</form>
											<!-- 댓글 수정 여기까지 -->
										</div>
									</div>
									<!-- 댓글 수정 클릭 시 뜨는 창 여기까지 -->	
									<!-- 댓글 삭제 Action -->
									<form action="<%=request.getContextPath() %>/Controller" method="post">
											<input type="hidden" name="watcha" value="watcha_comment_delete_action">
											<input type="hidden" name="eva_idx" value="<%=eva_idx %>">
											<input type="hidden" name="reply_idx" value="<%=dto.getReply_idx() %>">
										<button class="delete_text">댓글 삭제</button>
									</form>
									<!-- 댓글 삭제 Action -->
									<!-- 나가기 버튼 -->
									<button class="close_text">나가기</button>								
									<!-- 나가기 버튼 여기까지 -->
								</div>
							</div>
						</div>
					</div>
					<!-- 여기서부터 코멘트 div 끝 -->
				<% } %>
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