package com.mattrumschlag.pettracker;

import android.os.Bundle;

public class PetTrackerListActivity extends PetTrackerActivity {
	
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpets);
	}
}
