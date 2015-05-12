package com.mattrumschlag.pettracker;

import android.provider.BaseColumns;

public final class PetTrackerDatabase {
	
	private PetTrackerDatabase(){
		
	}
	
	public static final class Pets implements BaseColumns {
		public static final String PETS_TABLE_NAME = "table_pets";
		public static final String PET_NAME = "pet_name";
		public static final String PET_TYPE_ID = "pet_type_id";
		public static final String DEFAULT_SORT_ORDER = "pet_name ASC";
		
		private Pets() {
			
		}
		
		
	}
	
	public static final class PetType implements BaseColumns {
		public static final String PETTYPE_TABLE_NAME = "table_pettypes";
		public static final String PET_TYPE_NAME = "pet_type";
		public static final String DEFAULT_SORT_ORDER = "pet_type ASC";
		
		private PetType(){
			
		}
	}
}
