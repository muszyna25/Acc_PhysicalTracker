package com.example.accphysicaltracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.UserDataHandler;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
		SensorEventListener {

	// Buttons for configuration.
	private Button btn_start;
	private Button results;
	private Intent inte;

	@SuppressWarnings("deprecation")
	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_start = (Button) findViewById(R.id.start_btn);
		btn_start.setOnClickListener(this);

		results = (Button) findViewById(R.id.result_btn);
		results.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.start_btn:

			inte = new Intent(MainActivity.this, Calculation.class);
			inte.putExtra("timepicker", " ");

			// Add data to a bundle.
			Bundle extras = new Bundle();
			extras.putString("timepicker1", " ");

			// Add bundle to intent.
			inte.putExtras(extras);

			startActivity(inte);

			break;

		case R.id.result_btn:
			inte = new Intent(MainActivity.this, Results.class);
			inte.putExtra("timepicker", " ");

			// Add data to a bundle.
			Bundle extras2 = new Bundle();
			extras2.putString("timepicker1", " ");

			// Add bundle to intent.
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
			Toast.makeText(MainActivity.this, "About application !",
					Toast.LENGTH_SHORT).show();
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

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}