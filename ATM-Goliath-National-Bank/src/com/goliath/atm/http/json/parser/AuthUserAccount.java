package com.goliath.atm.http.json.parser;

import org.json.JSONObject;

import com.goliath.atm.http.RequestListenerInterface;
import com.goliath.atm.model.Account;

public class AuthUserAccount implements ParserInterface {
	
	private static String KEY_ACCOUNT_TAG = "key_account";
	private static String ERROR_TAG = "ERROR";
	

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		JSONObject json = new JSONObject(data);
		
		int error = -1;
		
		try {
			error = json.getInt(ERROR_TAG);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		if(error == 0) {
			requester.onReceivedError(0);
		} else {
			Account a = new Account();
			a.setKey(json.getInt(KEY_ACCOUNT_TAG));
			requester.onReceivedData(a);
		}	
	}

}
