package com.goliath.atm.http.json.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import com.goliath.atm.http.RequestListenerInterface;
import com.goliath.atm.model.Statement;

public class StatementParser implements ParserInterface {
	
	private static final String LIST_TAG = "list";
	private static final String DATE_TAG = "dateTime";
	private static final String TYPE_TAG = "tipoTransacao";
	private static final String BANK_TAG = "bancoTransacao";
	private static final String AG_TAG = "agTransacao";
	private static final String CC_TAG = "ccTransacao";
	private static final String DESCRIPTION_TAG = "descricao";
	private static final String VALUE_TAG = "valor";

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		JSONObject json = new JSONObject(data);
		
		JSONArray jsonArray = json.getJSONArray(LIST_TAG);
		for(int i = 0; i < jsonArray.length(); i++) {
			JSONObject o = (JSONObject)jsonArray.get(i);
			
			Statement s = new Statement();
			
			s.setDate(o.getString(DATE_TAG));
			s.setAccount(o.getInt(CC_TAG));
			s.setAgency(o.getInt(AG_TAG));
			s.setBank(o.getInt(BANK_TAG));
			s.setDescription(o.getString(DESCRIPTION_TAG));
			s.setType(o.getString(TYPE_TAG));
			s.setValue(o.getDouble(VALUE_TAG));
			
			requester.onReceivedData(s);
		}		
	}

}
