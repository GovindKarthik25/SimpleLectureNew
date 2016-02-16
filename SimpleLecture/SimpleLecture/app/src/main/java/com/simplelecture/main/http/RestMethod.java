package com.simplelecture.main.http;

//import com.pumpkin.angelsensorpoc.Utils;

import com.simplelecture.main.util.Util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by M1032185 on 2/3/2016.
 */
public class RestMethod implements IRestMethod {

    public static RestMethod restMethod = new RestMethod();

    private RestMethod() {

    }

    public static RestMethod getInstance() {
        return restMethod;
    }

    @Override
    public HttpResponse sendPostRequest(URI uri, String body) {

        HttpResponse httpResponse = null;
        try {
            URL url = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("content-type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);

            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(body.getBytes("UTF-8"));
            outputStream.close();

            httpResponse = new HttpResponse();
            httpResponse.setMethod("POST");
            httpResponse.setUri(uri);
            httpResponse.setStatusCode(connection.getResponseCode());
            httpResponse.setResponseBody(Util.streamlineHttpResponse(connection));
            httpResponse.setStatusLine(connection.getResponseMessage());
            httpResponse.setPostRequestBody(body);

            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    @Override
    public HttpResponse sendGetRequest(URI uri, String token) {

        HttpResponse response = null;
        try {
            URL url = new URL(uri.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("token", token);
            urlConnection.connect();

            response = new HttpResponse();
            response.setMethod("GET");
            response.setUri(uri);
            response.setStatusCode(urlConnection.getResponseCode());
            response.setResponseBody(Util.streamlineHttpResponse(urlConnection));
            response.setStatusLine(urlConnection.getResponseMessage());
            response.setPostRequestBody("");

            urlConnection.disconnect();

            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
