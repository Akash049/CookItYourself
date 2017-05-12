package com.curl.ciykit.curl.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.curl.ciykit.curl.DataBase.DataBaseHandler;
import com.curl.ciykit.curl.DataBase.UserDataModel;
import com.curl.ciykit.curl.FCMServices.SharedPrefManager;
import com.curl.ciykit.curl.curl.CurlActivity;

import java.util.List;

/**
 * Created by Akash Chandra on 08-02-2017.
 */

public class StartUpActivity extends AppCompatActivity {
    DataBaseHandler db;
    List<UserDataModel> userList;
    UserDataModel user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db = new DataBaseHandler(this);
        //db.getReadableDatabase();
        //userList = new ArrayList<UserDataModel>();
        //userList = db.GetUser();
        //user = userList.get(0);
        SharedPrefManager.getInstance(getApplicationContext()).saveNews(null);
        String LogState = SharedPrefManager.getInstance(getApplicationContext()).getLogState();
        if(LogState.equals("LOGGEDIN"))
        {
            startActivity(new Intent(StartUpActivity.this,CurlActivity.class));
        }
        else
        {
            startActivity(new Intent(StartUpActivity.this,FrontPage.class));
        }
    }
}
