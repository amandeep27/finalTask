package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.web.command.Command;
import com.web.command.exception.CommandException;
import com.web.domain.BookAuthor;
import com.web.domain.BookInfo;
import com.web.domain.BookType;
import com.web.service.BookService;
import com.web.service.exception.ServiceException;
import com.web.service.factory.ServiceFactory;

public class GetBookInfo implements Command {

	private static ServiceFactory serviceFactoryInstance;
	private static BookService bookInfoServiceInstance;
	private static BookInfo bookInfo;
	private static BookAuthor author;
	private static BookType type;
	private static HttpSession httpSession;

	private static final Logger LOGGER = Logger.getLogger(GetBookInfo.class);
	private static final String LANG_TYPE = "langType";
	private static final String ADMINISTRATOR_UPDATE_PAGE = "WEB-INF/jsp/updateBook.jsp";
	private static final String PAGE = "page";

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		httpSession = request.getSession();
		String langType = (String) httpSession.getAttribute(LANG_TYPE);
		request.setAttribute(PAGE, ADMINISTRATOR_UPDATE_PAGE);
		bookInfo = getBookInfoObject(request);
		serviceFactoryInstance = ServiceFactory.getServiceFactoryInstance();
		bookInfoServiceInstance = serviceFactoryInstance.getBookServiceInstance();
		try {
			bookInfo = bookInfoServiceInstance.getBookInfoObject(bookInfo, langType);
		} catch (ServiceException exception) {
			LOGGER.info("Service Exception");
			throw new CommandException(exception);
		}
		LOGGER.info(bookInfo);
		request.setAttribute("book", bookInfo);
		httpSession.setAttribute("list", bookInfo);

	}

	private BookInfo getBookInfoObject(HttpServletRequest request) {
		author = new BookAuthor(request.getParameter("author"));
		type = new BookType(request.getParameter("type"));
		bookInfo = new BookInfo();
		bookInfo.setBookId(Integer.parseInt(request.getParameter("id")));
		bookInfo.setAuthor(author);
		bookInfo.setType(type);
		return bookInfo;
	}

}
