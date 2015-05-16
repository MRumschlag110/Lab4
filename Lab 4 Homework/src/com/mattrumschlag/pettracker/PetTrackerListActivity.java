package com.mattrumschlag.pettracker;

import com.mattrumschlag.pettracker.PetTrackerDatabase.PetType;
import com.mattrumschlag.pettracker.PetTrackerDatabase.Pets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PetTrackerListActivity extends PetTrackerActivity {
	
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpets);
		
		fillPetList();
		
		final Button gotoEntry = (Button) findViewById(R.id.ButtonEnterMorePets);
        gotoEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // We're done here. Finish and return to the entry screen
                finish();
            }
        });
	}
	public void fillPetList() {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(Pets.PETS_TABLE_NAME + ", " + PetType.PETTYPE_TABLE_NAME);
		queryBuilder.appendWhere(Pets.PETS_TABLE_NAME + "." + Pets.PET_TYPE_ID + "=" + PetType.PETTYPE_TABLE_NAME + "." + PetType._ID);
		
		String asColumnsToReturn[] = {
				Pets.PETS_TABLE_NAME + "." + Pets.PET_NAME,
				Pets.PETS_TABLE_NAME + "." + Pets._ID,
				PetType.PETTYPE_TABLE_NAME + "." + PetType.PET_TYPE_NAME };
		
		mCursor = queryBuilder.query(mDB, asColumnsToReturn, null, null, null, null, Pets.DEFAULT_SORT_ORDER);
		
		startManagingCursor(mCursor);
		ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.pet_item, mCursor, new String[] {Pets.PET_NAME, PetType.PET_TYPE_NAME }, new int[] { R.id.TextView_PetName, R.id.TextView_PetType});
		
		ListView av = (ListView)findViewById(R.id.petList);
		av.setAdapter(adapter);
		
		av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				final long deletePetId = id;
				RelativeLayout item = (RelativeLayout)view;
				TextView nameView = (TextView)item.findViewById(R.id.TextView_PetName);
				String name = nameView.getText().toString();
				new AlertDialog.Builder(PetTrackerListActivity.this).setMessage("Delete Pet Record for " + name + "?")
				.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						deletePet(deletePetId);
						mCursor.requery();
					}
				}).show();
			}
		});
	}
	public void deletePet(Long id){
		String astrArgs[] = {id.toString() };
		mDB.delete(Pets.PETS_TABLE_NAME, Pets._ID + "=?", astrArgs);
	}
	
}
