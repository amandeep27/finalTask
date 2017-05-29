package com.web.service;

import java.util.List;
import com.web.domain.BookInfo;
import com.web.dto.Book;
import com.web.dto.GetBookInfo;
import com.web.service.exception.ServiceException;

public interface BookService {

	List<BookInfo> getBookInfo(GetBookInfo bookInfo) throws ServiceException;

	void addBook(Book book) throws ServiceException;

	void removeBook(String langType, int bookId) throws ServiceException;

	BookInfo getBookInfoObject(BookInfo bookInfo, String langType) throws ServiceException;

	boolean updateBookInfo(BookInfo bookOldInfo, Book bookNewInfo) throws ServiceException;

}
