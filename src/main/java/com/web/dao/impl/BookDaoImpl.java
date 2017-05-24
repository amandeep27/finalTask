package com.web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.web.dao.BookDao;
import com.web.dao.db.pool.NewConnectionPool;
import com.web.dao.exception.DaoException;
import com.web.domain.BookAuthor;
import com.web.domain.BookInfo;
import com.web.domain.BookType;
import com.web.domain.UserInfo;
import com.web.dto.Book;
import com.web.dto.GetBookInfo;

public class BookDaoImpl implements BookDao {

	private static UserInfo userInfo;
	private static BookInfo bookInfo;
	private static BookAuthor author;
	private static BookType type;
	private static NewConnectionPool connectionPool = NewConnectionPool.getInstance();
	private static boolean isTypeSet = false;
	private static boolean isTitleSet = false;
	private static boolean isAuthorSet = false;

	private static final Logger LOGGER = Logger.getLogger(BookDaoImpl.class);
	private static final String BOOK_INFO_ID = "book_info.bi_id";
	private static final String BOOK_INFO_TITLE = "book_info.bi_title";
	private static final String BOOK_INFO_DESC = "book_info.bi_desc";
	private static final String BOOK_INFO_PUBLISH_YEAR = "book_info.bi_publish_year";
	private static final String TYPE_NAME = "type_name";
	private static final String AUTHOR_NAME = "author_name";
	private static final String AUTHOR_ID = "ba_id";
	private static final String HAS_AUTHOR = "hasAuthor";
	private static final String BOOK_ID = "bi_id";
	private static final String BOOK_TYPE_ID = "bt_id";
	private static final String BOOK_TITLE = "bi_title";
	private static final String BOOK_DESCRIPTION = "bi_desc";
	private static final String BOOK_PUBLISH_YEAR = "bi_publish_year";
	private static final String HAS_TYPE = "hasType";
	private static final String HAS_TITLE = "hasTitle";
	private static final String TITLE_EXIST_EXCEPTION = "Data for this Title already Exists!";
	private static final String HAS_ENTRY = "entryExists";

