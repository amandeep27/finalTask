package com.web.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import com.web.dao.BookDao;
import com.web.dao.exception.DaoException;
import com.web.dao.factory.DaoFactory;
import com.web.domain.BookInfo;
import com.web.domain.BookType;
import com.web.dto.Book;
import com.web.dto.GetBookInfo;
import com.web.service.BookService;
import com.web.service.exception.ServiceException;

public class BookServiceImpl implements BookService {

	private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);

	private static DaoFactory daoFactoryInstance;
	private static BookDao bookInfoDao;
	private static BookType type;

	public List<BookInfo> getBookInfo(GetBookInfo bookInfo) throws ServiceException {
		List<BookInfo> bookList = null;

		try {
			daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
			bookInfoDao = daoFactoryInstance.getBookDaoInstance();
			bookInfo.setType(getBookType(bookInfo.getType()));

			bookList = bookInfoDao.getBookInfo(bookInfo);
		} catch (DaoException exception) {
			LOGGER.error("DAO Exception");
			throw new ServiceException(exception);
		}

		return bookList;
	}

	private String getBookType(String type) {
		if (type.equals("all")) {
			type = null;
		} else if (type.equals("e-book")) {
			type = "2";
		} else {
			type = "1";
		}
		return type;
	}

	public void addBook(Book book) throws ServiceException {

		daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
		bookInfoDao = daoFactoryInstance.getBookDaoInstance();
		try {
			bookInfoDao.addbook(book);
		} catch (DaoException exception) {
			LOGGER.error("DAO Exception");
			throw new ServiceException(exception);
		}
	}

	public void removeBook(String langType, int bookId) throws ServiceException {
		daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
		bookInfoDao = daoFactoryInstance.getBookDaoInstance();
		langType = langType.substring(0, 2);
		try {
			bookInfoDao.removeBook(langType, bookId);
		} catch (DaoException exception) {
			LOGGER.error("DAO Exception");
			throw new ServiceException(exception);
		}
	}

	public BookInfo getBookInfoObject(BookInfo bookInfo, String langType) throws ServiceException {
		String typeName = bookInfo.getType().getTypeName();
		type = new BookType(typeName);
		String typeId = getBookType(typeName);
		type.setTypeId(Integer.parseInt(typeId));
		bookInfo.setType(type);
		langType = langType.substring(0, 2);

		daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
		bookInfoDao = daoFactoryInstance.getBookDaoInstance();
		try {
			bookInfo = bookInfoDao.getBookInfoObject(bookInfo, langType);
		} catch (DaoException exception) {
			LOGGER.error("DAO Exception");
			throw new ServiceException(exception);
		}
		return bookInfo;
	}

	public boolean updateBookInfo(BookInfo bookOldInfo, Book bookNewInfo) throws ServiceException {
		boolean isUpdated = false;
		daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
		bookInfoDao = daoFactoryInstance.getBookDaoInstance();
		bookNewInfo.setBookLanguage(bookNewInfo.getBookLanguage().substring(0, 2));
		try {
			isUpdated = bookInfoDao.updateBookInfo(bookOldInfo, bookNewInfo);
		} catch (DaoException exception) {
			LOGGER.error("DAO Exception");
			throw new ServiceException(exception);
		}
		return isUpdated;
	}

}
