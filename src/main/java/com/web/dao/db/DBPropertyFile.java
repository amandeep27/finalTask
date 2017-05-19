package com.web.dao.db;

import java.util.ResourceBundle;

public class DBPropertyFile {

	private static volatile DBPropertyFile dbProperties;

	private static final String FILE_PATH = "db/db";
	private static final ResourceBundle RESOURCE = ResourceBundle.getBundle(FILE_PATH);
	private static final String DB_DRIVER = "db.driverName";
	private static final String DB_URL = "db.url";
	private static final String DB_USERNAME = "db.user";
	private static final String DB_PASSWORD = "db.password";
	private static final String DB_POOL_SIZE = "db.poolSize";

	private DBPropertyFile() {

	}

	public static DBPropertyFile getPropertyFileInstance() {
		if (dbProperties == null) {
			synchronized (DBPropertyFile.class) {
				if (dbProperties == null) {
					dbProperties = new DBPropertyFile();
				}
			}
		}
		return dbProperties;
	}

	public String getDBDriver() {
		return RESOURCE.getString(DB_DRIVER);
	}

	public String getDBUrl() {
		return RESOURCE.getString(DB_URL);
	}

	public String getDBUserName() {
		return RESOURCE.getString(DB_USERNAME);
	}

	public String getDBPassword() {
		return RESOURCE.getString(DB_PASSWORD);
	}

	public String getDBPoolSize() {
		return RESOURCE.getString(DB_POOL_SIZE);
	}

}
