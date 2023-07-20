<%@page import="java.io.File"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="data.User"%>
<%@ page import="repository.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	User user = (User)session.getAttribute("logonUser");	
	String id = user.getId();
	
	// SaveDirectory를 resource/profile/[로그인아이디]/랜덤
	String path = application.getRealPath("/resource/profile/"+id);
	File pathFile = new File(path);
	if(! pathFile.exists()) {
		pathFile.mkdirs();
	}
	
	System.out.println("path = " + path);
	MultipartRequest multi = new MultipartRequest(request, path,  1024*1024*20, "utf-8");
	
	String genCode = Long.toString(System.currentTimeMillis());
	// 파일이 안넘어오면 multi.getFile("attach") ==> null
	multi.getFile("attach").renameTo(new File(path, genCode));
	
	String nick = multi.getParameter("nick");
	String profile = multi.getParameter("profile");
	
	String img = "/resource/profile/"+id+"/"+genCode;
	
	int r = Users.updateOne(id, nick, profile, img);
	
	
	if(r == 1) {
		session.setAttribute("logonUser", Users.findById(id));
		response.sendRedirect("./index.jsp");
	
	} else {
		response.sendRedirect("./setting.jsp?error");
	}
%>
<%--
path : <%=path %><br/>
genCode : <%=genCode %><br/>
realFileAddress : <%=new File(path, genCode).getAbsolutePath() %><br/>
uri : <%="/resource/profile/"+id+"/"+genCode %><br/>
nick : <%=nick %><br/>
profile : <%=profile %><br/>
 --%>











