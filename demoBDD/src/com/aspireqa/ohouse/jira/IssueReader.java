package com.aspireqa.ohouse.jira;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class IssueReader {

	/**
	 * @param args
	 */
	public String getIssue(String issueId) throws ClientProtocolException, IOException
	{
		return new RestClient().getData(APIInfo.issueUrl+issueId);
	}
	
}
