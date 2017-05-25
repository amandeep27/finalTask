package com.web.service.factory;

import com.web.service.BookService;
import com.web.service.UserService;
import com.web.service.impl.BookServiceImpl;
import com.web.service.impl.UserServiceImpl;

public class ServiceFactory {

	private static volatile ServiceFactory serviceFactoryInstance;
	private static UserService userInfo = new UserServiceImpl();
	private static BookService bookInfo = new BookServiceImpl();

	private ServiceFactory() {

	}

	public static ServiceFactory getServiceFactoryInstance() {
		if (serviceFactoryInstance == null) {
			synchronized (ServiceFactory.class) {
				if (serviceFactoryInstance == null) {
					serviceFactoryInstance = new ServiceFactory();
				}
			}
		}
		return serviceFactoryInstance;
	}

	public UserService getUserServiceInstance() {
		return userInfo;
	}

	public BookService getBookServiceInstance() {
		return bookInfo;
	}

}
