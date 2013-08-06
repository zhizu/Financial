package com.accum.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FaRestClient {
	private static AsyncHttpClient client = new AsyncHttpClient();

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url,AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return relativeUrl;
	  }
}
