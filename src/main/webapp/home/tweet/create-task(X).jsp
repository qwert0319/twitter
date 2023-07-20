<%@ page import="java.io.File"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MultipartRequest multi = 
		new MultipartRequest(request, "d:\\attach", 1024*1024*20, "utf-8");

	String body = multi.getParameter("body");
	File file = multi.getFile("attach");
	
%>
<p>
	body : <%=body %>
</p>
<p>
	File : <%=file %>
</p>
<div>
	COS 라이브러리에서 제공하는 MultipartRequest 는	multiple 을 처리하기에 적합하지 않음.
	그래서 다른 클래스를 사용해야 함.
</div>
