package com.simplelecture.main.controller;

import com.simplelecture.main.bizprocess.DataSynchronizationBizProcess;

public class ForgotPasswordController {
	
	DataSynchronizationBizProcess dataSynchronizationBizProcess;

	public ForgotPasswordController() {
		dataSynchronizationBizProcess = new DataSynchronizationBizProcess();
	}

	public String getForgotPassword(String email) {
		try {
			return dataSynchronizationBizProcess.getForgotPassword(email);

		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e.toString());
		}
	}
	

}
