package com.example.accphysicaltracker;

import java.text.DecimalFormat;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
		SensorEventListener {

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

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		frequency_sampling = SensorManager.SENSOR_DELAY_NORMAL;

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

		System.arraycopy(event.values, 0, rawAcceleration, 0, event.values.length);
		
		rawAcceleration[0] = rawAcceleration[0] / SensorManager.GRAVITY_EARTH;
		rawAcceleration[1] = rawAcceleration[1] / SensorManager.GRAVITY_EARTH;
		rawAcceleration[2] = rawAcceleration[2] / SensorManager.GRAVITY_EARTH;

		// Display values using TextView.
		X_corr.setText("X axis" + "\t\t" + rawAcceleration[0]);
		Y_corr.setText("Y axis" + "\t\t" + rawAcceleration[1]);
		Z_corr.setText("Z axis" + "\t\t" + rawAcceleration[2]);

		long tS = event.timestamp;
		speed = new Speed(rawAcceleration[0], rawAcceleration[1], rawAcceleration[2], tS);
		
		Speed_value.setText("Speed" + "\t\t" + speed.getSpeed());
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
}