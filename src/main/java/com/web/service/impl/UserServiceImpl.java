package com.web.service.impl;

import org.apache.log4j.Logger;
import com.web.dao.UserDao;
import com.web.dao.exception.DaoException;
import com.web.dao.factory.DaoFactory;
import com.web.domain.UserInfo;
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
		signUpInfo = changePwdToHashedPwd(signUpInfo);

		userDao = daoFactoryInstance.getUserDaoInstance();
		try {
			flag = userDao.saveInfoInDB(signUpInfo);
		} catch (DaoException exception) {
			LOGGER.error("Dao Exception");
			throw new ServiceException(exception);
		}
		return flag;
	}

	private SignUpInfo changePwdToHashedPwd(SignUpInfo signUpInfo) throws ServiceException {
		pwdHashing = PasswordHashingUtil.getPwdHashInstance();
		String pwd = signUpInfo.getPassword();

		pwd = pwd + MAGIC_LINE;
		try {
			signUpInfo.setPassword(pwdHashing.generateHash(pwd));
		} catch (PwdHashingException exception) {
			LOGGER.error("Password Hashing error!");
			throw new ServiceException(exception);
		}
		return signUpInfo;
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
		signInInfo = changePwdToHashedPwd(signInInfo);
		if (signInInfo.getPassword().equals(password)) {
			validUser = true;
		}
		return validUser;
	}

	private SignInInfo changePwdToHashedPwd(SignInInfo signInInfo) throws ServiceException {
		pwdHashing = PasswordHashingUtil.getPwdHashInstance();
		String pwd = signInInfo.getPassword();

		pwd = pwd + MAGIC_LINE;
		try {
			signInInfo.setPassword(pwdHashing.generateHash(pwd));
		} catch (PwdHashingException exception) {
			LOGGER.error("Password Hashing error!");
			throw new ServiceException(exception);
		}
		return signInInfo;
	}

}
