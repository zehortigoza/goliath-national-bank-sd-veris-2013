package com.goliath.atm.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.goliath.atm.R;

public class main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_access);
    }
    
    public void accessAccount(View view) {
    	//call http thread to parse an json
    	Intent i = new Intent(this,AccountUsers.class);
    	startActivity(i);
    }
}