package com.curl.ciykit.curl;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.vipul.hp_hp.library.Layout_to_Image;

public class MainActivity extends AppCompatActivity {
    Layout_to_Image layout_to_image;
    LinearLayout linearLayout;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout)findViewById(R.id.activity_main);
        layout_to_image=new Layout_to_Image(MainActivity.this,linearLayout);
        bitmap=layout_to_image.convert_layout();
        //ImageView imageView = (ImageView)findViewById(R.id.post);
        //Drawable d = new BitmapDrawable(getResources(), bitmap);
        //imageView.setImageDrawable(d);
    }
}
