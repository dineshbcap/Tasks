package com.aspireqa.ohouse.jira;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.client.ClientProtocolException;

public class AttachmentReader {

	/**
	 * @param args
	 */
	public String getAttachment(String issueId) throws ClientProtocolException, IOException
	{
		return new RestClient().getData(APIInfo.attachmentUrl+getAttachmentId(issueId)+"/Story1.txt");
	}
	public String getAttachmentId(String issueId) throws ClientProtocolException, IOException
	{
		JSONObject response = (JSONObject) JSONSerializer.toJSON(new IssueReader().getIssue(issueId)); 
		JSONObject fields = response.getJSONObject("fields");
		JSONArray attachment = fields.getJSONArray("attachment");
		return attachment.getJSONObject(0).getString("id");
	}
}
