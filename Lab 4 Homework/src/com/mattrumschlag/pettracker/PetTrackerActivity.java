package com.mattrumschlag.pettracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class PetTrackerActivity extends ActionBarActivity {
	protected PetTrackerDatabaseHelper mDatabase = null;
	protected SQLiteDatabase mDB = null;
	protected Cursor mCursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDatabase = new PetTrackerDatabaseHelper(this.getApplicationContext());
		mDB = mDatabase.getWritableDatabase();
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		if(mDB != null) {
			mDB.close();
		}
		if(mDatabase != null){
			mDatabase.close();
		}
	}
}
