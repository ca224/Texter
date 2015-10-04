package com.njit.texter;

import java.io.ByteArrayOutputStream;

import com.njit.texter.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {

	// Declare Variable
	private Button signup;
	private DatePicker dob;
	private String usernametxt;
	private String passwordtxt;
	private String nicknametxt;
	private String dobtxt;
	private EditText password;
	private EditText username;
	private EditText nickname;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.signup);

		// Locate Button in welcome.xml
		nickname = (EditText) findViewById(R.id.nickname);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		signup = (Button) findViewById(R.id.SignupBtn);
		dob = (DatePicker) findViewById(R.id.dob);

		signup.setOnClickListener(new OnClickListener() {

			ParseUser user;

			public void onClick(View arg0) {
				// Retrieve the text entered from the EditText
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();
				nicknametxt = nickname.getText().toString();
				dobtxt = "" + Integer.toString(dob.getYear())
						+ Integer.toString(dob.getMonth())
						+ Integer.toString(dob.getDayOfMonth());
				// Force user to fill up the form
				if (usernametxt.equals("") && passwordtxt.equals("")
						&& nicknametxt.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please complete the sign up form",
							Toast.LENGTH_LONG).show();

				} else {
					// Save new user data into Parse.com Data Storage
					user = new ParseUser();
					user.setUsername(usernametxt);
					user.setPassword(passwordtxt);
					user.put("nickname", nicknametxt);
					user.put("dob", dobtxt);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					getRes("profile_default").compress(
							Bitmap.CompressFormat.PNG, 0, bos);
					byte[] photoData = bos.toByteArray();
					ParseFile photoFile = new ParseFile("photo.png", photoData);
					user.put("profilephoto", photoFile);
					photoFile.saveInBackground(new SaveCallback() {
						@Override
						public void done(ParseException e) {
							user.signUpInBackground(new SignUpCallback() {
								public void done(ParseException e) {
									if (e == null) {
										// Show a simple Toast message upon
										// successful registration
										Toast.makeText(getApplicationContext(),
												"Successfully Signed up.",
												Toast.LENGTH_LONG).show();
										ParseObject obj = new ParseObject(
												"follow");
										obj.put("target", user);
										obj.put("follower", user);
										obj.saveInBackground(new SaveCallback() {

											@Override
											public void done(ParseException e) {
												Intent intent = new Intent(
														SignupActivity.this,
														LoginSignupActivity.class);
												startActivity(intent);
												Toast.makeText(
														getApplicationContext(),
														"Please Login",
														Toast.LENGTH_LONG)
														.show();
												finish();
											}

										});
									} else {
										Toast.makeText(getApplicationContext(),
												"Sign up Error",
												Toast.LENGTH_LONG).show();
									}
								}
							});
						}
					});
				}

			}
		});
	}

	public Bitmap getRes(String name) {
		ApplicationInfo appInfo = getApplicationInfo();
		int resID = getResources().getIdentifier(name, "drawable",
				appInfo.packageName);
		return BitmapFactory.decodeResource(getResources(), resID);
	}
}
