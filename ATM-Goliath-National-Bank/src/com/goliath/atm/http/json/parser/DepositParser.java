package com.goliath.atm.http.json.parser;

import org.json.JSONObject;

import com.goliath.atm.http.RequestListenerInterface;

public class DepositParser implements ParserInterface {
	
	private static final String RESULT_TAG = "status";

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		JSONObject json = new JSONObject(data);
		
		requester.onReceivedData(json.getBoolean(RESULT_TAG));
		
	}

}
