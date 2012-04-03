package com.goliath.atm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.goliath.atm.R;
import com.goliath.atm.model.Account;
import com.goliath.atm.model.User;

public class MainScreen extends BaseActivity {
	
	private Account mAccount;
	private User mUser;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        
        Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAccount = (Account)extras.getSerializable(AccountUsers.ACCOUNT_TAG);
			mUser = (User)extras.getSerializable(AccountUsers.USER_TAG);
		}
	}
	
	public void withdraw(View view) {
		Intent i = new Intent(this,Withdraw.class);
		i.putExtra(AccountUsers.ACCOUNT_TAG, mAccount);
		i.putExtra(AccountUsers.USER_TAG, mUser);
		startActivity(i);
	}
	
	public void deposit(View view) {
		Intent i = new Intent(this,Deposit.class);
		i.putExtra(AccountUsers.ACCOUNT_TAG, mAccount);
		i.putExtra(AccountUsers.USER_TAG, mUser);
		startActivity(i);
	}
	
	public void balance(View view) {
		Intent i = new Intent(this,Balance.class);
		i.putExtra(AccountUsers.ACCOUNT_TAG, mAccount);
		i.putExtra(AccountUsers.USER_TAG, mUser);
		startActivity(i);
	}
	
	public void logOut(View view) {
		AccountAccess.setLogout(true);
		finish();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		mAccount = null;
		mUser = null;
	}

}