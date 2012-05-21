package com.goliath.atm.http.json.parser;

import org.json.JSONObject;

import com.goliath.atm.http.RequestListenerInterface;

public class BalanceParser implements ParserInterface {

	private static String VALUE_TAG = "value";

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		JSONObject json = new JSONObject(data);

		Double value = json.getDouble(VALUE_TAG);
		requester.onReceivedData(value);
	}

}
