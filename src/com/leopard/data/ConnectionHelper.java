/**
 * 
 */
package com.leopard.data;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.leopard.ui.ExampleApplication;
import com.vaadin.addon.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.ui.Window.Notification;

/**
 * @author Duncan
 * 
 */
public class ConnectionHelper {
	private String url;
	private static ConnectionHelper instance;
	public static JDBCConnectionPool connectionPool = null;
	// LOCAL SERVER
	// String serverName0 = "127.0.0.1";
	// int portNumber0 = 3306;
	// String uname0 = "root";
	// String pwd0 = "";

	// Test Server
	String serverName0 = "192.168.0.58";
	int portNumber0 = 3306;
	String uname0 = "mysql";
	String pwd0 = "mysql123";

	private ConnectionHelper() throws URISyntaxException {
		String driver = null;
		try {

			// Connect to DB
			driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			url = "jdbc:mysql://" + serverName0 + ":3306/dbEDUCATIONKE?user="
					+ uname0 + "&password=" + pwd0;
			// System.out.println("TRACE DB: " + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException,
			URISyntaxException {
		if (instance == null) {
			instance = new ConnectionHelper();
		}
		try {
			return DriverManager.getConnection(instance.url);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static JDBCConnectionPool initConnectionPool() {
		try {
			if (connectionPool == null) {
				connectionPool = new SimpleJDBCConnectionPool(
						"com.mysql.jdbc.Driver",
						"jdbc:mysql://192.168.0.58:3306/dbEDUCATIONKE?autoReconnect=true",
						"mysql", "mysql123", 3, 5);
			}

		} catch (SQLException e) {
			showError("Couldn't create the connection pool!");
			e.printStackTrace();
		}
		return connectionPool;
	}

	public static void showError(String errorString) {

		ExampleApplication.getProject().getMainWindow()
				.showNotification("Session Timed out, refresh the page to proceed", Notification.TYPE_HUMANIZED_MESSAGE);
		System.out.println("Connection Pooling Error:=>"+errorString);
//		ExampleApplication.getProject().getMainWindow()
//				.showNotification(errorString, Notification.TYPE_ERROR_MESSAGE);
	}

}