package com.monerytransfer.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.monerytransfer.dao.DBManager;

public class DBManagerImpl implements DBManager {
	public Connection getConnection() {
		try {
			String dbUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
			String user = "sa";
			String password = "";
			Properties info = new Properties();
			info.setProperty("user", user);
			info.put("password", password);
			return DriverManager.getConnection(dbUrl, user, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
