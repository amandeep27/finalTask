package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.web.command.Command;
import com.web.command.exception.CommandException;
import com.web.domain.UserInfo;
import com.web.service.BookService;
import com.web.service.exception.ServiceException;
import com.web.service.factory.ServiceFactory;

public class RemoveBook implements Command {

	private static ServiceFactory serviceFactoryInstance;
	private static BookService bookInfoServiceInstance;
	private static HttpSession httpSession;
	private static UserInfo userInfo;

	private static final Logger LOGGER = Logger.getLogger(RemoveBook.class);
	private static final String LANG_TYPE = "langType";
	private static final String USER_INFO = "userInfo";
	private static final String USER_HOME_PAGE = "WEB-INF/jsp/userHome.jsp";
	private static final String ADMINISTRATOR_HOME_PAGE = "WEB-INF/jsp/administratorHome.jsp";
	private static final String PAGE = "page";
	private static final String USER = "user";

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		
		int bookId = Integer.parseInt(request.getParameter("id"));
		httpSession = request.getSession();
		String langType = (String) httpSession.getAttribute(LANG_TYPE);
		userInfo = (UserInfo) httpSession.getAttribute(USER_INFO);
		
		if (userInfo.getUserRole().equalsIgnoreCase(USER)) {
			request.setAttribute(PAGE, USER_HOME_PAGE);
		} else {
			request.setAttribute(PAGE, ADMINISTRATOR_HOME_PAGE);
		}

		serviceFactoryInstance = ServiceFactory.getServiceFactoryInstance();
		bookInfoServiceInstance = serviceFactoryInstance.getBookServiceInstance();
		try {
			bookInfoServiceInstance.removeBook(langType, bookId);
		} catch (ServiceException exception) {
			LOGGER.error("Service Exception");
			throw new CommandException(exception);
		}

	}

}
