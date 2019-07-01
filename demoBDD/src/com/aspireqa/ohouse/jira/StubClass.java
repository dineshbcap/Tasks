package com.aspireqa.ohouse.jira;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class StubClass {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try {
			System.out.println(new AttachmentReader().getAttachment("OHUI-43"));
			System.out.println(new PostComment().addComment("OHUI-43","Testing- Adding comment through automation script"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}


}
