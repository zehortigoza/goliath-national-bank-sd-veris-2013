package com.goliath.atm.view;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.AddPfParser;
import com.goliath.atm.model.Account;

public class AddPf extends BaseActivity {
	private Account mAccount;

	private EditText mName;
	private EditText mCpf;
	private EditText mBank;
	private EditText mAgency;
	private EditText mAccountEdit;
	
	private static final String KEY_ADD_PF_URL = "cadFisica";
	private static final String KEY_NAME = "nome";
	private static final String KEY_CPF = "cpf";
	private static final String KEY_BANK = "idBancoDestino";
	private static final String KEY_ACCOUNT = "idContaDestino";
	private static final String KEY_ACCOUNT_FROM = "idContaOrigem";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_pf);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAccount = (Account) extras
					.getSerializable(AccountUsers.ACCOUNT_TAG);
		}

		mName = (EditText) findViewById(R.id.edit_name);
		mCpf = (EditText) findViewById(R.id.edit_cpf);
		mBank = (EditText) findViewById(R.id.edit_bank_code);
		mAgency = (EditText) findViewById(R.id.edit_agency);
		mAccountEdit = (EditText) findViewById(R.id.edit_account);
	}

	public void doDoc(View view) {
		if (mName.getEditableText().toString().length() > 0
				&& mCpf.getEditableText().toString().length() > 0
				&& mBank.getEditableText().toString().length() > 0
				&& mAgency.getEditableText().toString().length() > 0
				&& mAccountEdit.getEditableText().toString().length() > 0) {

			try {
				JSONObject j = new JSONObject();
				j.put(KEY_NAME, mName.getEditableText().toString());
				j.put(KEY_CPF,mCpf.getEditableText().toString());
				j.put(KEY_BANK, mBank.getEditableText().toString());
				j.put(KEY_ACCOUNT, mAccountEdit.toString());
				j.put(KEY_ACCOUNT_FROM,mAccount.getKey());

				RequestDataAsync request = new RequestDataAsync(sBaseUrl
						+ KEY_ADD_PF_URL, j, new AddPfParser(), this);
				request.execute();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.error));
			alertDialog.setMessage(getString(R.string.error_fill_all_fields));
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					return;
				}
			});
			alertDialog.show();
		}
	}

	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);
		if (status) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.sucess));
			alertDialog.setMessage(getString(R.string.add_pf_sucess));
			alertDialog.setCancelable(false);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					AddPf.this.finish();
					return;
				}
			});
			alertDialog.show();
		} else {
			genericHttpError();
		}
	}
}
