package com.web.service;

import com.web.domain.UserInfo;
import com.web.dto.EditInfo;
import com.web.dto.SignInInfo;
import com.web.dto.SignUpInfo;
import com.web.service.exception.ServiceException;

public interface UserService {
	boolean checksIfValidUser(SignInInfo signInInfo) throws ServiceException;

	UserInfo getUserInfoByName(SignInInfo signInInfo) throws ServiceException;

	boolean checkIfPasswordMatches(SignInInfo signInInfo, String password) throws ServiceException;

	boolean saveInfoInDB(SignUpInfo signUpInfo) throws ServiceException;

	UserInfo changeUserLanguage(UserInfo userInfo, String newLanguage) throws ServiceException;

	void editUserInformation(EditInfo info) throws ServiceException;
}
