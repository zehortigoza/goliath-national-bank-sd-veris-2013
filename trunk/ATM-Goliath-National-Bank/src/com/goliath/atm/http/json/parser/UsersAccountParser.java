package com.goliath.atm.http.json.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.goliath.atm.http.RequestListenerInterface;
import com.goliath.atm.model.User;


public class UsersAccountParser implements ParserInterface {
	
	private static final String ERROR_TAG = "ERROR";
	private static final String LIST_TAG = "list";
	private static final String NAME_TAG = "NOME";
	private static final String KEY_TAG = "ID";
	private static final String KEY_CC = "CCID";

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		
		Log.v("test","Json received = "+data);
		JSONObject json = new JSONObject(data);
		
		int errorCode = -1;
		
		try {
			errorCode = json.getInt(ERROR_TAG);
		} catch (JSONException ex) {
			// do nothing
		}
		
		if(errorCode > 0) {
			requester.onReceivedError(json.getInt(ERROR_TAG));
		} else {
			JSONArray jsonArray = json.getJSONArray(LIST_TAG);
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject o = (JSONObject)jsonArray.get(i);
				
				User u = new User();
				u.setKey(o.getInt(KEY_TAG));
				u.setName(o.getString(NAME_TAG));
				u.setCCId(o.getInt(KEY_CC));
				
				requester.onReceivedData(u);
			}
		}
	}

}
