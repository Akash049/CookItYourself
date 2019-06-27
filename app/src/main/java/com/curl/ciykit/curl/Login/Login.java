package com.curl.ciykit.curl.Login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.curl.ciykit.curl.R;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Akash Chandra on 02-02-2017.
 */

public class Login extends AppCompatActivity {
    @Bind(R.id.info_text)
    HTextView Info_Text;
    @Bind(R.id.info_name)
    HTextView Info_Name;
    @Bind(R.id.animate)
    Button animate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);
        ButterKnife.bind(this);
        animate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }, 500);
            }
        });

    }
}
