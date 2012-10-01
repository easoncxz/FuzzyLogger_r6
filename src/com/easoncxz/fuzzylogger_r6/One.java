package com.easoncxz.fuzzylogger_r6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class One extends Activity {
	private int mode;
	private String oldName;
	private TextView tvPrompt, tvOldName;
	private EditText et;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one);
		mode = getIntent().getExtras().getInt(List.EXTRA_KEY_MODE);
		oldName = getIntent().getExtras().getString(List.EXTRA_KEY_OLD_NAME);
		tvPrompt = (TextView) findViewById(R.id.one_tvPrompt);
		tvOldName = (TextView) findViewById(R.id.one_tvOldName);
		et = (EditText) findViewById(R.id.one_et);
		switch (mode) {
		case Record.MODE_ROWS:
			tvPrompt.setText(R.string.one_tvPrompt_row);
			et.setHint(R.string.one_etHint_row);
			break;
		case Record.MODE_COLS:
			tvPrompt.setText(R.string.one_tvPrompt_col);
			et.setHint(R.string.one_etHint_col);
			break;
		default:
			toaster("something went wrong with the text");
		}
		tvOldName.setText(oldName);
	}

	public void one_btn_onClick(View v) {
		if (et.getText().toString().length() > 0) {
			switch (mode) {
			case Record.MODE_ROWS:
				Home.updateRow(oldName, et.getText().toString());
				toaster("rename successfull");
				finish();
				break;
			case Record.MODE_COLS:
				Home.updateCol(oldName, et.getText().toString());
				toaster("rename successfull");
				finish();
				break;
			default:
				toaster("something went wrong with renaming");
			}
		} else {
			toaster("please give new name");
		}
	}

	private void toaster(String toToast) {
		Toast.makeText(getApplicationContext(), toToast, Toast.LENGTH_SHORT)
				.show();
	}
}
