package com.aspireqa.ohouse.jira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class RestClient {

	/**
	 * @param args
	 */
	HttpClient client;
	HttpGet get;
	HttpResponse response;
	BufferedReader rd;
	String responseString = "";
	Credentials credentials;
	HttpPost post;
	StringEntity entity;

	public RestClient() {
		client = new DefaultHttpClient();
		credentials = new UsernamePasswordCredentials(APIInfo.jiraUserName,
				APIInfo.jiraPassword);

	}

	public String getData(String resource)throws IOException,ClientProtocolException {
		HttpGet get = new HttpGet(APIInfo.jiraHostUrl+resource);
		get.addHeader(BasicScheme.authenticate(credentials, "US-ASCII", false));
		response = client.execute(get);
		rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line ="";
		  while ((line = rd.readLine()) != null) {
			  responseString+=line;
		  }

		return responseString;
	}

	public String postData(String resource, String requestBody)throws IOException,ClientProtocolException {
		post = new HttpPost(APIInfo.jiraHostUrl+resource);
		post.addHeader(BasicScheme.authenticate(credentials, "US-ASCII", false));
		entity= new StringEntity(requestBody,HTTP.UTF_8);
		entity.setContentType(APIInfo.contentType_JSON);
		post.setEntity(entity);
		response = client.execute(post);
		  rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		  String line ="";
		  while ((line = rd.readLine()) != null) {
			  responseString+=line;
		  }
		return responseString;
	}
}
