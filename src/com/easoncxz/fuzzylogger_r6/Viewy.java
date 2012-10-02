package com.easoncxz.fuzzylogger_r6;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Viewy extends ListActivity {
	private Spinner sp;
	private ListView lv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewy);
		Cursor cc = Home.getGroupsList_alpha();
		SimpleCursorAdapter sca = new SimpleCursorAdapter(
				getApplicationContext(), R.layout.simple_spinner_item, cc,
				new String[] { DBH.KEY_GEN_GROUP },
				new int[] { android.R.id.text1 });
		sca.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		sp = (Spinner) findViewById(R.id.view_sp);
		sp.setAdapter(sca);
		lv = getListView();
	}

	public void onResume() {
		super.onResume();
		fillListFull();
	}

	public void view_btnFilter_onClick(View v) {
		String cri = ((TextView) sp.getSelectedView()).getText().toString();
		Cursor cc = Home.getEntriesWeirdlyWhere(cri);
		SimpleCursorAdapter sca = new SimpleCursorAdapter(
				getApplicationContext(),
				R.layout.simple_list_item_2,
				cc,
				new String[] { DBH.KEY_ENTRY, DBH.KEY_ROW + "+" + DBH.KEY_COL },
				new int[] { android.R.id.text1, android.R.id.text2 });
		ListView lv = getListView();
		lv.setAdapter(sca);
	}

	public void view_btnReset_onClick(View v) {
		if (sp.getCount() > 0) {
			sp.setSelection(0);
		}
		fillListFull();
	}

	private void fillListFull() {
		Cursor cc = Home.getEntriesWeirdly();
		SimpleCursorAdapter sca = new SimpleCursorAdapter(
				getApplicationContext(),
				R.layout.simple_list_item_2,
				cc,
				new String[] { DBH.KEY_ENTRY, DBH.KEY_ROW + "+" + DBH.KEY_COL },
				new int[] { android.R.id.text1, android.R.id.text2 });
		lv.setAdapter(sca);
	}

}
