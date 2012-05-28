package com.goliath.atm.view;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.RequestPfParser;
import com.goliath.atm.http.json.parser.TedParser;
import com.goliath.atm.model.Account;
import com.goliath.atm.model.Pf;

public class Ted extends BaseActivity implements OnItemSelectedListener {
	private Account mAccount;

	private EditText mValueEdit;
	private Spinner mSpinner;
	private ArrayAdapter<Pf> mArrayAdapter;
	private Pf mSelectedPf = null;

	private static final String KEY_VALUE_TAG = "valor";
	private static final String KEY_BANK_FROM = "idBancoOrigem";
	private static final String KEY_ACCOUNT_FROM = "idContaOrigem";
	private static final String KEY_BANK_TO	 = "idBancoDestino";
	private static final String KEY_ACCOUNT_TO = "idContaDestino";
	private static final String TED_URL = "ted";// binder

	private static final String REQUEST_PF_URL = "getFisica";
	private static final String KEY_CCID = "contaId";

	private boolean mRequestingPf = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ted);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAccount = (Account) extras
					.getSerializable(AccountUsers.ACCOUNT_TAG);
		}

		mValueEdit = (EditText) findViewById(R.id.edit_value_doc);
		mSpinner = (Spinner) findViewById(R.id.pf_spinner);

		mArrayAdapter = new ArrayAdapter<Pf>(this,
				android.R.layout.simple_spinner_item, new Pf[100]);
		mSpinner.setAdapter(mArrayAdapter);
		mSpinner.setOnItemSelectedListener(this);

		//
		try {
			JSONObject j = new JSONObject();
			j.put(KEY_CCID, mAccount.getKey());

			RequestDataAsync request = new RequestDataAsync(sBaseUrl
					+ REQUEST_PF_URL, j, new RequestPfParser(), this);
			request.execute();
			mRequestingPf = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void doTed(View view) {
		if (mSelectedPf == null) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.error));
			alertDialog.setMessage(getString(R.string.select_a_pf));
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
				j.put(KEY_VALUE_TAG,mValueEdit.getEditableText().toString());
				j.put(KEY_BANK_FROM, sBankId);
				j.put(KEY_ACCOUNT_FROM,mAccount.getKey());
				j.put(KEY_BANK_TO,mSelectedPf.getIdBank());
				j.put(KEY_ACCOUNT_TO,mSelectedPf.getIdAccount());

				RequestDataAsync request = new RequestDataAsync(sBinderBaseUrl
						+ TED_URL, j, new TedParser(), this);
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
			if (mRequestingPf) {
				mRequestingPf = false;
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle(getString(R.string.withdraw_success));
				alertDialog.setMessage(getString(R.string.doc_ted_ok));
				alertDialog.setCancelable(false);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Ted.this.finish();
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
	public void onReceivedData(Object o) {
		if (o instanceof Pf) {
			Pf p = (Pf) o;
			mArrayAdapter.add(p);
			mArrayAdapter.notifyDataSetChanged();
		}
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		mSelectedPf = (Pf) mArrayAdapter.getItem(position);
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}
}
