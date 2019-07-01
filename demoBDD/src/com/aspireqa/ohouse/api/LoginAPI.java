package com.aspireqa.ohouse.api;

import org.json.JSONObject;

import com.aspireqa.ohouse.utilities.Reader;


public class LoginAPI extends ApiConnector
{
    public LoginAPI loginAPI;	
	
	private static String requestBody = "{\"EZMessage\":{\"action\":\"auth.login\",\"data\":{\"userName\":\"" + Reader.configMap.get("login.username") + "\",\"password\":\"" + Reader.configMap.get("login.password") + "\"}}}";
	
	public static String getAuthTokenFromloginAPI(String usrName, String pwd)
	{
		
		String token = "";
		
		
		try {
			String response = postRequest(Reader.configMap.get("api.login.endpoint"), "text/xml", "application/json", requestBody);
			JSONObject stringToJSON = new JSONObject(response);
			token = (String) stringToJSON.getJSONObject("EZMessage").getJSONObject("data").get("authToken");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

		return token;
	
}}
