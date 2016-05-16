<%@page import="com.bank.dao.DealDAO"%>
<%@page import="com.bank.dao.AccountDAO"%>
<%@page import="com.bank.vo.Account"%>
<%@page import="com.bank.vo.Deal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
		String selMid = request.getParameter("selMid");
		String transAccountNum = request.getParameter("transAccountNum");
		String content1 = request.getParameter("content1");
		String content2 = request.getParameter("content2");
		//content1=new String(content1.getBytes("ISO-8859-1"), "UTF-8");
		//content2=new String(content2.getBytes("ISO-8859-1"), "UTF-8");
		String account = request.getParameter("account");
		String am = request.getParameter("amount");
		int amount = Integer.parseInt(am);

		AccountDAO Adao = new AccountDAO();
		Account ta = Adao.searchAccount(transAccountNum);

		if (transAccountNum.equals(account)) {
			request.setAttribute("error", "already selected account.");
			request.getRequestDispatcher("transfer.jsp").forward(request, response);

		} else if (ta == null) {
			//String error = "AccountNumber is no exist";
			//response.sendRedirect("transfer.jsp?error=" + error);

			//방법2
			request.setAttribute("error", "account number is not exist.");
			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		} else {

			Deal td1 = new Deal(); //내 계좌
			Deal td2 = new Deal(); //보낼 계좌

			String kind = "tr";

			td1.setKind(kind);
			td1.setContent(content1);
			td1.setAmount(amount);
			td1.setFaccountNum(account);

			td2.setKind(kind);
			td2.setContent(content2);
			td2.setAmount(amount);
			td2.setFaccountNum(transAccountNum);

			//잔고
			DealDAO Ddao = new DealDAO();

			int balance1 = Ddao.searchDeal(account).getBalance();
			int balance2 = Ddao.searchDeal(transAccountNum).getBalance();

			int total1 = balance1 - amount;
			int total2 = balance2 + amount;

			td1.setBalance(total1);
			td2.setBalance(total2);

			int af = Ddao.transferDeal(td1, td2);

			if (af > 0) {
				response.sendRedirect("dealMenu.jsp?account=" + account + "&selMid=" + selMid);
			}
		}
	%>