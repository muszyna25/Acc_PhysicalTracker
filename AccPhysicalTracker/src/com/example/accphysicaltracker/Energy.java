package com.example.accphysicaltracker;

import android.util.Log;

public class Energy {
	
	private float caloricExp = 0;
	
	public Energy(){ }
	
	/**
	 * Function calculates calories expenditure in given time.
	 * @param weight - mass of object
	 * @param speed - current speed
	 * @return caloricExp - energy expenditure
	 */
	public float calculateExpenditure(double weight, float speed){
		
		caloricExp = (float) ((((0.2*speed)+(0.9*speed*1)+3.5) * weight/1000)*5);
		
		Log.d("ENERGY...", "Calories..." + getCaloricExp());
		
		return caloricExp;
	}

	public float getCaloricExp() {
		return caloricExp;
	}

	public void setCaloricExp(float caloricExp) {
		this.caloricExp = caloricExp;
	}
}
