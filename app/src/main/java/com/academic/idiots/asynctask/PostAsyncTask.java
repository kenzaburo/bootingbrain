package com.academic.idiots.asynctask;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public abstract class PostAsyncTask extends AsyncTaskBase {

	public PostAsyncTask(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		try {
			String url = params[0];
			String authorization = params[1];
			String content_json = null;
			if (params.length >= 3)
				content_json = params[2];
			Log.i("url", url);
			Log.i("content_json", content_json);
			// ------------------>>

			HttpPost httpPost = new HttpPost(url);
			HttpClient httpclient = new DefaultHttpClient();
			httpPost.addHeader("Content-Type", "application/json");
			if (authorization != null) {
				if (!authorization.equals(""))
					httpPost.addHeader("Authorization", authorization);
			}

			if (content_json != null)
				httpPost.setEntity(new StringEntity(content_json));

			HttpResponse response = httpclient.execute(httpPost);

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
}
