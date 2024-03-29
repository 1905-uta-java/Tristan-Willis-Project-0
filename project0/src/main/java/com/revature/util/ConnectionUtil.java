package com.revature.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		//String url = "jdbc:oracle:thin:@[endpoint]:[port]:ORCL";
		Driver d = new oracle.jdbc.OracleDriver();
		DriverManager.registerDriver(d);
		
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USER");
		String password = System.getenv("DB_PASS");
		if(connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
	}
	
}
