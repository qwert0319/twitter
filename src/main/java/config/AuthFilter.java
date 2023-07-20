package config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.User;

// @WebFilter({"/home/index.jsp", "/home/following.jsp"})
@WebFilter("/home/*")
public class AuthFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, 
							FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		System.out.println("doFilter.. at " + uri + " from " + request.getRemoteAddr());
		
		boolean logon = (boolean)request.getSession().getAttribute("logon");
		User logonUser = (User)request.getSession().getAttribute("logonUser");
		// if 를 걸어서
		// 현재 요청을 보낸 사용자의 세션에 logon 값이 true 면  통과
		
		if(!logon || logonUser == null) {
			response.sendRedirect("/flow/login.jsp?url="+uri);
		}else {
			chain.doFilter(request, response);	// 필터 통과시켜주는
			// 그게 아니면 /flow/login.jsp 로 리 다이렉트 시키는 필터를 만들어보자.
		}
		
	}
}


