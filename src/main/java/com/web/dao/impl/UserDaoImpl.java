package com.web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.web.dao.UserDao;
import com.web.dao.db.pool.NewConnectionPool;
import com.web.dao.exception.DaoException;
import com.web.domain.UserInfo;
import com.web.domain.UserLanguage;
import com.web.dto.SignInInfo;
import com.web.dto.SignUpInfo;

public class UserDaoImpl implements UserDao {

	private static NewConnectionPool connectionPool = NewConnectionPool.getInstance();

	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
	private static final String SELECT_QUERY = "select * from ul_info where ul_id = ?;";
	private static final String IF_VALID_USER = "select exists( select * from ul_info where ul_id = ?) as 'userExists';";
	private static final String GET_USER_LANG = "select * from ul_lang where l_id = ?;";
	private static final String USER_ID = "ul_id";
	private static final String USER_NAME = "ul_name";
	private static final String PWD = "ul_pwd";
	private static final String ROLE = "ul_role";
	private static final String LANG = "ul_lang_l_id";
	private static final String VALID_USER = "userExists";
	private static final String LANG_ID = "l_id";
	private static final String LANG_TYPE = "l_type";
	private static final String LANG_COUNTRY = "l_country";
	private static final String INSERT_QUERY = "insert into ul_info values(?, ?, ?, ?, ?);";
	private static final String GET_LANG_ID = "select l_id from ul_lang where l_type = ?;";
	private static final String UPDATE_QUERY = "update ul_info set ul_lang_l_id = ? where ul_id = ?;";
	private static final String GET_LANG_DATA = "select l_id, l_country, l_type from ul_lang where l_type = ?;";

	public UserInfo getUserInfoById(SignInInfo signInInfo) throws DaoException {
		UserInfo userInfo = new UserInfo();
		UserLanguage userLanguage = new UserLanguage();
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(SELECT_QUERY);
			preparedStmt.setInt(1, signInInfo.getUserId());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				userInfo.setUserId(Integer.parseInt(resultSet.getString(USER_ID)));
				userInfo.setUserName(resultSet.getString(USER_NAME));
				userInfo.setPassword(resultSet.getString(PWD));
				userInfo.setUserRole(resultSet.getString(ROLE));
				userLanguage = getUserLanguageId(resultSet.getInt(LANG));
				userInfo.setLang(userLanguage);
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

		return userInfo;
	}

	private UserLanguage getUserLanguageId(int langId) throws DaoException {
		UserLanguage userLang = new UserLanguage();
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(GET_USER_LANG);
			preparedStmt.setInt(1, langId);
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				userLang.setLangId(resultSet.getInt(LANG_ID));
				userLang.setLangType(resultSet.getString(LANG_TYPE));
				userLang.setCountry(resultSet.getString(LANG_COUNTRY));
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

		return userLang;
	}

	public boolean checksIfValidUser(SignInInfo signInInfo) throws DaoException {
		boolean isValidUser = false;
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		int validUser = 0;

		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(IF_VALID_USER);
			preparedStmt.setInt(1, signInInfo.getUserId());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				validUser = resultSet.getInt(VALID_USER);
			}
		} catch (SQLException exception) {
			LOGGER.info("SQL Exception", exception);
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

		if (validUser != 0) {
			isValidUser = true;
		}
		return isValidUser;
	}

	public boolean saveInfoInDB(SignUpInfo signUpInfo) throws DaoException {
		boolean flag = false;
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		int affectedRows = 0;

		int langId = getUserLanguageId(signUpInfo);
		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(INSERT_QUERY);
			preparedStmt.setInt(1, signUpInfo.getUserId());
			preparedStmt.setString(2, signUpInfo.getUserName());
			preparedStmt.setString(3, signUpInfo.getPassword());
			preparedStmt.setString(4, signUpInfo.getUserRole());
			preparedStmt.setInt(5, langId);
			affectedRows = preparedStmt.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error("SQL Exception", exception);
			throw new DaoException(exception);
		} finally {
			try {
				preparedStmt.close();
			} catch (SQLException e) {
				LOGGER.error("SQL Exception", e);
				throw new DaoException(e);
			}
			connectionPool.returnConnection(connection);
		}
		LOGGER.info("sign up info : " + signUpInfo);
		if (affectedRows > 0) {
			flag = true;
		}
		return flag;
	}

	private int getUserLanguageId(SignUpInfo signUpInfo) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		int langID = 0;

		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(GET_LANG_ID);
			preparedStmt.setString(1, signUpInfo.getLanguage());
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				langID = resultSet.getInt(LANG_ID);
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

		return langID;
	}

	public UserInfo changeUserLanguage(UserInfo userInfo, String newLanguage) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		UserLanguage language = getUserLanguageId(newLanguage);
		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(UPDATE_QUERY);
			preparedStmt.setInt(1, language.getLangId());
			preparedStmt.setInt(2, userInfo.getUserId());
			preparedStmt.executeUpdate();
		} catch (SQLException exception) {
			LOGGER.error("SQL Exception", exception);
			throw new DaoException(exception);
		} finally {
			try {
				preparedStmt.close();
			} catch (SQLException e) {
				LOGGER.error("SQL Exception", e);
				throw new DaoException(e);
			}
			connectionPool.returnConnection(connection);
		}
		userInfo.setLang(language);
		return userInfo;
	}

	private UserLanguage getUserLanguageId(String newLanguage) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		UserLanguage language = new UserLanguage();

		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(GET_LANG_DATA);
			preparedStmt.setString(1, newLanguage);
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				language.setLangId(resultSet.getInt(LANG_ID));
				language.setLangType(resultSet.getString(LANG_TYPE));
				language.setCountry(resultSet.getString(LANG_COUNTRY));
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

		return language;
	}

}
