package com.example.accphysicaltracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Summary extends Activity {

	private Intent info;
	private TextView Avg_speed;
	private TextView Distance;
	private TextView Expenditure;

	private Calculation final_results;
	private String infoResults = " ";

	private Button btnPostToWall1;
	private Button btnFbLogin;
	private Button logouttbn;
	private Activity context;

	// Your Facebook APP ID. You have to put your own facebook id.
	private static String APP_ID = "xxxxxxxxxxxxxxxxxxxxxxxx";

	// Instance of Facebook Class
	@SuppressWarnings("deprecation")
	private Facebook facebook = new Facebook(APP_ID);
	@SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;

	private SharedPreferences mPrefs;

	private int count = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);

		mAsyncRunner = new AsyncFacebookRunner(facebook);

		info = getIntent();

		// Get bundle from intent.
		Bundle bundle = info.getExtras();

		// Get status value from bundle.
		infoResults = bundle.getString("results1");

		btnFbLogin = (Button) findViewById(R.id.btn_fblogin1);

		btnFbLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Image Button", "button Clicked");
				loginToFacebook();
				Toast.makeText(getApplicationContext(), "Loged in!",
						Toast.LENGTH_SHORT).show();

				if (count == 0) {
					btnPostToWall1.setVisibility(View.VISIBLE);
					count = count + 1;
				}

			}
		});

		Button buttonsum = (Button) findViewById(R.id.quit2);
		btnPostToWall1 = (Button) findViewById(R.id.btn_fb_post_to_wall1);
		btnPostToWall1.setVisibility(View.INVISIBLE);

		buttonsum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent finish = new Intent(Summary.this, MainActivity.class);
				finish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(finish);
				finish();
			}
		});

		btnPostToWall1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (count == 1) {
					postToWall(infoResults);
					Toast.makeText(getApplicationContext(), "Result posted!",
							Toast.LENGTH_SHORT).show();
					btnPostToWall1.setVisibility(View.INVISIBLE);
					count = count + 1;
				} else {
					btnPostToWall1.setVisibility(View.INVISIBLE);
					Toast.makeText(getApplicationContext(),
							"Result was posted!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		logouttbn = (Button) findViewById(R.id.logoutbtn);
		logouttbn.setVisibility(View.INVISIBLE);

		logouttbn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				callFacebookLogout(getApplicationContext());
				Toast.makeText(getApplicationContext(), "Loged out!",
						Toast.LENGTH_SHORT).show();
				logouttbn.setVisibility(View.INVISIBLE);
				Intent finish = new Intent(Summary.this, MainActivity.class);
				finish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(finish);
				finish();

			}
		});

		logouttbn.setVisibility(View.INVISIBLE);

		Avg_speed = (TextView) findViewById(R.id.mean_speed);

		final_results = new Calculation();
		((TextView) findViewById(R.id.mean_speed)).setText(infoResults);

	}

	public void onClickQuit1(View v) {
		finish();
	}

	/**
	 * Function to post to facebook wall
	 * */
	@SuppressWarnings("deprecation")
	public void postToWall(final String message1) {

		new Thread() {
			@Override
			public void run() {
				try {
					Bundle parameters = new Bundle();
					parameters.putString("message", message1);

					@SuppressWarnings("deprecation")
					String response = facebook.request("me/feed", parameters,
							"POST");
					if (!response.equals("")) {
						if (!response.contains("error")) {
							context.runOnUiThread(successRunnable);
							Toast.makeText(context, "Result posted!",
									Toast.LENGTH_SHORT).show();
						} else {
							Log.e("Facebook error:", response);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private Runnable successRunnable = new Runnable() {
		@Override
		public void run() {
			Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	/**
	 * Function to Logout user from Facebook
	 * */
	@SuppressWarnings("deprecation")
	public void logoutFromFacebook() {
		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
						}

					});

				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	/**
	 * Function to login into facebook
	 * */
	@SuppressWarnings("deprecation")
	public void loginToFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);

			btnFbLogin.setVisibility(View.INVISIBLE);

			// Making post to wall visible
			logouttbn.setVisibility(View.VISIBLE);
			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream" },
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();

							// Making Login button invisible
							btnFbLogin.setVisibility(View.INVISIBLE);

							// Making post to wall visible
							btnPostToWall1.setVisibility(View.VISIBLE);
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}
	}

	public static void callFacebookLogout(Context context) {
		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
				// clear your preferences if saved
			}
		} else {

			session = new Session(context);
			Session.setActiveSession(session);

			session.closeAndClearTokenInformation();
		}

	}

}
