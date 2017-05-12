package com.curl.ciykit.curl.Abstract;

import android.view.View;

/**
 * Created by Akash Chandra on 06-02-2017.
 */

public abstract class DoubleclickListener implements View.OnClickListener{

    //private View.OnLongClickListener mOnLongClickListener;

    private static final long DOUBLE_CLICK_TIME_DELTA = 2000;//milliseconds

    private int i = 0;

    long lastClickTime = System.currentTimeMillis();

    @Override
    public void onClick(View v) {
        if(i==0)
        {
            i = 1;
            //onSingleClick(v);
            lastClickTime = System.currentTimeMillis();
        }
        else if(i==1)
        {
            long clickTime = System.currentTimeMillis();
            if(clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA)
            {
                onDoubleClick(v);
                i=0;
            }
            else{
                onSingleClick(v);
                i=0;
            }
        }
    }

    public abstract void onSingleClick(View v);
    public abstract void onDoubleClick(View v);
}
