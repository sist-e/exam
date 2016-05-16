<%@page import="com.bank.dao.AccountDAO"%>
<%@page import="com.bank.vo.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String accountNum = request.getParameter("accountNum");
	String pwd = request.getParameter("pwd");
	String kind = request.getParameter("kind");
	String locations = request.getParameter("locations");
	String selMid = request.getParameter("selMid");
	
	AccountDAO dao = new AccountDAO();
	Account checka = dao.searchAccount(accountNum);
	if(checka!=null) {
		Account tempA = new Account();
		tempA.setPwd(pwd);
		tempA.setKind(kind);
		tempA.setLocations(locations);
		tempA.setOwner(selMid);
		request.setAttribute("tempA", tempA);
		request.setAttribute("error", "account number is exist.");
		request.getRequestDispatcher("createAccount.jsp").forward(request, response);
	} else {	

		Account aa = new Account();
		aa.setAccountNum(accountNum);
		aa.setPwd(pwd);
		aa.setKind(kind);
		aa.setLocations(locations);
		aa.setOwner(selMid);

		int af = dao.addAccount(aa);
		if (af > 0) {
			response.sendRedirect("accountMenu.jsp?selMid=" + selMid);
		}
	}
%>  