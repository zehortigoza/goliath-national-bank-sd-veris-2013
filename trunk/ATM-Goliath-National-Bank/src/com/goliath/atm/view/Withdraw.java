package com.goliath.atm.view;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.goliath.atm.R;
import com.goliath.atm.http.RequestDataAsync;
import com.goliath.atm.http.json.parser.CanWithdrawParser;
import com.goliath.atm.model.Account;
import com.goliath.atm.model.User;

public class Withdraw extends BaseActivity {
	private static int Amount100 = 10;
	private static int Amount50 = 10;
	private static int Amount20 = 10;
	private static int Amount10 = 10;
	private static int Amount5 = 10;
	private static int Amount2 = 10;

	private EditText mValueEdit;

	private Account mAccount;
	private User mUser;
	
	private boolean mCanWithdraw = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.withdraw);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mAccount = (Account) extras
					.getSerializable(AccountUsers.ACCOUNT_TAG);
			mUser = (User) extras.getSerializable(AccountUsers.USER_TAG);
		}

		mValueEdit = (EditText) findViewById(R.id.edit_value_withdraw);
	}

	public void back(View view) {
		finish();
	}

	private static final String KEY_ACCOUNT_TAG = "key_conta";
	private static final String KEY_VALUE_TAG = "value";
	private static final String CAN_WITHDRAW_URL = "can_draw/";

	public void doWithdraw(View view) {
		float value = Float.parseFloat(mValueEdit.getEditableText().toString());
		String msg = new String();
		if (check(value, msg, false)) {
			try {
				JSONObject j = new JSONObject();
				j.put(KEY_ACCOUNT_TAG, mAccount.getKey());
				j.put(KEY_VALUE_TAG, value);
				
				mCanWithdraw = false;

				RequestDataAsync request = new RequestDataAsync(sBaseUrl
						+ CAN_WITHDRAW_URL, j, new CanWithdrawParser(), this);
				request.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.error));
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
	
	@Override
	public void onReceivedData(Object data) {
		if(data instanceof Boolean) {
			mCanWithdraw = (Boolean) data;
		}
	}
	
	@Override
	public void onRequestFinished(boolean status) {
		super.onRequestFinished(status);
		if(status) {
			if(mCanWithdraw) {
				float value = Float.parseFloat(mValueEdit.getEditableText().toString());
				String msg = new String();				
				check(value,msg, true);
				
				mValueEdit.setText("0");
				mCanWithdraw = false;
				
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle(getString(R.string.withdraw_success));
				alertDialog.setMessage(msg);
				alertDialog.setCancelable(false);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Withdraw.this.finish();
						return;
					}
				});
				alertDialog.show();				
			}
		} else {
			genericHttpError();
		}
	}

	private boolean check(float value, String notes, boolean updateNotes) {
		int resultDivision = 0;
		float rest = value;

		int widraw100 = 0;
		int widraw50 = 0;
		int widraw20 = 0;
		int widraw10 = 0;
		int widraw5 = 0;
		int widraw2 = 0;

		resultDivision = (int) (rest / 100);
		if (resultDivision > 0) {
			if (Amount100 >= resultDivision) {
				widraw100 = resultDivision;
				rest = value - (resultDivision * 100);
			} else {
				widraw100 = Amount100;
				rest = rest - (100 * Amount100);
			}
		}

		resultDivision = (int) (rest / 50);
		if (resultDivision > 0) {
			if (Amount50 >= resultDivision) {
				widraw50 = resultDivision;
				rest = rest - (resultDivision * 50);
			} else {
				widraw50 = Amount50;
				rest = rest - (50 * Amount50);
			}
		}

		resultDivision = (int) (rest / 20);
		if (resultDivision > 0) {
			if (Amount20 >= resultDivision) {
				widraw20 = resultDivision;
				rest = rest - (resultDivision * 20);
			} else {
				widraw20 = Amount20;
				rest = rest - (20 * Amount20);
			}
		}

		resultDivision = (int) (rest / 10);
		if (resultDivision > 0) {
			if (Amount10 >= resultDivision) {
				widraw10 = resultDivision;
				rest = rest - (resultDivision * 10);
			} else {
				widraw10 = Amount10;
				rest = rest - (10 * Amount10);
			}
		}

		resultDivision = (int) (rest / 5);
		if (resultDivision > 0) {
			if (Amount5 >= resultDivision) {
				widraw5 = resultDivision;
				rest = rest - (resultDivision * 5);
			} else {
				widraw5 = Amount5;
				rest = rest - (5 * Amount5);
			}
		}

		resultDivision = (int) (rest / 2);
		if (resultDivision > 0) {
			if (Amount2 >= resultDivision) {
				widraw2 = resultDivision;
				rest = rest - (resultDivision * 2);
			} else {
				widraw2 = Amount2;
				rest = rest - (2 * Amount2);
			}
		}

		if (rest > 0) {
			if (rest >= 2) {
				notes.concat("Não existem notas sulficientes para realizar esse saque");
				return false;
			} else {
				notes.concat("Valor inválido para saque");
				return false;
			}
		} else {
			if (updateNotes) {
				Amount100 -= widraw100;
				Amount50 -= widraw50;
				Amount20 -= widraw20;
				Amount10 -= widraw10;
				Amount5 -= widraw5;
				Amount2 -= widraw2;

				notes = notes.concat("Notas no dispensador:\n");

				if (widraw100 > 0) {
					notes = notes.concat(widraw100 + " notas de 100\n");
				}

				if (widraw50 > 0) {
					notes = notes.concat(widraw50 + " notas de 50\n");
				}

				if (widraw20 > 0) {
					notes = notes.concat(widraw20 + " notas de 20\n");
				}

				if (widraw10 > 0) {
					notes = notes.concat(widraw10 + " notas de 10\n");
				}

				if (widraw5 > 0) {
					notes = notes.concat(widraw5 + " notas de 5\n");
				}

				if (widraw2 > 0) {
					notes = notes.concat(widraw2 + " notas de 2\n");
				}
			}
			return true;
		}
	}

}
