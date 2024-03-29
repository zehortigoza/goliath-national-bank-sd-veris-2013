package com.goliath.atm.http.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import com.goliath.atm.http.RequestListenerInterface;
import com.goliath.atm.model.User;


public class UsersAccountParser implements JsonParserInterface {
	
	private static final String ERROR_TAG = "error";
	private static final String LIST_TAG = "list";
	private static final String NAME_TAG = "name";
	private static final String KEY_TAG = "key_user";

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		JSONObject json = new JSONObject(data);
		
		if(json.getInt(ERROR_TAG) == 1 || json.getInt(ERROR_TAG) == 2) {
			requester.onReceivedError(json.getInt(ERROR_TAG));
		} else {
			JSONArray jsonArray = json.getJSONArray(LIST_TAG);
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject o = (JSONObject)jsonArray.get(i);
				
				User u = new User();
				u.setKey(o.getInt(KEY_TAG));
				u.setName(o.getString(NAME_TAG));
				
				requester.onReceivedData(u);
			}
		}
	}

}
