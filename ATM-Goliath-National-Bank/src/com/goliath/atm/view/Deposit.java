package com.goliath.atm.view;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.DepositParser;
import com.goliath.atm.model.Account;

public class Deposit extends BaseActivity {
	private EditText mValue;

	private boolean mDeposit = false;

	private static String DEPOSIT_URL = "deposito";
	private static String VALUE_TAG = "valor";
	
	private Account mAccount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deposit);

		mValue = (EditText) findViewById(R.id.edit_value_deposit);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAccount = (Account) extras
					.getSerializable(AccountUsers.ACCOUNT_TAG);
		} else {
			finish();
		}
	}

	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);

		if (status) {
			if (mDeposit) {
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.create();
				alertDialog.setTitle(getString(R.string.success_deposit));
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								finish();
								return;
							}
						});
				alertDialog.show();
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.create();
				alertDialog.setTitle(getString(R.string.error_deposit));
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								finish();
								return;
							}
						});
				alertDialog.show();
			}
		} else {
			genericHttpError();
		}
	}

	@Override
	public void onReceivedData(Object data) {
		if (data instanceof Boolean) {
			mDeposit = (Boolean) data;
		}
	}

	public void doDeposit(View view) {
		float value = Float.parseFloat(mValue.getEditableText().toString());

		try {
			JSONObject j = new JSONObject();
			j.put(KEY_ACCOUNT_TAG, mAccount.getKey());
			j.put(VALUE_TAG, value);

			RequestDataAsync request = new RequestDataAsync(sBaseUrl
					+ DEPOSIT_URL, j, new DepositParser(), this);
			request.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
