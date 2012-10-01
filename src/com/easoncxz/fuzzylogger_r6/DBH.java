package com.easoncxz.fuzzylogger_r6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBH extends SQLiteOpenHelper {

	public static final String DATABASE_NAME="fzldb";
	public static final int DATABASE_VERSION=1;
	public static final String TABLE_ONLY_ONE="myBigTable";
	public static final String KEY_ID="_id";
	public static final String KEY_ROW="myRowKey";
	public static final String KEY_COL="myColKey";
	public static final String KEY_ENTRY="myEntryKey";

	public DBH(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlCreate="CREATE TABLE " + TABLE_ONLY_ONE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ROW + " TEXT,"
                + KEY_COL + " TEXT," + KEY_ENTRY+" TEXT)";
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_ONLY_ONE);
		onCreate(db);
	}


}