	public List<BookInfo> getBookInfo(GetBookInfo getBookInfo) throws DaoException {
		int count = 1;
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		List<BookInfo> bookList = new ArrayList<BookInfo>();
		userInfo = getBookInfo.getUserInfo();

		String bookInfoQuery = queryBuilder(getBookInfo, userInfo.getLang().getLangType());
		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(bookInfoQuery);
			if (isTypeSet) {
				preparedStmt.setInt(count, Integer.parseInt(getBookInfo.getType()));
				++count;
				isTypeSet = false;
			}
			if (isTitleSet) {
				preparedStmt.setString(count, getBookInfo.getTitle());
				++count;
				isTitleSet = false;
			}
			if (isAuthorSet) {
				preparedStmt.setString(count, getBookInfo.getAuthor());
				isAuthorSet = false;
			}
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				author = new BookAuthor(resultSet.getString(AUTHOR_NAME));
				type = new BookType(resultSet.getString(TYPE_NAME));
				bookInfo = new BookInfo(resultSet.getInt(BOOK_INFO_ID), resultSet.getString(BOOK_INFO_TITLE),
						resultSet.getString(BOOK_INFO_DESC), resultSet.getInt(BOOK_INFO_PUBLISH_YEAR), author, type);
				bookList.add(bookInfo);
			}

		} catch (SQLException exception) {
			LOGGER.error("SQL Exception", exception);
			throw new DaoException(exception);
		} finally {
			try {
				resultSet.close();
				preparedStmt.close();
			} catch (SQLException e) {
				LOGGER.error("SQL Exception", e);
				throw new DaoException(e);
			}

			connectionPool.returnConnection(connection);
		}
		return bookList;
	}

	private String queryBuilder(GetBookInfo bookInfo, String langType) {
		String book_info = "book_info_" + langType;
		String book_info_author = "book_info_author_" + langType;
		String book_author = "book_author_" + langType;
		String book_info_type = "book_info_type_" + langType;
		String book_type = "book_type_" + langType;

		StringBuffer query = new StringBuffer();
		query.append("select distinct book_info.bi_id, book_info.bi_title, book_info.bi_desc, ");
		query.append("book_info.bi_publish_year, book_type.bt_name as type_name, book_author.ba_name as author_name ");
		query.append("from " + book_info + " as book_info " + "join " + book_info_author + " as info_author ");
		query.append("on book_info.bi_id = info_author.bi_id " + "join " + book_author + " as book_author ");
		query.append("on info_author.ba_id = book_author.ba_id " + "join " + book_info_type + " as info_type ");
		query.append("on info_type.bi_id = book_info.bi_id " + "join " + book_type + " as book_type ");
		query.append("on book_type.bt_id = info_type.bt_id ");
		String conditions = getWhereConditionsForQuery(bookInfo);
		query.append(conditions);
		return query.toString();
	}

	private String getWhereConditionsForQuery(GetBookInfo bookInfo) {
		boolean flag = false;
		StringBuffer whereCondition = new StringBuffer();
		whereCondition.append("");
		if (bookInfo.getType() != null) {
			whereCondition.append("where book_type.bt_id = ? ");
			flag = true;
			isTypeSet = true;
		}
		if (bookInfo.getTitle() != null) {
			whereCondition.append(addConnectors(flag));
			whereCondition.append("book_info.bi_title = ? ");
			flag = true;
			isTitleSet = true;
		}
		if (bookInfo.getAuthor() != null) {
			whereCondition.append(addConnectors(flag));
			whereCondition.append("book_author.ba_name = ? ");
			isAuthorSet = true;
		}
		return whereCondition.toString();
	}

	private String addConnectors(boolean flag) {
		String query;
		if (flag == true) {
			query = " and ";
		} else {
			query = "where ";
		}
		return query;
	}

	public void addbook(Book book) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		String book_info = "book_info_" + book.getBookLanguage();
		String book_info_author = "book_info_author_" + book.getBookLanguage();
		String book_author = "book_author_" + book.getBookLanguage();
		String book_info_type = "book_info_type_" + book.getBookLanguage();
		String book_type = "book_type_en";

		String checkAuthorExists = "select exists(select * from " + book_author + " where ba_name=?) as hasAuthor";
		String insertAuthor = "insert into " + book_author + "(ba_name) values(?);";
		String getAuthorId = "select ba_id from " + book_author + " where ba_name=?;";
		String getBookTypeId = "select bt_id from " + book_type + " where bt_name=?;";
		String hasTitle = "select exists (select * from " + book_info + " where bi_title=?) as hasTitle;";
		String hasEntry = "select exists (select * from " + book_info
				+ " where bi_title=? and bi_desc=? and bi_publish_year=?) as hasTitle;";
		String insertBookInfo = "insert into " + book_info + "(bi_title, bi_desc, bi_publish_year) values(?, ?, ?);";
		String getBookInfoId = "select bi_id from " + book_info
				+ " where bi_publish_year=? and bi_title=? and bi_desc=?;";
		String insertBookInfoType = "insert into " + book_info_type + " values(?, ?);";
		String insertBookInfoAuthor = "insert into " + book_info_author + " values(?, ?);";
		String hasBookInfoAuthor = "select exists (select * from " + book_info_author
				+ " where bi_id=? and ba_id=?) as entryExists;";
		String hasBookInfoType = "select exists (select * from " + book_info_type
				+ " where bi_id=? and bt_id=?) as entryExists;";

		int authorExist = 0;
		int authorId = 0;
		int titleExist = 0;
		int entryExist = 0;

		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStmt = connection.prepareStatement(checkAuthorExists);
			preparedStmt.setString(1, book.getAuthor());

			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				authorExist = resultSet.getInt(HAS_AUTHOR);
			}
			connectionPool.close(resultSet, preparedStmt);

			resultSet = null;
			preparedStmt = null;
			if (authorExist == 0) {
				preparedStmt = connection.prepareStatement(insertAuthor);
				preparedStmt.setString(1, book.getAuthor());

				authorExist = preparedStmt.executeUpdate();
			}
			preparedStmt = connection.prepareStatement(getAuthorId);
			preparedStmt.setString(1, book.getAuthor());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				authorId = resultSet.getInt(AUTHOR_ID);
			}

			int bookTypeId = 0;
			preparedStmt = connection.prepareStatement(getBookTypeId);
			preparedStmt.setString(1, book.getType());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				bookTypeId = resultSet.getInt(BOOK_TYPE_ID);
			}

			int bookInfoId = 0;

			preparedStmt = connection.prepareStatement(hasTitle);
			preparedStmt.setString(1, book.getTitle());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				titleExist = resultSet.getInt(HAS_TITLE);
			}
			if (titleExist == 0) {
				preparedStmt = connection.prepareStatement(insertBookInfo);
				preparedStmt.setString(1, book.getTitle());
				preparedStmt.setString(2, book.getDescription());
				preparedStmt.setInt(3, book.getPublishYear());
				preparedStmt.executeUpdate();
			} else {
				preparedStmt = connection.prepareStatement(hasEntry);
				preparedStmt.setString(1, book.getTitle());
				preparedStmt.setString(2, book.getDescription());
				preparedStmt.setInt(3, book.getPublishYear());
				resultSet = preparedStmt.executeQuery();
				while (resultSet.next()) {
					entryExist = resultSet.getInt(HAS_TITLE);
				}
				if (entryExist != 1) {
					LOGGER.info("Data for this Title already Exists");
					throw new DaoException(TITLE_EXIST_EXCEPTION);
				}

			}

			preparedStmt = connection.prepareStatement(getBookInfoId);
			preparedStmt.setInt(1, book.getPublishYear());
			preparedStmt.setString(2, book.getTitle());
			preparedStmt.setString(3, book.getDescription());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				bookInfoId = resultSet.getInt(BOOK_ID);
			}

			preparedStmt = connection.prepareStatement(hasBookInfoType);
			preparedStmt.setInt(1, bookInfoId);
			preparedStmt.setInt(2, bookTypeId);
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				entryExist = resultSet.getInt(HAS_ENTRY);
			}
			if (entryExist == 0) {
				preparedStmt = connection.prepareStatement(insertBookInfoType);
				preparedStmt.setInt(1, bookInfoId);
				preparedStmt.setInt(2, bookTypeId);
				preparedStmt.executeUpdate();
			}

			preparedStmt = connection.prepareStatement(hasBookInfoAuthor);
			preparedStmt.setInt(1, bookInfoId);
			preparedStmt.setInt(2, authorId);
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				entryExist = resultSet.getInt(HAS_ENTRY);
			}
			if (entryExist != 1) {
				preparedStmt = connection.prepareStatement(insertBookInfoAuthor);
				preparedStmt.setInt(1, bookInfoId);
				preparedStmt.setInt(2, authorId);
				preparedStmt.executeUpdate();
			}

			connection.commit();

		} catch (SQLException exception) {
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.error("SQL Exception", e);
				throw new DaoException(e);
			}
			LOGGER.error("SQL Exception", exception);
			throw new DaoException(exception);
		} finally {
			connectionPool.close(resultSet, preparedStmt);
			connectionPool.returnConnection(connection);
		}

	}

	public void removeBook(String langType, int bookId) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String book_info = "book_info_" + langType;
		String book_info_author = "book_info_author_" + langType;
		String book_info_type = "book_info_type_" + langType;

		String deleteFromInfoAuthor = "delete from " + book_info_author + " where bi_id = ?;";
		String deleteFromInfoType = "delete from " + book_info_type + " where bi_id = ?;";
		String deleteFromInfo = "delete from " + book_info + " where bi_id = ?;";

		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStmt = connection.prepareStatement(deleteFromInfoAuthor);
			preparedStmt.setInt(1, bookId);
			preparedStmt.executeUpdate();
			connectionPool.close(preparedStmt);

			preparedStmt = null;
			preparedStmt = connection.prepareStatement(deleteFromInfoType);
			preparedStmt.setInt(1, bookId);
			preparedStmt.executeUpdate();
			connectionPool.close(preparedStmt);

			preparedStmt = null;
			preparedStmt = connection.prepareStatement(deleteFromInfo);
			preparedStmt.setInt(1, bookId);
			preparedStmt.executeUpdate();

			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException exception) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				LOGGER.error("SQL Exception", e);
				throw new DaoException(e);
			}
			LOGGER.error("SQL Exception", exception);
			throw new DaoException(exception);
		} finally {
			connectionPool.close(preparedStmt);
			connectionPool.returnConnection(connection);
		}

	}

	public BookInfo getBookInfoObject(BookInfo bookInfo, String langType) throws DaoException {
		author = new BookAuthor(bookInfo.getAuthor().getName());

		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		String book_info = "book_info_" + langType;
		String getBookFromId = "select bi_title, bi_desc, bi_publish_year from " + book_info + " where bi_id=?";
		String getAuthorIdFromName = "select ba_id from book_author_en where ba_name=?;";

		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(getBookFromId);
			preparedStmt.setInt(1, bookInfo.getBookId());

			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				bookInfo.setBooktitle(resultSet.getString(BOOK_TITLE));
				bookInfo.setDescription(resultSet.getString(BOOK_DESCRIPTION));
				bookInfo.setPublishYear(resultSet.getInt(BOOK_PUBLISH_YEAR));
			}
			connectionPool.close(resultSet, preparedStmt);

			resultSet = null;
			preparedStmt = null;

			preparedStmt = connection.prepareStatement(getAuthorIdFromName);
			preparedStmt.setString(1, bookInfo.getAuthor().getName());

			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				author.setId(resultSet.getInt(AUTHOR_ID));
			}
			bookInfo.setAuthor(author);
		} catch (SQLException exception) {
			LOGGER.error("SQL Exception", exception);
			throw new DaoException(exception);
		} finally {
			connectionPool.close(resultSet, preparedStmt);
			connectionPool.returnConnection(connection);
		}

		return bookInfo;

	}

	public boolean updateBookInfo(BookInfo bookOldInfo, Book bookNewInfo) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean isUpdated = false;
		int affectedRows = 0;
		int newTypeId = 0;
		int hasType = 0;
		String book_info = "book_info_" + bookNewInfo.getBookLanguage();
		String book_author = "book_author_" + bookNewInfo.getBookLanguage();
		String book_type = "book_type_en";
		String book_info_type = "book_info_type_" + bookNewInfo.getBookLanguage();

		String updateBookInfo = "update " + book_info
				+ " set bi_title=?, bi_desc=?, bi_publish_year=? where bi_id = ?;";
		String updateAuthorInfo = "update " + book_author + " set ba_name = ? where ba_id = ?;";
		String getBookTypeId = "select bt_id from " + book_type + " where bt_name = ?;";
		String checkTypeExistence = "select exists (select * from " + book_info_type
				+ " where bi_id=? and bt_id=?) as hasType;";
		String updateInfoType = "update " + book_info_type + " set bt_id = ? where bi_id = ?;";
		String deleteInfoType = "delete from " + book_info_type + " where bt_id = ? and bi_id = ?;";

		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStmt = connection.prepareStatement(updateBookInfo);
			preparedStmt.setString(1, bookNewInfo.getTitle());
			preparedStmt.setString(2, bookNewInfo.getDescription());
			preparedStmt.setInt(3, bookNewInfo.getPublishYear());
			preparedStmt.setInt(4, bookOldInfo.getBookId());
			affectedRows = preparedStmt.executeUpdate();
			if (affectedRows > 0) {
				isUpdated = true;
			}
			connectionPool.close(preparedStmt);

			preparedStmt = null;
			preparedStmt = connection.prepareStatement(updateAuthorInfo);
			preparedStmt.setString(1, bookNewInfo.getAuthor());
			preparedStmt.setInt(2, bookOldInfo.getAuthor().getId());
			affectedRows = preparedStmt.executeUpdate();
			if (affectedRows > 0) {
				isUpdated = true;
			}
			connectionPool.close(preparedStmt);

			preparedStmt = null;
			preparedStmt = connection.prepareStatement(getBookTypeId);
			preparedStmt.setString(1, bookNewInfo.getType());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				newTypeId = resultSet.getInt(BOOK_TYPE_ID);
			}
			connectionPool.close(resultSet, preparedStmt);

			if (newTypeId != bookOldInfo.getType().getTypeId()) {
				resultSet = null;
				preparedStmt = null;
				preparedStmt = connection.prepareStatement(checkTypeExistence);
				preparedStmt.setInt(1, bookOldInfo.getBookId());
				preparedStmt.setInt(2, newTypeId);
				resultSet = preparedStmt.executeQuery();
				while (resultSet.next()) {
					hasType = resultSet.getInt(HAS_TYPE);
				}

				connectionPool.close(resultSet, preparedStmt);
				preparedStmt = null;
				if (hasType != 0) {
					preparedStmt = connection.prepareStatement(deleteInfoType);
					preparedStmt.setInt(1, bookOldInfo.getType().getTypeId());
					preparedStmt.setInt(2, bookOldInfo.getBookId());
					affectedRows = preparedStmt.executeUpdate();
					if (affectedRows > 0) {
						isUpdated = true;
					}

				} else {
					preparedStmt = connection.prepareStatement(updateInfoType);
					preparedStmt.setInt(1, newTypeId);
					preparedStmt.setInt(2, bookOldInfo.getBookId());
					affectedRows = preparedStmt.executeUpdate();
					if (affectedRows > 0) {
						isUpdated = true;
					}

				}
			}
			connection.commit();

		} catch (SQLException exception) {
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.error("SQL Exception", e);
				throw new DaoException(e);
			}
			LOGGER.error("SQL Exception", exception);
			throw new DaoException(exception);
		} finally {

			connectionPool.close(preparedStmt);
			connectionPool.returnConnection(connection);
		}

		return isUpdated;
	}

}
