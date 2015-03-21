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

import com.academic.idiots.bootingbrain.adapter.MainAdapter;
import com.academic.idiots.bootingbrain.model.GridViewModel;
import com.academic.idiots.bootingbrain.nextnumber.NextNumberActivity;
import com.academic.idiots.nsd.NsdChatActivity;
import com.simple.fb.SimpleFacebook;
import com.simple.fb.Permission.Type;
import com.simple.fb.entities.Profile;
import com.simple.fb.entities.Profile.Properties;
import com.simple.fb.listeners.OnLoginListener;
import com.simple.fb.listeners.OnProfileListener;

import android.app.Activity;
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
import android.widget.GridView;
import android.widget.Toast;

public class HomeActivity extends Activity {

    private Button btnGame3;
	private Button btn_go, btn_game_2, btn_nsd;
    private GridView gridView;
    private Context mContext = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

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

        List<GridViewModel> list = new ArrayList<GridViewModel>();
        list.add(new GridViewModel("Matching Picture", R.drawable.logo_matching_picture));
        list.add(new GridViewModel("Master Number", R.drawable.logo_master_number));
        list.add(new GridViewModel("Next Number", R.drawable.logo_next_number));
        list.add(new GridViewModel("Master Colors", R.drawable.logo_master_color));

        MainAdapter mainAdapter = new MainAdapter(this,0,list);
        gridView = (GridView) findViewById(R.id.gr_main);
        gridView.setAdapter(mainAdapter);
	}



//	String data;
//
//	private String requestLoginWithFacebookAccount() {
//		// Create a new HttpClient and Post Header
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpPost httppost = new HttpPost("http://104.236.123.60:8888/users/login");
//		try {
//			// Add your data
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
//					10);
//                nameValuePairs.add(new BasicNameValuePair("uid", mFacebookAccount
//					.getId()));
//			nameValuePairs.add(new BasicNameValuePair("username", mFacebookAccount
//					.getName()));
//			nameValuePairs.add(new BasicNameValuePair("email", mFacebookAccount
//					.getEmail()));
//			nameValuePairs.add(new BasicNameValuePair("gender",
//					mFacebookAccount.getGender()));
//			nameValuePairs.add(new BasicNameValuePair("image", mFacebookAccount
//					.getLink()));
//            nameValuePairs.add(new BasicNameValuePair("provider","facebook"));
//
////			nameValuePairs.add(new BasicNameValuePair("api_key",
////					"00848j4hj9d833333"));
//
//            nameValuePairs.add(new BasicNameValuePair("birthday",
//                    mFacebookAccount.getDOB()));
//
//			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//			// Execute HTTP Post Request
//			HttpResponse response = httpclient.execute(httppost);
//			int status = response.getStatusLine().getStatusCode();
//			Log.w("respond", status + "");
//			if (status == 200) {
//				HttpEntity entity = response.getEntity();
//				String respondingData = EntityUtils.toString(entity);
//				Log.w("respond", respondingData);
//				return respondingData;
//			}
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//		}
//
//		return null;
//	}



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
