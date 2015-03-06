package com.academic.idiots.bootingbrain;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.academic.idiots.bootingbrain.nextnumber.NextNumberActivity;
import com.academic.idiots.nsd.NsdChatActivity;
import com.simple.fb.SimpleFacebook;
import com.simple.fb.Permission.Type;
import com.simple.fb.entities.Profile;
import com.simple.fb.entities.Profile.Properties;
import com.simple.fb.listeners.OnLoginListener;
import com.simple.fb.listeners.OnProfileListener;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity {

    private Button btnGame3;
	Button btn_go, btn_facebook, btn_game_2, btn_nsd;
	private SimpleFacebook mSimpleFacebook;
    private Context mContext = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mSimpleFacebook.getInstance(this);
		btn_go = (Button) findViewById(R.id.btn_go);
		btn_go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						PlayGroundActivity.class);
				startActivity(intent);
			}
		});

        btn_nsd  = (Button)findViewById(R.id.btn_nsd);
        btn_nsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NsdChatActivity.class);
                startActivity(intent);
            }
        });
		btn_facebook = (Button) findViewById(R.id.btn_facebook);
		btn_facebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(HomeActivity.this,
				// LoginFacebookActivity.class);
				// startActivity(intent);
				facebookLoginProcess();
			}
		});

		btn_game_2 = (Button) findViewById(R.id.btn_game_2);
		btn_game_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						MatchingPictureActivity.class);
				startActivity(intent);
			}
		});

        //Next number
        btnGame3 = (Button) findViewById(R.id.btn_game_3);
        btnGame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NextNumberActivity.class);
                startActivity(intent);
            }
        });
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.academic.idiots.bootingbrain",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("MY KEY HASH:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {
		}

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void facebookLoginProcess() {
		mSimpleFacebook.login(new OnLoginListener() {
			@Override
			public void onFail(String reason) {
				Log.w(getClass().getSimpleName(), "Failed to login");
			}

			@Override
			public void onException(Throwable throwable) {
				Log.w(getClass().getSimpleName(), "onException");
			}

			@Override
			public void onThinking() {
				// show progress bar or something to the user while login is
				// happening
				Log.w(getClass().getSimpleName(), "onThinking");
			}

			@Override
			public void onNotAcceptingPermissions(Type type) {
				Log.w(getClass().getSimpleName(), "onNotAcceptingPermissions");
			}

			@Override
			public void onLogin() {
				// change the state of the button or do whatever you want
				Log.w(getClass().getSimpleName(), "onLogin");
				if (mSimpleFacebook.isLogin()) {
					Properties properties = getFacebookDefaultProperties();
					mSimpleFacebook.getProfile(properties,
							new OnProfileListener() {
								@Override
								public void onComplete(Profile profile) {
									onProfileLoaded(profile);
								}
							});
				}
			}

			// @Override
			// public void onNotAcceptingPermissions()
			// {
			// Log.w(getClass().getSimpleName(),
			// "User didn't accept read permissions");
			// }
		});

	}

	private Properties getFacebookDefaultProperties() {
		Properties properties = new Properties.Builder().add(Properties.ID)
				.add(Properties.NAME).add(Properties.EMAIL)
				.add(Properties.FIRST_NAME).add(Properties.LAST_NAME)
				.add(Properties.BIRTHDAY).add(Properties.PICTURE)
				.add(Properties.GENDER).add(Properties.LOCALE)
				.add(Properties.WORK).add(Properties.EDUCATION)
				.add(Properties.LINK).build();
		return properties;
	}

	UserAccount_Facebook mFacebookAccount;

	private void onProfileLoaded(Profile profile) {
        Log.i("Log","onProfileLoaded");
		String id = profile.getId();
		String name = profile.getName();
		String email = profile.getEmail();
		String firstName = profile.getFirstName();
		String lastName = profile.getLastName();
		String DOB = profile.getBirthday();
		String profileImage = profile.getPicture();
		String gender = profile.getGender();
		String locale = profile.getLocale();
		String link = profile.getLink();

		mFacebookAccount = new UserAccount_Facebook();
		mFacebookAccount.setId(id);
		mFacebookAccount.setName(name);
		mFacebookAccount.setEmail(email);
		mFacebookAccount.setFirstName(firstName);
		mFacebookAccount.setLastName(lastName);
		mFacebookAccount.setDOB(DOB);
		mFacebookAccount.setProfileImage(profileImage);
		mFacebookAccount.setGender(gender);
		mFacebookAccount.setLocale(locale);
		mFacebookAccount.setLink(link);

		PostExecute postExecute = new PostExecute() {

			@Override
			public void fireResults(String data) {
				onResult(data);
                Log.e("Log",data);
			}
		};
		postExecute.execute("");
        Log.e("Log","execute");
	}

	private void onResult(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        this.btn_facebook.setEnabled(false);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSimpleFacebook = SimpleFacebook.getInstance(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
	}

	String data;

	private String requestLoginWithFacebookAccount() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://104.236.123.60:8888/users/login");
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					10);
                nameValuePairs.add(new BasicNameValuePair("uid", mFacebookAccount
					.getId()));
			nameValuePairs.add(new BasicNameValuePair("username", mFacebookAccount
					.getName()));
			nameValuePairs.add(new BasicNameValuePair("email", mFacebookAccount
					.getEmail()));
			nameValuePairs.add(new BasicNameValuePair("gender",
					mFacebookAccount.getGender()));
			nameValuePairs.add(new BasicNameValuePair("image", mFacebookAccount
					.getLink()));
            nameValuePairs.add(new BasicNameValuePair("provider","facebook"));

