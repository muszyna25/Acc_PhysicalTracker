package com.example.accphysicaltracker;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Results extends Activity {

	private DBHelper mydb = new DBHelper(this);
	ListView obj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		Log.d("Read:", "Reading 1..");
		List<String> result = mydb.getAllContacts();
		Log.d("Read:", "Reading 2...");
		for(String s: result){
			String log = s;
			Log.d("", log);
		}
		
		Log.d("Read:", "Reading 3...");
		obj = (ListView)findViewById(R.id.listView1);
		
		ArrayAdapter<String> arrayAdapter =      
			      new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, result);
	
		Log.d("Read:", "Reading 4...");
		//adding it to the list view.
	    Log.d("Read:", "Reading 5...");
	    obj.setAdapter(arrayAdapter);
	    Log.d("Read:", "Reading 6...");
	}
}
