package com.goliath.atm.view;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goliath.atm.R;
import com.goliath.atm.model.User;

public class AccountUsers extends BaseActivity {
	
	private HorizontalScrollView mHScrollView;
	private TextView mLabelName;
	
	private TextView mLabelPassword;
	private EditText mEditPassword;
	private Button mSubmit;
	private LinearLayout mLl;
	private TextView mLabelWelcome;
	
	private boolean mNameSelected = false;
	private User mUserSelected;
	private static final String TAG_NAME_SELECTED = "user_selected";
	
	private String mAg;
	private String mCc;
	private ArrayList<User> mListUser;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_users);
        
        mHScrollView = (HorizontalScrollView) findViewById(R.id.hscrollview_names);
        mLabelName = (TextView) findViewById(R.id.label_name);
        
        mLabelPassword = (TextView) findViewById(R.id.label_password);
        mEditPassword = (EditText) findViewById(R.id.password_text);
        mSubmit = (Button) findViewById(R.id.submit_bnt);
        mLabelWelcome = (TextView) findViewById(R.id.text_welcome);
        
        mLl = (LinearLayout) findViewById(R.id.bnt_names_containner);
        
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
        	mAg = extras.getString(AccountAccess.AG_TAG);
        	mCc = extras.getString(AccountAccess.CC_TAG);
        	Serializable s = extras.getSerializable(AccountAccess.LIST_TAG);
        	mListUser = (ArrayList<User>) s;
        }
        
        if(mListUser == null) {
        	finish();
        } else {
        	mLl.removeAllViews();
        	
        	for(User u : mListUser) {
        		Button b = new Button(this);        		
        		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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
    	if(AccountAccess.getLogout()) {
    		finish();
    	}
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
			mLabelWelcome.setText(getString(R.string.welcome_msg).replace("?",mUserSelected.getName()));
			mLabelWelcome.setVisibility(View.VISIBLE);
		} else {
			mLabelPassword.setVisibility(View.GONE);
			mEditPassword.setVisibility(View.GONE);
			mSubmit.setVisibility(View.GONE);
			mLabelWelcome.setVisibility(View.GONE);
			
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
	
	public void submitUser(View view) {
		//start http thread...
		Intent i = new Intent(this,MainScreen.class);
		startActivity(i);
	}
	
	public void userSelected(String name) {		
		for(User u : mListUser) {
			if(u.getName().equals(name)) {
				mUserSelected = u;
				mNameSelected = true;
				change();
				break;
			}
		}
	}

}
