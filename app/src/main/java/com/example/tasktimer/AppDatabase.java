package com.example.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

class AppDatabase extends SQLiteOpenHelper {
	private static final String TAG = "AppDatabase";

	public static final String DATABASE_NAME = "TaskTimer.db";
	public static final int DATABASE_VERSION = 1;

	private static AppDatabase instance;


	public AppDatabase(@Nullable Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(TAG, "AppDatabase: Constructor called");
	}

	static AppDatabase getInstance(Context context){
		if(instance == null) instance = new AppDatabase(context);
		Log.d(TAG, "getInstance: Starts");
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate: Starts");
		String sSQL;
		sSQL = "CREATE TABLE " + TasksContract.TABLE_NAME + "("
				+ TasksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
				+ TasksContract.Columns.TASKS_NAME + " TEXT NOT NULL, "
				+ TasksContract.Columns.TASKS_DESCRIPTION + " TEXT, "
				+ TasksContract.Columns.TASKS_SORTORDER + " INTEGER );";
		Log.d(TAG, "onCreate: " + sSQL);
		db.execSQL(sSQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade: starts");
		switch(oldVersion) {
			case 1:
				// upgrade logic from version 1
				break;
			default:
				throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
		}
		Log.d(TAG, "onUpgrade: ends");

	}
}
