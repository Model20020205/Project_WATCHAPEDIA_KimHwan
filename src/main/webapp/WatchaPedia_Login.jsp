<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
// 	Integer mm_idx = (Integer) session.getAttribute("mm_idx");
// 	// 로그인 후 뒤로가기 시 로그인창 표출되는 이슈 확인 필요(세션 체크 후 메인페이지 이동 등) 20240921
// 	if(mm_idx!=null) {
// 		RequestDispatcher rd = request.getRequestDispatcher("WatchaPedia_Detail_Page.jsp");
// 		rd.forward(request, response);
// 	}
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>로그인 페이지</title>
	<link rel="stylesheet" href="WatchaPedia_Login.css">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	
	<script>
		/* 유효성 체크 */
		$(function(){
			$("#loginCheck").click(function(e){
				// 기본 제출 방지
				e.preventDefault();
				
				// 아이디, 비밀번호의 값을 변수에 저장
				let user_id = $("#user_id").val();
				let user_pw = $("#user_pw").val();
				
				// 아무것도 입력하지 않았을 때 (아이디)
				if(user_id==="") {
					alert("아이디를 입력해주세요.");
					$("#user_id").focus();
					return false;
				}
				
				// 아무것도 입력하지 않았을 때 (비밀번호)
				if(user_pw==="") {
					alert("비밀번호를 입력해주세요.");
					$("#user_pw").focus();
					return false;
				}
				
				// 이메일 형식 유효성 체크
				// 정규표현식.test(대상문자열)
				let email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
				if(!email.test(user_id)) {
					alert("이메일 형식에 맞게 다시 입력해주세요.");
					return false;
				}
				
				// 비밀번호는 형식 영문자 유효성 체크
				let pw = user_pw.charAt(0);
				if(!((pw>='A' && pw<='Z') || (pw>='a' && pw<='z'))) {
					alert("비밀번호는 영문자로 시작해주세요.");
					return false;
				}
				
				alert("로그인되었습니다.");
				$("form").submit();
			});
		});
		/* 유효성 체크 여기까지 */
	</script>
	
</head>
<body>
	<!-- 로그인 여기서부터 -->
	<div class="login_main_screen">
		<div class="login_box">
			<div class="login_inner">
				<div class="login_header">
					<img src="img/watcha.png">
				</div>
				<div class="login_word">로그인</div>
				<div class="input_box">
					<div class="input_inner">
						<div class="input_inner_box">
						<!-- 로그인 Action -->
							<form name="frm" action="<%=request.getContextPath() %>/Controller" method="post">
								<input type="hidden" name="watcha" value="login_action">
								<div class="input_ep">
									<input id="user_id" name="id" type="text" placeholder="이메일" />
								</div>
								<div class="input_ep">
									<input id="user_pw" name="password" type="password" placeholder="비밀번호" />
								</div>
								<div class="login_button_box">
									<input type="submit" class="login_button" value="로그인" id="loginCheck">
								</div>
							</form>
							<script>
								if(<%=request.getAttribute("loginfal") != null %>) {
									alert("로그인에 실패하였습니다.");
								}
							</script>
						<!-- 로그인 Action 여기까지 -->
						</div>
						<div class="account_ask">
							<div class="account_box">
								계정이 없으신가요?
							</div>
						<!-- 회원가입 페이지 이동 -->
							<a class="register_ask" href="WatchaPedia_Register.jsp">회원가입</a>
						<!-- 회원가입 페이지 이동 여기까지 -->
						</div>
						<!-- 메인 페이지 이동 -->
							<a class="register_ask" href="Controller?watcha=watcha_main_action">홈페이지로 이동</a>
						<!-- 메인 페이지 이동 여기까지 -->
						<div class="hr_line_box">
							<hr class="hr_line">
							<div class="line_word">
								OR
							</div>
							<hr class="hr_line">
						</div>
						<div class="social_login">
							<div class="social_img">
								<img src="https://an2-ast.amz.wtchn.net/ayg/images/icSocialKakao.444bf93dafbd611eb4d1.svg?as-resource">
							</div>
							<div class="social_img">
								<img src="https://an2-ast.amz.wtchn.net/ayg/images/icSocialGoogle.a946248e0b9a978c9f77.svg?as-resource">
							</div>
							<div class="social_img">
								<img src="https://an2-ast.amz.wtchn.net/ayg/images/icSocialTwitter.cc596d57b16f837b9729.svg?as-resource">
							</div>
							<div class="social_img">
								<img src="https://an2-ast.amz.wtchn.net/ayg/images/icSocialApple.484c0da1e6a00a23e23f.svg?as-resource">
							</div>
							<div class="social_img">
								<img src="https://an2-ast.amz.wtchn.net/ayg/images/icSocialLine.7f8a1fd121c282009db6.svg?as-resource">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 로그인 여기까지 -->
</body>
</html>