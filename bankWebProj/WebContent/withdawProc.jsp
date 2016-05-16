<%@page import="com.bank.dao.DealDAO"%>
<%@page import="com.bank.vo.Deal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String selMid = request.getParameter("selMid");
	String account = request.getParameter("account");
	String amount = request.getParameter("amount");
	String content = request.getParameter("content");
	//content=new String(content.getBytes("ISO-8859-1"), "UTF-8");
	int money = Integer.parseInt(amount);
	Deal deal = null;
	DealDAO dao = new DealDAO();
	int total;
	//가장최근 거래의 발란스 가져오기
	deal = dao.searchDeal(account);
	if(deal == null) {
		total = -1;
		deal = new Deal();
	} else {
		total = deal.getBalance() - money;
	}
	if(total < 0) {
		String error = "low balance";
		response.sendRedirect("dealMenu.jsp?error="+error+"&account="+account);
	} else {
		deal.setFaccountNum(account);
		deal.setKind("wi");
		deal.setContent(content);
		deal.setAmount(money);

		deal.setBalance(total);
		int af = dao.insertDeal(deal);

		if (af > 0) {
			response.sendRedirect("dealMenu.jsp?account="+account+"&selMid="+selMid);
		}
	}
%>  