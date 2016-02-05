package com.simplelecture.main.service;

import com.simplelecture.main.constants.Constants;

public class ForgotPassword_WebService extends BaseWebservice {

	public ForgotPassword_WebService() {
		
	}
	
	/**
	 * Description : getForgotPassword
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public String getForgotPassword(String email) throws Exception {
		String jsonInputData = "{\"UserName\":\"" + email + "\"}";

		//Log.i("jsonInputData-->", jsonInputData);

		response = wsClient.wsConnect(Constants.GET_FORGOTPASSWORD, "", "", jsonInputData, "Post");
		//Log.i("jsonOutputCreate-->", response);
		return response;
	}

}
