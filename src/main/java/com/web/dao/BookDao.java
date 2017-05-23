package com.web.dao;

import java.util.List;
import com.web.dao.exception.DaoException;
import com.web.domain.BookInfo;
import com.web.dto.Book;
import com.web.dto.GetBookInfo;

public interface BookDao {

	List<BookInfo> getBookInfo(GetBookInfo bookInfo) throws DaoException;

	void addbook(Book book) throws DaoException;

	void removeBook(String langType, int bookId) throws DaoException;

	BookInfo getBookInfoObject(BookInfo bookInfo, String langType) throws DaoException;

	boolean updateBookInfo(BookInfo bookOldInfo, Book bookNewInfo) throws DaoException;

}
