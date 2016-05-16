<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String account = request.getParameter("account");
	String selMid = request.getParameter("selMid");
	//String error = request.getParameter("error");
	//보내는 속성을 바꿧으니까 받는거도 바꿔줘야함
	//object로만 보내주니까 형변환해주기
	String error = (String)request.getAttribute("error");   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>이체</title>
</head>
<body>
	<p>이체 정보 입력하세요.</p>
	<form action="transferProc.jsp" method="post">
		<ul>
			<li>
				<label>이체 계좌번호:</label>
				<input name="transAccountNum" value=""/>
				<%if(error!=null){ %>
				<p><%=error %></p>
				<%} %>
			</li>
			<li>
				<label>이체 금액:</label>
				<input name="amount" value=""/>
			</li>
			<li>
				<label>이체하는 통장 내용:</label>
				<input name="content1" value=""/>
			
			</li>
			<li>
				<label>이체받는 계좌에 적힐 내용:</label>
				<input name="content2" value=""/>
			</li>
		</ul>
		<input type="hidden" name="kind" value="tr" />
		<input type="hidden" name="account" value="<%=account %>" />
		<input type="hidden" name="selMid" value="<%=selMid%>"/>
		<input type="submit" value="이체" />
	</form>
	<a href = "dealMenu.jsp?selMid=<%=selMid%>&account=<%=account%>" > > 거래 메뉴로 되돌아가기</a>  
</body>
</html>