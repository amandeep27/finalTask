package com.web.listener;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;
import com.web.dao.db.pool.NewConnectionPool;

public class ContextListener implements ServletContextListener {

	private static ServletContext context;
	private static NewConnectionPool connectionPool;

	public void contextDestroyed(ServletContextEvent arg0) {
		context = null;
		connectionPool.destroy();
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
		connectionPool = NewConnectionPool.getInstance();
		connectionPool.init();

	}

	public static ServletContext getApplicationContext() {
		return context;
	}

}
