package com.web.dao.db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;
import com.web.dao.db.DBPropertyFile;
import com.web.dao.db.pool.exception.ConnectionPoolException;
import com.web.dao.exception.DaoException;

public class ConnectionPool {
	private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	private static volatile ConnectionPool INSTANCE;

	private ConnectionPool() {
		DBPropertyFile dbFile = DBPropertyFile.getPropertyFileInstance();
		this.driverName = dbFile.getDBDriver();
		this.url = dbFile.getDBUrl();
		this.user = dbFile.getDBUserName();
		this.password = dbFile.getDBPassword();
		this.poolSize = Integer.parseInt(dbFile.getDBPoolSize());
	}

	public static ConnectionPool getInstance() {
		if (INSTANCE == null) {
			synchronized (ConnectionPool.class) {
				if (INSTANCE == null) {
					INSTANCE = new ConnectionPool();
				}
			}
		}
		return INSTANCE;
	}

	public void initPoolData() throws ConnectionPoolException {
		try {
			Class.forName(driverName);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				PooledConnection polledConnection = new PooledConnection(connection, connectionQueue,
						givenAwayConQueue);
				connectionQueue.add(polledConnection);
			}
		} catch (ClassNotFoundException exception) {
			throw new ConnectionPoolException("Can't find database driver class", exception);
		} catch (SQLException exception) {
			throw new ConnectionPoolException("SQLException in ConnectionPool", exception);
		}
	}

	public synchronized void dispose() {
		clearConnectionQueue();
	}

	private void clearConnectionQueue() {
		try {
			closeConnectionsQueue(givenAwayConQueue);
			closeConnectionsQueue(connectionQueue);
		} catch (SQLException e) {
			LOGGER.error("Error while trying to close connections", e);
		}
	}

	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection;
		try {
			connection = connectionQueue.take();
			givenAwayConQueue.add(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error while trying to connect to the data sources.", e);
		}
		return connection;
	}

	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			LOGGER.error("Connection isn't in pool", e);
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

	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).closeConnection();
		}
	}
}
