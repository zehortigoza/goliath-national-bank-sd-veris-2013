package com.goliath.atm.view;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.StatementParser;
import com.goliath.atm.model.Account;
import com.goliath.atm.model.Statement;

public class StatementActivity extends BaseActivity {
	private Account mAccount;

	private static final String STATEMENT_URL = "extrato";
	private static final String KEY_CCID = "contaId";
	
	private LinearLayout mLinear;
	
	private LayoutInflater mInflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statement);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAccount = (Account) extras
					.getSerializable(AccountUsers.ACCOUNT_TAG);
		}

		mLinear = (LinearLayout) findViewById(R.id.linear_layout);
		
		mInflater = (LayoutInflater)getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		try {
			JSONObject j = new JSONObject();
			j.put(KEY_CCID, mAccount.getKey());

			RequestDataAsync request = new RequestDataAsync(sBaseUrl
					+ STATEMENT_URL, j, new StatementParser(), this);
			request.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);
		if (!status) {
			genericHttpError();
		}
	}

	@Override
	public void onReceivedData(Object o) {
		if (o instanceof Statement) {
			final Statement s = (Statement) o;
			Runnable r = new Runnable() {				
				public void run() {
					LinearLayout l = (LinearLayout) mInflater.inflate(R.layout.statement_line, mLinear);
					((TextView)l.findViewById(R.id.text)).setText(s.toString());
					mLinear.addView(l);
				}
			};
			runOnUiThread(r);
		}
	}
}
