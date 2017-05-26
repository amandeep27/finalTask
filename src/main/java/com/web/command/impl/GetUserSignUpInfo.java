package com.web.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import com.web.command.Command;
import com.web.command.exception.CommandException;
import com.web.dto.SignUpInfo;
import com.web.service.UserService;
import com.web.service.exception.ServiceException;
import com.web.service.factory.ServiceFactory;

public class GetUserSignUpInfo implements Command {

	private static SignUpInfo signUp;
	private static ServiceFactory serviceFactoryInstance;
	private static UserService userService;
	private static GetUserSignInInfo signIn;
	private static HttpSession httpSession;

	private static final Logger LOGGER = Logger.getLogger(GetUserSignUpInfo.class);
	private static final String PAGE = "page";
	private static final String LOGIN_PAGE = "login.jsp";
	private static final String SIGNUP_PAGE = "signUp.jsp";
	private static final String ERROR = "signUpError";
	private static final String DUPLICATE_ENTRY_ERROR = "Duplicate entry for entered UserName!!";

	public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		boolean flag = false;
		signUp = setSignUpInfo(request);
		serviceFactoryInstance = ServiceFactory.getServiceFactoryInstance();
		userService = serviceFactoryInstance.getUserServiceInstance();
		try {
			request.setAttribute(PAGE, LOGIN_PAGE);
			flag = userService.saveInfoInDB(signUp);
			if (flag) {
				LOGGER.info("Logged in!!");
				request.setAttribute("name", signUp.getName());
				request.setAttribute("password", signUp.getPassword());
				signIn = new GetUserSignInInfo();
				signIn.execute(request, response);
			}
		} catch (ServiceException exception) {
			httpSession = request.getSession();
			httpSession.setAttribute(ERROR, DUPLICATE_ENTRY_ERROR);
			request.setAttribute(PAGE, SIGNUP_PAGE);
			LOGGER.error("Service Exception");
			throw new CommandException(exception);
		}
	}

	private SignUpInfo setSignUpInfo(HttpServletRequest request) {
		signUp = new SignUpInfo();
		signUp.setName(request.getParameter("name"));
		signUp.setUserName(request.getParameter("uName"));
		signUp.setUserRole(request.getParameter("role"));
		signUp.setPassword(request.getParameter("pwd"));
		signUp.setRepeatPassword(request.getParameter("pwd-repeat"));
		signUp.setLanguage(request.getParameter("lang"));
		return signUp;
	}

}
