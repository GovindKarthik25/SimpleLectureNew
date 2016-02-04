package com.simplelecture.main.splash;

import android.app.Application;
import android.content.Context;

public class Controller extends Application {
	public static Context context;
	//public CommonModel commonModel;

	@Override
	public void onCreate() {
		try {

			super.onCreate();
			context = getApplicationContext();
			//commonModel = new CommonModel();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/*public CommonModel getCommonModel() {
		return commonModel;*/
	}


