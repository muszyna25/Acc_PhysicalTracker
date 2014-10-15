package com.example.accphysicaltracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {

	private RadioGroup radioSexGroup;
	private RadioButton radioSexButton;
	Intent info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		info = getIntent();
		
		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		radioSexGroup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.radioSex:
			// get selected radio button from radioGroup
			int selectedId = radioSexGroup.getCheckedRadioButtonId();

			// find the radiobutton by returned id
			radioSexButton = (RadioButton) findViewById(selectedId);

			Toast.makeText(Register.this, radioSexButton.getText(),
					Toast.LENGTH_SHORT).show();

			break;
		}

	}

}
