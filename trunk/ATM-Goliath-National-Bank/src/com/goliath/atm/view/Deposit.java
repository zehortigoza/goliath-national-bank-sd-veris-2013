package com.goliath.atm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.goliath.atm.R;

public class Deposit extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deposit);
	}

	public void back(View view) {
		finish();
	}

	public void doDeposit(View view) {
		// check if account have enough money and there is notes available for
		// this
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(R.string.error));
		alertDialog.setMessage("This is an alert");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}
		});

		alertDialog.show();
	}
}