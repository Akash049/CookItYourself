package com.curl.ciykit.curl.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.curl.ciykit.curl.DataBase.DataBaseHandler;
import com.curl.ciykit.curl.DataBase.UserDataModel;
import com.curl.ciykit.curl.R;
import com.curl.ciykit.curl.curl.CurlActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Akash Chandra on 02-02-2017.
 */

public class PersonlizeActivity extends AppCompatActivity {
    @Bind(R.id.user_img)
    CircleImageView userImg;
    @Bind(R.id.user_name)
    TextView Username;
    @Bind(R.id.user_email)
    TextView UserEmail;
    @Bind(R.id.news)
     Button read;
    String Name,ImgUrl,Email;
    DataBaseHandler db;
    UserDataModel user;
    List<UserDataModel> userList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personlize);
        ButterKnife.bind(this);
        userList = new ArrayList<UserDataModel>();
        //String test = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
        //Toast.makeText(getApplicationContext(),test,Toast.LENGTH_SHORT).show();
        //db = new DataBaseHandler(this);
        //db.getReadableDatabase();
        //userList = db.GetUser();
        //user = userList.get(0);
        //Toast.makeText(getApplicationContext(),String.valueOf(user.getUserLoggedInState()),Toast.LENGTH_SHORT).show();
        Bundle extra = getIntent().getExtras();
        if(extra !=null){
            Name = extra.getString("NAME");
            ImgUrl = extra.getString("URL");
            Email = extra.getString("EMAIL");

            Username.setText(Name);
            UserEmail.setText(Email);
            Glide.with(userImg.getContext())
                    .load(ImgUrl)
                    .into(userImg);
        }
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonlizeActivity.this, CurlActivity.class));
            }
        });
    }
    @Override
    protected void onStop() {
        super.onPause();
        finish();
    }
}
