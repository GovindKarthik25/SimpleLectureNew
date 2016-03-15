/**
 * Module: YAP
 * Package Name : com.webtraits.yap
 * Source File : User.java 
 * Author: karthik.rao, SMNetserv, Bangalore
 *
 */
package com.simplelecture.main.model;


public class UserModel {

	private String UserID;
	private String UserName;
	private String FirstName;
	private String LastName;
	private String EmailID;
	private String password;
	private String MobileNo;

	/**
	 * 
	 */
	public UserModel() {
		super();
	}


	public UserModel(String userID, String userName, String firstName, String lastName, String emailID, String mobileNo, String password) {
		UserID = userID;
		UserName = userName;
		FirstName = firstName;
		LastName = lastName;
		EmailID = emailID;
		MobileNo = mobileNo;
		this.password = password;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailID() {
		return EmailID;
	}

	public void setEmailID(String emailID) {
		EmailID = emailID;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

}
