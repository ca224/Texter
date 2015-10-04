package com.njit.texter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.njit.texter.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginSignupActivity extends Activity {
	// variables
	private Button loginbutton;
	private Button signupbutton;
	private String usernametxt;
	private String passwordtxt;
	private EditText password;
	private EditText username;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from main.xml
		setContentView(R.layout.loginsignup);
		// Locate EditTexts in main.xml
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		// Locate Buttons in main.xml
		loginbutton = (Button) findViewById(R.id.login);
		signupbutton = (Button) findViewById(R.id.signup);

		// Login Button Click Listener
		loginbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Retrieve the text entered from the EditText
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();

				// Send data to Parse.com for verification
				ParseUser.logInInBackground(usernametxt, passwordtxt,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									// If user exist and authenticated, send
									// user to PostListActivity.class
									Intent intent = new Intent(
											LoginSignupActivity.this,
											PostListActivity.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"Successfully Logged in",
											Toast.LENGTH_LONG).show();
									finish();
								} else {
									Toast.makeText(
											getApplicationContext(),
											"No such user exist, please signup",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});

		// Sign up Button Click Listener
		signupbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				Intent intent = new Intent(LoginSignupActivity.this,
						SignupActivity.class);
				startActivity(intent);
			}
		});

	}
}
