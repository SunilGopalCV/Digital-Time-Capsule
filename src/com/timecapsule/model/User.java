package com.timecapsule.model;

public class User {
	private String email;
	private String password;

	public User(String email, String password) {
		this.email = email != null ? email.trim().toLowerCase() : "";
		this.password = password != null ? password : "";
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email != null ? email.trim().toLowerCase() : "";
	}

	public void setPassword(String password) {
		this.password = password != null ? password : "";
	}

	@Override
	public String toString() {
		return "User{email='" + email + "'}";
	}
}
