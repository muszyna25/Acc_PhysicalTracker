package com.example.accphysicaltracker;


import java.util.Calendar;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity implements OnClickListener {

	//Reference to abstract action.
	private Intent inte;

	//View of set time.
	private TextView from_time;
	private TextView to_time;

	//Variable holds values put in time pickers.
	private String set_from;
	private String set_to;

	//Variable for storing current time.
	private int mHour, mMinute, mHour1, mMinute1;
	private int pass_hour, pass_minute, pass_hour1, pass_minute1;

	//Buttons for configuration.
	private Button btn_start;
	private Button btn_from;
	private Button btn_to;
	private boolean checked = false;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_start = (Button) findViewById(R.id.start_btn);
		btn_start.setEnabled(false);
		btn_start.setOnClickListener(this);

		btn_from = (Button) findViewById(R.id.btnFROM);
		btn_from.setOnClickListener(this);

		btn_to = (Button) findViewById(R.id.btnTO);
		btn_to.setEnabled(false);
		btn_to.setOnClickListener(this);

		from_time = (TextView) findViewById(R.id.textView3);
		to_time = (TextView) findViewById(R.id.textView4);

		set_from = from_time.getText().toString();
		set_to = to_time.getText().toString();

		Log.d("edittext", "edittext..." + set_from);
		Log.d("edittext", "edittext..." + set_to);
		
	}

	@Override
	public void onClick(View v) {
		if (v == btn_from) {

			//Process to get Current Time.
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			//Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							//Display Selected time in textbox.
							pass_hour = hourOfDay;
							pass_minute = minute;

							if (minute < 10)
								from_time.setText(hourOfDay + ":0" + minute);
							else
								from_time.setText(hourOfDay + ":" + minute);
							
						}
					}, mHour, mMinute, false);
			tpd.show();
			btn_to.setEnabled(true);
		}

		if (v == btn_to) {

			//Process to get Current Time.
			final Calendar c = Calendar.getInstance();
			mHour1 = c.get(Calendar.HOUR_OF_DAY);
			mMinute1 = c.get(Calendar.MINUTE);

			//Launch Time Picker Dialog.
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							//Display Selected time in textbox.
							pass_hour1 = hourOfDay;
							pass_minute1 = minute;

							if (minute < 10)
								to_time.setText(hourOfDay + ":0" + minute);
							else
								to_time.setText(hourOfDay + ":" + minute);
							
							int min_diff = pass_minute1 - pass_minute;
							int hour_diff = pass_hour1 - pass_hour;
							((TextView) findViewById(R.id.durationTime)).setText(hour_diff + "hours" + ":" + min_diff + "minutes");
						}
					}, mHour1, mMinute1, false);
			tpd.show();
			btn_start.setEnabled(true);
			setChecked(true);
			
		}

		switch (v.getId()) {		
		case R.id.start_btn:

			int min_shift = pass_minute - 1;
			
			int long_gap = 1000; // Length of gap.
			int dot = 200;
			long[] pattern = {
			    0,  // Start immediately
			    long_gap, dot,
			    long_gap, dot
			};
			//20 5 :

			String taken_time = Integer.toString(pass_hour1) + ":" + Integer.toString(pass_minute1) + ":" + "0";
			String taken_hour = taken_time.substring(0, 2);
			String taken_min = taken_time.substring(3, 5);
			String taken_sec = taken_time.substring(6, 7);
			
			Log.d("TESTING...", "TESITNG..." + taken_hour + " " + taken_min + " " + taken_sec);
			
			for (;;) {
				//Using Calendar class to get current time.
				Calendar ci = Calendar.getInstance();
				String CiDateTime = ci.get(Calendar.HOUR_OF_DAY) + ":" + ci.get(Calendar.MINUTE) + ":" + ci.get(Calendar.SECOND);
				//Log.d("TIME CHECK....", "" + pass_hour + ":" + pass_minute + "AND" + CiDateTime);
				
				if (CiDateTime.equals(pass_hour + ":" + min_shift + ":" + "30")) {
					Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					
					// Only perform this pattern one time (-1 means "do not repeat")
					vib.vibrate(pattern, -1);
				}
				
				if (CiDateTime.equals(pass_hour + ":" + pass_minute + ":" + "0")) {
					Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					long[] pattern1 = {
							0,
							long_gap,dot
					};
					// Only perform this pattern one time (-1 means "do not repeat")
					vib.vibrate(pattern1, -1);
					
					System.gc();
					break;
				}
			}
			
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
			r.play();
			android.os.SystemClock.sleep(1000); //1000ms = 1 second delay to play sound.
			
///////////			inte = new Intent(MainActivity.this, SettingsTime.class);
			inte.putExtra("timepicker", Integer.toString(pass_hour) + ":" + Integer.toString(pass_minute));

			//Add data to a bundle.
			Bundle extras = new Bundle();
			extras.putString("timepicker1", Integer.toString(pass_hour1) + ":" + Integer.toString(pass_minute1) + ":" + "0");

			//Add bundle to intent.
			inte.putExtras(extras);

			startActivity(inte);
			
			break;
		}

	}
	
	public void onClickQuit(View v) {
		finish();
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}