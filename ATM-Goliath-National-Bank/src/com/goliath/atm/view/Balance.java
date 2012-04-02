package com.goliath.atm.view;

import android.os.Bundle;
import android.view.View;

import com.goliath.atm.R;

public class Balance extends BaseActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance);
	}
	
	public void back(View view) {
		finish();
	}
}
