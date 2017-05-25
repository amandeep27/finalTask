package com.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import com.web.domain.UserInfo;

public class RoleFilter implements Filter {

	private static HttpSession httpSession;
	private static UserInfo userInfo;

	private static final String VALID_USER = "validUser";
	private static final Logger LOGGER = Logger.getLogger(RoleFilter.class);
	private static final String LOGIN_PAGE = "login.jsp";
	private static final String USER_INFO = "userInfo";
	private static final String ADMINISTRATOR = "administrator";
	private static final String USER_HOME_PAGE = "userHome";

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info("in authentication filter!!");
		checkForValidUser(req, res, chain);
	}

	private void checkForValidUser(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		httpSession = request.getSession();
		if (httpSession.getAttribute(VALID_USER) != null && (Boolean) httpSession.getAttribute(VALID_USER)) {
			checkForURI(req, res, chain);
		} else {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(LOGIN_PAGE);
		}

	}

	private void checkForURI(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (request.getRequestURI().contains("/FinalWeb04/administratorHome")) {
			checkForRole(req, res, chain);
		} else {
			chain.doFilter(req, res);
		}
	}

	private void checkForRole(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		httpSession = request.getSession();
		userInfo = (UserInfo) httpSession.getAttribute(USER_INFO);
		if (userInfo.getUserRole().equalsIgnoreCase(ADMINISTRATOR)) {
			chain.doFilter(req, res);
		} else {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(USER_HOME_PAGE);
		}

	}

	public void destroy() {

	}

}
