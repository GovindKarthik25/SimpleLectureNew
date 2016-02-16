package com.simplelecture.main.http;

import java.net.URI;

/**
 * Created by M1032185 on 2/3/2016.
 */
public interface IRestMethod {

    public HttpResponse sendPostRequest(URI uri, String body);

    public HttpResponse sendGetRequest(URI uri,String token);
}
