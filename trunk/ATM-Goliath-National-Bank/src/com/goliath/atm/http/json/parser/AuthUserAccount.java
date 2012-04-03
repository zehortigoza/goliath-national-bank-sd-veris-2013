package com.goliath.atm.http.json.parser;

import org.json.JSONObject;

import com.goliath.atm.http.RequestListenerInterface;
import com.goliath.atm.model.Account;

public class AuthUserAccount implements ParserInterface {
	
	private static String KEY_ACCOUNT_TAG = "key_account";
	

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		JSONObject json = new JSONObject(data);
		
		if(json.getInt(KEY_ACCOUNT_TAG) > 0) {
			Account a = new Account();
			a.setKey(json.getInt(KEY_ACCOUNT_TAG));
			requester.onReceivedData(a);
		} else {
			requester.onReceivedError(0);
		}		
	}

}
