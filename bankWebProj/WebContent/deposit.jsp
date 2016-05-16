<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String account = request.getParameter("account");
   String selMid = request.getParameter("selMid");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>입금</title>
</head>
<body>
   <p>입금 정보 입력하세요.</p>
   <form action="depositProc.jsp" method="post">
      <ul>
         <li>
            <label>입금 내용:</label>
            <input name="content" value=""/>
         </li>
         <li>
            <label>입금 금액:</label>
            <input name="amount" value=""/>
         </li>
      </ul>
      <input type="hidden" name="kind" value="de" />
      <input type="hidden" name="selMid" value="<%=selMid %>" />
      <input type="hidden" name="account" value="<%=account %>" />
      <input type="submit" value="입금" />
   </form>
      <li>
         <a href="dealMenu.jsp?selMid=<%=selMid %>&account=<%=account %>"> > 거래 메뉴로 가기 </a>
      </li>
</body>
</html>