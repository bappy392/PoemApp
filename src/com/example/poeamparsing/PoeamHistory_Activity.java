package com.example.poeamparsing;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

public class PoeamHistory_Activity extends Activity {

	DatabaseHistory dbHistory;
	ListView lv;
	ArrayList<String> listPoem=new ArrayList<String>();
	ArrayAdapter<String> adapter;
	Typeface font;
	ArrayList<History> allHistory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poeam_history_);
		// Show the Up button in the action bar.
		setupActionBar();
		lv=(ListView)findViewById(R.id.lvHistory);
		dbHistory=new DatabaseHistory(getApplicationContext());
		font=Typeface.createFromAsset(getAssets(), "avro.ttf");
		
		 allHistory=dbHistory.getAllPoemHistory();
		
		if(allHistory!=null && allHistory.size()>0){
			for (History history : allHistory) {
				listPoem.add(history.getTitle());
			}
			
		}
		
		adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_font_style, listPoem){ 

            public View getView(int pos, View convertView,
                    android.view.ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.listview_font_style, null);
                }
                TextView tv = (TextView) v.findViewById(R.id.txtID);
                tv.setText(listPoem.get(pos));
                tv.setTypeface(font); 
                return v;
            }; 
        };
		lv.setAdapter(adapter);
		
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long length) {
				
				Intent i=new Intent(PoeamHistory_Activity.this, PoeamHistoryDetails_Activity.class);
				i.putExtra("getName", allHistory.get(position));
				//Toast.makeText(getApplicationContext(), data.get(position), Toast.LENGTH_LONG).show();
				startActivity(i);
				
			}
	    	
	    	
		});
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poeam_history_, menu);
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
