package com.example.poeamparsing;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

//shuvropall@yahoo.com

public class MainActivity extends Activity  implements
  SearchView.OnQueryTextListener{


	ListView lv;
	ArrayAdapter<String> adapter;
	Context context;
	ArrayList<String> data;
	Typeface font;
	Button btnUpdate;
	boolean click=true;
	private SearchView mSearchview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);
		btnUpdate=(Button) findViewById(R.id.btnUpdate);
		lv=(ListView)findViewById(R.id.listView1);
		data=new ArrayList<String>();
		context=this; 
		font=Typeface.createFromAsset(getAssets(), "avro.ttf");
      
		if(!isConnection(this)){
			Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Is connected", Toast.LENGTH_LONG).show();
		}
		
		
		adapter=new ArrayAdapter<String>(this, R.layout.listview_font_style, data){ 

            public View getView(int pos, View convertView,
                    android.view.ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.listview_font_style, null);
                }
                TextView tv = (TextView) v.findViewById(R.id.txtID);
                tv.setText(data.get(pos));
                tv.setTypeface(font); 
                return v;
            }; 
        };
		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
	
		 
		
		Toast.makeText(this, "Loasing please wait...", Toast.LENGTH_LONG).show();
		
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(click){
					click=false;
				if(isConnection(context)){
					new AsyncLoadWriterName().execute();
					Toast.makeText(getApplicationContext(), "Update Poem", Toast.LENGTH_LONG).show();
					//lv.notifyAll();
					}
			}
	    }
		});
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long length) {
				
				Intent i=new Intent(MainActivity.this, Title_List.class);
				i.putExtra("getName", data.get(position));
				//Toast.makeText(getApplicationContext(), data.get(position), Toast.LENGTH_LONG).show();
				startActivity(i);
				
			}
		
		});
			
		
		
		data.add("Jibanananda Das");
		data.add("Omio chakraborty");
		data.add("Orun Mitro");
		data.add("Arunava Ghosh");
		data.add("Allen Ginsberg");
		data.add("Abu Zafar Obaidullah");
		data.add("Abdul Latif");
		data.add("Alauddin Al Azad");
		data.add("Asad Chowdhury");
		
		
	}
	
	protected class AsyncLoadWriterName extends AsyncTask<Void, JSONObject, ArrayList<WriterNameTable>>{

		ArrayList<WriterNameTable> writerNameTable=null;
		@Override
		protected ArrayList<WriterNameTable> doInBackground(Void... arg0) {
			
			RestAPI api=new RestAPI();
			
			try {
				JSONObject jsonObj= api.GetWriterName();
				
				JSONParser parser=new JSONParser();
				
				writerNameTable=parser.parserWriterName(jsonObj);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return writerNameTable;
		}
		
		@Override
		protected void onPostExecute(ArrayList<WriterNameTable> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			for (int i = 0; i < result.size(); i++) {
				//data.add(result.get(i).getNo()+" "+result.get(i).getName());
				data.add(result.get(i).getName());
			}
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "Loading Completed", Toast.LENGTH_LONG).show();
			
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
	
	//---------------------------Search view----------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchview = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);

		return true;
	}

	private void setupSearchView(MenuItem searchItem) {
		if (isAlwaysExpanded()) {
			mSearchview.setIconifiedByDefault(false);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
		SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
		if(searchManager!=null){
			List<SearchableInfo> searchables=searchManager.getSearchablesInGlobalSearch();
			
			SearchableInfo info=searchManager.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchables) {
				if(inf.getSuggestAuthority()!=null && inf.getSuggestAuthority().startsWith("applications")){
					info=inf; 
				}
			}
			mSearchview.setSearchableInfo(info);
		}
	  mSearchview.setSubmitButtonEnabled(true);
	  mSearchview.setQueryHint("Search Here");
       mSearchview.setOnQueryTextListener(this);
      
	} 

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub	
		if(TextUtils.isEmpty(newText)){
		lv.clearTextFilter();
		}else{
			lv.setFilterText(newText.toString());
		}
		
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		//mStatusView.setText("Query= "+ query+" : submitted");
		return false;
	}
   public boolean onClose(){
	//   mStatusView.setText("Closed");
	return false;
   }
	protected boolean isAlwaysExpanded() {
		return false;
	}
	
	public void btnHistorys(View v){
		Intent intent=new Intent(MainActivity.this, PoeamHistory_Activity.class);
		startActivity(intent);
	}
	

}
