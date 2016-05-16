<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String error = request.getParameter("error");
	String account = request.getParameter("account");
	String selMid = request.getParameter("selMid");
	
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
		<p>거래 메뉴</p>
	<% }%>
	<ul>
		<li>
			<a href="dealList.jsp?account=<%=account %>&selMid=<%=selMid%>">[1] 거래내역조회</a>
		</li>
		<li>
			<a href="deposit.jsp?account=<%=account %>&selMid=<%=selMid%>">[2] 입금</a>
		</li>
		<li>
			<a href="withdraw.jsp?account=<%=account %>&selMid=<%=selMid%>">[3] 출금</a>
		</li>
		<li>
			<a href="transfer.jsp?account=<%=account %>&selMid=<%=selMid%>">[4] 이체</a>
		</li>
		<li>
			<a href="AccountList.jsp?selMid=<%=selMid%>" >[5] 되돌아가기</a>
		</li>
	</ul>
</body>
</html>