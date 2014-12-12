package com.example.accphysicaltracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;

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

	ArrayList<Float> sp = new ArrayList<Float>();
	ArrayList<Float> dist = new ArrayList<Float>();
	ArrayList<Float> expen = new ArrayList<Float>();

	private long start_game;
	public long end_time;
	private boolean stop = true;
	private Handler handler = new Handler();
	DBHelper mydb = new DBHelper(this);

	float way = 0;

	public float getWay() {
		return way;
	}

	public void setWay(float way) {
		this.way = way;
	}

	public float getCalories() {
		return calories;
	}

	public void setCalories(float calories) {
		this.calories = calories;
	}

	public float getAvg_velocity() {
		return avg_velocity;
	}

	public void setAvg_velocity(float avg_velocity) {
		this.avg_velocity = avg_velocity;
	}

	float calories = 0;
	float avg_velocity = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculation);

		start_game = System.currentTimeMillis();
		handler.postDelayed(thread, 1);

		Button button2 = (Button) findViewById(R.id.quit1);
		button2.setOnClickListener(this);

		info = getIntent();

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		frequency_sampling = SensorManager.SENSOR_DELAY_GAME;

		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				frequency_sampling);

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

					((TextView) findViewById(R.id.real_dist))
							.setText("Distance" + "\t\t" + speed.getDistance());

					((TextView) findViewById(R.id.calories)).setText("Calories"
							+ "\t\t" + speed.getExpenditure());

					sp.add(speed.getSpeedAfter());
					dist.add(speed.getDistance());
					expen.add(speed.getExpenditure());

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

	public void calculateSummary(ArrayList<Float> sp1, ArrayList<Float> dist1,
			ArrayList<Float> expen1) {

		for (int i = 0; i < dist1.size(); i++) {
			way = way + dist1.get(i);
		}

		for (int j = 0; j < expen1.size(); j++) {
			calories = calories + expen1.get(j);
		}

		float velocity = 0;

		for (int x = 0; x < sp1.size(); x++) {
			velocity = velocity + sp1.get(x);
		}

		avg_velocity = velocity / sp1.size();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		// Stop in case of any problems. Return to previous activity.
		case R.id.quit1:
			SimpleDateFormat sdf1 = new SimpleDateFormat("mm.ss.SSS");
			end_time = System.currentTimeMillis();
			Date r1 = new Date((end_time - start_game));

			calculateSummary(sp, dist, expen);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			if (Float.toString(avg_velocity) == "NaN")
				avg_velocity = 0;

			if (Float.toString(way) == "NaN")
				way = 0;

			if (Float.toString(calories) == "NaN")
				calories = 0;

			String person_result = dateFormat.format(date) + "\n"
					+ sdf1.format(r1) + " sec\n" + Float.toString(way) + " m\n"
					+ Float.toString(avg_velocity) + " m/s\n"
					+ Float.toString(calories) + " cal";

			mydb.insertScore(person_result);

			Log.d("Pushed", "Pushing.." + sdf1.format(r1));

			handler.removeCallbacks(thread);

			Intent inte = new Intent(Calculation.this, Summary.class);
			inte.putExtra("timepicker", " ");

			// Add data to a bundle.
			Bundle extras2 = new Bundle();
			extras2.putString("results1",
					"Total time: " + sdf1.format(r1) + "\n" + "Average speed: "
							+ Float.toString(getAvg_velocity()) + "\n"
							+ "Distance: " + Float.toString(getWay()) + "\n"
							+ "Expenditure: " + Float.toString(getCalories()));

			// Add bundle to intent.
			inte.putExtras(extras2);

			startActivity(inte);
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

	}

	private Runnable thread = new Runnable() {
		@SuppressLint("SimpleDateFormat")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			SimpleDateFormat sdf = new SimpleDateFormat("mm.ss.SSS");
			long t = System.currentTimeMillis();
			Date r = new Date((t - start_game));
			TextView ch = (TextView) findViewById(R.id.timeLabel);
			ch.setText(sdf.format(r));
			handler.postDelayed(thread, 1);
		}
	};

}
