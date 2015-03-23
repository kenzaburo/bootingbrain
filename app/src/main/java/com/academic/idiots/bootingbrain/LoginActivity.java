package com.academic.idiots.bootingbrain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.simple.fb.Permission;
import com.simple.fb.SimpleFacebook;
import com.simple.fb.entities.Profile;
import com.simple.fb.listeners.OnLoginListener;
import com.simple.fb.listeners.OnProfileListener;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Thien on 3/18/2015.
 */
public class LoginActivity extends Activity {
    Button btn_facebook;
    private SimpleFacebook mSimpleFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mSimpleFacebook.getInstance(this);

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
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
            public void onNotAcceptingPermissions(Permission.Type type) {
                Log.w(getClass().getSimpleName(), "onNotAcceptingPermissions");
            }

            @Override
            public void onLogin() {
                // change the state of the button or do whatever you want
                Log.w(getClass().getSimpleName(), "onLogin");
                if (mSimpleFacebook.isLogin()) {
                    Profile.Properties properties = getFacebookDefaultProperties();
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

    private void onResult(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        this.btn_facebook.setEnabled(false);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
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

    private Profile.Properties getFacebookDefaultProperties() {
        Profile.Properties properties = new Profile.Properties.Builder().add(Profile.Properties.ID)
                .add(Profile.Properties.NAME).add(Profile.Properties.EMAIL)
                .add(Profile.Properties.FIRST_NAME).add(Profile.Properties.LAST_NAME)
                .add(Profile.Properties.BIRTHDAY).add(Profile.Properties.PICTURE)
                .add(Profile.Properties.GENDER).add(Profile.Properties.LOCALE)
                .add(Profile.Properties.WORK).add(Profile.Properties.EDUCATION)
                .add(Profile.Properties.LINK).build();
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

    private abstract class PostExecute extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
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

}
