package com.goliath.atm.view;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.UsersAccountParser;
import com.goliath.atm.model.User;

public class AccountAccess extends BaseActivity {
	private static boolean LOGOUT = false;
	private EditText mAgencyEdit;
	private EditText mAccountEdit;
	private int mErroId;
	private ArrayList<User> mUserList;

	public static final String AG_TAG = "agencia";
	public static final String CC_TAG = "conta";
	public static final String LIST_TAG = "list";
	private static final String URL_REQUEST_USERS = "getClientes";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_access);

		mAgencyEdit = (EditText) findViewById(R.id.edit_agency);
		mAccountEdit = (EditText) findViewById(R.id.edit_account);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (LOGOUT) {
			LOGOUT = false;
		}
	}

	public void accessAccount(View view) {
		mErroId = 0;
		String agency = mAgencyEdit.getEditableText().toString();
		String account = mAccountEdit.getEditableText().toString();
		try {
			JSONObject j = new JSONObject();
			j.put(AG_TAG, agency);
			j.put(CC_TAG, account);

			mUserList = new ArrayList<User>();

			RequestDataAsync request = new RequestDataAsync(sBaseUrl
					+ URL_REQUEST_USERS, j, new UsersAccountParser(), this);
			request.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setLogout(boolean logout) {
		LOGOUT = logout;
	}

	public static boolean getLogout() {
		return LOGOUT;
	}

	@Override
	public void onReceivedData(Object data) {
		if (data instanceof User) {
			User u = (User) data;
			mUserList.add(u);
		}
	}

	@Override
	public void onReceivedError(int id) {
		mErroId = id;
	}

	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);

		if (mErroId == 0 && status == true) {
			Intent i = new Intent(this, AccountUsers.class);
			i.putExtra(LIST_TAG, mUserList);

			startActivity(i);

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.error));

			String msg;

			switch (mErroId) {
			case 1:
				msg = getString(R.string.account_not_found);
				break;
			case 2:
				msg = getString(R.string.account_inative);
				break;
			case 3:
				msg = getString(R.string.account_without_clients);
			default:
				msg = getString(R.string.generic_error);
				break;
			}

			alertDialog.setMessage(msg);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					return;
				}
			});

			alertDialog.show();
		}
	}
}