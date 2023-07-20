<%--
	이 페이지의 목적은 로그인을 처리해주는 작업
 --%>
<%@page import="data.User"%>
<%@page import="repository.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String url = request.getParameter("url");
	String userId = request.getParameter("id");
	String password = request.getParameter("pass");
	
	User one = Users.findById(userId);
	if(one == null) {
		response.sendRedirect("./login.jsp?error");
	} else {
	
		String dbPass = one.getPass();
		if(password.equals(dbPass)) {
			session.setAttribute("logon", true);
			response.sendRedirect(url);
			session.setAttribute("logonUser", one);
		}else {
			response.sendRedirect("./login.jsp?error");
		}
	}
%>