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

public class AuthenticationFilter implements Filter {

	private static HttpSession httpSession;
	private static final String VALID_USER = "validUser";
	private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);
	private static final String LOGIN_PAGE = "login.jsp";

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info("in authentication filter!!");
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		httpSession = request.getSession();
		if (httpSession.getAttribute(VALID_USER) != null && (Boolean) httpSession.getAttribute(VALID_USER)) {
			chain.doFilter(req, res);
		} else {
			response.sendRedirect(LOGIN_PAGE);
		}
	}

	public void destroy() {

	}

}
