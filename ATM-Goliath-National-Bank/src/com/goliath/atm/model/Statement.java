package com.goliath.atm.model;

public class Statement {
	private String mDate;
	private String mType;
	private int mBank;
	private int mAgency;
	private int mAccount;
	private String mDescription;
	private double mValue;
	
	@Override
	public String toString() {
		return mDate + " | " + mType + " | " + mBank + " | " + mAgency + " | " + mAccount + " | " + mValue + " | " + mDescription;
	}
	
	public String getDate() {
		return mDate;
	}
	public void setDate(String date) {
		mDate = date;
	}
	public String getType() {
		return mType;
	}
	public void setType(String type) {
		mType = type;
	}
	public int getBank() {
		return mBank;
	}
	public void setBank(int bank) {
		mBank = bank;
	}
	public int getAgency() {
		return mAgency;
	}
	public void setAgency(int agency) {
		mAgency = agency;
	}
	public int getAccount() {
		return mAccount;
	}
	public void setAccount(int account) {
		mAccount = account;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		mDescription = description;
	}
	public double getValue() {
		return mValue;
	}
	public void setValue(double value) {
		mValue = value;
	}
}
