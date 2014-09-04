package com.example.poeamparsing;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHistory extends SQLiteOpenHelper {

	private Context context;
	public static final String DB_NAME = "poem_history";
	public static final int DB_VERSION = 1;
	public static final String POEM_HISTORY_TABLE = "allhistory";
	public static final String ID_FIELD = "_id";
	public static final String TITLE_FIELD = "title";
	public static final String DETAILS_FIELD = "details";

	public static final String POEM_HISTORY_TABLE_SQL = " CREATE TABLE "
			+ POEM_HISTORY_TABLE + " ( " + ID_FIELD + " INTEGER PRIMARY KEY, " + TITLE_FIELD
			+ " TEXT, " + DETAILS_FIELD + " TEXT)";

	public DatabaseHistory(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		this.context = context;
        
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(POEM_HISTORY_TABLE_SQL);
		Log.e("--->Table Create----->", POEM_HISTORY_TABLE_SQL);
     
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public long insertPoemHistory(History allhistory){
		
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(TITLE_FIELD, allhistory.getTitle());
		values.put(DETAILS_FIELD, allhistory.getDetails());
		
		long inserted=db.insert(POEM_HISTORY_TABLE, null, values);
		
		db.close();
		return inserted;
	}
	
	public ArrayList<History> getAllPoemHistory(){
		
		ArrayList<History> allPoemHistory=new ArrayList<History>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(POEM_HISTORY_TABLE, null, null, null, null, null, null);
		if(cursor!=null && cursor.getCount()>0){
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id=cursor.getInt(cursor.getColumnIndex(ID_FIELD));
				String title=cursor.getString(cursor.getColumnIndex(TITLE_FIELD));
				String details=cursor.getString(cursor.getColumnIndex(DETAILS_FIELD));
				History allhistory=new History(title, details);
				allPoemHistory.add(allhistory);
                cursor.moveToNext();				
			}
		}
		cursor.close();
		db.close();
		return allPoemHistory;
	}

}
