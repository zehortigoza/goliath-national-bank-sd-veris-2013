package com.goliath.atm.view;

import com.goliath.atm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Balance extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance);
	}
	
	public void back(View view) {
		finish();
	}
}
