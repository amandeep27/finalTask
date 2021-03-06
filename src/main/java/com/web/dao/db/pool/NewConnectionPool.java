package com.web.dao.db.pool;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;

import com.web.dao.exception.DaoException;

public class NewConnectionPool {

	private static volatile NewConnectionPool instance;
	private static final String FILE_PATH = "db/db";
	private static final String DB_URI = "db.jawsURL";
	private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
	private static final String DB_DRIVER_CLASS = "db.driverName";
	private static final ResourceBundle RESOURCE = ResourceBundle.getBundle(FILE_PATH);
	private static final int MAXPOOLSIZE = 2;

	private static BlockingQueue<Connection> idleQueue;
	private static BlockingQueue<Connection> runningQueue;

	private NewConnectionPool() {

	}

	public static NewConnectionPool getInstance() {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new NewConnectionPool();
				}
			}
		}
		return instance;
	}

	public void init() {
		Connection con = null;

		URI jdbUri = null;

		try {
			jdbUri = new URI(RESOURCE.getString(DB_URI));
			String userName = jdbUri.getUserInfo().split(":")[0];
			String password = jdbUri.getUserInfo().split(":")[1];
			String port = String.valueOf(jdbUri.getPort());
			Class.forName(RESOURCE.getString(DB_DRIVER_CLASS));
			String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
			int connectionNo = 0;
			idleQueue = new ArrayBlockingQueue<Connection>(MAXPOOLSIZE, true);
			for (; connectionNo < MAXPOOLSIZE; connectionNo++) {
				con = createConnection(jdbUrl, userName, password);
				idleQueue.put(con);
			}

		} catch (URISyntaxException e) {
			throw new RuntimeException("URi syntax Error", e);

		} catch (InterruptedException e) {
			throw new RuntimeException("Cannot add Connection to pool", e);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot add Connection to pool", e);
		}

	}

	private Connection createConnection(String jdbUrl, String userName, String password) {
		Connection connnection = null;
		try {
			connnection = DriverManager.getConnection(jdbUrl, userName, password);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot create Connection", e);
		}
		return connnection;
	}

	public Connection getConnection() {
		Connection con = null;

		try {
			con = idleQueue.take();
			if (runningQueue == null) {
				runningQueue = new ArrayBlockingQueue<Connection>(MAXPOOLSIZE, true);

			}
			runningQueue.put(con);

		} catch (InterruptedException e) {
			throw new RuntimeException("Thread Interrupted", e);
		}
		return con;

	}

	public void returnConnection(Connection connection) {

		runningQueue.remove(connection);
		try {
			idleQueue.put(connection);
		} catch (InterruptedException e) {
			throw new RuntimeException("Thread Interrupted ", e);
		}
	}

	public void close(PreparedStatement preparedStmt) throws DaoException {
		try {
			preparedStmt.close();
		} catch (SQLException e) {
			throw new DaoException("Prepared Statement isn't closed" + e);
		}
	}

	public void close(ResultSet rs, PreparedStatement preparedStmt) throws DaoException {
		close(preparedStmt);
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DaoException("ResultSet isn't closed" + e);
		}
	}

	public void destroy() {
		try {
			if (idleQueue != null) {
				for (Connection connection : idleQueue) {
					connection.close();
					System.out.println("connection closed." + connection.toString());
				}
			}
			if (runningQueue != null) {
				for (Connection connection : runningQueue) {
					connection.close();
					System.out.println("connection closed." + connection.toString());
				}
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot Close Connection", e);
		}

	}

}
