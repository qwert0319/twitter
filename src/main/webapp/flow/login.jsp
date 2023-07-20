<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String error = request.getParameter("error");
	String url = request.getParameter("url");
	url = url == null? "/home/index.jsp" : url;
%>
<%@ include file="/commons/top.jsp"%>
<%@ include file="/commons/cancel.jsp"%>
<div class="root">
	<form action="./login-task.jsp" method="post" class="join-form">
		<input type="hidden" name="url" value="<%=url%>"/>
		<h2>#트위터에 로그인하기</h2>
		<div style="margin: 0.4em">
			<input type="text" placeholder="아이디" name="id" value="master" 
				class="join-input" required/>
		</div>
		<div style="margin: 0.4em">
			<input type="password" placeholder="비밀번호" name="pass"
				value="1111"
				class="join-input" />
		</div>
		<%if(error!= null){ %>
		<div style="color:red">
			아이디 또는 비밀번호가 일치하지 않습니다.
		</div>
		<%} %>
		<div style="margin: 0.4em">
			<button type="submit" class="join-btn">다음</button>
		</div>
	</form>
	<p style="flex:0.1">
		계정이 없으신가요? <a href="./join.jsp">가입하기</a>
	</p>
</div>
<%@ include file="/commons/bottom.jsp"%>