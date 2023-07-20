<%@page import="java.io.File"%>
<%@page import="com.oreilly.servlet.multipart.FilePart"%>
<%@ page import="com.oreilly.servlet.multipart.ParamPart"%>
<%@ page import="com.oreilly.servlet.multipart.Part"%>
<%@ page import="com.oreilly.servlet.multipart.MultipartParser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	MultipartParser parser = new MultipartParser(request, 1024 * 1024 * 50);
	parser.setEncoding("utf-8");
	
	while(true) {
		Part part = parser.readNextPart();
		if(part == null)
			break;
		
		out.println("<p>"+part+"</p>");
		if(part.isParam()) {
			ParamPart param = (ParamPart) part;
			out.println("<p>" + param.getName() + " / " + param.getStringValue()+"</p>");
		} else {
			FilePart file = (FilePart) part;
			out.println("<p>" + file.getName() + " / " +  file.getFileName() +" / "  +"</p>");
			// 자동으로 업로드가 처리가 안되기 때문에
			// 수동으로 어디다가 write 할껀지 결정해서
			File target = new File("d:\\attach", file.getFileName());
			
			file.writeTo(target);	// 진행
		}
	}
	
%>


