//			nameValuePairs.add(new BasicNameValuePair("api_key",
//					"00848j4hj9d833333"));

            nameValuePairs.add(new BasicNameValuePair("birthday",
                    mFacebookAccount.getDOB()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			int status = response.getStatusLine().getStatusCode();
			Log.w("respond", status + "");
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				String respondingData = EntityUtils.toString(entity);
				Log.w("respond", respondingData);
				return respondingData;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		return null;
	}

	private abstract class PostExecute extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// Create a new HttpClient and Post Header
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                        10);
                nameValuePairs.add(new BasicNameValuePair("uid", mFacebookAccount
                        .getId()));
                nameValuePairs.add(new BasicNameValuePair("username", mFacebookAccount
                        .getName()));
                nameValuePairs.add(new BasicNameValuePair("email", mFacebookAccount
                        .getEmail()));
                nameValuePairs.add(new BasicNameValuePair("gender",
                        mFacebookAccount.getGender()));
                nameValuePairs.add(new BasicNameValuePair("image", mFacebookAccount
                        .getLink()));
                nameValuePairs.add(new BasicNameValuePair("provider","facebook"));

//			nameValuePairs.add(new BasicNameValuePair("api_key",
//					"00848j4hj9d833333"));

                nameValuePairs.add(new BasicNameValuePair("birthday",
                        mFacebookAccount.getDOB()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);

				int status = response.getStatusLine().getStatusCode();
				Log.w("respond", status + "");
				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String respondingData = EntityUtils.toString(entity);
					Log.w("respond", respondingData);
				return respondingData;
				}


                if (status == 406) {
                    HttpEntity entity = response.getEntity();
                    String respondingData = EntityUtils.toString(entity);
                    Log.w("respond", respondingData);
                    return respondingData;
                }

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
                Log.e("ERROR", e.getClass().getName()+" " +e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
                Log.e("ERROR", e.getClass().getName()+" " +e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			fireResults(result);
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

		public abstract void fireResults(String data);
	}

//    private class LongOperation extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            return requestLoginWithFacebookAccount();
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        protected void onPreExecute() {}
//
//        @Override
//        protected void onProgressUpdate(Void... values) {}
//    }
}
