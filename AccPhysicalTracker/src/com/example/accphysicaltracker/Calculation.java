package com.example.accphysicaltracker;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Calculation extends Activity implements OnClickListener,
		SensorEventListener {

	// References to sensor - accelerometer.
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	int frequency_sampling = 0;

	// View on values of acceleration.
	TextView X_corr;
	TextView Y_corr;
	TextView Z_corr;

	private float[] rawAcceleration = new float[3];
	private Speed speed;
	TextView Speed_value;

	long now = 0;
	long time = 0;
	int temp = 0;
	private static final double nbElements = 100;

	private SaveData data = new SaveData();
	
	Intent info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculation);
		
		Button button2 = (Button) findViewById(R.id.quit1);
	    button2.setOnClickListener(this);
		
		info = getIntent();

		// Time from which application starts.
		//String time_set = info.getStringExtra("timepicker");
		//Log.d("Time from set...", "TIME SET FROM..." + time_set);

		// Get bundle from intent.
		//Bundle bundle = info.getExtras();

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		frequency_sampling = SensorManager.SENSOR_DELAY_FASTEST;

		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				frequency_sampling);

		// Get text views.
		X_corr = (TextView) findViewById(R.id.xcoor); // Create X axis object.
		Y_corr = (TextView) findViewById(R.id.ycoor); // Create Y axis object.
		Z_corr = (TextView) findViewById(R.id.zcoor); // Create Z axis object.

		Speed_value = (TextView) findViewById(R.id.real_speed);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		long tS;

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

			System.arraycopy(event.values, 0, rawAcceleration, 0,
					event.values.length);

			rawAcceleration[0] = rawAcceleration[0]
					/ SensorManager.GRAVITY_EARTH;
			rawAcceleration[1] = rawAcceleration[1]
					/ SensorManager.GRAVITY_EARTH;
			rawAcceleration[2] = rawAcceleration[2]
					/ SensorManager.GRAVITY_EARTH;

			// Display values using TextView.
			X_corr.setText("X axis" + "\t\t" + rawAcceleration[0]);
			Y_corr.setText("Y axis" + "\t\t" + rawAcceleration[1]);
			Z_corr.setText("Z axis" + "\t\t" + rawAcceleration[2]);

			tS = event.timestamp;
			speed = new Speed();

			//data = new SaveData();

			if (now != 0) {
				temp++;

				if (temp == nbElements) {
					time = tS - now;

					speed.calculateSpeed(rawAcceleration[0],
							rawAcceleration[1], rawAcceleration[2], time);

					((TextView) findViewById(R.id.real_speed)).setText("Speed"
							+ "\t\t" + speed.getSpeedAfter());

					((TextView) findViewById(R.id.real_dist))
							.setText("Distance" + "\t\t" + speed.getDistance());

					((TextView) findViewById(R.id.calories)).setText("Calories"
							+ "\t\t" + speed.getExpenditure());

					//data.saveData(speed.getSpeedAfter(), speed.getDistance(),
					//		speed.getExpenditure());

					temp = 0;
				}
			}
			// To set up now on the first event and do not change it while we do
			// not have "nbElements" events
			if (temp == 0) {
				now = tS;
			}
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		//Stop in case of any problems. Return to previous activity.
		case R.id.quit1:
			Intent finish = new Intent(Calculation.this, MainActivity.class);
			finish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(finish);
			finish();
			break;
		}

	}
	
	public void onClickQuit(View v) {
		finish();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
		//handler.removeCallbacks(thread);

	}
}
