package com.example.accphysicaltracker;

import android.util.Log;

public class Speed {

	private float speedBefore = 0;
	private float speedAfter = 0;
	private float distance = 0;
	private float acceleration = 0;
	private Distance dist;
	private Energy energy;
	private float expenditure = 0;
	
	public Speed() { 
		setDist(new Distance());
		setEnergy(new Energy());
	}

	/**
	 * Function that calculates speed for a given acceleration.
	 * @param x - first element of 3-dim vector
	 * @param y - second element of 3-dim vector
	 * @param z - third element of 3-dim vector
	 * @param diffTime - time interval
	 * @return speedAfter - actual speed of object
	 */
	public float calculateSpeed(float x, float y, float z, long diffTime) {

		//Calculate acceleration as a magnitude of 3-dim vector.
		acceleration = (float) Math.sqrt(x * x + y * y + z * z);

		//Calculate given time.
		float t = ((float) diffTime / 1000000000);

		//Calculate given speed.
		speedAfter = speedBefore + acceleration * t;
		
		//Calculate given distance.
		distance = dist.calulateDistance(speedBefore, t, acceleration);

		setExpenditure(energy.calculateExpenditure(100, speedAfter));
		
		Log.d("SPEED...", "Speed..." + getAcceleration() + " " + getSpeedBefore() + " " + getSpeedAfter() + " " + t);

		return speedAfter;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getSpeedBefore() {
		return speedBefore;
	}

	public void setSpeedBefore(float speedBefore) {
		this.speedBefore = speedBefore;
	}

	public float getSpeedAfter() {
		return speedAfter;
	}

	public void setSpeedAfter(float speedAfter) {
		this.speedAfter = speedAfter;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public Distance getDist() {
		return dist;
	}

	public void setDist(Distance dist) {
		this.dist = dist;
	}

	public Energy getEnergy() {
		return energy;
	}

	public void setEnergy(Energy energy) {
		this.energy = energy;
	}

	public float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(float expenditure) {
		this.expenditure = expenditure;
	}

}