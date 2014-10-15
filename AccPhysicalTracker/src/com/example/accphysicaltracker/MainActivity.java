package com.example.accphysicaltracker;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, SensorEventListener {

	
	// Buttons for configuration.
	private Button btn_start;
	private Button results;
	private Button register;
	
	//Reference to abstract action.
	private Intent inte;
	
	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_start = (Button) findViewById(R.id.start_btn);
		btn_start.setOnClickListener(this);
		
		results = (Button) findViewById(R.id.result_btn);
		results.setOnClickListener(this);
		
		register = (Button) findViewById(R.id.register_btn);
		register.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.start_btn:
			
			inte = new Intent(MainActivity.this, Calculation.class);
			inte.putExtra("timepicker", " ");

			//Add data to a bundle.
			Bundle extras = new Bundle();
			extras.putString("timepicker1", " ");

			//Add bundle to intent.
			inte.putExtras(extras);

			startActivity(inte);
			
			break;
			
		case R.id.register_btn:
			inte = new Intent(MainActivity.this, Register.class);
			inte.putExtra("timepicker", " ");

			//Add data to a bundle.
			Bundle extras1 = new Bundle();
			extras1.putString("timepicker1", " ");

			//Add bundle to intent.
			inte.putExtras(extras1);

			startActivity(inte);
			
			break;
			
		case R.id.result_btn:
			inte = new Intent(MainActivity.this, Results.class);
			inte.putExtra("timepicker", " ");

			//Add data to a bundle.
			Bundle extras2 = new Bundle();
			extras2.putString("timepicker1", " ");

			//Add bundle to intent.
			inte.putExtras(extras2);

			startActivity(inte);
			
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch (item.getItemId()) {
	        case R.id.about_text:
	            Toast.makeText(MainActivity.this, "About application !", Toast.LENGTH_SHORT).show();
	            Intent i = new Intent(MainActivity.this, About.class);
		    	startActivity(i);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public void onClickQuit(View v) {
		finish();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
}