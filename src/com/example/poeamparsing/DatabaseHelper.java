package com.example.poeamparsing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private SQLiteDatabase database;
	public static String DB_PATH;
	private Context context;
	public static final String DB_NAME="poem_sqldb";
	public static final int DB_VERSION=1;
	public static final String POEM_TABLE="poem";
	public static final String ID_FIELD="_id";
	public static final String NAME_FIELD="name";
	public static final String TITLE_FIELD="title";
	public static final String DETAILS_FIELD="details";
	
	public static final String POEM_TABLE_SQL=" CREATE TABLE "+POEM_TABLE+" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "+
	NAME_FIELD+" TEXT, "+TITLE_FIELD+" TEXT, "+DETAILS_FIELD+" TEXT)";
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		this.context=context;
		String PKG_NAME=context.getPackageName();
		DB_PATH="/data/data/"+PKG_NAME+"/databases/";		
		this.database=openDatabase();		
		
	}
 
	
	public SQLiteDatabase openDatabase(){
		
		String path=DB_PATH+DB_NAME;
		if(database==null){
			createDatabase();
		    database=SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		}
		return database;
	}
	
	public SQLiteDatabase getDatabase(){
		return this.database;
	}
	
	private void createDatabase(){
		boolean dbExits=checkDB();
		if(!dbExits){
			this.getReadableDatabase();
			Log.e(getClass().getName(), "Database does'nt exits copy database from assets");
			copyDatabase();
			
		}else{
			Log.e(getClass().getName(), "Database already exits");
		}	
	}
	
	private void copyDatabase(){
		//copy database file from assets to data/data/pkg/databases
		try {
			InputStream dbInputStream=context.getAssets().open(DB_NAME);
		    String path=DB_PATH+DB_NAME;
			OutputStream dbOutputStream=new FileOutputStream(path);
			byte[] buffer=new byte[4096];
		    int readCount=0;
			while((readCount=dbInputStream.read(buffer))>0){
				dbOutputStream.write(buffer, 0, readCount);
	
			}
			
		  dbInputStream.close();
		  dbOutputStream.close();
		  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean checkDB(){
		String path=DB_PATH+DB_NAME;
		File file=new File(path);
		if(file.exists()){
			Log.e(getClass().getName(), "Database already exites");
			return true;
		}
		Log.e(getClass().getName(), "Database does not exites");
		return false;
	}
	
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        //db.execSQL(POEM_TABLE_SQL);
       // Log.e("--->Table create---->", POEM_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public long insertPoem(PoemSQLTable pst){
		
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(NAME_FIELD, pst.getName());
		values.put(TITLE_FIELD, pst.getTitle());
		values.put(DETAILS_FIELD, pst.getDetails());
		
		long inserted=db.insert(POEM_TABLE, null, values);
	
		db.close();
		return inserted;
		
	}
	
	
	public ArrayList<PoemSQLTable> getAllPoeamSQLTable(){
		ArrayList<PoemSQLTable> allPoeamSQLTable=new ArrayList<PoemSQLTable>();
	    SQLiteDatabase db=this.getReadableDatabase();
	    Cursor cursor=db.query(POEM_TABLE, null, null, null, null, null, null);
	    if (cursor!=null && cursor.getCount()>0) {
	    	cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {		
			
			int id=cursor.getInt(cursor.getColumnIndex(ID_FIELD));
			String name=cursor.getString(cursor.getColumnIndex(NAME_FIELD));
			String title=cursor.getString(cursor.getColumnIndex(TITLE_FIELD));
			String details=cursor.getString(cursor.getColumnIndex(DETAILS_FIELD));
			PoemSQLTable pst=new PoemSQLTable(id, name, title, details);
			allPoeamSQLTable.add(pst);
			
			cursor.moveToNext();
		}	
	   
		}	
		cursor.close();
		db.close();
		return allPoeamSQLTable;
	}
	
	public ArrayList<PoemSQLTable> SearchPoem(String keyword){
		ArrayList<PoemSQLTable> pst=new ArrayList<PoemSQLTable>();
		SQLiteDatabase db=this.getReadableDatabase();
		
		Cursor cursor=db.query(POEM_TABLE, null, NAME_FIELD+" LIKE '%"+keyword+"%'" , null,null, null, null);
		if(cursor!=null && cursor.getCount()>0){
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {	
			String name=cursor.getString(cursor.getColumnIndex(NAME_FIELD));
			String title=cursor.getString(cursor.getColumnIndex(TITLE_FIELD));
			String details=cursor.getString(cursor.getColumnIndex(DETAILS_FIELD));
			PoemSQLTable p=new PoemSQLTable(name, title, details);
			pst.add(p);
		    cursor.moveToNext();
			}
		}
		
		cursor.close();
		db.close();
		return pst;
	}
	

}
