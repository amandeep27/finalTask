package com.web.dao;

import com.web.dao.exception.DaoException;
import com.web.domain.UserInfo;
import com.web.dto.EditInfo;
import com.web.dto.SignInInfo;
import com.web.dto.SignUpInfo;

public interface UserDao {

	UserInfo getUserInfoById(SignInInfo signInInfo) throws DaoException;

	boolean checksIfValidUser(SignInInfo signInInfo) throws DaoException;

	boolean saveInfoInDB(SignUpInfo signUpInfo) throws DaoException;

	UserInfo changeUserLanguage(UserInfo userInfo, String newLanguage) throws DaoException;

	void editUserInformation(EditInfo info) throws DaoException;

	String getUserPwd(int id) throws DaoException;
}
