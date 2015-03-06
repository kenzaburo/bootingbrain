package com.academic.idiots.asynctask;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class AsyncTaskBase extends
		AsyncTask<String, String, JSONObject> {

	Context mContext;

	public AsyncTaskBase(Context context) {
		mContext = context;
	}

	public ProgressDialog progressDialog;

	/**
	 * url url Authorization bearer access token code;
	 */
	@Override
	protected abstract JSONObject doInBackground(String... params);

	protected void onPostExecute(JSONObject data) {
		fireResults(data);
	}

	public abstract void fireResults(JSONObject data);
}
