package com.web.listener;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.web.dao.db.pool.ConnectionPool;
import com.web.dao.db.pool.exception.ConnectionPoolException;

public class ContextListener implements ServletContextListener {

	private static ServletContext context;
	private static ConnectionPool connectionPool;
	private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		context = null;
		connectionPool.dispose();
	}

	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		initailizeLogger(context);
		createConnectionPool();
	}

	private void initailizeLogger(ServletContext context) {
		String log4jConfigFile = context.getInitParameter("log4j-config-location");
		String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		PropertyConfigurator.configure(fullPath);
	}

	private void createConnectionPool() {
		connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException exception) {
			LOGGER.info("ConnectionPool Exception" + exception);
			throw new RuntimeException(exception);
		}
	}

	public static ServletContext getApplicationContext() {
		return context;
	}

}
