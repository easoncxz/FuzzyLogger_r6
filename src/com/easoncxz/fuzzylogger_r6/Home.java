package com.easoncxz.fuzzylogger_r6;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Home extends ListActivity {
	static DBH dbh;
	static SQLiteDatabase db;

	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		lv = getListView();
		dbh = new DBH(getApplicationContext());
		db = dbh.getWritableDatabase();
		testInsert();
	}

	@Override
	public void onResume() {
		super.onResume();
		fillList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void home_btnRecord_onClick(View v) {
		Intent intent = new Intent(getApplicationContext(), Record.class);
		startActivity(intent);
	}

	public void home_btnView_onClick(View v) {
		Intent intent = new Intent(getApplicationContext(), Viewy.class);
		startActivity(intent);
	}

	private void fillList() {
		Cursor cc = getEntriesRecent20_v1();
		SimpleCursorAdapter sca = new SimpleCursorAdapter(
				getApplicationContext(),
				R.layout.simple_list_item_2,
				cc,
				new String[] { DBH.KEY_ROW + "+" + DBH.KEY_COL, DBH.KEY_ENTRY },
				new int[] { android.R.id.text1, android.R.id.text2 });
		// my '+' tweak here -----------------^
		lv.setAdapter(sca);
	}

	static Cursor getRowList() {
		return db.query(true, DBH.TABLE_ONLY_ONE, new String[] { DBH.KEY_ID,
				DBH.KEY_ROW }, null, null, null, null, null, null);
	}

	static Cursor getRowList_v2() {
		return db.rawQuery("SELECT " + DBH.KEY_ID + "," + DBH.KEY_ROW
				+ " FROM " + DBH.TABLE_ONLY_ONE + " WHERE " + DBH.KEY_ROW
				+ " NOT NULL" + " GROUP BY " + DBH.KEY_ROW, null);
		// select Id,BrandName from brand where brandName not null group by
		// BrandName
	}

	static Cursor getColList() {
		return db.query(true, DBH.TABLE_ONLY_ONE, new String[] { DBH.KEY_ID,
				DBH.KEY_COL }, null, null, null, null, null, null);
	}

	static Cursor getColList_v2() {
		return db.rawQuery("SELECT " + DBH.KEY_ID + "," + DBH.KEY_COL
				+ " FROM " + DBH.TABLE_ONLY_ONE + " WHERE " + DBH.KEY_COL
				+ " NOT NULL GROUP BY " + DBH.KEY_COL, null);
	}

	static Cursor getGroupsList_alpha() {
		return db.rawQuery("SELECT * FROM (SELECT " + DBH.KEY_ID + ","
				+ DBH.KEY_ROW + " AS " + DBH.KEY_GEN_GROUP + " FROM "
				+ DBH.TABLE_ONLY_ONE + " WHERE " + DBH.KEY_ENTRY
				+ " NOT NULL UNION SELECT " + DBH.KEY_ID + "," + DBH.KEY_COL
				+ " AS " + DBH.KEY_GEN_GROUP + " FROM " + DBH.TABLE_ONLY_ONE
				+ " WHERE " + DBH.KEY_ENTRY + " NOT NULL) GROUP BY "
				+ DBH.KEY_GEN_GROUP, null);
	}

	static Cursor getEntries() {
		return db.query(true, DBH.TABLE_ONLY_ONE, new String[] { DBH.KEY_ID,
				DBH.KEY_ROW, DBH.KEY_COL, DBH.KEY_ENTRY }, null, null, null,
				null, null, null);
	}

	static Cursor getEntriesWeirdly() {
		return db.rawQuery("SELECT " + DBH.KEY_ID + "," + DBH.KEY_ROW
				+ "||', '||" + DBH.KEY_COL + " AS '" + DBH.KEY_ROW + "+"
				+ DBH.KEY_COL + "'," + DBH.KEY_ENTRY + " FROM "
				+ DBH.TABLE_ONLY_ONE + " WHERE " + DBH.KEY_ENTRY
				+ " NOT NULL ORDER BY " + DBH.KEY_ID + " , " + DBH.KEY_ROW
				+ " , " + DBH.KEY_COL + " , " + DBH.KEY_ENTRY, null);
		// TODO to test
	}

	static Cursor getEntriesWeirdlyWhere(String groupName) {
		return db.rawQuery("SELECT " + DBH.KEY_ID + "," + DBH.KEY_ROW
				+ "||', '||" + DBH.KEY_COL + " AS '" + DBH.KEY_ROW + "+"
				+ DBH.KEY_COL + "'," + DBH.KEY_ENTRY + " FROM "
				+ DBH.TABLE_ONLY_ONE + " WHERE (" + DBH.KEY_ROW + "='"
				+ groupName + "' OR " + DBH.KEY_COL + "='" + groupName
				+ "') AND " + DBH.KEY_ENTRY + " NOT NULL ORDER BY "
				+ DBH.KEY_ROW + "+" + DBH.KEY_COL, null);
		// TODO to test
	}

	static Cursor getEntriesRecent20_v1() {
		return db.rawQuery("SELECT " + DBH.KEY_ID + "," + DBH.KEY_ROW
				+ "||', '||" + DBH.KEY_COL + " AS '" + DBH.KEY_ROW + "+"
				+ DBH.KEY_COL + "'," + DBH.KEY_ENTRY + " FROM "
				+ DBH.TABLE_ONLY_ONE + " WHERE " + DBH.KEY_ENTRY
				+ " NOT NULL ORDER BY " + DBH.KEY_ID + " DESC LIMIT 20", null);
	}

	static void testInsert() {
		Cursor cc = db.query(false, "sqlite_sequence", new String[] { "name",
				"seq" }, "name=?", new String[] { DBH.TABLE_ONLY_ONE }, null,
				null, null, null);
		if (cc.moveToFirst()) {
		}
		int seq = cc.getInt(cc.getColumnIndex("seq"));
		if (seq < 1) {
			ContentValues cv = new ContentValues();
			cv.put(DBH.KEY_ROW, "default row");
			cv.put(DBH.KEY_COL, "default col");
			db.insert(DBH.TABLE_ONLY_ONE, null, cv);
		}
	}

	static void putNewRow(String newRow) {
		ContentValues cv = new ContentValues();
		cv.put(DBH.KEY_ROW, newRow);
		cv.putNull(DBH.KEY_COL);
		cv.putNull(DBH.KEY_ENTRY);
		db.insert(DBH.TABLE_ONLY_ONE, null, cv);
	}

	static void putNewCol(String newCol) {
		ContentValues cv = new ContentValues();
		cv.putNull(DBH.KEY_ROW);
		cv.put(DBH.KEY_COL, newCol);
		cv.putNull(DBH.KEY_ENTRY);
		db.insert(DBH.TABLE_ONLY_ONE, null, cv);
	}

	static void putEntry(String wRow, String wCol, String entry) {
		ContentValues cv = new ContentValues();
		cv.put(DBH.KEY_ROW, wRow);
		cv.put(DBH.KEY_COL, wCol);
		cv.put(DBH.KEY_ENTRY, entry);
		db.insert(DBH.TABLE_ONLY_ONE, null, cv);
	}

	static void updateRow(String oldRow, String newRow) {
		ContentValues cv = new ContentValues();
		cv.put(DBH.KEY_ROW, newRow);
		db.update(DBH.TABLE_ONLY_ONE, cv, DBH.KEY_ROW + "=?",
				new String[] { oldRow });
	}

	static void updateCol(String oldCol, String newCol) {
		ContentValues cv = new ContentValues();
		cv.put(DBH.KEY_COL, newCol);
		db.update(DBH.TABLE_ONLY_ONE, cv, DBH.KEY_COL + "=?",
				new String[] { oldCol });
	}

	static void deleteWhereGroup(String groupToDel) {
		db.delete(DBH.TABLE_ONLY_ONE, DBH.KEY_ROW + "=? OR " + DBH.KEY_COL
				+ "=?", new String[] { groupToDel, groupToDel });
	}
}
