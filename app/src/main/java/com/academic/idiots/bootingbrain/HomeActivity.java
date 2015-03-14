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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

        btn_nsd = (Button) findViewById(R.id.btn_nsd);
        btn_nsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NsdChatActivity.class);
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

        MainAdapter mainAdapter = new MainAdapter(this, 0, list);
        gridView = (GridView) findViewById(R.id.gr_main);
        gridView.setAdapter(mainAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(HomeActivity.this,
                                MatchingPictureActivity.class);
                        startActivity(intent);
                        break;
                    case 1:

                        break;

                    case 2:
                        break;

                    case 3:
                        break;
                }
            }
        });
    }
    // font initalize
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
