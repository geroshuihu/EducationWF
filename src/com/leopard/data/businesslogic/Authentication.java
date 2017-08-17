package com.leopard.data.businesslogic;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.leopard.data.*;

public class Authentication {

	// Fake a database connection using com.itmill.dev.example.data.Database
	private Database DBConnection;
	Connection c = null;
	PreparedStatement ps = null;
	String sql = "";
	ResultSet rs = null;
	String fullname = "";
	String fname = "";
	String lname = "";

	public Authentication() {
		DBConnection = new Database();
	}

	/*
	 * Authenticates the user and returns the corresponding User object.
	 */
	public User Authenticate(String loginname, String password) {

		sql = "SELECT * FROM tusers WHERE username LIKE ? && password LIKE ?";
		rs = null;
		fullname = "";
		fname = "";
		lname = "";
		try {
			c = ConnectionHelper.getConnection();
			ps = c.prepareStatement(sql);
			ps.setString(1, loginname);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {				
				fname = rs.getString("firstname");
						lname = rs.getString("lastname");
				fullname = fname+","+lname;
				System.out.println("allow Fullname: "+fullname);
			}

		} catch (SQLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionHelper.close(c);
		}

		// if ((loginname.equals("demo")) && (password.equals("demo"))) {
		
		if (fullname.length() != 0) {
//			System.out.println("lent:"+fullname.length());
			return DBConnection.getUser(fname, lname);

		} else {
			return null;
		}

	}

}
