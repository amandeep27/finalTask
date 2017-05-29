package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.web.command.Command;
import com.web.command.exception.CommandException;
import com.web.domain.UserInfo;
import com.web.dto.Book;
import com.web.service.BookService;
import com.web.service.exception.ServiceException;
import com.web.service.factory.ServiceFactory;

public class AddBookInDB implements Command {

	private static ServiceFactory serviceFactoryInstance;
	private static BookService bookInfoServiceInstance;
	private static Book book;
	private static HttpSession httpSession;
	private static UserInfo userInfo;
	private static boolean hasError = false;

	private static final String USER_INFO = "userInfo";
	private static final String USER_HOME_PAGE = "userHome";
	private static final String ADMINISTRATOR_HOME_PAGE = "administratorHome";
	private static final String PAGE = "page";
	private static final String USER = "user";
	private static final String ERROR = "AddBookError";
	private static final String USER_ERROR = "Invalid data entered!!";
	private static final Logger LOGGER = Logger.getLogger(AddBookInDB.class);

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		serviceFactoryInstance = ServiceFactory.getServiceFactoryInstance();
		bookInfoServiceInstance = serviceFactoryInstance.getBookServiceInstance();
		book = getBookObject(request);

		httpSession = request.getSession();
		if (hasError) {
			httpSession.removeAttribute(ERROR);
			hasError = false;
		}
		
		userInfo = (UserInfo) httpSession.getAttribute(USER_INFO);
		if (userInfo.getUserRole().equalsIgnoreCase(USER)) {
			request.setAttribute(PAGE, USER_HOME_PAGE);
		} else {
			request.setAttribute(PAGE, ADMINISTRATOR_HOME_PAGE);
		}
		
		try {
			bookInfoServiceInstance.addBook(book);
		} catch (ServiceException exception) {
			hasError = true;
			httpSession.setAttribute(ERROR, USER_ERROR);
			LOGGER.info("Service Exception");
			throw new CommandException(exception);
		}

	}

	private Book getBookObject(HttpServletRequest request) {
		book = new Book();
		book.setBookLanguage(request.getParameter("lang"));
		book.setTitle(request.getParameter("title"));
		book.setDescription(request.getParameter("description"));
		book.setPublishYear(Integer.parseInt(request.getParameter("year")));
		book.setAuthor(request.getParameter("author"));
		book.setType(request.getParameter("type"));
		return book;
	}

}
