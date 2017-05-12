package com.curl.ciykit.curl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vipul.hp_hp.library.Layout_to_Image;

/**
 * Created by Akash Chandra on 10-01-2017.
 */

public class AddContent {
    View view;
    Layout_to_Image layout_to_image;
    LinearLayout linearLayout;
    Bitmap bitmap;
    Typeface newsHeadFont;

    public AddContent(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false);

        TextView date = (TextView)view.findViewById(R.id.date);
        TextView head = (TextView)view.findViewById(R.id.head);
        TextView data = (TextView)view.findViewById(R.id.news_data);
        TextView category = (TextView)view.findViewById(R.id.category);
        TextView source = (TextView)view.findViewById(R.id.source);
        ImageView imageView = (ImageView)view.findViewById(R.id.news_head_img);

        //Appplying the variables
        date.setText("Monday 7 Jan 2017");
        head.setText("This is the news head to be checked");
        data.setText("This is just a random news data entered to check the validity of the code");
        category.setText("Sports");
        source.setText("The Hindu");

        linearLayout = (LinearLayout)view.findViewById(R.id.activity_main);
        layout_to_image=new Layout_to_Image(context,linearLayout);
        }

    public AddContent(Context context,int news_id,String news_head, String news_data, String news_img_url,String news_category,String news_date,String news_source,Typeface newsHeadFont, Typeface newsDataFont)
    {
        view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView head = (TextView)view.findViewById(R.id.head);
        TextView data = (TextView)view.findViewById(R.id.news_data);
        TextView category = (TextView)view.findViewById(R.id.category);
        TextView source = (TextView)view.findViewById(R.id.source);
        ImageView imageView = (ImageView)view.findViewById(R.id.news_head_img);

        //Appplying the variables
        head.setTypeface(newsHeadFont);
        data.setTypeface(newsDataFont);
        date.setText(news_date);
        head.setText(news_head);
        data.setText(news_data);
        category.setText(news_category);
        source.setText(news_source);

        linearLayout = (LinearLayout)view.findViewById(R.id.activity_main);
        layout_to_image=new Layout_to_Image(context,linearLayout);
    }
    public AddContent(Context context,int news_id,String news_head, String news_data, Bitmap news_image, String news_img_url,String news_category,String news_date,String news_source,Typeface newsHeadFont,Typeface newsDataFont)
    {
        view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView head = (TextView)view.findViewById(R.id.head);
        TextView data = (TextView)view.findViewById(R.id.news_data);
        TextView category = (TextView)view.findViewById(R.id.category);
        TextView source = (TextView)view.findViewById(R.id.source);
        ImageView imageView = (ImageView)view.findViewById(R.id.news_head_img);

        //Appplying the variables
        imageView.setImageBitmap(news_image);
        data.setTypeface(newsDataFont);
        date.setText(news_date);
        head.setTypeface(newsHeadFont);
        head.setText(news_head);
        data.setText(news_data);
        category.setText(news_category);
        source.setText(news_source);

        linearLayout = (LinearLayout)view.findViewById(R.id.activity_main);
        layout_to_image=new Layout_to_Image(context,linearLayout);
    }
    public AddContent(Context context,int news_id,String news_head, String news_data, Bitmap news_image, String news_img_url,String news_category,String news_date,String news_source,Typeface newsHeadFont,Typeface newsDataFont, int size)
    {
        view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView head = (TextView)view.findViewById(R.id.head);
        TextView data = (TextView)view.findViewById(R.id.news_data);
        TextView category = (TextView)view.findViewById(R.id.category);
        TextView source = (TextView)view.findViewById(R.id.source);
        ImageView imageView = (ImageView)view.findViewById(R.id.news_head_img);
        //float initSize = data.getTextSize();
        data.setTextSize(TypedValue.COMPLEX_UNIT_SP,10+size);
        //Appplying the variables
        imageView.setImageBitmap(news_image);
        data.setTypeface(newsDataFont);
        date.setText(news_date);
        head.setTypeface(newsHeadFont);
        head.setText(news_head);
        data.setText(news_data);
        category.setText(news_category);
        source.setText(news_source);

        linearLayout = (LinearLayout)view.findViewById(R.id.activity_main);
        layout_to_image=new Layout_to_Image(context,linearLayout);
    }


        public Bitmap getContent(){
            bitmap=layout_to_image.convert_layout();
            return bitmap;
        }
}
