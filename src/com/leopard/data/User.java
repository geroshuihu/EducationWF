package com.leopard.data;

/*
 * Simple User ID class.
 */
public class User {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String accesslevel;
	
	public User(String first, String last){
		firstname = first;
		lastname = last;
	}
	
	public String getFirstName(){
		return firstname;
	}
	
	public String getLastName(){
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccesslevel() {
		return accesslevel;
	}

	public void setAccesslevel(String accesslevel) {
		this.accesslevel = accesslevel;
	}
	
	
}
