<%@page import="com.bank.vo.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String selMid = request.getParameter("selMid");
	String error = (String)request.getAttribute("error");
	Account a = (Account)request.getAttribute("tempA");
	
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>계좌 정보 입력</p>
	<form action="createAccountProc.jsp" method="post">  
		<ul>
			<li>
				<label>계좌번호:</label>
				<input name="accountNum" value =""/>
				<%if(error!=null){ %>
				<p><%=error %></p>
				<%} %>
			</li>
			<li>
				<label>계좌비밀번호:</label>
				<%if(a!=null) { %>
					<input name="pwd" value="<%= a.getPwd() %>"/>
				<% } else {  %>
					<input name="pwd" value=""/>
				<%} %>
			</li>
			<li>
				<label>계좌종류:</label>
				<%if(a!=null) { %>
					<input name="kind" value ="<%= a.getKind() %>"/>
				<% } else {  %>
					<input name="kind" value =""/>
				<%} %>
			</li>
			<li>
				<label>개설지점:</label>
				<%if(a!=null) { %>
					<input name="locations" value="<%= a.getLocations() %>"/>
				<% } else {  %>
					<input name="locations" value =""/>
				<%} %>
			</li>
		</ul>
		<input type="hidden" name="selMid" value="<%=selMid%>"/>  <!-- 눈에보이진않고 나중에 submit할때 함께 넘어감 -->
		<input type="submit" value="가입"/>  
	</form>
</body>
</html>