<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String logoutMessage = (String) request.getAttribute("logoutMessage");
	if(logoutMessage != null) {
%>		
	<script>
		alert("<%=logoutMessage %>");
	</script>
<% 
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그아웃 페이지</title>
</head>
<body>
	
</body>
</html>