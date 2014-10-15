package com.example.accphysicaltracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import android.os.Environment;

public class SaveData {

	private String comma = new String(" | ");
	FileOutputStream output = null;
	FileWriter writer;
	File file;

	// Path to saved file.
	private String nameStr = new String(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/save.txt");

	public SaveData() { }
	
	/**
	 * Function saves the data of performance.
	 * @param speed
	 * @param distance
	 * @param expenditure
	 */
	public void saveData(float speed, float distance, float expenditure) {

		file = new File(nameStr);

		DecimalFormat df = new DecimalFormat("#.####");
		String eol = System.getProperty("line.separator");

		try {

			writer = new FileWriter(file, true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			writer.write(df.format(speed) + comma + df.format(distance) + comma + df.format(expenditure) + eol);

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
