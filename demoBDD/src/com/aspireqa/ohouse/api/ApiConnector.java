package com.aspireqa.ohouse.api;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class ApiConnector {	
	
	static HttpClient httpclient = new DefaultHttpClient();
	static HttpResponse response ;
	
	static HttpPost httppost;
	
	
	
	public static String postRequest(String url, String acceptType, String contentType, String requestBody)
	{

		String responseData = "";
		try
		{
		httppost = new HttpPost(url);
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, 
			        new HttpHost("proxy.aspiresys.com", 3128, "http"));
		httppost = setAcceptTypeHeader(acceptType);
		httppost = setContentTypeHeader(contentType);
		
		httppost.setEntity( new StringEntity(requestBody));
		
	
		response = httpclient.execute(httppost);

		responseData = EntityUtils.toString(response.getEntity());
        
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		httppost.releaseConnection();	

		return responseData;		
	}
	
	public static HttpPost setAcceptTypeHeader(String acceptTypeValue)
	{
		
		httppost.addHeader("Accept", acceptTypeValue);	
		
		return httppost;		
	}
  
	public static HttpPost setContentTypeHeader(String contentTypeValue)
	{
		
		httppost.addHeader("Content-Type", contentTypeValue);	
		
		return httppost;		
	}
}
