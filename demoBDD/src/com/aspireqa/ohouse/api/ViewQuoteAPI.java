package com.aspireqa.ohouse.api;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aspireqa.ohouse.utilities.Reader;


public class ViewQuoteAPI extends ApiConnector{
	
	final String username = Reader.configMap.get("login.username");
	final String password = Reader.configMap.get("login.password");
	
	String strikePrice = "TOCOMPUTE";
	String requestBodyForPUT = "{\"EZMessage\":{\"action\":\"view.quote.list\",\"data\":{\"authToken\":\"" + LoginAPI.getAuthTokenFromloginAPI(username,password) + "\",\"key\":[\"MARKETSTATUS\",\"GOOG:20140131:" + strikePrice + ":P\",\"QQQQ:::S\",],\"addExtended\":[\"QQQQ:::S\"],\"addStockDetails\":[\"QQQQ:::S\"],\"addFundamentalDetails\":[\"QQQQ:::S\"]}}}";
	String requestBodyForCALL = "{\"EZMessage\":{\"action\":\"view.quote.list\",\"data\":{\"authToken\":\"" + LoginAPI.getAuthTokenFromloginAPI(username,password) + "\",\"key\":[\"MARKETSTATUS\",\"GOOG:20140131:" + strikePrice + ":C\",\"QQQQ:::S\",],\"addExtended\":[\"QQQQ:::S\"],\"addStockDetails\":[\"QQQQ:::S\"],\"addFundamentalDetails\":[\"QQQQ:::S\"]}}}";
	String responseBody;
	
	private String calculateStrikePrice(String series)
	{
		System.out.println(series);
		//String sprice = series.split("(W)")[1].trim();
		
		return ("112750" + "00");
	}
	
	private String roundOff(String value)
	{
		
		double d = Math.round((Double.parseDouble(value)) * 100.0) / 100.0;
		
		return (String.valueOf(d));
	}

	public HashMap<String, String> callViewQuoteForPUTOption(String strikeprice)
	{
		String strikePrice = calculateStrikePrice(strikeprice);
		requestBodyForPUT = requestBodyForPUT.replace("TOCOMPUTE", strikePrice);
		HashMap<String, String> quoteLookupFromAPI = new HashMap<String, String>();
		
		try {
			
			responseBody = postRequest(Reader.configMap.get("api.viewQuote.endpoint"), "text/xml", "application/json", requestBodyForPUT);

			JSONObject stringToJSON = new JSONObject(responseBody).getJSONObject("EZMessage").getJSONObject("data");
			
			JSONArray jarray = (JSONArray)stringToJSON.get("quote");

			jarray.getJSONObject(1);
			
			quoteLookupFromAPI.put("putBidPrice", roundOff(jarray.getJSONObject(1).getString("bid")));
			quoteLookupFromAPI.put("putAskPrice", roundOff(jarray.getJSONObject(1).getString("ask")));
			quoteLookupFromAPI.put("putBidSize", jarray.getJSONObject(1).getString("bidSize"));
			quoteLookupFromAPI.put("putAskSize", jarray.getJSONObject(1).getString("askSize"));
			quoteLookupFromAPI.put("putVolume", jarray.getJSONObject(1).getString("volume"));
			quoteLookupFromAPI.put("putOI", jarray.getJSONObject(1).getString("oi"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quoteLookupFromAPI;
	}
	
	
	public HashMap<String, String> callViewQuoteForCALLOption(String strikeprice)
	{
		HashMap<String, String> quoteLookupFromAPI = new HashMap<String, String>();
		String strikePrice = calculateStrikePrice(strikeprice);
		requestBodyForCALL = requestBodyForCALL.replace("TOCOMPUTE", strikePrice);
		
		try {
			
			responseBody = postRequest(Reader.configMap.get("api.viewQuote.endpoint"), "text/xml", "application/json", requestBodyForCALL);

			JSONObject stringToJSON = new JSONObject(responseBody).getJSONObject("EZMessage").getJSONObject("data");
			
			JSONArray jarray = (JSONArray)stringToJSON.get("quote");

			jarray.getJSONObject(1); //json array to get first obj
			
			quoteLookupFromAPI.put("callBidPrice", roundOff(jarray.getJSONObject(1).getString("bid")));
			quoteLookupFromAPI.put("callAskPrice", roundOff(jarray.getJSONObject(1).getString("ask")));
			quoteLookupFromAPI.put("callBidSize", jarray.getJSONObject(1).getString("bidSize"));
			quoteLookupFromAPI.put("callAskSize", jarray.getJSONObject(1).getString("askSize"));
			quoteLookupFromAPI.put("callVolume", jarray.getJSONObject(1).getString("volume"));
			quoteLookupFromAPI.put("callOI", jarray.getJSONObject(1).getString("oi"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quoteLookupFromAPI;
	}
	
}
