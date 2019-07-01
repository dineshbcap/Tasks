package com.aspireqa.ohouse.jira;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import net.sf.json.JSONObject;

public class PostComment {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String addComment(String issueId,String comment) throws ClientProtocolException, IOException
	{
		return new RestClient().postData(APIInfo.issueUrl+issueId+"/comment?expand", buildJsonComment(comment));
	}
	public String buildJsonComment(String commentString)
	{
		JSONObject comment=new JSONObject();
		comment.put("body", commentString);
		JSONObject visibility=new JSONObject();
		visibility.put("type", "role");
		visibility.put("value", "Administrators");
		comment.put("visibility", (Object)visibility);
		return comment.toString();
	}

}
