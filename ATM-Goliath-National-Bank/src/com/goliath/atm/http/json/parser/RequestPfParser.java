package com.goliath.atm.http.json.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import com.goliath.atm.http.RequestListenerInterface;
import com.goliath.atm.model.Pf;

public class RequestPfParser implements ParserInterface {    
        
    private static final String CPF_TAG = "cpf";
	private static final String NAME_TAG = "nome";
	private static final String Bank_ID_TAG = "idBancoDestino";
	private static final String ACCOUNT_ID_TAG = "idContaDestino";
	private static final String LIST_TAG = "list";

	public void parse(String data, RequestListenerInterface requester)
			throws Exception {
		JSONObject json = new JSONObject(data);
		
		JSONArray jsonArray = json.getJSONArray(LIST_TAG);
		for(int i = 0; i < jsonArray.length(); i++) {
			JSONObject o = (JSONObject)jsonArray.get(i);
			
			Pf p = new Pf();
			p.setCpf(o.getString(CPF_TAG));
			p.setName(o.getString(NAME_TAG));
			p.setIdBank(o.getInt(Bank_ID_TAG));
			p.setIdAccount(o.getInt(ACCOUNT_ID_TAG));		
			
			requester.onReceivedData(p);
		}
		
	}

}
