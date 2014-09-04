package com.example.poeamparsing;

import java.util.ArrayList;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Title_List extends Activity {

	ListView lv;
	ArrayAdapter<String> adapter;
	Context context;
	ArrayList<String> data1;
	ArrayList<String> data2;
	String search=null;
	Typeface font;
	DatabaseHelper dbHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title__list);
		// Show the Up button in the action bar.
		setupActionBar();
		font=Typeface.createFromAsset(getAssets(), "avro.ttf");
		lv=(ListView)findViewById(R.id.lvList);
		data1=new ArrayList<String>();
		data2=new ArrayList<String>();
		
		context=this;
		
		
		search=getIntent().getStringExtra("getName");

		
		dbHelper=new DatabaseHelper(getApplicationContext());
		SqlData();
		
		adapter=new ArrayAdapter<String>(this, R.layout.listview_font_style , data1){

            public View getView(int pos, View convertView,
                    android.view.ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.listview_font_style, null);
                }
                TextView tv = (TextView) v.findViewById(R.id.txtID);
                tv.setText(data1.get(pos));
                tv.setTypeface(font);
                return v;
            };
        };
				
		lv.setAdapter(adapter);
		Toast.makeText(this, "Loasing please wait...", Toast.LENGTH_LONG).show();
		
		
		if(isConnection(this)){
		new AsyncLoadWriterDetails().execute(search);
		}
	
		Toast.makeText(getApplicationContext(), "searching: "+search, Toast.LENGTH_LONG).show();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long length) {
				
				Intent i=new Intent(Title_List.this, PoeamDetails.class);
				i.putExtra("getTitle", data1.get(position));
				i.putExtra("getDetails", data2.get(position));
				//Toast.makeText(getApplicationContext(), data1.get(position), Toast.LENGTH_LONG).show();
				startActivity(i);
				 
			}
		
		});
		
		
	}
	
	protected class AsyncLoadWriterDetails extends AsyncTask<String, JSONObject, ArrayList<WriterDetailsTable>>{

		ArrayList<WriterDetailsTable> writerDetailsTable=null;
		@Override
		protected ArrayList<WriterDetailsTable> doInBackground(String... param) {
			
			RestAPI api=new RestAPI();
			
			try {
				if(isConnection(context)){
					
				JSONObject jsonObj= api.GetWriterDetials(param[0]);
				
				JSONParser parser=new JSONParser();
				
				writerDetailsTable=parser.parserWriterDetails(jsonObj);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return writerDetailsTable;
		}
		
		@Override
		protected void onPostExecute(ArrayList<WriterDetailsTable> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			for (int i = 0; i < result.size(); i++) {
				//data.add(result.get(i).getNo()+" "+result.get(i).getName());
				//data.add(result.get(i).getTitle()+"\n"+"-------------------"+"\n \n"+ result.get(i).getDetails());
				data1.add(result.get(i).getTitle());
				data2.add(result.get(i).getDetails());
			}
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "Loading Completed", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
	
	public void SqlData(){
		
		ArrayList<PoemSQLTable> pst=dbHelper.SearchPoem(search);
	    if(pst!=null && pst.size()>0){
	    	for (int i = 0; i < pst.size(); i++) {
	    	//	Toast.makeText(getApplicationContext(), ""+pst.size(), Toast.LENGTH_LONG).show();
				data1.add(pst.get(i).getTitle());
				data2.add(pst.get(i).getDetails());
				
			}

	    }
		
		
	}
	
	
	public static boolean isConnection(Context con) {
	    ConnectivityManager conMgr = (ConnectivityManager) con
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (conMgr.getActiveNetworkInfo() != null
	            && conMgr.getActiveNetworkInfo().isAvailable()
	            && conMgr.getActiveNetworkInfo().isConnected()) {
	        return true;
	    } else {
	        return false;
	    }
	}
	

	
	
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title__list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
