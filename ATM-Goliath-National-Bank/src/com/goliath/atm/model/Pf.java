package com.goliath.atm.model;

public class Pf {
	public String getmCpf() {
		return mCpf;
	}

	public void setCpf(String mCpf) {
		this.mCpf = mCpf;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public int getIdBank() {
		return mIdBank;
	}

	public void setIdBank(int idBank) {
		this.mIdBank = idBank;
	}

	public int getIdAccount() {
		return mIdAccount;
	}

	public void setIdAccount(int idAccount) {
		this.mIdAccount = idAccount;
	}

	private String mCpf;
	private String mName;
	private int mIdBank;
	private int mIdAccount;
	
	@Override
	public String toString() {
		return mName;
	}
}
