package com.academic.idiots.asynctask;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public abstract class GetAsyncTasks extends AsyncTaskBase {

	public GetAsyncTasks(Context context) {
		super(context);
	}

	/**
	 * 0. ul url 
	 * 1. Authorization bearer access token code
	 * 2 .
	 */
	@Override
	protected JSONObject doInBackground(String... params) {
		try {
			String url = params[0];
			String authorization = params[1];
			Log.i("url", url);
			// ------------------>>
			HttpGet httpGET = new HttpGet(url);
			HttpClient httpclient = new DefaultHttpClient();
			httpGET.addHeader("Content-Type", "application/json");
			
			if (authorization != null) {
				if (!authorization.equals(""))
					httpGET.addHeader("Authorization", authorization);
			}
			
			
			HttpResponse response = httpclient.execute(httpGET);
			
			// StatusLine stat = response.getStatusLine();
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);

				JSONObject jsono = new JSONObject(data);

				return jsono;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return null;
	}

	public abstract void fireResults(JSONObject data);
}