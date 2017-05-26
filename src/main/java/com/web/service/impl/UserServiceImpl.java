package com.web.service.impl;

import org.apache.log4j.Logger;
import com.web.dao.UserDao;
import com.web.dao.exception.DaoException;
import com.web.dao.factory.DaoFactory;
import com.web.domain.UserInfo;
import com.web.dto.EditInfo;
import com.web.dto.SignInInfo;
import com.web.dto.SignUpInfo;
import com.web.service.UserService;
import com.web.service.exception.ServiceException;
import com.web.service.impl.validator.SignUpInfoValidator;
import com.web.util.PasswordHashingUtil;
import com.web.util.exception.PwdHashingException;

public class UserServiceImpl implements UserService {
	private static DaoFactory daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
	private static SignUpInfoValidator validator;
	private static UserDao userDao;
	private static UserInfo userInfo;
	private static PasswordHashingUtil pwdHashing;

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	private static final String MAGIC_LINE = "Lot of Magic Added";

	public boolean saveInfoInDB(SignUpInfo signUpInfo) throws ServiceException {
		boolean flag;
		validator = SignUpInfoValidator.getSignUpInfoValidator();
		validator.checkBothPasswords(signUpInfo.getPassword(), signUpInfo.getRepeatPassword());
		signUpInfo.setPassword(changePwdToHashedPwd(signUpInfo.getPassword()));

		userDao = daoFactoryInstance.getUserDaoInstance();
		try {
			flag = userDao.saveInfoInDB(signUpInfo);
		} catch (DaoException exception) {
			LOGGER.error("Dao Exception");
			throw new ServiceException(exception);
		}
		return flag;
	}

	public UserInfo changeUserLanguage(UserInfo userInfo, String newLanguage) throws ServiceException {
		userDao = daoFactoryInstance.getUserDaoInstance();
		if (!userInfo.getLang().getLangType().equals(newLanguage)) {
			try {
				userInfo = userDao.changeUserLanguage(userInfo, newLanguage);
			} catch (DaoException exception) {
				LOGGER.error("DAO Exception");
				throw new ServiceException(exception);
			}
		}
		return userInfo;
	}

	public boolean checksIfValidUser(SignInInfo signInInfo) throws ServiceException {
		boolean validUser = false;

		userDao = daoFactoryInstance.getUserDaoInstance();
		try {
			validUser = userDao.checksIfValidUser(signInInfo);
		} catch (DaoException exception) {
			LOGGER.error("Dao Exception");
			throw new ServiceException(exception);
		}

		return validUser;
	}

	public UserInfo getUserInfoById(SignInInfo signInInfo) throws ServiceException {
		userDao = daoFactoryInstance.getUserDaoInstance();
		try {
			userInfo = userDao.getUserInfoById(signInInfo);
		} catch (DaoException exception) {
			LOGGER.error("Dao Exception");
			throw new ServiceException(exception);
		}
		return userInfo;
	}

	public boolean checkIfPasswordMatches(SignInInfo signInInfo, String password) throws ServiceException {
		boolean validUser = false;
		signInInfo.setPassword(changePwdToHashedPwd(signInInfo.getPassword()));
		if (signInInfo.getPassword().equals(password)) {
			validUser = true;
		}
		return validUser;
	}

	private String changePwdToHashedPwd(String pwd) throws ServiceException {
		pwdHashing = PasswordHashingUtil.getPwdHashInstance();

		pwd = pwd + MAGIC_LINE;
		try {
			pwd = pwdHashing.generateHash(pwd);
		} catch (PwdHashingException exception) {
			LOGGER.error("Password Hashing error!");
			throw new ServiceException(exception);
		}
		return pwd;
	}

	public void editUserInformation(EditInfo info) throws ServiceException {
		validator = SignUpInfoValidator.getSignUpInfoValidator();
		validator.checkBothPasswords(info.getPwd(), info.getPwdRepeat());
		info.setOldPwd(changePwdToHashedPwd(info.getOldPwd()));
		info.setPwd(changePwdToHashedPwd(info.getPwd()));
		userDao = daoFactoryInstance.getUserDaoInstance();
		try {
			String dbPwd = userDao.getUserPwd(info.getId());
			validator.checkBothPasswords(dbPwd, info.getOldPwd());
			userDao.editUserInformation(info);
		} catch (DaoException exception) {
			LOGGER.error("Dao Exception");
			throw new ServiceException(exception);
		}
	}

}
