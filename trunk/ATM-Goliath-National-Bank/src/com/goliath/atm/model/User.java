package com.goliath.atm.model;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mName;
	private int mKey;
	
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		this.mName = name;
	}
	public int getKey() {
		return mKey;
	}
	public void setKey(int key) {
		this.mKey = key;
	}
}
