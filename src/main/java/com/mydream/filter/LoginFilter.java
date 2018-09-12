package com.mydream.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

@Component
@WebFilter(urlPatterns = "/*",filterName = "loginFilter")
public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		HttpSession httpSession = httpServletRequest.getSession();
		String uri = httpServletRequest.getServletPath(); //访问路径
		String url = httpServletRequest.getRequestURI().toString();
		System.out.println("1==="+uri);
		System.out.println("2==="+url);
		
		
		boolean tag = new LoginFilter().accessUrl(url);
		
		if(tag) {
			System.err.println("authuer====="+httpSession.getAttribute("authuser"));
			if(httpSession.getAttribute("authuser") == null) {
				httpServletResponse.sendRedirect("login");
				return;
			}
		}
		
		chain.doFilter(httpServletRequest, httpServletResponse);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 判断是否为免验证路径
	 * @param url
	 * @return
	 */
	public boolean accessUrl(String url) {
		ArrayList<String> accessUrl = new ArrayList<>();
		accessUrl.add("login");
		accessUrl.add("assets");
		accessUrl.add("bootstrapvalidator");
		accessUrl.add("css");
		accessUrl.add("fonts");
		accessUrl.add("img");
		accessUrl.add("js");
		accessUrl.add("media");
		accessUrl.add("toMain");
		boolean tag = true;
		for (String string : accessUrl) {
			if(url.contains(string)) {
				tag = false;
			}
		}
		return tag;
	}
	
}
