package com.easoncxz.fuzzylogger_r6;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Record extends Activity {
	
	private Spinner spRows;
	private Spinner spCols;
	// private Button btnEditRows;
	// private Button btnCommit;
	// private Button btnEditCols;
	// private Button btnCancel;
	private EditText et;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		spRows = (Spinner) findViewById(R.id.record_spRows);
		spCols = (Spinner) findViewById(R.id.record_spCols);
		// btnEditRows = (Button) findViewById(R.id.record_btnEditRows);
		// btnEditCols = (Button) findViewById(R.id.record_btnEditCols);
		// btnCommit = (Button) findViewById(R.id.record_btnCommit);
		// btnCancel = (Button) findViewById(R.id.record_btnCancel);
		et = (EditText) findViewById(R.id.record_etEntry);
	}
	
	private void fillList(){
		//TODO
	}

}
