<%@page import="com.bank.dao.DealDAO"%>
<%@page import="com.bank.vo.Deal"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String account = request.getParameter("account");
	String selMid = request.getParameter("selMid");
	
	List<Deal> list = null;
	DealDAO dao = new DealDAO();
	list = dao.getDeals(account);
	
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
			<th>거래종류</th>
			<th>거래내용</th>
			<th>거래날짜</th>
			<th>입/출금</th>
			<th>잔고</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(int i=0; i<list.size();i++) {
			%>
			<tr>
				<td><%=list.get(i).getKind() %></td>
				<td><%=list.get(i).getContent() %></td>
				<td><%= list.get(i).getDealdate() %></td>
				<td><%= list.get(i).getAmount() %></td>
				<td><%= list.get(i).getBalance() %></td>
			</tr>
			<% } %>
		</tbody>
	</table>
	<a href = "dealMenu.jsp?selMid=<%=selMid%>&account=<%=account%>" > > 거래 메뉴로 되돌아가기</a>

</body>
</html>