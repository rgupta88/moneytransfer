package com.monerytransfer.resource;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.monerytransfer.injection.ApplicationContext;

public class TestServer {
	Server jettyServer;

	public TestServer() {
		 jettyServer = new Server(8090);
	}

	public void start() {

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class,
				"/moneytransfer/api/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
				ApplicationContext.class.getCanonicalName());
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.monerytransfer.resource");
			try {
				jettyServer.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

	public void stop() {

		try {
			jettyServer.stop();
			jettyServer.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
