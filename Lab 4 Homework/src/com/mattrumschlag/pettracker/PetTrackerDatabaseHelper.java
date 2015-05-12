package com.mattrumschlag.pettracker;

import com.mattrumschlag.pettracker.PetTrackerDatabase.PetType;
import com.mattrumschlag.pettracker.PetTrackerDatabase.Pets;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetTrackerDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "pet_tracker.db";
	private static final int DATABASE_VERSION = 1;
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + PetType.PETTYPE_TABLE_NAME + " ("
				+ PetType._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ PetType.PET_TYPE_NAME + " TEXT" + ");");
		db.execSQL("CREATE TABLE " + Pets.PETS_TABLE_NAME + "("
				+ Pets._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ Pets.PET_NAME + " TEXT,"
				+ Pets.PET_TYPE_ID + " INTEGER" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	PetTrackerDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onOpen(SQLiteDatabase db){
		super.onOpen(db);
	}
}
