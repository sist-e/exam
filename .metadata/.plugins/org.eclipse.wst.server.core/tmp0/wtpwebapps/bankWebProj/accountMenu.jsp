<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String selMid = request.getParameter("selMid");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>계좌 메뉴</p>
	<ul>
		<li>
			<a href="createAccount.jsp?selMid=<%=selMid %>">계좌 생성</a>  <!-- href 하이퍼레퍼런스 누르면 이동 -->
		</li>
		<li>
			<a href="AccountList.jsp?selMid=<%=selMid %>">계좌 조회</a>
		</li>
	</ul>
</body>
</html>