package com.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;
import com.web.util.exception.PwdHashingException;

public class PasswordHashingUtil {

	private static final Logger LOGGER = Logger.getLogger(PasswordHashingUtil.class);

	private static volatile PasswordHashingUtil pwdHash;

	private PasswordHashingUtil() {

	}

	public static PasswordHashingUtil getPwdHashInstance() {
		if (pwdHash == null) {
			synchronized (PasswordHashingUtil.class) {
				if (pwdHash == null) {
					pwdHash = new PasswordHashingUtil();
				}
			}
		}
		return pwdHash;
	}

	public String generateHash(String pwd) throws PwdHashingException {
		StringBuilder hash = new StringBuilder();
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(pwd.getBytes());
			char digits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException exception) {
			LOGGER.error("NoSuchAlgorithm Exception", exception);
			throw new PwdHashingException(exception);
		}

		return hash.toString();
	}
}
