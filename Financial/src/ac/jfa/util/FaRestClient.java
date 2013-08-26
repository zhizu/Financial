package ac.jfa.util;

import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.AsyncHttpResponseHandler;
import ac.jfa.download.RequestParams;

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
