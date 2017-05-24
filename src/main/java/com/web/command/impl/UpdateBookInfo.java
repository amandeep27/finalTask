package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.web.command.Command;
import com.web.command.exception.CommandException;
import com.web.domain.BookInfo;
import com.web.dto.Book;
import com.web.service.BookService;
import com.web.service.exception.ServiceException;
import com.web.service.factory.ServiceFactory;

public class UpdateBookInfo implements Command {
	private static ServiceFactory serviceFactoryInstance;
	private static BookService bookInfoServiceInstance;
	private static Book bookNewInfo;
	private static BookInfo bookOldInfo;
	private static HttpSession httpSession;

	private static final Logger LOGGER = Logger.getLogger(UpdateBookInfo.class);
	private static final String LANG_TYPE = "langType";
	private static final String ADMINISTRATOR_HOME_PAGE = "WEB-INF/jsp/administratorHome.jsp";
	private static final String PAGE = "page";
	private static final String BOOK_LIST = "list";

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		LOGGER.info("Title : " + request.getParameter("title"));
		LOGGER.info("Desc : " + request.getParameter("description"));
		LOGGER.info("Year : " + request.getParameter("year"));
		LOGGER.info("Author : " + request.getParameter("author"));
		LOGGER.info("Type : " + request.getParameter("type"));

		boolean isUpdated = false;

		httpSession = request.getSession();
		String langType = (String) httpSession.getAttribute(LANG_TYPE);
		bookOldInfo = (BookInfo) httpSession.getAttribute(BOOK_LIST);
		bookNewInfo = getUpdatedBookInfoObject(request, langType);

		serviceFactoryInstance = ServiceFactory.getServiceFactoryInstance();
		bookInfoServiceInstance = serviceFactoryInstance.getBookServiceInstance();
		try {
			isUpdated = bookInfoServiceInstance.updateBookInfo(bookOldInfo, bookNewInfo);
		} catch (ServiceException exception) {
			LOGGER.info("Service Exception");
			throw new CommandException(exception);
		}
		if (isUpdated) {
			LOGGER.info("updated");
		} else {
			LOGGER.info("not updated");
		}
		request.setAttribute(PAGE, ADMINISTRATOR_HOME_PAGE);
		httpSession.removeAttribute(BOOK_LIST);
	}

	private Book getUpdatedBookInfoObject(HttpServletRequest request, String langType) {
		bookNewInfo = new Book();
		bookNewInfo.setBookLanguage(langType);
		bookNewInfo.setTitle(request.getParameter("title"));
		bookNewInfo.setDescription(request.getParameter("description"));
		bookNewInfo.setPublishYear(Integer.parseInt(request.getParameter("year")));
		bookNewInfo.setAuthor(request.getParameter("author"));
		bookNewInfo.setType(request.getParameter("type"));
		return bookNewInfo;
	}

}
