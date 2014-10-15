package com.example.accphysicaltracker;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "results.db";
	public static final String TABLE_NAME = "results";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_SCORE = "score";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		Log.d("CREATE++", "DATABASE1: results");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE results"
				+ "(id INTEGER PRIMARY KEY, score text)");
		Log.d("CREATE++", "DATABASE: " + db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS results");
		onCreate(db);
	}

	public void insertScore(String queryValues) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("score", queryValues);

		db.insert(TABLE_NAME, null, contentValues);
		db.close();
	}

	public Cursor getData(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery(
				"select * from results where id=" + id + "", null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
		return numRows;
	}

	public boolean updateContact(Integer id, String score) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_SCORE, score);

		db.update("results", contentValues, "id = ? ",
				new String[] { Integer.toString(id) });
		return true;
	}

	public List<String> getAllContacts() {
		List<String> contactList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
		Log.d("CREATE++", "DATABASE: " + selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				String contact1;
				contact1 = cursor.getString(1);

				// Adding contact to list
				contactList.add(contact1);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}
}
