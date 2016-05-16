<%@page import="com.bank.dao.AccountDAO"%>
<%@page import="com.bank.vo.Account"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String selMid = request.getParameter("selMid");

	List<Account> list = null;
	AccountDAO dao = new AccountDAO();
	list = dao.getAccounts(selMid);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="3" width=50%>
		<thead>
			<tr>
				<th>계좌번호</th>
				<th>계좌종류</th>
				<th>계좌지점</th>
				<th>개설일</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(int i=0; i<list.size();i++) {
			%>
			<tr>
				<td><a
					href="dealMenu.jsp?selMid=<%=list.get(i).getOwner()%>&account=<%=list.get(i).getAccountNum()%>"><%=list.get(i).getAccountNum() %></a></td>
				<td><%= list.get(i).getKind()%></td>
				<td><%= list.get(i).getLocations()%></td>
				<td><%= list.get(i).getRegdate()%></td>
			</tr>
			<% } %>
		</tbody>
	</table>
	<li>
		<a href="accountMenu.jsp?selMid=<%=selMid %>"> > 계좌 메뉴로 가기 </a>
	</li>


</body>
</html>