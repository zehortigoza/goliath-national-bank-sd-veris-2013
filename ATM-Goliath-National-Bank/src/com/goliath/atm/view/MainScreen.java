package com.goliath.atm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.goliath.atm.R;

public class MainScreen extends BaseActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
	}
	
	public void withdraw(View view) {
		Intent i = new Intent(this,Withdraw.class);
		startActivity(i);
	}
	
	public void deposit(View view) {
		Intent i = new Intent(this,Deposit.class);
		startActivity(i);
	}
	
	public void balance(View view) {
		Intent i = new Intent(this,Balance.class);
		startActivity(i);
	}
	
	public void logOut(View view) {
		AccountAccess.setLogout(true);
		finish();
	}

}
