package com.goliath.atm.http.json.parser;

import com.goliath.atm.http.RequestListenerInterface;


public interface ParserInterface {
	public void parse(String data, RequestListenerInterface requester) throws Exception;
}
