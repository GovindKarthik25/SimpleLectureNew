package com.simplelecture.main.service;

import com.google.gson.Gson;
import com.simplelecture.main.model.ReturnCodes;

public class BaseWebservice {
	protected WSClient wsClient = new WSClient();
	protected String response = null;
	protected Gson gson;

	public BaseWebservice() {
		wsClient = new WSClient();
		gson = wsClient.getGsonBuilder();
		ReturnCodes.ERRSTRING = "";
	}
}
