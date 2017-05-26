package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.web.command.Command;
import com.web.command.exception.CommandException;

public class ChangePageLanguage implements Command {

	private static final String PAGE = "page";
	private static final String LOGIN_PAGE = "/login.jsp";
	private static final String SIGN_UP_PAGE = "/signUp.jsp";
	private static final String LANG_TYPE = "langType";

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		request.setAttribute(LANG_TYPE, request.getParameter(LANG_TYPE));
		if (request.getParameter("newpage") != null && request.getParameter("newpage").equals("signup")) {
			request.setAttribute(PAGE, SIGN_UP_PAGE);
		} else {
			request.setAttribute(PAGE, LOGIN_PAGE);
		}

	}

}
