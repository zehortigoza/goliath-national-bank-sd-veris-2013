package com.goliath.atm.view;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.AuthUserAccount;
import com.goliath.atm.model.Account;
import com.goliath.atm.model.User;

public class AccountUsers extends BaseActivity {
	private TextView mLabelName;//label ask to user select your name
	private LinearLayout mLl;//layout to be populate with all users names of account

	private TextView mLabelPassword;//label password
	private EditText mEditPassword;//field password
	private Button mSubmit;

	private TextView mLabelWelcome;

	//private boolean mNameSelected = false;
	private User mUserSelected;	

	private ArrayList<User> mListUser;
	
	private static final String TAG_NAME_SELECTED = "user_selected";
	
	private static String PASS_USER_TAG = "senha";
	private static String CCID_TAG = "clienteContaId";
	private static final String AUTH_ACCOUNT_URL = "autenticaCliente";	
	public static final String ACCOUNT_TAG = "account";
	public static final String USER_TAG = "user";
	
	private boolean mFlagError = false;
	private Account mAccount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_users);
		
		mLabelName = (TextView) findViewById(R.id.label_name);

		mLabelPassword = (TextView) findViewById(R.id.label_password);
		mEditPassword = (EditText) findViewById(R.id.password_text);
		mSubmit = (Button) findViewById(R.id.submit_bnt);
		mLabelWelcome = (TextView) findViewById(R.id.text_welcome);

		mLl = (LinearLayout) findViewById(R.id.bnt_names_containner);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Serializable s = extras.getSerializable(AccountAccess.LIST_TAG);
			mListUser = (ArrayList<User>) s;
		}

		if (mListUser == null) {
			finish();
		} else {
			mLl.removeAllViews();

			for (User u : mListUser) {
				Button b = new Button(this);
				LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				b.setLayoutParams(lp);
				b.setText(u.getName());
				b.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Button b2 = (Button) v;
						userSelected(b2.getText().toString());
					}
				});

				mLl.addView(b);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (AccountAccess.getLogout()) {
			finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(TAG_NAME_SELECTED, mUserSelected);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		mUserSelected = (User)savedInstanceState.getSerializable(TAG_NAME_SELECTED);
		change();
	}

	private void change() {
		if (mUserSelected != null) {
			mLl.setVisibility(View.GONE);
			mLabelName.setVisibility(View.GONE);

			mLabelPassword.setVisibility(View.VISIBLE);
			mEditPassword.setVisibility(View.VISIBLE);
			mSubmit.setVisibility(View.VISIBLE);
			mLabelWelcome.setText(getString(R.string.welcome_msg).replace("?",
					mUserSelected.getName()));
			mLabelWelcome.setVisibility(View.VISIBLE);
		} else {
			mLabelPassword.setVisibility(View.GONE);
			mEditPassword.setVisibility(View.GONE);
			mSubmit.setVisibility(View.GONE);
			mLabelWelcome.setVisibility(View.GONE);

			mLl.setVisibility(View.VISIBLE);
			mLabelName.setVisibility(View.VISIBLE);
		}
	}

	public void back(View view) {
		if (mUserSelected == null) {
			finish();
		} else {
			mUserSelected = null;
			change();
		}
	}

	public void submitUser(View view) {
		try {
			mFlagError = false;
			mAccount = null;
			
			JSONObject j = new JSONObject();
			//j.put(KEY_USER_TAG, mUserSelected.getKey());
			j.put(PASS_USER_TAG, mEditPassword.getEditableText().toString());
			j.put(CCID_TAG, mUserSelected.getCCId());

			RequestDataAsync request = new RequestDataAsync(sBaseUrl
					+ AUTH_ACCOUNT_URL, j, new AuthUserAccount(), this);
			request.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void userSelected(String name) {
		for (User u : mListUser) {
			if (u.getName().equals(name)) {
				mUserSelected = u;
				change();
				break;
			}
		}
	}

	@Override
	public void onReceivedData(Object data) {
		if(data instanceof Account) {
			mAccount = (Account)data;
		}
	}

	@Override
	public void onReceivedError(int idError) {
		mFlagError = true;
	}
	
	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);
		
		if(!status) {
			genericHttpError();
		} else {
			if(mFlagError) {
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle(getString(R.string.error));
				alertDialog.setMessage("Senha incorreta!");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
					}
				});		
				alertDialog.show();
			} else {
				Intent i = new Intent(this,MainScreen.class);
				i.putExtra(ACCOUNT_TAG, mAccount);
				i.putExtra(USER_TAG, mUserSelected);
				startActivity(i);
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		mAccount = null;
		mUserSelected = null;
	}

}
