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

public class GetRequestFilter implements Filter {
	private static HttpSession httpSession;
	private static UserInfo userInfo;

	private static final Logger LOGGER = Logger.getLogger(GetRequestFilter.class);
	private static final String USER_INFO = "userInfo";
	private static final String ADMINISTRATOR = "administrator";
	private static final String USER_HOME_PAGE = "userHome";
	private static final String LOGIN_PAGE = "login.jsp";

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info("in getrequest filter!!");
		if (getQuery(req) != null) {
			checkUserInfoObj(req, res, chain);
		} else {
			chain.doFilter(req, res);
		}

	}

	private String getQuery(ServletRequest req) {
		HttpServletRequest request = (HttpServletRequest) req;
		String queryString = request.getQueryString();
		return queryString;
	}

	private void checkUserInfoObj(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		httpSession = request.getSession();
		userInfo = (UserInfo) httpSession.getAttribute(USER_INFO);
		if (userInfo != null) {
			checkQueryString(req, res, chain);
		} else {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(LOGIN_PAGE);
		}
	}

	private void checkQueryString(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (!getQuery(req).contains("&submit=getBook") && !getQuery(req).contains("submit=getPage")) {
			checkForAdminPages(req, res, chain);
		} else {
			chain.doFilter(req, res);
		}
	}

	private void checkForAdminPages(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (getQuery(req).contains("submit=getBookInfo") || getQuery(req).contains("submit=removeBook")) {
			checkUserRole(req, res, chain);

		}
	}

	private void checkUserRole(ServletRequest req, ServletResponse res, FilterChain chain)
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
