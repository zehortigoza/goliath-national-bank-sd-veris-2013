package com.goliath.atm.model;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int mKey;

	public int getKey() {
		return mKey;
	}

	public void setKey(int key) {
		mKey = key;
	}

}
