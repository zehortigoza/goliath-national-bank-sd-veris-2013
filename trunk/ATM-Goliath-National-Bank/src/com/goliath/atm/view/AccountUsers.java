package com.goliath.atm.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.goliath.atm.R;

public class AccountUsers extends Activity {
	
	private HorizontalScrollView mHScrollView;
	private TextView mLabelName;
	
	private TextView mLabelPassword;
	private EditText mEditPassword;
	private Button mSubmit;
	
	private boolean mNameSelected = false;
	private static final String TAG_NAME_SELECTED = "user_selected";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_users);
        
        mHScrollView = (HorizontalScrollView) findViewById(R.id.hscrollview_names);
        mLabelName = (TextView) findViewById(R.id.label_name);
        
        mLabelPassword = (TextView) findViewById(R.id.label_password);
        mEditPassword = (EditText) findViewById(R.id.password_text);
        mSubmit = (Button) findViewById(R.id.submit_bnt);
    }
	
	@Override
	protected void onSaveInstanceState (Bundle outState) {
		outState.putBoolean(TAG_NAME_SELECTED, mNameSelected);
	}
	
	@Override
	protected void onRestoreInstanceState (Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		mNameSelected = savedInstanceState.getBoolean(TAG_NAME_SELECTED);
		change();
	}
	
	private void change() {
		if(mNameSelected) {
			mHScrollView.setVisibility(View.GONE);
			mLabelName.setVisibility(View.GONE);
			
			mLabelPassword.setVisibility(View.VISIBLE);
			mEditPassword.setVisibility(View.VISIBLE);
			mSubmit.setVisibility(View.VISIBLE);
		} else {
			mLabelPassword.setVisibility(View.GONE);
			mEditPassword.setVisibility(View.GONE);
			mSubmit.setVisibility(View.GONE);
			
			mHScrollView.setVisibility(View.VISIBLE);
			mLabelName.setVisibility(View.VISIBLE);
		}
	}
	
	public void back(View view) {
		if(mNameSelected == false) {
			finish();
		} else {
			mNameSelected = false;
			change();
		}
	}
	
	public void nameSelected(View view) {	
		mNameSelected = true;
		change();
	}

}
