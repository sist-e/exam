<%@page import="com.bank.dao.MemberDAO"%>
<%@page import="com.bank.vo.Member"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String mid = request.getParameter("mid");
	String pwd = request.getParameter("pwd");

	////-------------------db code
	
	MemberDAO dao = new MemberDAO();
	Member member = dao.getMember(mid);
	
	////-----------------------------------------------------------
	if (member == null) {
		//String error = "ID not exist";
		String error = "아이디 없음.";
		//다시 login.jsp로 보내주는 일시키기. 페이지 이동방식1
		//response.sendRedirect("bankLogin.jsp?error="+error); //?lang으로 상태값전달
		response.sendRedirect("bankLogin.jsp?error="+URLEncoder.encode(error,"UTF-8"));		
	} else if (!member.getPwd().equals(pwd)) {
		String error = "PWD is incorrect";
		response.sendRedirect("bankLogin.jsp?error="+error);
	} else {
		String selMid = member.getMid();
		response.sendRedirect("accountMenu.jsp?selMid="+selMid);
	}

%>
