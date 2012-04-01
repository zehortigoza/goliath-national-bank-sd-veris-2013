package com.goliath.atm.http;

public interface RequestListenerInterface {
	public void onReceivedData(Object data);
	public void onRequestFinished(boolean status);
	public void onReceivedError(int idError);
}
