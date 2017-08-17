package com.leopard.data.businesslogic;

import java.util.List;

import com.leopard.data.Database;

public class Channel {

	// Fake a database connection using com.itmill.dev.example.data.Database.
	private Database DBConnection;
	
	public Channel(){
		DBConnection = new Database();
	}
	
	/*
	 * Retrieves the log from the database and forwards it. 
	 * No actual data manipulation occurs here because theoretically this could be run on the data server.
	 * @param String channel
	 */
	public List<String[]> getChannelLog(String channel){
		
		// Return the log.
		return DBConnection.getLog(channel);
		

	}
	
}
