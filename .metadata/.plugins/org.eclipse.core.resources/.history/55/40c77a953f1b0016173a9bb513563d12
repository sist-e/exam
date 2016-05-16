<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String error = request.getParameter("error");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% if(error!=null){ %>
		<p><%=error%></p>
	<% } else{ %>
		<p>로그인을 해주세요.</p>
	<% }%>
	<form action="loginProc.jsp" method="post">  
		<ul>
			<li>
				<label>아이디:</label>
				<input name="mid" value =""/>
			</li>
		</ul>
		<ul>
			<li>
				<label>비밀번호:</label>
				<input name="pwd" value=""/>
			</li>
		</ul>
		<input type="submit" value="로그인"/>  
	</form>

</body>
</html>