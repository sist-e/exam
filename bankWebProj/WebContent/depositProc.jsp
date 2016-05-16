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
	deal = dao.searchDeal(account);
	int total;
	if(deal==null) {
		total = money;
		deal = new Deal();
	} else {
		total = money + deal.getBalance();
	}
	deal.setBalance(total);
	deal.setFaccountNum(account);
	deal.setAmount(money);
	deal.setContent(content);
	deal.setKind("de");
	int af = dao.insertDeal(deal);
	if(af>0) {
		//객체로 넘겨주는법 (위에 따로 request로 받음)
		//AccountDAO adao = new AccountDAO();
		//Account a = adao.searchAccount(account);
		response.sendRedirect("dealMenu.jsp?account="+account+"&selMid="+selMid);
	} 	
%>  