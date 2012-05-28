package com.goliath.atm.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestListenerInterface;

public class BaseActivity extends Activity implements RequestListenerInterface {
	
	private ProgressDialog mProgressDialog;
	protected static String sBaseUrl = "http://192.168.43.109/Goliah/JSON_WS/Bank.asmx/";
	protected static String sBinderBaseUrl = "";
	
	protected static String KEY_ACCOUNT_TAG = "contaId";
	
	protected int sBankId = 1;//1 = goliath | 2 = universal
	
	public void onReceivedData(Object data) {
		
	}

	public void onRequestFinished(boolean status) {
		if(mProgressDialog != null && !isFinishing()) {
			mProgressDialog.dismiss();	
			mProgressDialog = null;
		}
	}

	public void onReceivedError(int idError) {
		
	}

	public void startRequest() {
		Runnable r =  new Runnable() {			
			public void run() {
				mProgressDialog = new ProgressDialog(BaseActivity.this);
				mProgressDialog.setTitle(getString(R.string.in_progress));
				mProgressDialog.setMessage(getString(R.string.loading));
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();				
			}
		};
		runOnUiThread(r);
	}
	
	protected void genericHttpError() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(R.string.error));
		alertDialog.setMessage(getString(R.string.generic_error));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}
		});		
		alertDialog.show();
	}
	
	public void back(View view) {
		finish();
	}

}
