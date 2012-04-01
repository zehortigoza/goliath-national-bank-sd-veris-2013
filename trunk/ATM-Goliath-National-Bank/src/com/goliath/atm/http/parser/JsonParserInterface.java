package com.goliath.atm.http.parser;

import com.goliath.atm.http.RequestListenerInterface;


public interface JsonParserInterface {
	public void parse(String data, RequestListenerInterface requester) throws Exception;
}
