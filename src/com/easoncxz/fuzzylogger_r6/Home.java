package com.easoncxz.fuzzylogger_r6;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Home extends ListActivity {
	
	static DBH dbh;
	static SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		dbh = new DBH(getApplicationContext());
		db = dbh.getWritableDatabase();
		testInsert();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	// @Override
	// protected void onActivityResult(int reqCode,int reslCode,Intent data){
	// }

	public void home_btnRecord_onClick(View v) {
		Intent intent=new Intent(getApplicationContext(),Record.class);
		startActivity(intent);
	}

	public void home_btnView_onClick(View v) {
		Intent intent=new Intent(getApplicationContext(),Viewy.class);
		startActivity(intent);
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
	static Cursor getEntries() {
		return db.query(true, DBH.TABLE_ONLY_ONE, new String[] { DBH.KEY_ID,
				DBH.KEY_ROW, DBH.KEY_COL, DBH.KEY_ENTRY }, null, null, null,
				null, null, null);
	}
	static Cursor getEntriesRecent20_underDevelopment() {
		// TODO develop
		return null;
	}
	static void testInsert() {
		ContentValues cv = new ContentValues();
		cv.put(DBH.KEY_ROW, "default test text");
		db.insert(DBH.TABLE_ONLY_ONE, null, cv);
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
		// TODO to be tested.
	}
}
