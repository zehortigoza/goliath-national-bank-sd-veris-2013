package com.goliath.atm.view;

import android.app.Activity;
import android.app.ProgressDialog;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestListenerInterface;

public class BaseActivity extends Activity implements RequestListenerInterface {
	
	private ProgressDialog mProgressDialog;
	protected String mBaseUrl = "http://localhost/";
	
	public void onReceivedData(Object data) {
		
	}

	public void onRequestFinished(boolean status) {
		if(mProgressDialog != null) {
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

}
