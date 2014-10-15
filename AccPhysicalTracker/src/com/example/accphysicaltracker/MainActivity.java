package com.example.accphysicaltracker;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, SensorEventListener {

	// References to sensor - accelerometer.
	private SensorManager mSensorManager;
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

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void onClickQuit(View v) {
		finish();
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

			if (now != 0) {
				temp++;

				if (temp == nbElements) {
					time = tS - now;

					speed.calculateSpeed(rawAcceleration[0],
							rawAcceleration[1], rawAcceleration[2], time);
					
					((TextView) findViewById(R.id.real_speed)).setText("Speed"
							+ "\t\t" + speed.getSpeedAfter());
							
					((TextView) findViewById(R.id.real_dist)).setText("Distance"
							+ "\t\t" + speed.getDistance());
					
					((TextView) findViewById(R.id.calories)).setText("Calories"
							+ "\t\t" + speed.getExpenditure());

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
}