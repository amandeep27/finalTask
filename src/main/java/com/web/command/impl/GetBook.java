package com.web.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.web.command.Command;
import com.web.command.exception.CommandException;
import com.web.domain.BookInfo;
import com.web.domain.UserInfo;
import com.web.dto.GetBookInfo;
import com.web.service.BookService;
import com.web.service.exception.ServiceException;
import com.web.service.factory.ServiceFactory;

public class GetBook implements Command {

	private static ServiceFactory serviceFactoryInstance;
	private static BookService bookInfoServiceInstance;
	private static GetBookInfo bookInfo;
	private static UserInfo userInfo;
	private static HttpSession httpSession;

	private static final Logger LOGGER = Logger.getLogger(GetBook.class);
	private static final String USER_INFO = "userInfo";
	private static final String USER = "user";
	private static final String PAGE = "page";
	private static final String DISPLAY_USER_PAGE = "WEB-INF/jsp/userHome.jsp";
	private static final String DISPLAY_ADMINISTRATOR_PAGE = "WEB-INF/jsp/administratorHome.jsp";
	private static final String HAS_RESULT = "has_result";
	private static final String GOT_RESULTS = "gotResult";
	private static final boolean RESULTS = true;

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		bookInfo = new GetBookInfo();
		List<BookInfo> bookList = null;
		boolean has_result;

		httpSession = request.getSession();
		userInfo = (UserInfo) httpSession.getAttribute(USER_INFO);
		LOGGER.info(userInfo);
		bookInfo.setUserInfo(userInfo);
		serviceFactoryInstance = ServiceFactory.getServiceFactoryInstance();
		bookInfoServiceInstance = serviceFactoryInstance.getBookServiceInstance();
		try {
			if (!request.getParameter("title").isEmpty()) {
				bookInfo.setTitle(request.getParameter("title"));
			}
			if (!request.getParameter("author").isEmpty()) {
				bookInfo.setAuthor(request.getParameter("author"));
			}

			bookInfo.setType(request.getParameter("type"));
			bookList = bookInfoServiceInstance.getBookInfo(bookInfo);
			LOGGER.info(bookList);
			if (!bookList.isEmpty()) {
				request.setAttribute("bookList", bookList);
				has_result = true;
			} else {
				has_result = false;
			}
			request.setAttribute(HAS_RESULT, has_result);
			if (userInfo.getUserRole().equalsIgnoreCase(USER)) {
				request.setAttribute(PAGE, DISPLAY_USER_PAGE);
			} else {
				request.setAttribute(PAGE, DISPLAY_ADMINISTRATOR_PAGE);
			}

			request.setAttribute(GOT_RESULTS, RESULTS);
		} catch (ServiceException exception) {
			LOGGER.info("Service Exception");
			throw new CommandException(exception);
		}

	}

}
