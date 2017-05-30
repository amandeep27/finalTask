package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.web.command.Command;
import com.web.command.exception.CommandException;

public class GetPage implements Command {
	private static final String PAGE = "page";

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String page = request.getParameter(PAGE);
		request.setAttribute(PAGE, page);

	}

}
