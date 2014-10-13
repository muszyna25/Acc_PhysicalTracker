package com.example.accphysicaltracker;

import android.hardware.SensorEvent;
import android.util.Log;

public class Speed {

	private long lastUpdate = 0;
	private float Speed = 0;
	long now = 0;
	long time = 0;
	int temp = 0;
	private static final double nbElements = 100;

	public Speed(float x, float y, float z, long tS) {

		/*long curTime = System.currentTimeMillis();

		if ((curTime - lastUpdate) > nbElements) {
			long diffTime = (curTime - lastUpdate);
			lastUpdate = curTime;
			setSpeed(calculateSpeed(x, y, z, diffTime));
		}*/
		//Log.d("TESTING...", "TESITNG..." + now + " " + tS);
		
		
		
	}

	private float calculateSpeed(float x, float y, float z, long diffTime) {
		// float speed = Math.abs(x + y + z)/ diffTime * 10000;
		Log.d("TESTING...", "TESITNG..." + x + " " + y + " " + z + " " + diffTime);
		float speed = (float) (Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2))) / diffTime * 100000000);
		Log.d("SPEED...", "Speed..." + Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2))) / nbElements * 1000000000 );
		return speed;
	}
	
	public void avarageSpeed(float x, float y, float z, SensorEvent event){
		long tS = event.timestamp;
		
		if (now != 0) {
			temp++;
			Log.d("TESTING...", "TESITNG..." + "HUJ1");
			if (temp == nbElements) {
				time = tS - now;
				setSpeed(calculateSpeed(x, y, z, time));
				temp = 0;
			}
		}
		// To set up now on the first event and do not change it while we do
		// not have "nbElements" events
		if (temp == 0) {
			now = tS;
		}
	}

	public float getSpeed() {
		return Speed;
	}

	public void setSpeed(float speed) {
		Speed = speed;
	}
}
