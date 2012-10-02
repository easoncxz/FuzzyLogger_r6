package com.easoncxz.fuzzylogger_r6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class List extends ListActivity {
	public static final int MENU_ITEM_ID_DELETE = 100;
	public static final int MENU_ITEM_ID_RENAME = 200;
	public static final int MENU_ITEM_ORDER_DELETE = 100;
	public static final int MENU_ITEM_ORDER_RENAME = 200;
	public static final int MENU_GROUP_ONLY_ONE = 100;
	public static final String EXTRA_KEY_MODE = "rowORcol";
	public static final String EXTRA_KEY_OLD_NAME = "oldNameKey";
	private static final int DIALOG_ID_DELETE = 1;
	private Bundle extras;
	private ListView lv;
	private TextView tvPrompt, tvEmpty;
	private EditText et;
	private int mode;
	private AdapterView.AdapterContextMenuInfo info;
	private String caption;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		extras = getIntent().getExtras();
		mode = extras.getInt(Record.KEY_MODE);
		tvPrompt = (TextView) findViewById(R.id.list_tvPrompt);
		tvEmpty = (TextView) findViewById(android.R.id.empty);
		et = (EditText) findViewById(R.id.list_et);
		lv = getListView();
		setTextApprop(mode);
		registerForContextMenu(lv);
	}

	public void onResume() {
		super.onResume();
		fillListApprop(mode);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		caption = ((TextView) info.targetView).getText().toString();
		menu.setHeaderTitle(caption);
		menu.add(MENU_GROUP_ONLY_ONE, MENU_ITEM_ID_DELETE,
				MENU_ITEM_ORDER_DELETE, R.string.list_menu_Delete);
		menu.add(MENU_GROUP_ONLY_ONE, MENU_ITEM_ID_RENAME,
				MENU_ITEM_ORDER_RENAME, R.string.list_menu_Rename);
	}

	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_ID_DELETE:
			showDialog(DIALOG_ID_DELETE);
			break;
		case MENU_ITEM_ID_RENAME:
			Intent intent = new Intent(getApplicationContext(), One.class);
			intent.putExtra(EXTRA_KEY_MODE, mode);
			intent.putExtra(EXTRA_KEY_OLD_NAME, caption);
			startActivity(intent);
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ID_DELETE:
			AlertDialog.Builder builder = new AlertDialog.Builder(List.this);
			switch (mode) {
			case Record.MODE_ROWS:
				builder.setTitle(caption);
				builder.setMessage(R.string.list_alert_Delete_message_row);
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Home.deleteWhereGroup(caption);
								toaster("delete successful");
								fillListApprop(mode);
							}
						});
				builder.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				return builder.create();
			case Record.MODE_COLS:
				builder.setTitle(caption);
				builder.setMessage(R.string.list_alert_Delete_message_col);
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Home.deleteWhereGroup(caption);
								toaster("delete successful");
								fillListApprop(mode);
							}
						});
				builder.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				return builder.create();
			default:
				toaster("something went wrong with deleting");
				return null;
			}
		default:
			toaster("something went wrong with deleting");
			return null;
		}
	}

	public void list_btnAdd_onClick(View v) {
		if (et.getText().toString().length() > 0) {
			switch (mode) {
			case Record.MODE_ROWS:
				Home.putNewRow(et.getText().toString());
				et.setText("");
				toaster("new row added");
				fillListApprop(mode);
				break;
			case Record.MODE_COLS:
				Home.putNewCol(et.getText().toString());
				et.setText("");
				toaster("new column added");
				fillListApprop(mode);
				break;
			default:
				toaster("commit failed");
			}
		} else {
			toaster("please provide new name");
		}
	}

	private void setTextApprop(int mode) {
		// if (mode == Record.MODE_ROWS) {
		// tvPrompt.setText(R.string.list_tvPrompt_row);
		// tvEmpty.setText(R.string.list_tvEmpty_row);
		// et.setHint(R.string.list_etHint_row);
		// }
		// if (mode == Record.MODE_COLS) {
		// tvPrompt.setText(R.string.list_tvPrompt_col);
		// tvEmpty.setText(R.string.list_tvEmpty_col);
		// et.setHint(R.string.list_etHint_col);
		// }

		switch (mode) {
		case Record.MODE_ROWS:
			tvPrompt.setText(R.string.list_tvPrompt_row);
			tvEmpty.setText(R.string.list_tvEmpty_row);
			et.setHint(R.string.list_etHint_row);
			break;
		case Record.MODE_COLS:
			tvPrompt.setText(R.string.list_tvPrompt_col);
			tvEmpty.setText(R.string.list_tvEmpty_col);
			et.setHint(R.string.list_etHint_col);
			break;
		default:
			toaster("something went wrong");
		}
	}

	private void fillListApprop(int mode) {
		Cursor cc;
		SimpleCursorAdapter sca;
		String[] from;
		int[] to;
		// if (mode == Record.MODE_ROWS) {
		// cc = Home.getRowList_v2();
		// from = new String[] { DBH.KEY_ROW };
		// to = new int[] { android.R.id.text1 };
		// sca = new SimpleCursorAdapter(getApplicationContext(),
		// R.layout.simple_list_item_1, cc, from, to);
		// lv.setAdapter(sca);
		// }
		// if (mode == Record.MODE_COLS) {
		// cc = Home.getColList_v2();
		// from = new String[] { DBH.KEY_COL };
		// to = new int[] { android.R.id.text1 };
		// sca = new SimpleCursorAdapter(getApplicationContext(),
		// R.layout.simple_list_item_1, cc, from, to);
		// lv.setAdapter(sca);
		// }

		switch (mode) {
		case Record.MODE_ROWS:
			cc = Home.getRowList_v2();
			from = new String[] { DBH.KEY_ROW };
			to = new int[] { android.R.id.text1 };
			sca = new SimpleCursorAdapter(getApplicationContext(),
					R.layout.simple_list_item_1, cc, from, to);
			lv.setAdapter(sca);
			break;
		case Record.MODE_COLS:
			cc = Home.getColList_v2();
			from = new String[] { DBH.KEY_COL };
			to = new int[] { android.R.id.text1 };
			sca = new SimpleCursorAdapter(getApplicationContext(),
					R.layout.simple_list_item_1, cc, from, to);
			lv.setAdapter(sca);
			break;
		default:
			toaster("something went wrong when filling in the list");
		}
	}

	private void toaster(String toToast) {
		Toast.makeText(getApplicationContext(), toToast, Toast.LENGTH_SHORT)
				.show();
	}

}