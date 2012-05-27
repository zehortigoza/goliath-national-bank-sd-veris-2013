package com.goliath.atm.view;

import org.json.JSONObject;

import android.os.Bundle;
import android.widget.TextView;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.BalanceParser;
import com.goliath.atm.model.Account;

public class Balance extends BaseActivity {
	private static final String KEY_ACCOUNT_TAG = "contaId";
	private static final String URL_BALANCE = "extrato";
	private Double mValue = 0.0;
	private TextView mBalance;
	private Account mAccount;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.balance);
		mBalance = (TextView) findViewById(R.id.balance_txt);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			mAccount = (Account) extras
					.getSerializable(AccountUsers.ACCOUNT_TAG);
		} else {
			finish();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mBalance.setText("0.0");
		try {
			JSONObject j = new JSONObject();
			j.put(KEY_ACCOUNT_TAG, mAccount.getKey());

			RequestDataAsync request = new RequestDataAsync(sBaseUrl
					+ URL_BALANCE, j, new BalanceParser(), this);
			request.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onReceivedData(Object data) {
		mValue = (Double) data;
	}

	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);
		if(status) {
			mBalance.setText(mValue+"");
		} else {
			genericHttpError();
		}
	}
}
