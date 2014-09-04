package com.example.poeamparsing;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;

public class PoeamDetails extends Activity {

	TextView txtTitle,txtDetails;
	String title,details;
	DatabaseHistory dbHistory;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poeam_details);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Typeface tf=Typeface.createFromAsset(getAssets(), "avro.ttf");
		
	    
		txtTitle=(TextView)findViewById(R.id.txtTitle);
		txtDetails=(TextView)findViewById(R.id.txtDetails);
		
		title=getIntent().getStringExtra("getTitle");
		details=getIntent().getStringExtra("getDetails");
		
		
		txtTitle.setTypeface(tf);
	    txtDetails.setTypeface(tf);
		
		txtTitle.setText(title);
		txtDetails.setText(details);
		
		dbHistory=new DatabaseHistory(getApplicationContext());
		
		
	}
	
	public void btnSaves(View v){
		History allHistory=new History(title, details);
		long inserted=dbHistory.insertPoemHistory(allHistory);
		if(inserted>0){
			Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Not Saved", Toast.LENGTH_LONG).show();
		}
		
		
	}
	
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//        this.finish();
//       
//	}
	
	
	

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
		getMenuInflater().inflate(R.menu.poeam_details, menu);
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
