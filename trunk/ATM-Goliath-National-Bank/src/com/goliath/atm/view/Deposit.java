package com.goliath.atm.view;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.BalanceParser;
import com.goliath.atm.model.User;

public class Deposit extends BaseActivity {
	private User mUser;
	private EditText mValue;
	
	private static String DEPOSIT_URL = "deposit/";
	private static String KEY_ACCOUNT_TAG = "key_conta";
	private static String VALUE_TAG = "value";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deposit);
		
		mValue = (EditText) findViewById(R.id.edit_value_deposit);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			mUser = (User)extras.getSerializable(AccountUsers.ACCOUNT_TAG);
		} else {
			finish();
		}
	}
	
	@Override
    public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);
		
		if(status) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.success_deposit));
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();
					return;
				}
			});
			alertDialog.show();
		} else {
			genericHttpError();
		}
	}

	public void doDeposit(View view) {
		float value = Float.parseFloat(mValue.getEditableText().toString());
		float auxValue = value;
		
		while(auxValue >= 5) {//100,50,20,10,5,2
			auxValue-=5;
		}
		while(auxValue >= 2){
			auxValue-=2;
		}
		if(auxValue > 0) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.error));
			alertDialog.setMessage(getString(R.string.invalid_deposit));
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					return;
				}
			});
			alertDialog.show();
		} else {
			try{
				JSONObject j = new JSONObject();
				j.put(KEY_ACCOUNT_TAG, mUser.getKey());
				j.put(VALUE_TAG, value);
	
				RequestDataAsync request = new RequestDataAsync(sBaseUrl
						+ DEPOSIT_URL, j, new BalanceParser(), this);
				request.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
