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

import org.apache.log4j.Logger;

public class NoCacheFilter implements Filter {
	private static final Logger LOGGER = Logger.getLogger(NoCacheFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		LOGGER.info("in no cache filter" + request.getRequestURI());
		LOGGER.info(request.getRequestURL());
		LOGGER.info(request.getRequestURL().toString());
		LOGGER.info(request.getQueryString());
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(req, res);

	}

	public void init(FilterConfig config) throws ServletException {

	}

}
