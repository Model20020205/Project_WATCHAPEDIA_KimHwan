<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입 페이지</title>
	<link rel="stylesheet" href="WatchaPedia_Register.css">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		/* 회원가입 유효성 체크 */
		$(function(){
			$("#register_button").click(function(e) {
				// 기본 폼 제출 방지
				e.preventDefault();
				
				// 이름, 이메일, 비밀번호 값 가져오기
				let user_nickname = $("#user_nickname").val();
				let user_id = $("#user_email").val();
				let user_pw = $("#user_password").val();
				
				if(user_nickname=="") {
					$("#user_nickname").focus();
					alert("이름을 입력해주세요.");
					return false;
				}
				if(user_id=="") {
					$("#user_email").focus();
					alert("이메일을 입력해주세요.");
					return false;
				}
				if(user_pw=="") {
					$("#user_password").focus();
					alert("비밀번호를 입력해주세요.");
					return false;
				}
				
				// 이메일 형식 유효성 체크
				let email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
				if(!email.test(user_id)) {
					alert("이메일 형식에 맞게 다시 입력해주세요.");
					return false;
				}
				
				// 비밀번호 형식 영문자 유효성 체크
				let pw = user_pw.charAt(0);
				if(!((pw>='A' && pw<='Z') || (pw>='a' && pw<='z'))) {
					alert("비밀번호는 영문자로 시작해주세요.");
					return false;
				}
				
				// 이메일 중복 체크
				$.ajax ({
					type: 'post',
					url: 'RegisterDuplicationCheck',
					dataType: 'JSON',
					data: { user_id : user_id },
					success: function(res) {
						if(res.result === 1) {
							alert("이미 사용 중인 아이디입니다.");
							e.preventDefault();
						} else {
							alert("사용 가능한 아이디입니다.");
							$("form").submit();
						}
					},
					error: function(a, b, c) {
						alert("실패;;;");
					}
				});
				alert("회원가입이 완료되었습니다.");
 			});
		});
		/* 회원가입 유효성 체크 여기까지 */
	</script>
</head>
<body>
	<!-- 회원가입 여기서부터 시작 -->
	<div class="register_main_screen">
		<div class="login_box">
			<div class="login_inner">
				<div class="register_header">
					<img class="register_img" src="img/watcha.png">
				</div>
				<div class="login_word">회원가입</div>
				<div class="input_box">
					<div class="input_inner">
					<!-- 회원가입 Action -->
						<form action="Controller" method="post" id="submit_proceed">
							<input type="hidden" name="watcha" value="register_action">
							<div class="input_inner_box">
								<div class="input_ep">
									<input id="user_nickname" type="text" name="nickname" placeholder="이름" />
								</div>
								<div class="input_ep">
									<input id="user_email" type="text" name="id" placeholder="이메일" />
								</div>
								<div class="input_ep">
									<input id="user_password" type="password" name="password" placeholder="비밀번호" />
								</div>
								<div class="login_button_box">
									<input type="submit" value="회원가입" id="register_button">
								</div>
							</div>
						</form>
						<!-- 회원가입 Action 여기까지 -->
						
						<div class="account_ask">
							<div class="account_box">
								이미 가입하셨나요?
							</div>
							<!-- 로그인 페이지로 이동 -->
							<a class="register_ask" href="WatchaPedia_Login.jsp">로그인</a>
							<!-- 로그인 페이지로 이동 여기까지 -->
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
	<!-- 회원가입 여기서부터 끝 -->
</body>
</html>