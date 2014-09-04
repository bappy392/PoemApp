package com.example.poeamparsing;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
	public JSONParser() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public ArrayList<WriterNameTable> parserWriterName(JSONObject object){
		ArrayList<WriterNameTable> arrayList=new ArrayList<WriterNameTable>();
		
		try {
			JSONArray jsonArray=object.getJSONArray("Value");
			JSONObject jsonObj=null;
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObj=jsonArray.getJSONObject(i);
				arrayList.add(new WriterNameTable(jsonObj.getInt("no"), jsonObj.getString("name")));
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("JSONParser => parserWriterName", e.getMessage());
		}
		
		
		return arrayList;
	}
	
	public ArrayList<WriterDetailsTable> parserWriterDetails(JSONObject object){
		ArrayList<WriterDetailsTable> arrayList=new ArrayList<WriterDetailsTable>();
		
		try {
			JSONArray jsonArray=object.getJSONArray("Value");
			JSONObject jsonObj=null;
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObj=jsonArray.getJSONObject(i);
				arrayList.add(new WriterDetailsTable(jsonObj.getString("title"), jsonObj.getString("details")));
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("JSONParser => parserWriterName", e.getMessage());
		}
		
		
		return arrayList;
	}
	
	
	
	
	
	

}
