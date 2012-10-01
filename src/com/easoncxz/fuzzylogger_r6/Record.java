package com.easoncxz.fuzzylogger_r6;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Record extends Activity {
	public static final String KEY_MODE = "whatMode";
	public static final int MODE_ROWS = 100;
	public static final int MODE_COLS = 200;

	private Spinner spRows;
	private Spinner spCols;
	private EditText et;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		spRows = (Spinner) findViewById(R.id.record_spRows);
		spCols = (Spinner) findViewById(R.id.record_spCols);
		et = (EditText) findViewById(R.id.record_etEntry);
	}

	public void onResume() {
		super.onResume();
		fillSp();
	}

	public void record_btnEditRows_onClick(View v) {
		Intent intent = new Intent(getApplicationContext(), List.class);
		intent.putExtra(KEY_MODE, MODE_ROWS);
		startActivity(intent);
	}

	public void record_btnEditCols_onClick(View v) {
		Intent intent = new Intent(getApplicationContext(), List.class);
		intent.putExtra(KEY_MODE, MODE_COLS);
		startActivity(intent);
	}

	public void record_btnCommit_onClick(View v) {
		if (spRows.getCount() >= 1 && spCols.getCount() >= 1) {
			String row = ((TextView) spRows.getSelectedView()).getText()
					.toString();
			String col = ((TextView) spCols.getSelectedView()).getText()
					.toString();
			String entry = et.getText().toString();
			if (row != null && col != null && entry.length() > 0) {
				Home.putEntry(row, col, entry);
				et.setText("");
				toaster("entry committed");
			} else {
				toaster("more criteria needed");
			}
		} else {
			toaster("more criteria needed");
		}
	}

	public void record_btnCancel_onClick(View v) {
		// toaster("nothing changed");
		finish();
	}

	private void fillSp() {
		Cursor rows = Home.getRowList_v2();
		String[] rowsFrom = new String[] { DBH.KEY_ROW };
		int[] rowsTo = new int[] { android.R.id.text1 };
		SimpleCursorAdapter scaRows = new SimpleCursorAdapter(
				getApplicationContext(), R.layout.simple_spinner_item, rows,
				rowsFrom, rowsTo);
		scaRows.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spRows.setAdapter(scaRows);
		Cursor cols = Home.getColList_v2();
		String[] colsFrom = new String[] { DBH.KEY_COL };
		int[] colsTo = new int[] { android.R.id.text1 };
		SimpleCursorAdapter scaCols = new SimpleCursorAdapter(
				getApplicationContext(), R.layout.simple_spinner_item, cols,
				colsFrom, colsTo);
		scaCols.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spCols.setAdapter(scaCols);
	}

	private void toaster(String toToast) {
		Toast.makeText(getApplicationContext(), toToast, Toast.LENGTH_SHORT)
				.show();
	}
}
