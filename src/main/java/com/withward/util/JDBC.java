package com.withward.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBC {
	public static Connection getConnection() throws SQLException {
		Connection c = null;
		DriverManager.registerDriver(new Driver());
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/withward_dev", "dan_admin", "ceru1014");
		return c;
	}
}
