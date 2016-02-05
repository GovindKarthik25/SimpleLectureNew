/**
 * Module: SIMPLE LECTURE
 * Package Name : com.simplelecture.main
 * Source File : ResponseModel.java
 * Author: karthik.rao, Bangalore
 *
 */
package com.simplelecture.main.model.viewmodel;

public class ResponseModel {
	
	private String StatusCode;
	private String ResponseStatus;
	private String  Message;
	/**
	 * 
	 */
	public ResponseModel() {
		super();
	}
	/**
	 * @param statusCode
	 * @param responseStatus
	 * @param message
	 */
	public ResponseModel(String statusCode, String responseStatus, String message) {
		super();
		StatusCode = statusCode;
		ResponseStatus = responseStatus;
		Message = message;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return StatusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	/**
	 * @return the responseStatus
	 */
	public String getResponseStatus() {
		return ResponseStatus;
	}
	/**
	 * @param responseStatus the responseStatus to set
	 */
	public void setResponseStatus(String responseStatus) {
		ResponseStatus = responseStatus;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		Message = message;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [StatusCode=" + StatusCode + ", ResponseStatus="
				+ ResponseStatus + ", Message=" + Message + "]";
	}
	
	

}
