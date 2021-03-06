package com.monerytransfer.main;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.monerytransfer.injection.ApplicationContext;

/*
 * Author : Ravi Gupta
 * Main to start rest application
 * */
public class App {
	final static Logger logger = Logger.getLogger(App.class);
	public static void main(String[] args) throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		Server jettyServer = new Server(8090);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class,
				"/moneytransfer/api/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
				ApplicationContext.class.getCanonicalName());
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.monerytransfer.resource");
		try {
			jettyServer.start();
			logger.info("Server started.");
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}
