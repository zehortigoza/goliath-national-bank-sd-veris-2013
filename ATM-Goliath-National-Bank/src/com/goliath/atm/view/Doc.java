package com.goliath.atm.view;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.DocParser;
import com.goliath.atm.model.Account;

public class Doc extends BaseActivity {
	private Account mAccount;
	private static float MAX_VALUE = 1000;

	private EditText mBankCodeEdit;
	private EditText mAccountEdit;
	private EditText mAgencyEdit;
	private EditText mValueEdit;

	private static final String KEY_VALUE_TAG = "valor";
	private static final String KEY_BANK_FROM = "idBancoOrigem";
	private static final String KEY_ACCOUNT_FROM = "idContaOrigem";
	private static final String KEY_BANK_TO = "idBancoDestino";
	private static final String KEY_AGENCY_TO = "agenciaId";
	private static final String KEY_ACCOUNT_TO = "contaId";
	private static final String DOC_URL = "doc";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAccount = (Account) extras
					.getSerializable(AccountUsers.ACCOUNT_TAG);
		}

		mBankCodeEdit = (EditText) findViewById(R.id.edit_bank_code);
		mAgencyEdit = (EditText) findViewById(R.id.edit_agency_code);
		mAccountEdit = (EditText) findViewById(R.id.edit_account_code);
		mValueEdit = (EditText) findViewById(R.id.edit_value_doc);
	}

	public void doDoc(View view) {
		float value = Float.parseFloat(mValueEdit.getEditableText().toString());
		if (value > MAX_VALUE) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.error));
			alertDialog.setMessage(getString(R.string.error_max_value_doc));
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					return;
				}
			});
			alertDialog.show();
		} else {
			try {
				JSONObject j = new JSONObject();
				j.put(KEY_VALUE_TAG, value);
				j.put(KEY_BANK_FROM, sBankId);
				j.put(KEY_ACCOUNT_FROM, mAccount.getKey());
				j.put(KEY_BANK_TO, mBankCodeEdit.getEditableText().toString());
				j.put(KEY_AGENCY_TO, mAccountEdit.getEditableText().toString());
				j.put(KEY_ACCOUNT_TO, mAgencyEdit.getEditableText().toString());

				RequestDataAsync request = new RequestDataAsync(sBinderBaseUrl
						+ DOC_URL, j, new DocParser(), this);
				request.execute();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);
		if (status) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.withdraw_success));
			alertDialog.setMessage(getString(R.string.doc_ted_ok));
			alertDialog.setCancelable(false);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Doc.this.finish();
					return;
				}
			});
			alertDialog.show();	
		} else {
			genericHttpError();
		}
	}
}
