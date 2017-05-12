package com.curl.ciykit.curl.Login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curl.ciykit.curl.DataBase.DataBaseHandler;
import com.curl.ciykit.curl.FCMServices.SharedPrefManager;
import com.curl.ciykit.curl.R;
import com.curl.ciykit.curl.apiInterface.userAPI;
import com.curl.ciykit.curl.model.ResponseCode;
import com.curl.ciykit.curl.utils.config;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import com.facebook.FacebookSdk;

/**
 * Created by Akash Chandra on 02-02-2017.
 */

public class FrontPage extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    @Bind(R.id.sign_in_button)
    SignInButton signInButton;
    @Bind(R.id.info_text)
    HTextView Info_Text;
    @Bind(R.id.info_name)
    HTextView Info_Name;
    @Bind(R.id.text1)
    TextView textView1;
    @Bind(R.id.text2)
    TextView textView2;
    @Bind(R.id.logo)
    ImageView logo;

    DataBaseHandler db;

    //Facebook Login Data
    private CallbackManager callbackManager;
    private TextView textView;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private  String APP_ID = "384291378608471";
    LoginButton loginButton;

    String gcm_token;
    Animation InFromLeft,InFromBottom,InFromRight;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FrontPage.this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_home_activity);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                startActivity(new Intent(FrontPage.this,PersonlizeActivity.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        ButterKnife.bind(this);
        db = new DataBaseHandler(this);
        SharedPrefManager.getInstance(getApplicationContext()).saveNews(null);
        if(isNetworkAvailable(FrontPage.this))new DownloadNewsObject().execute("");
        else Toast.makeText(FrontPage.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        InFromLeft = AnimationUtils.loadAnimation(FrontPage.this,R.anim.anim_translate_in_left);
        InFromBottom = AnimationUtils.loadAnimation(FrontPage.this,R.anim.anim_translate_in_bottm);
        InFromRight = AnimationUtils.loadAnimation(FrontPage.this,R.anim.anim_translate_in_right);
        textView1.startAnimation(InFromLeft);
        textView2.startAnimation(InFromRight);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                Info_Text.setVisibility(View.VISIBLE);
            }
        },1300);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Info_Text.setAnimateType(HTextViewType.FALL);
                Info_Text.animateText("Start new with");
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Info_Name.setVisibility(View.VISIBLE);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Info_Name.setAnimateType(HTextViewType.LINE);
                                Info_Name.animateText("NewsBook");
                            }
                        }, 500);
                    }
                }, 700);
            }
        }, 1500);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                signInButton.setVisibility(View.VISIBLE);
                signInButton.startAnimation(InFromBottom);
                Info_Text.setVisibility(View.INVISIBLE);
                logo.setVisibility(View.VISIBLE);
            }
        }, 4200);



        //gcm_token= SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(this);
    }
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            /*registerUser("google_login",acct.getDisplayName().toString(),acct.getEmail().toString(),acct.getPhotoUrl().toString());
            SharedPrefManager.getInstance(getApplicationContext()).saveuser_Name(acct.getDisplayName().toString());
            SharedPrefManager.getInstaznce(getApplicationContext()).saveuser_Email(acct.getEmail().toString());
            SharedPrefManager.getInstance(getApplicationContext()).saveuser_ImgURL(acct.getPhotoUrl().toString());*/
            String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
            RegisterUser(acct.getDisplayName().toString(),acct.getEmail().toString(),token,acct.getPhotoUrl().toString(),"google_plus_login");
            SharedPrefManager.getInstance(getApplicationContext()).saveLogState("LOGGEDIN");
            //db.getWritableDatabase();
            /*if(db.isEmpty())
            {
                db.AddUser(new UserDataModel(acct.getDisplayName().toString(),acct.getEmail().toString(),acct.getPhotoUrl().toString(),1,"USERID"));
                db.close();
            }
            else{
                Toast.makeText(FrontPage.this, "Data Already there", Toast.LENGTH_LONG).show();
            }*/
        } else {
            Toast.makeText(FrontPage.this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            if(isNetworkAvailable(FrontPage.this))signIn();
            else Toast.makeText(FrontPage.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private class DownloadNewsObject extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("url");
            String jsonResult = "";
            try {HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();}
            catch (ClientProtocolException e) {e.printStackTrace();}
            catch (IOException e){e.printStackTrace();}return jsonResult;}
        @Override
        protected void onPreExecute() {super.onPreExecute();}
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            SharedPrefManager.getInstance(getApplicationContext()).saveNews(result);
            //Toast.makeText(getApplicationContext(),"DataBase Updates ",Toast.LENGTH_LONG).show();
            }
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {while ((rLine = br.readLine()) != null){answer.append(rLine);}}
            catch (IOException e) {e.printStackTrace();}return answer;}}

    public void RegisterUser(String username,String user_email,String gcm_token,String Image_url,String loginType)
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(config.ROOT_URL)
                .build();
        final userAPI api = restAdapter.create(userAPI.class);
        api.addUser(
                username,
                user_email,
                gcm_token,
                Image_url,
                loginType,
                new Callback<ResponseCode>() {
                    @Override
                    public void success(ResponseCode responseCode, retrofit.client.Response response) {
                        String status = responseCode.getStatus().toString();
                        if(status.equals("success"))
                        {
                            String UserId = responseCode.getStatus().toString();
                            SharedPrefManager.getInstance(getApplicationContext()).saveUserId(UserId);
                        }else{
                            Toast.makeText(getApplicationContext(),"Already Registered!!!",Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getApplicationContext(),responseCode.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
        startActivity(new Intent(FrontPage.this,PersonlizeActivity.class).putExtra("URL",Image_url).putExtra("NAME",username)
                .putExtra("EMAIL",user_email));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
