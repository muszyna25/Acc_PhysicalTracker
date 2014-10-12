package com.example.accphysicaltracker;

import android.util.Log;

public class Speed {

	private long lastUpdate = 0;
	private float Speed = 0;
	long now = 0;
	int temp = 0;
	int time = 0;
	private static final double nbElements = 100;

	public Speed(float x, float y, float z, long tS) {

		long curTime = System.currentTimeMillis();

		if ((curTime - lastUpdate) > nbElements) {
			long diffTime = (curTime - lastUpdate);
			lastUpdate = curTime;
			setSpeed(calculateSpeed(x, y, z, diffTime));
		}
	}

	private float calculateSpeed(float x, float y, float z, long diffTime) {
		// float speed = Math.abs(x + y + z)/ diffTime * 10000;
		Log.d("TESTING...", "TESITNG..." + x + " " + y + " " + z + " " + diffTime);
		float speed = (float) (Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2))) / diffTime * 100000000);
		Log.d("SPEED...", "Speed..." + speed);
		return speed;
	}

	public float getSpeed() {
		return Speed;
	}

	public void setSpeed(float speed) {
		Speed = speed;
	}
}
