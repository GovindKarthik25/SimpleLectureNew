/**
 * Module: SIMPLE LECTURE
 * Package Name : com.simplelecture.main.service
 * Source File : WSClient.java 
 * Author: karthik.rao, Bangalore
 *
 */
package com.simplelecture.main.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplelecture.main.constants.Constants;


public class WSClient {

	@SuppressWarnings("deprecation")
//	DefaultHttpClient httpClient = null;

	public WSClient() {

	}

	/**
	 * Description : wsCall
	 * 
	 * @param methodName
	 * @param header
	 * @param jsonInputHeader
	 * @param jsonInputData
	 * @param httpType
	 * @return
	 * @throws Exception
	 */
//	@SuppressWarnings("deprecation")
	/*private String wsCall(String methodName, String header, String jsonInputHeader, String jsonInputData, String httpType)
			throws Exception {
		String responseString = "";
		HttpResponse response;
		String serviceURL = Constants.SERVICE_URL + methodName;
		Log.v("wsCall - serviceURL - ", serviceURL);
		// Log.v("wsCall - jsonInputData - ", jsonInputData);
		HttpParams httpParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		// HttpConnectionParams.setSoTimeout(my_httpParams, 15000);

		if (httpClient == null)
			httpClient = new DefaultHttpClient(httpParams);

		if (httpType.equals("Get")) { // Get method
			String urlStr = serviceURL;// "http://abc.dev.domain.com/0007AC/ads/800x480 15sec h.264.mp4";
			URL url = new URL(urlStr);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(),
					url.getHost(), url.getPort(), url.getPath(),
					url.getQuery(), url.getRef());
			url = uri.toURL();
		//	Log.i("url", "url " + url);

			HttpGet getRequest = new HttpGet(url.toString());

			StringEntity requestParams = new StringEntity(jsonInputHeader);
			requestParams.setContentType("application/json");
			if (!jsonInputHeader.equals("")) {
				getRequest.addHeader(header, jsonInputHeader);
				getRequest.addHeader("Content-Type", "application/json");
			}
			response = httpClient.execute(getRequest);

		} else { // Post method
			HttpPost postRequest = new HttpPost(serviceURL);
			StringEntity requestParams = new StringEntity(jsonInputData);
			requestParams.setContentType("application/json");
			postRequest.setEntity(requestParams);

			if (!header.equals("")) {
				postRequest.addHeader(header, jsonInputHeader);
				// postRequest.addHeader("Content-Type", "application/json");
			}
			response = httpClient.execute(postRequest);
		}
		Log.i("wsCall - statusCode - ", response.getStatusLine().getStatusCode() + "");

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException(""
					+ response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		while ((output = br.readLine()) != null) {
			responseString = output;
		}
		//
		// Log.i("wsCall - responseString:", methodName + " - "
		// +responseString);

		// SessionManager.httpClient.getConnectionManager().shutdown();

		*//*
		 * responseString = responseString.replaceAll("'''", "'");
		 * responseString = responseString.replaceAll("''", "'"); responseString
		 * = responseString.replaceAll("'", "''");
		 *//*// SQLLITE Insert issue.

		return responseString;
	}

	*//**
	 * Description : wsConnect
	 * 
	 * @param methodName
	 * @param header
	 * @param jsonInputHeader
	 * @param jsonInputData
	 * @param httpType
	 * @return
	 *//*
	public String wsConnect(String methodName, String header,
			String jsonInputHeader, String jsonInputData, String httpType) {
		String responseString = "";
	//	Log.i("wsConnect - ", "{" + methodName + "}");
		try {

			responseString = wsCall(methodName, header, jsonInputHeader,
					jsonInputData, httpType);

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WSClient - wsConnect", "Exception : " + e.getMessage());

		}
		return responseString;
	}

	
	public Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		return builder.create();
	}

*/
}