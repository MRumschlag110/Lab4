package com.mattrumschlag.pettracker;

import com.mattrumschlag.pettracker.PetTrackerDatabase.PetType;
import com.mattrumschlag.pettracker.PetTrackerDatabase.Pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class PetTrackerEntryActivity extends PetTrackerActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.petentry);
		
		fillAutoCompleteFromDatabase();
	
	final Button savePet = (Button)findViewById(R.id.ButtonSave);
	savePet.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			final EditText petName = (EditText)findViewById(R.id.EditTextName);
			final EditText petType = (EditText)findViewById(R.id.EditTextSpecies);
			mDB.beginTransaction();
			try {
				long rowId = 0;
				String strPetType = petType.getText().toString().toLowerCase();
				SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
				queryBuilder.setTables(PetType.PETTYPE_TABLE_NAME);
				queryBuilder.appendWhere(PetType.PET_TYPE_NAME + "='" 
				+ strPetType + "'");

				Cursor c = queryBuilder.query(mDB, null, null, null, null, null, null);
				
				
				
				
				
				if (c.getCount() == 0) {
					ContentValues typeRecordToAdd = new ContentValues();
					typeRecordToAdd.put(PetType.PET_TYPE_NAME, strPetType);
					rowId = mDB.insert(PetType.PETTYPE_TABLE_NAME, PetType.PET_TYPE_NAME, typeRecordToAdd);
					fillAutoCompleteFromDatabase();
				} else {
					c.moveToFirst();
					rowId = c.getLong(c.getColumnIndex(PetType._ID));
				}
				c.close();
				
				ContentValues petRecordToAdd = new ContentValues();
				petRecordToAdd.put(Pets.PET_NAME, petName.getText().toString());
				petRecordToAdd.put(Pets.PET_TYPE_ID, rowId);
	mDB.insert(Pets.PETS_TABLE_NAME, Pets.PET_NAME, petRecordToAdd);
	mDB.setTransactionSuccessful();
			} finally{
				mDB.endTransaction();
			}
			petName.setText(null);
			petType.setText(null);
		}
	});
	final Button gotoList = (Button)findViewById(R.id.ButtonShowPets);
	gotoList.setOnClickListener(new View.OnClickListener()
	{
		public void onClick(View v) {
			Intent intent = new Intent(PetTrackerEntryActivity.this, PetTrackerListActivity.class);
			startActivity(intent);
		}
	});
}
	void fillAutoCompleteFromDatabase()
	{
		mCursor = mDB.query(PetType.PETTYPE_TABLE_NAME, new String[] {PetType.PET_TYPE_NAME, PetType._ID}, null, null,
				null, null, PetType.DEFAULT_SORT_ORDER);
		
		startManagingCursor(mCursor);
		
		int iNumberOfSpeciesTypes = mCursor.getCount();
		String astrAutoTextOptions[] = new String[iNumberOfSpeciesTypes];
		if ((iNumberOfSpeciesTypes > 0) && (mCursor.moveToFirst()))
		{
			for(int i = 0; i < iNumberOfSpeciesTypes; i++)
			{
				astrAutoTextOptions[i] = mCursor.getString(mCursor.getColumnIndex(PetType.PET_TYPE_NAME));
				mCursor.moveToNext();
			}
			ArrayAdapter<String> adapter =
			        new ArrayAdapter<String>(
			            this,
			            android.R.layout.simple_dropdown_item_1line,
			            astrAutoTextOptions);

				AutoCompleteTextView text = (AutoCompleteTextView) findViewById(R.id.EditTextSpecies);
				text.setAdapter(adapter);
		}
	}
}
