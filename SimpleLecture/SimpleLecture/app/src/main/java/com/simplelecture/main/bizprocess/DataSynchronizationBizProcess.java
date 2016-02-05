package com.simplelecture.main.bizprocess;

import com.simplelecture.main.service.ForgotPassword_WebService;

public class DataSynchronizationBizProcess {


	/**
	 * @param customerObj
	 * @return
	 */
/*	public String getLoginDetails(CustomerModel customerObj) {
		try {
			String output = new ValidateUserCredential_WebService().getValidateUserCredential(customerObj);

			return output;
		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e.toString());
		}
	}*/

	/**
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @param apiKey
	 * @return
	 */
/*	public String getChangePassword(String userName, String oldPassword, String newPassword, String apiKey) {
		try {
			String output = new ChangePassword_WebService().getChangePassword(userName, oldPassword, newPassword, apiKey);

			return output;
		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e.toString());
		}
	}*/

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return
	 */
	/*public String getCreateAccount(String firstName, String lastName, String email, String phoneNumber) {
		try {
			String output = new CreateAccount_WebService().getCreateAccount(firstName, lastName, email, phoneNumber);

			return output;
		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e.toString());
		}
	}*/


	/**
	 * @param email
	 * @return
	 */
	public String getForgotPassword(String email) {
		try {
			String output = new ForgotPassword_WebService().getForgotPassword(email);

			return output;
		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e.toString());
		}
	}


}
