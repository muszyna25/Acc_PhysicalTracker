package com.example.accphysicaltracker;

import android.util.Log;

public class Distance {

	private float distance = 0;
	
	public Distance(){ }
	
	/**
	 * Function calculates distance in given interval of time.
	 * @param speedBefore - initial speed
	 * @param t - time interval
	 * @param acceleration - known acceleration
	 * @return distance - measured distance
	 */
	public float calulateDistance(float speedBefore, float t, float acceleration){
	
		distance = speedBefore * t + acceleration * t * t / 2;
		Log.d("DISTANCE...", "Distance..." + getDistance());
		
		return distance;
	}

	public float getDistance() {
		return distance;
	}

	public float setDistance(float distance) {
		this.distance = distance;
		return distance;
	}
}
