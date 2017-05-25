package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import com.web.command.Command;
import com.web.command.exception.CommandException;
import com.web.domain.UserInfo;
import com.web.dto.EditInfo;
import com.web.service.UserService;
import com.web.service.exception.ServiceException;
import com.web.service.factory.ServiceFactory;

public class EditUserInfo implements Command {
	private static UserInfo userInfo;
	private static HttpSession httpSession;
	private static EditInfo info;
	private static ServiceFactory serviceFactoryInstance;
	private static UserService userService;

	private static final Logger LOGGER = Logger.getLogger(EditUserInfo.class);
	private static final String USER_INFO = "userInfo";
	private static final String PAGE = "page";
	private static final String EDIT_INFO_PAGE = "editInfo";
	private static final String USER_HOME_PAGE = "userHome";
	private static final String ADMINISTRATOR_HOME_PAGE = "administratorHome";
	private static final String USER = "user";
	private static final String EDIT_INFO_ERROR = "editInfoError";
	private static final String ERROR = "Passwords doesn't match!!";
	private static final String UNAME = "uName";

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		info = getEditInfoObject(request);
		serviceFactoryInstance = ServiceFactory.getServiceFactoryInstance();
		userService = serviceFactoryInstance.getUserServiceInstance();

		httpSession = request.getSession();
		userInfo = (UserInfo) httpSession.getAttribute(USER_INFO);
		if (userInfo.getUserRole().equalsIgnoreCase(USER)) {
			request.setAttribute(PAGE, USER_HOME_PAGE);
		} else {
			request.setAttribute(PAGE, ADMINISTRATOR_HOME_PAGE);
		}
		info.setId(userInfo.getUserId());
		try {
			userService.editUserInformation(info);
			httpSession.setAttribute(UNAME, info.getName());
		} catch (ServiceException exception) {
			request.setAttribute(PAGE, EDIT_INFO_PAGE);
			httpSession.setAttribute(EDIT_INFO_ERROR, ERROR);
			LOGGER.info("Service Exception");
			throw new CommandException(exception);
		}

	}

	private EditInfo getEditInfoObject(HttpServletRequest request) {
		info = new EditInfo();
		info.setName(request.getParameter("uName"));
		info.setOldPwd(request.getParameter("oldpwd"));
		info.setPwd(request.getParameter("pwd"));
		info.setPwdRepeat(request.getParameter("pwd-repeat"));
		return info;
	}

}
