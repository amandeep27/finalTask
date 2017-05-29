package com.web.dao.factory;

import com.web.dao.BookDao;
import com.web.dao.UserDao;
import com.web.dao.impl.BookDaoImpl;
import com.web.dao.impl.UserDaoImpl;

public class DaoFactory {

	private static volatile DaoFactory daoFactoryInstance;
	private static UserDao userInfo = new UserDaoImpl();
	private static BookDao bookInfo = new BookDaoImpl();

	private DaoFactory() {

	}

	public static DaoFactory getDaoFactoryInstance() {
		if (daoFactoryInstance == null) {
			synchronized (DaoFactory.class) {
				if (daoFactoryInstance == null) {
					daoFactoryInstance = new DaoFactory();
				}
			}
		}
		return daoFactoryInstance;
	}

	public UserDao getUserDaoInstance() {
		return userInfo;
	}

	public BookDao getBookDaoInstance() {
		return bookInfo;
	}

}
